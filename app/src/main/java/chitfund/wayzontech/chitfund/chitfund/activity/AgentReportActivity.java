package chitfund.wayzontech.chitfund.chitfund.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import chitfund.wayzontech.chitfund.chitfund.R;
import chitfund.wayzontech.chitfund.chitfund.adapter.AgentReportAdapter;
import chitfund.wayzontech.chitfund.chitfund.httpHelper.AgentURL;
import chitfund.wayzontech.chitfund.chitfund.model.AgentReport;
import chitfund.wayzontech.chitfund.chitfund.session.SubdomainSession;
import chitfund.wayzontech.chitfund.chitfund.volley.VolleySingleton;

public class AgentReportActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private ArrayList<AgentReport> agentReportArrayList;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private AgentReportAdapter agentReportAdapter;
    private TextView textViewDate;
    private Button buttonDate;
    private String strDate, strMemberName, strGroupName, strAmount, strCollectionType, strDateTime;
    public static final String AGENT_REPORT_URL = "Registerweb/group_member_info";
    private int date_Year, date_Month, date_Day;
    private Calendar calendar;
    private String strRoleId = String.valueOf(4);
    private SubdomainSession session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_report);

        recyclerView = findViewById(R.id.recyclerViewAgentReport);
        textViewDate = findViewById(R.id.text_getAgentReportDate);
        buttonDate = findViewById(R.id.button_getAgentReport);

        session = new SubdomainSession(this);
        calendar = Calendar.getInstance();
        date_Year = calendar.get(Calendar.YEAR);
        date_Month = calendar.get(Calendar.MONTH);
        date_Day = calendar.get(Calendar.DAY_OF_MONTH);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        textViewDate.setOnClickListener(this);
        textViewDate.setText(sdf.format(calendar.getTime()));

        initialization();

    }

    private void initialization() {

        agentReportArrayList = new ArrayList<>();
        agentReportAdapter = new AgentReportAdapter(this, agentReportArrayList);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(agentReportAdapter);


    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        month = month + 1;
        textViewDate.setText(dayOfMonth + "-" + month
                + "-" + year);
    }

    private void getDate() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this, AgentReportActivity.this, date_Year, date_Month, date_Day);
        datePickerDialog.show();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.text_getAgentReportDate:
                getDate();
                break;

            case R.id.button_getAgentReport:
                agentReport();
                break;
        }

    }

    private void agentReport() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        strDate = textViewDate.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://" + session.getSubDomain() + AgentURL.AGENT_BASE_URL + AGENT_REPORT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if (jsonObject.getString("success").equals("1")) {

                                JSONArray jsonArray = jsonObject.getJSONArray("get_agent_report");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject report = jsonArray.getJSONObject(i);

                                    strAmount = report.getString("final_am");
                                    strDateTime = report.getString("dateTime");
                                    strMemberName = report.getString("customer_name");
                                    strGroupName = report.getString("");
                                    strCollectionType = report.getString("");


                                    AgentReport agentReport = new AgentReport();

                                    agentReport.setAmount(strAmount);
                                    agentReport.setDateTime(strDateTime);
                                    agentReport.setMemberName(strMemberName);
                                    agentReport.setGroupName(strGroupName);
                                    agentReport.setCollectionType(strCollectionType);
                                    agentReportArrayList.add(agentReport);


                                    progressDialog.dismiss();
                                    Toast.makeText(AgentReportActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                    agentReportAdapter.notifyDataSetChanged();

                                }

                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(AgentReportActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                agentReportAdapter.notifyDataSetChanged();
                            }


                        } catch (JSONException e) {
                            progressDialog.dismiss();
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("dateTime", strDate);
                params.put("agent_id", session.getUserID());
                params.put("role_id", strRoleId);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        initialization();
    }
}

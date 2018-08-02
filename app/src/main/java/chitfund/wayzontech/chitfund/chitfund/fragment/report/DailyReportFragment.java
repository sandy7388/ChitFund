package chitfund.wayzontech.chitfund.chitfund.fragment.report;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import chitfund.wayzontech.chitfund.chitfund.R;
import chitfund.wayzontech.chitfund.chitfund.adapter.agentReportAdapter.DailyAgentReportAdapter;
import chitfund.wayzontech.chitfund.chitfund.httpHelper.AgentURL;
import chitfund.wayzontech.chitfund.chitfund.model.agentReport.DailyReport;
import chitfund.wayzontech.chitfund.chitfund.session.SubdomainSession;
import chitfund.wayzontech.chitfund.chitfund.volley.VolleySingleton;

public class DailyReportFragment extends Fragment implements AdapterView.OnItemSelectedListener,
        View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private ArrayList<String> groupName;
    private RecyclerView recyclerView;
    private String strRemainingDays, inputDateString;
    private static final String AGENT_GROUP_URL = "Registerweb/group_name_info";
    private ArrayList<DailyReport> dailyReportArrayList;
    private RecyclerView.LayoutManager layoutManager;
    private String strGroupName, strMemberName, strDateTime, strAmount, strCollectionType, outputDateStr;
    private DailyAgentReportAdapter adapter;
    private JSONArray groupArray, memberArray, bankArray;
    private ProgressDialog progressDialog;
    private JSONObject serverResponse, groupNameObject, serverResponseMember,
            memberNameObject, serverResponseBank, bankNameObject;
    private String strGroupNameJSON, strGroupName1, strGroupIdJSON,
            strGroupId, strMemberName1, strMemberId, strMemberNameJSON,
            strMemberIdJSON, strBankName, strBankId, strBankNameJSON,
            strBankIdJSON, strRoleId = String.valueOf(4);
    private Spinner spinnerGroupName;
    private int date_Year, date_Month, date_Day;
    private Calendar calendar;
    private TextView textViewDate;
    private Button buttonDate;
    private static final String AGENT_DAILY_REPORT_URL = "Registerweb/report2/";
    private SubdomainSession session;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_daily_report,
                container, false);
        //getActivity().setTitle("Daily Collection Report");
        initialization(view);
        recyclerViewInit();
        getGroupName();
        return view;
    }

    void initialization(View view) {
        groupName = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyclerViewAgentDailyReport);
        session = new SubdomainSession(getActivity());
        spinnerGroupName = view.findViewById(R.id.spinnerGrpNameDailyReportAgent);
        textViewDate = view.findViewById(R.id.editText_search_agentDaily_report);
        buttonDate = view.findViewById(R.id.button_search_agentDaily_report);
        buttonDate.setOnClickListener(this);
        spinnerGroupName.setOnItemSelectedListener(this);
        textViewDate.setOnClickListener(this);
        calendar = Calendar.getInstance();
        date_Year = calendar.get(Calendar.YEAR);
        date_Month = calendar.get(Calendar.MONTH);
        date_Day = calendar.get(Calendar.DAY_OF_MONTH);

        //Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        textViewDate.setText(sdf.format(calendar.getTime()));
    }

    void recyclerViewInit() {
        dailyReportArrayList = new ArrayList<>();
        adapter = new DailyAgentReportAdapter(dailyReportArrayList);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        month = month + 1;
        textViewDate.setText(dayOfMonth + "-" + month
                + "-" + year);
    }

    private void getDate() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getActivity(), R.style.DialogTheme, this, date_Year, date_Month, date_Day);
        datePickerDialog.show();
    }

    void getGroupName() {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://" + session.getSubDomain() + AgentURL.AGENT_BASE_URL + AGENT_GROUP_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            serverResponse = new JSONObject(response);

                            if (serverResponse.getString("success").equals("1")) {
                                groupArray = serverResponse.getJSONArray("Agent_info");

                                for (int i = 0; i < groupArray.length(); i++) {
                                    groupNameObject = groupArray.getJSONObject(i);

                                    strGroupNameJSON = groupNameObject.getString("group_name");
                                    strGroupIdJSON = groupNameObject.getString("group_id");

                                    groupName.add(strGroupNameJSON);
                                }

                                spinnerGroupName.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.spinner_layout, groupName));

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> param = new HashMap<>();
                param.put("agent_id", session.getUserID());
                param.put("role_id", strRoleId);
                return param;
            }
        };

        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Spinner spinner = (Spinner) parent;
        switch (spinner.getId()) {
            case R.id.spinnerGrpNameDailyReportAgent:

                strGroupName = spinner.getAdapter().getItem(position).toString();
                for (int i = 0; i < groupArray.length(); i++) {
                    try {

                        if (strGroupNameJSON.equals(strGroupName)) {
                            strGroupId = strGroupIdJSON;
                            System.out.println("strGroupId" + strGroupId);

                            //getAdvanceReport();

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.editText_search_agentDaily_report:
                try {
                    getDate();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case R.id.button_search_agentDaily_report:
                try {
                    getDailyReport();
                } catch (Exception e) {
                    e.printStackTrace();
                }


                break;
        }
    }

    @SuppressLint("SimpleDateFormat")
    private void getDailyReport() {
        try {

            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Please wait.....!");
            progressDialog.show();
            progressDialog.setCancelable(true);

            //advanceReportArrayList=new ArrayList<AdvanceReport>();

            strDateTime = textViewDate.getText().toString();
            DateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
            DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = null;
            try {
                date = inputFormat.parse(strDateTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            outputDateStr = outputFormat.format(date);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://" + session.getSubDomain() + AgentURL.AGENT_BASE_URL + AGENT_DAILY_REPORT_URL,
                    new Response.Listener<String>() {
                        @SuppressLint("SimpleDateFormat")
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);

                                if (jsonObject.getString("success").equals("1")) {
                                    JSONArray jsonArray = jsonObject.getJSONArray("Agentwise_collection_report");
                                    for (int i = 0; i < jsonArray.length(); i++) {

                                        JSONObject object = jsonArray.getJSONObject(i);
                                        strMemberName = object.getString("member_name");
                                        strCollectionType = object.getString("collection_type");
                                        strAmount = object.getString("amount");


                                        DailyReport dailyReport = new DailyReport();
                                            dailyReport.setMemberName(strMemberName);
                                            dailyReport.setCollectionType(strCollectionType);
                                            dailyReport.setAmount(strAmount);
                                            progressDialog.dismiss();

                                            dailyReportArrayList.add(dailyReport);
                                            adapter.notifyDataSetChanged();

                                    }
                                } else
                                    progressDialog.dismiss();
                                //Toast.makeText(getContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

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
                    params.put("agent_id", session.getUserID());
                    params.put("group_id", strGroupId);
                    params.put("role_id", strRoleId);
                    params.put("date", outputDateStr);
                    return params;
                }
            };
            recyclerViewInit();
            VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

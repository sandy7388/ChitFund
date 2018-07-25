package chitfund.wayzontech.chitfund.chitfund.fragment.report;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import chitfund.wayzontech.chitfund.chitfund.R;
import chitfund.wayzontech.chitfund.chitfund.adapter.agentReportAdapter.AdvanceAgentReportAdapter;
import chitfund.wayzontech.chitfund.chitfund.httpHelper.AgentURL;
import chitfund.wayzontech.chitfund.chitfund.model.agentReport.AdvanceReport;
import chitfund.wayzontech.chitfund.chitfund.session.AgentSession;
import chitfund.wayzontech.chitfund.chitfund.volley.VolleySingleton;

public class AdvanceReportFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private ArrayList<String> groupName;
    private RecyclerView recyclerView;
    private String strRemainingDays, inputDateString;
    private AgentSession agentSession;
    private ArrayList<AdvanceReport> advanceReportArrayList;
    private RecyclerView.LayoutManager layoutManager;
    private String strGroupName, strMemberName, strDateTime, strAmount;
    private AdvanceAgentReportAdapter adapter;
    private JSONArray groupArray, memberArray, bankArray;
    private ProgressDialog progressDialog;
    private JSONObject serverResponse, groupNameObject, serverResponseMember,
            memberNameObject, serverResponseBank, bankNameObject;
    private String strGroupNameJSON, strGroupName1, strGroupIdJSON,
            strGroupId, strMemberName1, strMemberId, strMemberNameJSON,
            strMemberIdJSON, strBankName, strBankId, strBankNameJSON,
            strBankIdJSON, strRoleId = String.valueOf(4);
    private Spinner spinnerGroupName;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_advance_report, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewAgentAdvanceReport);
        agentSession = new AgentSession(getActivity());
        spinnerGroupName = view.findViewById(R.id.spinnerGrpNameAdvanceReportAgent);
        spinnerGroupName.setOnItemSelectedListener(this);
        //getActivity().setTitle("Advance Collection Report");
        recyclerViewInit();
        getGroupName();
        return view;
    }


    void recyclerViewInit() {
        groupName = new ArrayList<>();
        advanceReportArrayList = new ArrayList<>();
        adapter = new AdvanceAgentReportAdapter(advanceReportArrayList);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    void getAdvanceReport() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Please wait.....!");
        progressDialog.show();
        progressDialog.setCancelable(true);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AgentURL.AGENT_ADVANCE_REPORT_URL,
                new Response.Listener<String>() {
                    @SuppressLint("SimpleDateFormat")
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if (jsonObject.getString("success").equals("1")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("Agentwise_Advance_collection_report");
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);
                                    strMemberName = object.getString("member_name");
                                    strDateTime = object.getString("receipt_date");
                                    strAmount = object.getString("amount");


                                    DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                                    DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
                                    Date date = inputFormat.parse(strDateTime);
                                    String outputDateStr = outputFormat.format(date);
                                    AdvanceReport advanceReport = new AdvanceReport();
                                    advanceReport.setMemberName(strMemberName);
                                    advanceReport.setDateTime(outputDateStr);
                                    advanceReport.setAmount(strAmount);
                                    progressDialog.dismiss();
                                    advanceReportArrayList.add(advanceReport);
                                    adapter.notifyDataSetChanged();

                                }


                            } else
                                progressDialog.dismiss();
                            //Toast.makeText(getContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            progressDialog.dismiss();
                            e.printStackTrace();
                        } catch (ParseException e) {
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
                params.put("agent_id", agentSession.getUserID());
                params.put("group_id", strGroupId);
                params.put("role_id", strRoleId);
                return params;
            }
        };
        recyclerViewInit();
        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);

    }

    void getGroupName() {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, AgentURL.AGENT_GROUP_URL,
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

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> param = new HashMap<>();
                param.put("agent_id", agentSession.getUserID());
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
            case R.id.spinnerGrpNameAdvanceReportAgent:

                strGroupName = spinner.getAdapter().getItem(position).toString();
                for (int i = 0; i < groupArray.length(); i++) {
                    try {

                        if (strGroupNameJSON.equals(strGroupName)) {
                            strGroupId = strGroupIdJSON;
                            System.out.println("strGroupId" + strGroupId);

                            getAdvanceReport();

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
}

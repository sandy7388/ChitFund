package chitfund.wayzontech.chitfund.chitfund.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import chitfund.wayzontech.chitfund.chitfund.R;
import chitfund.wayzontech.chitfund.chitfund.adapter.MemberReportAdapter;
import chitfund.wayzontech.chitfund.chitfund.httpHelper.URLs;
import chitfund.wayzontech.chitfund.chitfund.model.MemberReport;
import chitfund.wayzontech.chitfund.chitfund.session.SessionManager;
import chitfund.wayzontech.chitfund.chitfund.volley.VolleySingleton;

public class MemberReportActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Spinner spinner;
    private JSONObject serverResponseObject,jsonObject,object;
    private JSONArray groupJsonArray,memberJsonArray;
    private ArrayList<MemberReport> memberReportArrayList;
    private ProgressDialog progressDialog;
    private ArrayList<String> grpName;
    private MemberReportAdapter memberReportAdapter;
    private String memberId,memberName,memberMobile,groupName,groupId;
    private SessionManager sessionManager;
    private Button buttonDeleteGroup;
    public MemberReportActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_report);
        if (getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Member Report");
        }

        spinner = findViewById(R.id.spinnerMemberReport);
        recyclerView =  findViewById(R.id.recyclerViewMemberReport);
        initController();
        recyclerView();

    }

    void initController()
    {
        grpName = new ArrayList<>();
        progressDialog = new ProgressDialog(this);
        sessionManager = new SessionManager(this);
        spinner.setOnItemSelectedListener(this);
        getMemberReport();
    }

    void recyclerView()
    {
        memberReportArrayList = new ArrayList<>();
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        memberReportAdapter = new MemberReportAdapter(this,memberReportArrayList);
        recyclerView.setAdapter(memberReportAdapter);

    }


    void getMemberReport()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.GROUP_REPORT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        try {
                            // response
                            serverResponseObject = new JSONObject(response);
                            // groupinfo array
                            groupJsonArray = serverResponseObject.getJSONArray("groupinfo");

                            for (int i= 0;i<groupJsonArray.length();i++)
                            {
                                // object from groupInfo array
                                jsonObject = groupJsonArray.getJSONObject(i);

                                grpName.add(jsonObject.getString("group_name"));

                                // array from innner object
                                memberJsonArray = jsonObject.getJSONArray("member_info");
                                for (int j=0;j<memberJsonArray.length();j++)
                                {
                                    object = memberJsonArray.getJSONObject(j);

                                    memberId = object.getString("user_id");
                                    memberName = object.getString("member_name");
                                    memberMobile = object.getString("member_mobile");

                                    MemberReport memberReport = new MemberReport();

                                    memberReport.setMember_id(memberId);
                                    memberReport.setMember_name(memberName);
                                    memberReport.setMember_mobile(memberMobile);

                                    memberReportArrayList.add(memberReport);
                                    memberReportAdapter.notifyDataSetChanged();
                                }
                            }
                            spinner.setAdapter(new ArrayAdapter<String>(MemberReportActivity.this,
                                    android.R.layout.simple_spinner_dropdown_item,grpName));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("userid",sessionManager.getUserID());
                return map;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Spinner spinner = (Spinner) parent;
        groupName = spinner.getAdapter().getItem(position).toString();
        if (spinner.getId()==R.id.spinnerMemberReport)
        {
            for (int i=0;i<groupJsonArray.length();i++)
            {
                try {
                    if (groupJsonArray.getJSONObject(i).getString("group_name").equals(groupName))
                    {
                        memberReportArrayList = new ArrayList<>();
                        groupId = groupJsonArray.getJSONObject(i).getString("group_id");
                        memberJsonArray = groupJsonArray.getJSONObject(i).getJSONArray("member_info");

                        for (int j = 0; j < memberJsonArray.length(); j++)
                        {
                            object = memberJsonArray.getJSONObject(j);

                            memberId = object.getString("member_id");
                            memberName = object.getString("member_name");
                            memberMobile = object.getString("member_mobile");

                            MemberReport memberReport = new MemberReport();

                            memberReport.setMember_id(memberId);
                            memberReport.setMember_name(memberName);
                            memberReport.setMember_mobile(memberMobile);

                            memberReportArrayList.add(memberReport);

                        }
                        memberReportAdapter = new MemberReportAdapter(this, memberReportArrayList);
                        recyclerView.setAdapter(memberReportAdapter);
                        memberReportAdapter.notifyDataSetChanged();
                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

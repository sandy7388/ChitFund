package chitfund.wayzontech.chitfund.chitfund.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
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

public class MemberReportFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Spinner spinner;
    private JSONObject serverResponseObject,jsonObject,object;
    private JSONArray groupJsonArray,memberJsonArray;
    private ArrayList<MemberReport> memberReportArrayList;
    private ProgressDialog progressDialog;
    private ArrayList<String> grpName;
    private MemberReportAdapter memberReportAdapter;
    private String memberId,memberName,memberMobile,groupName;
    private SessionManager sessionManager;
    private Button buttonDeleteGroup;
    public MemberReportFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_member_report,
                container,false);
        initController(view);
        return view;
    }

    void initController(View view)
    {
        spinner = view.findViewById(R.id.spinnerMemberReport);
        recyclerView =  view.findViewById(R.id.recyclerViewMemberReport);
        memberReportArrayList = new ArrayList<>();
        grpName = new ArrayList<>();
        progressDialog = new ProgressDialog(getContext());
        sessionManager = new SessionManager(getActivity());
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        memberReportAdapter = new MemberReportAdapter(getContext(),memberReportArrayList);
        recyclerView.setAdapter(memberReportAdapter);
        spinner.setOnItemSelectedListener(this);
        getMemberReport();
    }


    void getMemberReport()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.JOINED_GROUP,
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

                                    memberId = object.getString("member_id");
                                    memberName = object.getString("member_name");
                                    memberMobile = object.getString("member_mobile");

                                    MemberReport memberReport = new MemberReport(memberId,memberName,memberMobile);

                                    memberReportArrayList.add(memberReport);
                                    memberReportAdapter.notifyDataSetChanged();
                                }
                            }
                            spinner.setAdapter(new ArrayAdapter<String>(getActivity(),
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

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Spinner spinner = (Spinner) parent;
        groupName = spinner.getAdapter().getItem(position).toString();
        if (spinner.getId()==R.id.spinnerMemberReport)
        {
            try {


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

                        memberId = object.getString("member_id");
                        memberName = object.getString("member_name");
                        memberMobile = object.getString("member_mobile");

                        MemberReport memberReport = new MemberReport(memberId,memberName,memberMobile);

                        memberReportArrayList.add(memberReport);
                        memberReportAdapter.notifyDataSetChanged();
                    }
                }
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

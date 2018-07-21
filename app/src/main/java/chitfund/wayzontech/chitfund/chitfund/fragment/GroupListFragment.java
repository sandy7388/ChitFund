package chitfund.wayzontech.chitfund.chitfund.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import chitfund.wayzontech.chitfund.chitfund.activity.MainActivity;
import chitfund.wayzontech.chitfund.chitfund.adapter.GroupListAdapter;
import chitfund.wayzontech.chitfund.chitfund.httpHelper.URLs;
import chitfund.wayzontech.chitfund.chitfund.model.MemberName;
import chitfund.wayzontech.chitfund.chitfund.session.MemberSession;
import chitfund.wayzontech.chitfund.chitfund.volley.VolleySingleton;


public class GroupListFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private Spinner spinnerGrpName;
    private TextView textViewAmount;
    private Button button;
    private ProgressDialog progressDialog;
    private ArrayList<String> grpName;
    private RecyclerView recyclerView;
    private JSONArray groupInfo;
    private String member_Name,memberId,groupName,groupId;
    private JSONObject serverResponse,object;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<MemberName> memberNameArrayList;
    private GroupListAdapter groupListAdapter;
    MemberSession memberSession;
    public GroupListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_group_list,
                container, false);
        getActivity().setTitle("Group List");
        initRecyclerView(view);
        recyclerView();
        return view;
    }

    public void initRecyclerView(View view)
    {
        grpName = new ArrayList<String>();
        progressDialog = new ProgressDialog(getContext());
        memberSession = new MemberSession(getActivity());
        button = view.findViewById(R.id.btn_joinGrpFrag);
        textViewAmount = view.findViewById(R.id.text_amtGrpFrg);
        spinnerGrpName = view.findViewById(R.id.spinner_groupName);
        recyclerView =  view.findViewById(R.id.recyclerview_grpListFrag);
        spinnerGrpName.setOnItemSelectedListener(this);
        button.setOnClickListener(this);
        getList();

    }

    private void recyclerView()
    {

        memberNameArrayList = new ArrayList<>();
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        groupListAdapter = new GroupListAdapter(memberNameArrayList,getContext());
        recyclerView.setAdapter(groupListAdapter);
    }
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_joinGrpFrag:
                joinGroup();
                break;
        }

    }


    private void getList()
    {

        StringRequest stringRequest = new StringRequest(Request.Method.GET,URLs.GROUP_DETAILS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            serverResponse = new JSONObject(response);

                            if (serverResponse.getString("success").equals("1")) {
                                groupInfo = serverResponse.getJSONArray("group_info");

                                for (int i = 0; i < groupInfo.length(); i++) {
                                    object = groupInfo.getJSONObject(i);

                                    grpName.add(object.getString("group_name"));

                                    String amount = object.getString("amount");
                                    textViewAmount.setText(amount);

                                    JSONArray jsonArray = object.getJSONArray("group_member");

                                    for (int j = 0; j < jsonArray.length(); j++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject(j);

                                        member_Name = jsonObject.getString("member_name");

                                        memberId = jsonObject.getString("member_id");

                                        MemberName member = new MemberName();
                                        member.setName(member_Name);
                                        member.setId(memberId);

                                        memberNameArrayList.add(member);
                                        groupListAdapter.notifyDataSetChanged();
                                    }

                                }

                                Toast.makeText(getContext(), serverResponse.getString("message"), Toast.LENGTH_SHORT).show();
                                spinnerGrpName.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, grpName));

                            } else {
                                Toast.makeText(getContext(), serverResponse.getString("message"), Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Spinner spinner = (Spinner) parent;
        groupName = spinner.getAdapter().getItem(position).toString();
        if (spinner.getId()==R.id.spinner_groupName)
        {
            for (int i=0;i<groupInfo.length();i++)
            {
                try {
                    if (groupInfo.getJSONObject(i).getString("group_name").equals(groupName))
                    {
                        memberNameArrayList=new ArrayList<MemberName>();
                        groupId = groupInfo.getJSONObject(i).getString("group_id");
                        textViewAmount.setText(groupInfo.getJSONObject(i).getString("amount"));
                        System.out.println("group_id" +groupId);
                        JSONArray jsonArray = groupInfo.getJSONObject(i).getJSONArray("group_member");

                        for (int j=0;j<jsonArray.length();j++)
                        {
                            JSONObject jsonObject = jsonArray.getJSONObject(j);

                            member_Name = jsonObject.getString("member_name");

                            memberId = jsonObject.getString("member_id");

                            MemberName member = new MemberName();
                            member.setName(member_Name);
                            member.setId(memberId);

                            memberNameArrayList.add(member);
                        }
                        groupListAdapter = new GroupListAdapter(memberNameArrayList,getContext());
                        recyclerView.setAdapter(groupListAdapter);
                        groupListAdapter.notifyDataSetChanged();

                    }
                }

                catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        textViewAmount.setText("");
    }


    private void joinGroup()
    {
        progressDialog.show();
        progressDialog.setMessage("Please wait....!");
        progressDialog.setCancelable(true);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,URLs.JOIN_GROUP,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("success").equals("1"))
                            {
                                progressDialog.dismiss();
                                Toast.makeText(getContext(),jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getActivity(), MainActivity.class));
                            }

                            else
                                progressDialog.dismiss();
                                Toast.makeText(getContext(),jsonObject.getString("message"),Toast.LENGTH_SHORT).show();

                        }
                        catch (JSONException e)
                        {
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
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();
                params.put("user_id", memberSession.getUserID());
                params.put("groupid",groupId);
                return params;
            }
        };
        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }

}

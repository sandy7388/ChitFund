package chitfund.wayzontech.chitfund.chitfund.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import chitfund.wayzontech.chitfund.chitfund.R;
import chitfund.wayzontech.chitfund.chitfund.adapter.GroupListAdapter;
import chitfund.wayzontech.chitfund.chitfund.httpHelper.URLs;
import chitfund.wayzontech.chitfund.chitfund.model.MemberName;
import chitfund.wayzontech.chitfund.chitfund.volley.VolleySingleton;


public class GroupListFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private Spinner spinnerGrpName;
    private TextView textViewAmount;
    private ArrayList<String> grpName;
    private RecyclerView recyclerView;
    private JSONArray groupInfo;
    private String member_Name;
    private JSONObject serverResponse,object;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<MemberName> memberNameArrayList;
    private GroupListAdapter groupListAdapter;
    public GroupListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_group_list, container, false);
        initRecyclerView(view);
        return view;
    }

    public void initRecyclerView(View view)
    {
        recyclerView =  view.findViewById(R.id.recyclerview_grpListFrag);
        textViewAmount = view.findViewById(R.id.text_amtGrpFrg);
        spinnerGrpName = view.findViewById(R.id.spinner_groupName);
        memberNameArrayList = new ArrayList<>();
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        groupListAdapter = new GroupListAdapter(memberNameArrayList,getContext());
        recyclerView.setAdapter(groupListAdapter);
        grpName = new ArrayList<String>();
        spinnerGrpName.setOnItemSelectedListener(this);
        getList();

    }

    void getList()
    {

        StringRequest stringRequest = new StringRequest(URLs.GROUP_DETAILS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            serverResponse = new JSONObject(response);

                            groupInfo = serverResponse.getJSONArray("");

                            for (int i=0;i<groupInfo.length();i++)
                            {
                                object = groupInfo.getJSONObject(i);
                                grpName.add(object.getString(""));

                                String amount = object.getString("");
                                textViewAmount.setText(amount);

                                JSONArray jsonArray = object.getJSONArray("");

                                for (int j=0;j<jsonArray.length();j++)
                                {
                                    JSONObject jsonObject = jsonArray.getJSONObject(j);

                                    member_Name = jsonObject.getString("");

                                    MemberName member = new MemberName();
                                    member.setName(member_Name);

                                    memberNameArrayList.add(member);
                                }

                            }

                            spinnerGrpName.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, grpName));
                            groupListAdapter.notifyDataSetChanged();

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

        //textViewAmount.setText(getAmount(position));

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        textViewAmount.setText("");
    }
}

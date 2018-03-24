package chitfund.wayzontech.chitfund.chitfund.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import chitfund.wayzontech.chitfund.chitfund.R;
import chitfund.wayzontech.chitfund.chitfund.adapter.JoinedGroupAdapter;
import chitfund.wayzontech.chitfund.chitfund.httpHelper.URLs;
import chitfund.wayzontech.chitfund.chitfund.model.JoinedGroup;
import chitfund.wayzontech.chitfund.chitfund.session.SessionManager;
import chitfund.wayzontech.chitfund.chitfund.volley.VolleySingleton;


public class JoinedGroupFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerView;
    private SessionManager sessionManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<JoinedGroup> joinedGroupArrayList;
    private RecyclerView.LayoutManager layoutManager;
    private JoinedGroupAdapter joinedGroupAdapter;
    String grpId,grpName,grpJoiningDate,grpAmount;
    public JoinedGroupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_joined_group,
                container, false);
        initialization(view);
        getJoinedGroup();
        return view;
    }

    private void initialization(View view)
    {
        swipeRefreshLayout = view.findViewById(R.id.swipeToRefreshJoinedGroup);
        recyclerView = view.findViewById(R.id.recyclerViewJoinedGroup);
        sessionManager = new SessionManager(getActivity());
        joinedGroupArrayList = new ArrayList<>();
        joinedGroupAdapter = new JoinedGroupAdapter(getContext(),joinedGroupArrayList);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(joinedGroupAdapter);
        swipeRefreshLayout.setOnRefreshListener(this);

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                getJoinedGroup();

            }
        });
    }


    @Override
    public void onRefresh() {

        getJoinedGroup();
    }

    private void getJoinedGroup()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.JOIN_GROUP,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            JSONArray jsonArray = jsonObject.getJSONArray("joined_group");
                            for (int i=0;i<jsonArray.length();i++)
                            {
                                JSONObject object = jsonArray.getJSONObject(i);
                                grpId = object.getString("group_id");
                                grpName = object.getString("group_name");
                                grpJoiningDate = object.getString("joining_date");
                                grpAmount = object.getString("amount");

                                JoinedGroup joinedGroup = new JoinedGroup();

                                joinedGroup.setGroup_id(grpId);
                                joinedGroup.setGroup_name(grpName);
                                joinedGroup.setJoining_date(grpJoiningDate);
                                joinedGroup.setAmount(grpAmount);

                                joinedGroupArrayList.add(joinedGroup);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        swipeRefreshLayout.setRefreshing(false);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                error.printStackTrace();
                swipeRefreshLayout.setRefreshing(false);

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userid",sessionManager.getUserID());
                return params;
            }
        };
        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }
}

package chitfund.wayzontech.chitfund.chitfund.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    private String strRemainingDays,inputDateString;
    private SessionManager sessionManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<JoinedGroup> joinedGroupArrayList;
    private RecyclerView.LayoutManager layoutManager;
    private JoinedGroupAdapter joinedGroupAdapter;
    private String grpId,grpName,auctionDate,auctionTime,grpAmount;
    private DateFormat date,time;
    private Date d;
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
        //getJoinedGroup();
        recyclerViewInit();

        return view;
    }

    private void initialization(View view)
    {
        swipeRefreshLayout = view.findViewById(R.id.swipeToRefreshJoinedGroup);
        recyclerView = view.findViewById(R.id.recyclerViewJoinedGroup);
        sessionManager = new SessionManager(getActivity());

        swipeRefreshLayout.setOnRefreshListener(this);

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                getJoinedGroup();

            }
        });
    }



    void recyclerViewInit()
    {
        joinedGroupArrayList = new ArrayList<>();
        joinedGroupAdapter = new JoinedGroupAdapter(getContext(),joinedGroupArrayList);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(joinedGroupAdapter);
    }


    @Override
    public void onRefresh() {

        getJoinedGroup();
    }

    private void getJoinedGroup()
    {
        joinedGroupArrayList=new ArrayList<JoinedGroup>();
        //swipeRefreshLayout.setRefreshing(true);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.JOINED_GROUP,
                new Response.Listener<String>() {
                    @SuppressLint("SimpleDateFormat")
                    @Override
                    public void onResponse(String response)
                    {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if (jsonObject.getString("success").equals("1")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("groupinfo");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    grpId = object.getString("group_id");
                                    grpName = object.getString("group_name");
                                    auctionDate = object.getString("auction_date");
                                    grpAmount = object.getString("amount");
                                    //auctionTime = object.getString("auction_time");

                                    JoinedGroup joinedGroup = new JoinedGroup();

                                    joinedGroup.setGroup_id(grpId);
                                    joinedGroup.setGroup_name(grpName);

                                    joinedGroup.setAmount(grpAmount);
                                    joinedGroup.setTime(auctionDate);

                                    try {
                                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                                        d = dateFormat.parse(auctionDate);
                                        date = new SimpleDateFormat("dd-MM-yyyy");
                                        time = new SimpleDateFormat("hh:mm:ss");
                                        System.out.println("Date: " + date.format(d));
                                        System.out.println("Time: " + time.format(d));
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    joinedGroup.setNext_date(date.format(d));
                                    joinedGroupArrayList.add(joinedGroup);
                                    swipeRefreshLayout.setRefreshing(false);

                                    joinedGroupAdapter.notifyDataSetChanged();

                                }
                            }
                            else
                                swipeRefreshLayout.setRefreshing(false);
                                Toast.makeText(getContext(),"No groups available",Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            swipeRefreshLayout.setRefreshing(false);
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                swipeRefreshLayout.setRefreshing(false);
                error.printStackTrace();
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
        recyclerViewInit();
    }
}

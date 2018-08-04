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
import chitfund.wayzontech.chitfund.chitfund.adapter.JoinedGroupAdapter;
import chitfund.wayzontech.chitfund.chitfund.httpHelper.URLs;
import chitfund.wayzontech.chitfund.chitfund.model.JoinedGroup;
import chitfund.wayzontech.chitfund.chitfund.session.SubdomainSession;
import chitfund.wayzontech.chitfund.chitfund.volley.VolleySingleton;


public class JoinedGroupFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerView;
    private String strRemainingDays,inputDateString;
    public static final String JOINED_GROUP = "groupinfo/userwisegroupList";
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<JoinedGroup> joinedGroupArrayList;
    private RecyclerView.LayoutManager layoutManager;
    private JoinedGroupAdapter joinedGroupAdapter;
    private String grpId, grpName, auctionDate, auctionType, grpAmount, strAuctionDate, strDay;
    private DateFormat date,time;
    private Date d, date1;
    private JoinedGroup joinedGroup;
    private SubdomainSession session;
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
        getActivity().setTitle("Joined Group");
        recyclerViewInit();

        return view;
    }

    private void initialization(View view)
    {
        swipeRefreshLayout = view.findViewById(R.id.swipeToRefreshJoinedGroup);
        recyclerView = view.findViewById(R.id.recyclerViewJoinedGroup);
        session = new SubdomainSession(getActivity());

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
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://" + session.getSubDomain() + URLs.BASE_URL + JOINED_GROUP,
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
                                    if (object.getString("auction_type").equals("Monthly")) {
                                        auctionType = object.getString("auction_type");
                                        grpId = object.getString("group_id");
                                        grpName = object.getString("group_name");
                                        auctionDate = object.getString("auction_date");
                                        grpAmount = object.getString("amount");
                                        //auctionTime = object.getString("auction_time");


                                        try {
                                            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
                                            d = dateFormat.parse(auctionDate);
                                            date = new SimpleDateFormat("dd-MM-yyyy");
                                            time = new SimpleDateFormat("hh:mm:ss a");
//                                            System.out.println("Date: " + date.format(d));
//                                            System.out.println("Time: " + time.format(d));
                                            Calendar myCal = Calendar.getInstance();
                                            myCal.setTime(d);
                                            myCal.add(Calendar.MONTH, +1);
                                            d = myCal.getTime();
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }

                                        joinedGroup = new JoinedGroup();

                                        joinedGroup.setGroup_id(grpId);
                                        joinedGroup.setGroup_name(grpName);
                                        joinedGroup.setType(auctionType);
                                        joinedGroup.setAmount(grpAmount);
                                        joinedGroup.setTime(auctionDate);
                                        joinedGroup.setNext_date(date.format(d));

                                    }
                                    if (object.getString("auction_type").equals("Weekly")) {
                                        auctionType = object.getString("auction_type");
                                        grpId = object.getString("group_id");
                                        grpName = object.getString("group_name");
                                        auctionDate = object.getString("auction_date");
                                        grpAmount = object.getString("amount");
                                        //auctionTime = object.getString("auction_time");
                                        try {
                                            Date date1 = new Date();
                                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                                            strAuctionDate = formatter.format(date1).concat(" " + auctionDate);
                                            System.out.println("Date Format with MM/dd/yyyy : " + strAuctionDate);


                                            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd EEEE hh:mm:ss a");

                                            DateFormat dateFormat1 = new SimpleDateFormat("EEEE hh:mm:ss a");
                                            dateFormat1.parse(strAuctionDate);
                                            d = dateFormat.parse(strAuctionDate);
                                            date = new SimpleDateFormat("dd-MM-yyyy");
                                            time = new SimpleDateFormat("hh:mm:ss");
                                            System.out.println("strDay: " + date.format(dateFormat1.parse(strAuctionDate)));
                                            System.out.println("Time: " + time.format(d));

                                            Calendar myCal = Calendar.getInstance();
                                            myCal.setTime(d);
                                            myCal.add(Calendar.DAY_OF_WEEK, +7);
                                            d = myCal.getTime();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                        joinedGroup = new JoinedGroup();

                                        joinedGroup.setGroup_id(grpId);
                                        joinedGroup.setGroup_name(grpName);
                                        joinedGroup.setType(auctionType);
                                        joinedGroup.setAmount(grpAmount);
                                        joinedGroup.setTime(strAuctionDate);
                                        joinedGroup.setNext_date(auctionDate);
                                    }

                                    if (object.getString("auction_type").equals("Daily")) {
                                        auctionType = object.getString("auction_type");
                                        grpId = object.getString("group_id");
                                        grpName = object.getString("group_name");
                                        auctionDate = object.getString("auction_date");
                                        grpAmount = object.getString("amount");
                                        //auctionTime = object.getString("auction_time");

                                        try {

                                            Date date1 = new Date();
                                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                                            strAuctionDate = formatter.format(date1).concat(" " + auctionDate);
                                            System.out.println("Date Format with MM/dd/yyyy : " + strAuctionDate);

                                            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
                                            d = dateFormat.parse(strAuctionDate);
                                            date = new SimpleDateFormat("dd-MM-yyyy");
                                            time = new SimpleDateFormat("hh:mm:ss");
                                            System.out.println("Date: " + date.format(d));
                                            System.out.println("Time: " + time.format(d));
                                            Calendar myCal = Calendar.getInstance();
                                            myCal.setTime(d);
                                            myCal.add(Calendar.HOUR_OF_DAY, +24);
                                            d = myCal.getTime();
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }

                                        joinedGroup = new JoinedGroup();

                                        joinedGroup.setGroup_id(grpId);
                                        joinedGroup.setGroup_name(grpName);
                                        joinedGroup.setType(auctionType);
                                        joinedGroup.setAmount(grpAmount);
                                        joinedGroup.setTime(strAuctionDate);
                                        joinedGroup.setNext_date(date.format(d));

                                    }

                                    joinedGroupArrayList.add(joinedGroup);
                                    swipeRefreshLayout.setRefreshing(false);
                                    joinedGroupAdapter.notifyDataSetChanged();

                                }
                            }
                            else
                                swipeRefreshLayout.setRefreshing(false);
                            //Toast.makeText(getContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

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
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("userid", session.getUserID());
                return params;
            }
        };
        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
        recyclerViewInit();
    }
}

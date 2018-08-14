package chitfund.wayzontech.chitfund.chitfund.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
    SimpleDateFormat currentDateFormatter;
    public static final String JOINED_GROUP = "groupinfo/userwisegroupList";
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<JoinedGroup> joinedGroupArrayList;
    private RecyclerView.LayoutManager layoutManager;
    private JoinedGroupAdapter joinedGroupAdapter;
    private String strRemainingDays, inputDateString;
    private String currentStrDate, grpId, grpName, auctionDate, auctionType, grpAmount, strAuctionDate, strDay;
    private DateFormat date, time;
    private JoinedGroup joinedGroup;
    private SubdomainSession session;
    private Date d, date1, currentDate;
    private SimpleDateFormat simpleDateFormat;
    private String auctionDateStr, auctionCurrentDate, strDayDate, strAuctionDay;
    private String groupDuration, groupMember;

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

//        currentDate = new Date();
//        currentDateFormatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
//        currentStrDate= currentDateFormatter.format(currentDate);
//        System.out.println(currentStrDate);

        currentDate = new Date();
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
        currentStrDate = simpleDateFormat.format(currentDate);
        try {
            currentDate = simpleDateFormat.parse(currentStrDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return view;
    }

    private void initialization(View view) {
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


    void recyclerViewInit() {
        joinedGroupArrayList = new ArrayList<>();
        joinedGroupAdapter = new JoinedGroupAdapter(getContext(), joinedGroupArrayList);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(joinedGroupAdapter);
    }


    @Override
    public void onRefresh() {

        getJoinedGroup();
    }

    private void getJoinedGroup() {
        joinedGroupArrayList = new ArrayList<JoinedGroup>();
        //swipeRefreshLayout.setRefreshing(true);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://" + session.getSubDomain() + URLs.BASE_URL + JOINED_GROUP,
                new Response.Listener<String>() {
                    @SuppressLint("SimpleDateFormat")
                    @Override
                    public void onResponse(String response) {
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
                                        groupDuration = object.getString("duration");
                                        groupMember = object.getString("total_member");
                                        //auctionTime = object.getString("auction_time");


                                        try {
                                            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
                                            d = dateFormat.parse(auctionDate);
                                            //d = dateFormat.parse("2018-08-07 11:45:00 AM");
                                            date = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
                                            time = new SimpleDateFormat("hh:mm:ss a");


                                            if (d.compareTo(currentDate) < 0) {
//                                                Log.d("isdate",d.toString());
//
//                                                Log.d("isdate","before");

                                                Calendar myCal = Calendar.getInstance();
                                                date1 = new Date();
                                                myCal.setTime(d);
                                                myCal.add(Calendar.MONTH, +1);
                                                date1 = myCal.getTime();
                                                Log.d("isdate", date1.toString());

                                                joinedGroup = new JoinedGroup();

                                                joinedGroup.setGroup_id(grpId);
                                                joinedGroup.setGroup_name(grpName);
                                                joinedGroup.setType(auctionType);
                                                joinedGroup.setAmount(grpAmount);
                                                joinedGroup.setGroupMember(groupMember);
                                                joinedGroup.setGroupDuration(groupDuration);
                                                joinedGroup.setTime(auctionDate);
                                                joinedGroup.setNext_date(date.format(date1));

                                            } else {
//                                                Log.d("isdate","after");
//
//                                                Log.d("isdate",d.toString());

                                                joinedGroup = new JoinedGroup();

                                                joinedGroup.setGroup_id(grpId);
                                                joinedGroup.setGroup_name(grpName);
                                                joinedGroup.setType(auctionType);
                                                joinedGroup.setAmount(grpAmount);
                                                joinedGroup.setGroupMember(groupMember);
                                                joinedGroup.setGroupDuration(groupDuration);
                                                joinedGroup.setTime(auctionDate);
                                                joinedGroup.setNext_date(date.format(d));

                                                Log.d("isdatef", date.format(d));


                                            }


                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }


                                    }
                                    if (object.getString("auction_type").equals("Weekly")) {
                                        auctionType = object.getString("auction_type");
                                        grpId = object.getString("group_id");
                                        grpName = object.getString("group_name");
                                        auctionDate = object.getString("auction_date");
                                        grpAmount = object.getString("amount");
                                        groupDuration = object.getString("duration");
                                        groupMember = object.getString("total_member");
                                        Log.d("auctionDate", auctionDate);
                                        Log.d("auctionDatecr", currentDate.toString());

                                        Date auctionDateD = new Date();

                                        SimpleDateFormat simpleDateFormatAuction = new SimpleDateFormat("EEEE hh:mm:ss a");

                                        try {
                                            auctionDateD = simpleDateFormatAuction.parse(auctionDate);

                                            auctionDateStr = simpleDateFormatAuction.format(auctionDateD);

                                            Log.d("auctionDateStr", auctionDateStr);

                                            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("EEEE");

                                            Date auctionDayDate = new Date();

                                            auctionDayDate = simpleDateFormat2.parse(auctionDateStr);

                                            strAuctionDay = simpleDateFormat2.format(auctionDayDate);

                                            Log.d("strAuctionDay", strAuctionDay);


                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }

                                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE hh:mm:ss a");
                                        String crDate = simpleDateFormat.format(currentDate);


                                        ///////////////////
                                        Date currentDate = new Date();

                                        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("EEEE hh:mm:ss a");


                                        try {
                                            currentDate = simpleDateFormat1.parse(crDate);

                                            auctionCurrentDate = simpleDateFormat1.format(currentDate);

                                            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("EEEE");

                                            Date dayDate = new Date();

                                            dayDate = simpleDateFormat2.parse(auctionCurrentDate);

                                            strDayDate = simpleDateFormat2.format(dayDate);

                                            Log.d("strDayDate", strDayDate);

                                            Log.d("auctionCurrentDate", auctionCurrentDate);
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }


                                        if (strAuctionDay.equals(strDayDate)) {
                                            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd");

                                            Date dateFromDay = new Date();

                                            String date = simpleDateFormat2.format(dateFromDay).concat(" " + auctionDateStr);

                                            SimpleDateFormat simpleDateFormat3 = new SimpleDateFormat("yyyy-MM-dd EEEE hh:mm:ss a");

                                            Date newDate = new Date();

                                            try {
                                                newDate = simpleDateFormat3.parse(date);


                                            } catch (ParseException e) {
                                                e.printStackTrace();
                                            }

                                            //Log.d("isdateweeklySameDay", date);

                                            Log.d("isdateweeklySame", "Same");

                                            joinedGroup = new JoinedGroup();

                                            joinedGroup.setGroup_id(grpId);
                                            joinedGroup.setGroup_name(grpName);
                                            joinedGroup.setType(auctionType);
                                            joinedGroup.setAmount(grpAmount);
                                            joinedGroup.setTime(auctionDate);
                                            joinedGroup.setGroupMember(groupMember);
                                            joinedGroup.setGroupDuration(groupDuration);
                                            joinedGroup.setNext_date(simpleDateFormat3.format(newDate));


                                            Log.d("isdateweeklySameDay", simpleDateFormat3.format(newDate));
                                            //joinedGroup.setNext_date(date.format(d));
                                        } else {
                                            Log.d("isdateweeklySame", "different");

                                            joinedGroup = new JoinedGroup();

                                            joinedGroup.setGroup_id(grpId);
                                            joinedGroup.setGroup_name(grpName);
                                            joinedGroup.setType(auctionType);
                                            joinedGroup.setAmount(grpAmount);
                                            joinedGroup.setTime(auctionDate);
                                            joinedGroup.setNext_date(auctionDateStr);
                                            joinedGroup.setGroupMember(groupMember);
                                            joinedGroup.setGroupDuration(groupDuration);

                                        }


                                    }

                                    if (object.getString("auction_type").equals("Daily")) {
                                        auctionType = object.getString("auction_type");
                                        grpId = object.getString("group_id");
                                        grpName = object.getString("group_name");
                                        auctionDate = object.getString("auction_date");
                                        grpAmount = object.getString("amount");
                                        groupDuration = object.getString("duration");
                                        groupMember = object.getString("total_member");
                                        //auctionTime = object.getString("auction_time");

                                        try {

                                            Date date1 = new Date();
                                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                                            strAuctionDate = formatter.format(date1).concat(" " + auctionDate);
                                            System.out.println("Date Format with MM/dd/yyyy : " + strAuctionDate);

                                            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
                                            //d = dateFormat.parse("2018-08-07 11:45:00 AM");
                                            d = dateFormat.parse(strAuctionDate);
                                            date = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
                                            time = new SimpleDateFormat("hh:mm:ss");
                                            System.out.println("Date: " + date.format(d));
                                            System.out.println("Time: " + time.format(d));

                                            if (d.compareTo(currentDate) < 0) {

                                                Calendar myCal = Calendar.getInstance();
                                                date1 = new Date();
                                                myCal.setTime(d);
                                                myCal.add(Calendar.HOUR_OF_DAY, +24);
                                                date1 = myCal.getTime();
                                                Log.d("isdate", date1.toString());

                                                joinedGroup = new JoinedGroup();

                                                joinedGroup.setGroup_id(grpId);
                                                joinedGroup.setGroup_name(grpName);
                                                joinedGroup.setType(auctionType);
                                                joinedGroup.setAmount(grpAmount);
                                                joinedGroup.setTime(auctionDate);
                                                joinedGroup.setNext_date(date.format(date1));
                                                joinedGroup.setGroupMember(groupMember);
                                                joinedGroup.setGroupDuration(groupDuration);

                                            } else {

                                                joinedGroup = new JoinedGroup();

                                                joinedGroup.setGroup_id(grpId);
                                                joinedGroup.setGroup_name(grpName);
                                                joinedGroup.setType(auctionType);
                                                joinedGroup.setAmount(grpAmount);
                                                joinedGroup.setTime(auctionDate);
                                                joinedGroup.setNext_date(date.format(d));
                                                joinedGroup.setGroupMember(groupMember);
                                                joinedGroup.setGroupDuration(groupDuration);

                                                Log.d("isdatef", date.format(d));


                                            }
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }

                                    }

                                    joinedGroupArrayList.add(joinedGroup);
                                    swipeRefreshLayout.setRefreshing(false);
                                    joinedGroupAdapter.notifyDataSetChanged();

//                                    if (groupMember.equals(groupDuration))
//                                    {
//
//                                    }
//
//                                    else {
//                                        Toast.makeText(getContext(), "One of your group have less member than desired", Toast.LENGTH_SHORT).show();
//                                    }



                                }
                            } else
                                swipeRefreshLayout.setRefreshing(false);
                            //Toast.makeText(getContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            swipeRefreshLayout.setRefreshing(false);
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                swipeRefreshLayout.setRefreshing(false);
                error.printStackTrace();
            }
        }) {
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

package chitfund.wayzontech.chitfund.chitfund.fragment;

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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import chitfund.wayzontech.chitfund.chitfund.R;
import chitfund.wayzontech.chitfund.chitfund.adapter.LastAuctionAdapter;
import chitfund.wayzontech.chitfund.chitfund.httpHelper.URLs;
import chitfund.wayzontech.chitfund.chitfund.model.LastAuction;
import chitfund.wayzontech.chitfund.chitfund.session.SessionManager;
import chitfund.wayzontech.chitfund.chitfund.volley.VolleySingleton;


public class LastAuctionFragment extends Fragment
        implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private ProgressDialog progressDialog;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<LastAuction> lastAuctionArrayList;
    private LastAuctionAdapter lastAuctionAdapter;
    private Button getLastAuction;
    private TextView dateLastAuction;
    private Calendar calendar;
    private SessionManager session;
    private String strDate,date,amount,lockAmount,groupName,receivedBy,closedOn;
    private int date_Year,date_Month,date_Day;
    public LastAuctionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_last_auction,
                container, false);
        initRecyclerView(view);
        getActivity().setTitle("Last Auction Details");
        return view;
    }
    @SuppressLint("SimpleDateFormat")
    private void initRecyclerView(View view)
    {
        getLastAuction = view.findViewById(R.id.button_getLastAuction);
        dateLastAuction = view.findViewById(R.id.text_getLastAuctionDate);
        lastAuctionArrayList = new ArrayList<>();
        recyclerView =  view.findViewById(R.id.recyclerViewLastAuctionAdapter);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        lastAuctionAdapter = new LastAuctionAdapter(getContext(),lastAuctionArrayList);
        recyclerView.setAdapter(lastAuctionAdapter);

        getLastAuction.setOnClickListener(this);
        dateLastAuction.setOnClickListener(this);
        progressDialog = new ProgressDialog(getActivity());
        calendar= Calendar.getInstance();
        date_Year=calendar.get(Calendar.YEAR);
        date_Month=calendar.get(Calendar.MONTH);
        date_Day=calendar.get(Calendar.DAY_OF_MONTH);

        //Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        dateLastAuction.setText( sdf.format(calendar.getTime()));
        dateLastAuction.setOnClickListener(this);
        session = new SessionManager(getActivity());
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
    {
        month=month+1;
        dateLastAuction.setText(year + "-" + month
                + "-" + dayOfMonth);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.button_getLastAuction:
                getLastAuctionDetails();
                break;
            case R.id.text_getLastAuctionDate:
                getDate();
                break;
        }
    }

    private void getDate()
    {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(), this, date_Year, date_Month, date_Day);
        datePickerDialog.show();
    }


    private void getLastAuctionDetails()
    {
        progressDialog.setMessage("Please wait....!");
        progressDialog.show();
        progressDialog.setCancelable(false);
        strDate = dateLastAuction.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.LAST_AUCTION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if (jsonObject.getString("success").equals("1")) {

                                JSONArray jsonArray = jsonObject.getJSONArray("auction_list");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    date = object.getString("date");
                                    amount = object.getString("amount");
                                    lockAmount = object.getString("lock_amount");
                                    groupName = object.getString("group_name");
                                    receivedBy = object.getString("received_by");
                                    closedOn = object.getString("closed_on");

                                    LastAuction lastAuction = new LastAuction();
                                    lastAuction.setDate(date);
                                    lastAuction.setAmount(amount);
                                    lastAuction.setLock_amount(lockAmount);
                                    lastAuction.setGroup_name(groupName);
                                    lastAuction.setReceived_by(receivedBy);
                                    lastAuction.setClosed_on(closedOn);

                                    lastAuctionArrayList.add(lastAuction);

                                    progressDialog.dismiss();
                                    //Toast.makeText(getActivity(),jsonObject.getString("message"),Toast.LENGTH_SHORT).show();

                                }

                                lastAuctionAdapter.notifyDataSetChanged();

                            }
                            else
                                progressDialog.dismiss();
                                //Toast.makeText(getActivity(),jsonObject.getString("message"),Toast.LENGTH_SHORT).show();



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
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("auctiondate",strDate);
                //params.put("userid",session.getUserID());
                return params;
            }
        };
        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }
}

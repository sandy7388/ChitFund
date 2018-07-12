package chitfund.wayzontech.chitfund.chitfund.activity;

import android.app.ProgressDialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

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
import chitfund.wayzontech.chitfund.chitfund.adapter.AdvanceReportAdapter;
import chitfund.wayzontech.chitfund.chitfund.httpHelper.URLs;
import chitfund.wayzontech.chitfund.chitfund.model.AdvanceInstallmentReport;
import chitfund.wayzontech.chitfund.chitfund.session.SessionManager;
import chitfund.wayzontech.chitfund.chitfund.volley.VolleySingleton;

public class AdvanceReportActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private SessionManager session;
    private ProgressDialog progressDialog;
    private String strTicketNo,strAuctionNo,strMemberName,strCollectionType,
            strAmount,strChequeNo,strChequeDate,strBankName,
            strReceiptNo,strReceiptDate;
    private ArrayList<AdvanceInstallmentReport> advanceInstallmentReportArrayList;
    private AdvanceReportAdapter adavanceReportAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advance_report);

        if (getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Advance Installment Report");
        }

        recyclerView = findViewById(R.id.recyclerViewAdvanceReport);
        swipeRefreshLayout = findViewById(R.id.swipeToRefreshAdvanceReport);
        initRecyclerView();
    }

    private void initRecyclerView() {
        session = new SessionManager(this);
        advanceInstallmentReportArrayList = new ArrayList<>();
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adavanceReportAdapter = new AdvanceReportAdapter(this,advanceInstallmentReportArrayList);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                getAdvanceReport();

            }
        });
    }

    private void getAdvanceReport()
    {
        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setMessage("Please wait....!");
        progressDialog.setCancelable(true);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.ADVANCE_REPORT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            JSONArray jsonArray = jsonObject.getJSONArray("advancecolReport");

                            for (int i = 0; i<jsonArray.length();i++) {

                                JSONObject object = jsonArray.getJSONObject(i);

                                if (jsonObject.getString("success").equals("1")) {
                                    strTicketNo = object.getString("ticket_no");
                                    strAuctionNo = object.getString("auction_id");
                                    strMemberName = object.getString("member_name");
                                    strCollectionType = object.getString("collection_type");
                                    strAmount = object.getString("amount");
                                    strChequeNo = object.getString("cheque_no");
                                    strChequeDate = object.getString("date1");
                                    strBankName = object.getString("bank_name");
                                    strReceiptNo = object.getString("entry_no");
                                    strReceiptDate = object.getString("date1");

                                    AdvanceInstallmentReport installmentReport =
                                            new AdvanceInstallmentReport(strTicketNo,
                                            strMemberName, strCollectionType, strAmount,strReceiptNo, strReceiptDate);

                                    advanceInstallmentReportArrayList.add(installmentReport);

                                    adavanceReportAdapter.notifyDataSetChanged();

                                    progressDialog.dismiss();
                                    swipeRefreshLayout.setRefreshing(false);
                                    Toast.makeText(AdvanceReportActivity.this,jsonObject.getString("message"),Toast.LENGTH_SHORT).show();


                                }
                                else
                                    progressDialog.dismiss();
                                swipeRefreshLayout.setRefreshing(false);
                                Toast.makeText(AdvanceReportActivity.this,jsonObject.getString("message"),Toast.LENGTH_SHORT).show();

                            }

                        } catch (JSONException e) {
                            swipeRefreshLayout.setRefreshing(false);

                            progressDialog.dismiss();
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                swipeRefreshLayout.setRefreshing(false);

                progressDialog.dismiss();

                error.printStackTrace();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id",session.getUserID());
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    public void onRefresh()
    {
        getAdvanceReport();

    }
}

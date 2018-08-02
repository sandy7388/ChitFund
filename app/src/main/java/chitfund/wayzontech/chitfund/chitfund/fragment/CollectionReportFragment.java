package chitfund.wayzontech.chitfund.chitfund.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import chitfund.wayzontech.chitfund.chitfund.adapter.CollectionReportAdapter;
import chitfund.wayzontech.chitfund.chitfund.httpHelper.URLs;
import chitfund.wayzontech.chitfund.chitfund.model.CollectionReport;
import chitfund.wayzontech.chitfund.chitfund.session.SubdomainSession;
import chitfund.wayzontech.chitfund.chitfund.volley.VolleySingleton;

public class CollectionReportFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ArrayList<CollectionReport> collectionReportArrayList;
    private CollectionReportAdapter collectionReportAdapter;
    private static final String COLLECTION_REPORT = "groupinfo/collectionreport";
    private ProgressDialog progressDialog;
    private String strTicketNo, strAuctionNo, strMemberName, strCollectionType,
            strAmount, strChequeNo, strChequeDate, strBankName,
            strReceiptNo, strReceiptDate;
    private SubdomainSession session;

    public CollectionReportFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_collection_report, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewCollectionReport);
        swipeRefreshLayout = view.findViewById(R.id.swipeToRefreshCollectionReport);
        initRecyclerView();
        return view;
    }

    void initRecyclerView() {

        session = new SubdomainSession(getContext());

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                getData();

            }
        });

        recyclerview();

    }

    void recyclerview() {
        collectionReportArrayList = new ArrayList<>();
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        collectionReportAdapter = new CollectionReportAdapter(collectionReportArrayList, getContext());
        recyclerView.setAdapter(collectionReportAdapter);
    }

    void getData() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        progressDialog.setMessage("Please wait....!");
        progressDialog.setCancelable(true);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://" + session.getSubDomain() + URLs.BASE_URL + COLLECTION_REPORT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            JSONArray jsonArray = jsonObject.getJSONArray("colreport");

                            for (int i = 0; i < jsonArray.length(); i++) {

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

                                    CollectionReport collectionReport = new CollectionReport(strTicketNo, strAuctionNo,
                                            strMemberName, strCollectionType, strAmount, strChequeNo, strChequeDate,
                                            strBankName, strReceiptNo, strReceiptDate);

                                    collectionReportArrayList.add(collectionReport);

                                    collectionReportAdapter.notifyDataSetChanged();

                                    progressDialog.dismiss();
                                    swipeRefreshLayout.setRefreshing(false);
                                    Toast.makeText(getContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                                } else
                                    progressDialog.dismiss();
                                swipeRefreshLayout.setRefreshing(false);
                                Toast.makeText(getContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

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
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", session.getUserID());
                return params;
            }
        };
        recyclerview();
        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }

    @Override
    public void onRefresh() {
        getData();
    }
}

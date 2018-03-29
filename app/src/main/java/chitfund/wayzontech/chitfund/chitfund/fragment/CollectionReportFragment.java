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
import chitfund.wayzontech.chitfund.chitfund.adapter.CollectionReportAdapter;
import chitfund.wayzontech.chitfund.chitfund.httpHelper.URLs;
import chitfund.wayzontech.chitfund.chitfund.model.CollectionReport;
import chitfund.wayzontech.chitfund.chitfund.session.SessionManager;
import chitfund.wayzontech.chitfund.chitfund.volley.VolleySingleton;

public class CollectionReportFragment extends Fragment
{
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ArrayList<CollectionReport> collectionReportArrayList;
    private CollectionReportAdapter collectionReportAdapter;
    private SessionManager session;
    private ProgressDialog progressDialog;
    private String strTicketNo,strAuctionNo,strMemberName,strCollectionType,
                    strAmount,strChequeNo,strChequeDate,strBankName,
                    strReceiptNo,strReceiptDate;
    public CollectionReportFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collection_report,
                container,false);
        recyclerView = view.findViewById(R.id.recyclerViewCollectionReport);
        initRecyclerView();
        getData();
        return view;
    }

   void initRecyclerView()
   {

       session = new SessionManager(getActivity());
       collectionReportArrayList = new ArrayList<>();
       layoutManager = new LinearLayoutManager(getActivity());
       recyclerView.setLayoutManager(layoutManager);
       recyclerView.setHasFixedSize(true);
       collectionReportAdapter = new CollectionReportAdapter(collectionReportArrayList,getContext());
       recyclerView.setAdapter(collectionReportAdapter);

   }

   void getData()
   {
       progressDialog = new ProgressDialog(getContext());
       progressDialog.show();
       progressDialog.setMessage("Please wait....!");
       progressDialog.setCancelable(true);
       StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.COLLECTION_REPORT,
               new Response.Listener<String>() {
                   @Override
                   public void onResponse(String response)
                   {
                       try {
                           JSONObject jsonObject = new JSONObject(response);

                           JSONArray jsonArray = jsonObject.getJSONArray("colreport");

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

                                   CollectionReport collectionReport = new CollectionReport(strTicketNo, strAuctionNo,
                                           strMemberName, strCollectionType, strAmount, strChequeNo, strChequeDate,
                                           strBankName, strReceiptNo, strReceiptDate);

                                   collectionReportArrayList.add(collectionReport);

                                   collectionReportAdapter.notifyDataSetChanged();

                                   progressDialog.dismiss();
                               }
                           }

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
               Map<String, String> params = new HashMap<>();
               params.put("memberid",session.getMemberID());
               return params;
           }
       };
       VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
   }

}

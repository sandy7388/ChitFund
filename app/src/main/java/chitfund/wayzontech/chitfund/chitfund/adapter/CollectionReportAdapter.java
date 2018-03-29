package chitfund.wayzontech.chitfund.chitfund.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import chitfund.wayzontech.chitfund.chitfund.R;
import chitfund.wayzontech.chitfund.chitfund.model.CollectionReport;

public class CollectionReportAdapter extends RecyclerView.Adapter<CollectionReportAdapter.CollectionReportViewHolder> {

    private ArrayList<CollectionReport> collectionReportArrayList;
    private Context context;

    public CollectionReportAdapter(ArrayList<CollectionReport> collectionReportArrayList, Context context) {
        this.collectionReportArrayList = collectionReportArrayList;
        this.context = context;
    }

    @Override
    public CollectionReportViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_collection_report,parent,false);
        return new CollectionReportViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CollectionReportViewHolder holder, int position) {

        CollectionReport collectionReport = collectionReportArrayList.get(position);

        holder.ticketNo.setText(collectionReport.getTokan_no());
        holder.auctionNo.setText(collectionReport.getAuction_no());
        holder.memberName.setText(collectionReport.getMember_name());
        holder.collectionType.setText(collectionReport.getCollection_type());
        holder.amount.setText(collectionReport.getAmount());
        holder.chequeNo.setText(collectionReport.getCheque_no());
        holder.chequeDate.setText(collectionReport.getCheque_date());
        holder.bankName.setText(collectionReport.getBank_name());
        holder.receiptNo.setText(collectionReport.getReceipt_no());
        holder.receiptDate.setText(collectionReport.getReceipt_date());
    }

    @Override
    public int getItemCount() {
        return collectionReportArrayList.size();
    }

    public class CollectionReportViewHolder extends RecyclerView.ViewHolder {

        private TextView ticketNo,auctionNo,memberName,collectionType,
                        amount,chequeNo,chequeDate,bankName,receiptNo,
                        receiptDate;
        public CollectionReportViewHolder(View itemView) {
            super(itemView);

            ticketNo = itemView.findViewById(R.id.ticketNo);
            auctionNo = itemView.findViewById(R.id.auctionNo);
            memberName = itemView.findViewById(R.id.memberName);
            collectionType = itemView.findViewById(R.id.collectionType);
            amount = itemView.findViewById(R.id.textViewAmount);
            chequeNo = itemView.findViewById(R.id.chequeNo);
            chequeDate = itemView.findViewById(R.id.chequeDate);
            bankName = itemView.findViewById(R.id.bankName);
            receiptNo = itemView.findViewById(R.id.receiptNo);
            receiptDate = itemView.findViewById(R.id.receiptDate);
        }
    }
}

package chitfund.wayzontech.chitfund.chitfund.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import chitfund.wayzontech.chitfund.chitfund.R;
import chitfund.wayzontech.chitfund.chitfund.model.UpcomingReport;

public class UpcomingReportAdapter extends RecyclerView.Adapter<UpcomingReportAdapter.UpcomingReportHolder> {

    private Context context;
    private ArrayList<UpcomingReport> upcomingReportArrayList;

    public UpcomingReportAdapter(Context context, ArrayList<UpcomingReport> upcomingReportArrayList) {
        this.context = context;
        this.upcomingReportArrayList = upcomingReportArrayList;
    }

    @Override
    public UpcomingReportHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_upcoming_report,
                        parent,false);
        return new UpcomingReportHolder(view);

    }

    @Override
    public void onBindViewHolder(UpcomingReportHolder holder, int position) {

        UpcomingReport upcomingReport = upcomingReportArrayList.get(position);

        holder.ticketNo.setText(upcomingReport.getTicket_no());
        holder.memberName.setText(upcomingReport.getMember_name());
        holder.collectionType.setText(upcomingReport.getCollection_type());
        holder.amount.setText(upcomingReport.getAmount());
        holder.chequeNo.setText(upcomingReport.getCheque_no());
        holder.chequeDate.setText(upcomingReport.getDate1());
        holder.bankName.setText(upcomingReport.getBank_name());
        holder.receiptNo.setText(upcomingReport.getDate1());
        holder.receiptDate.setText(upcomingReport.getEntry_no());

    }

    @Override
    public int getItemCount() {
        return upcomingReportArrayList.size();
    }

    public class UpcomingReportHolder extends RecyclerView.ViewHolder {

        private TextView ticketNo,memberName,collectionType,
                amount,chequeNo,chequeDate,bankName,receiptNo,
                receiptDate;
        public UpcomingReportHolder(View itemView) {
            super(itemView);

            ticketNo = itemView.findViewById(R.id.ticketNoUpcoming);
            memberName = itemView.findViewById(R.id.memberNameUpcoming);
            collectionType = itemView.findViewById(R.id.collectionTypeUpcoming);
            amount = itemView.findViewById(R.id.amountUpcoming);
            chequeNo = itemView.findViewById(R.id.chequeNoUpcoming);
            chequeDate = itemView.findViewById(R.id.chequeDateUpcoming);

            bankName = itemView.findViewById(R.id.bankNameUpcoming);
            receiptNo = itemView.findViewById(R.id.receiptNoUpcoming);
            receiptDate = itemView.findViewById(R.id.receiptDateUpcoming);
        }
    }
}

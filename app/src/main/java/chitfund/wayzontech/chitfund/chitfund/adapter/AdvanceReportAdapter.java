package chitfund.wayzontech.chitfund.chitfund.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import chitfund.wayzontech.chitfund.chitfund.R;
import chitfund.wayzontech.chitfund.chitfund.model.AdvanceInstallmentReport;

public class AdvanceReportAdapter extends RecyclerView.Adapter<AdvanceReportAdapter.AdavanceReportHolder> {

    private Context context;
    private ArrayList<AdvanceInstallmentReport> advanceInstallmentReportArrayList;

    public AdvanceReportAdapter(Context context, ArrayList<AdvanceInstallmentReport> advanceInstallmentReportArrayList) {
        this.context = context;
        this.advanceInstallmentReportArrayList = advanceInstallmentReportArrayList;
    }

    @Override
    public AdavanceReportHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_advance_report,parent,false);
        return new AdavanceReportHolder(view);
    }

    @Override
    public void onBindViewHolder(AdavanceReportHolder holder, int position)
    {
        AdvanceInstallmentReport advanceInstallmentReport = advanceInstallmentReportArrayList.get(position);

        holder.ticketNo.setText(advanceInstallmentReport.getTicket_no());
        holder.memberName.setText(advanceInstallmentReport.getMember_name());
        holder.collectionType.setText(advanceInstallmentReport.getCollection_type());
        holder.amount.setText(advanceInstallmentReport.getAmount());
        holder.receiptNo.setText(advanceInstallmentReport.getReceipt_no());
        holder.receiptDate.setText(advanceInstallmentReport.getReceipt_date());


    }

    @Override
    public int getItemCount() {
        return advanceInstallmentReportArrayList.size();
    }

    public class AdavanceReportHolder extends RecyclerView.ViewHolder {

        private TextView ticketNo,memberName,collectionType,amount,receiptNo,receiptDate;
        public AdavanceReportHolder(View itemView) {
            super(itemView);

            ticketNo = itemView.findViewById(R.id.ticketNoAdvance);
            memberName = itemView.findViewById(R.id.memberNameAdvance);
            collectionType = itemView.findViewById(R.id.collectionTypeAdvance);
            amount = itemView.findViewById(R.id.textViewAmountAdvance);
            receiptNo = itemView.findViewById(R.id.receiptNoAdvance);
            receiptDate = itemView.findViewById(R.id.receiptDateAdvance);
        }
    }
}

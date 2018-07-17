package chitfund.wayzontech.chitfund.chitfund.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import chitfund.wayzontech.chitfund.chitfund.R;
import chitfund.wayzontech.chitfund.chitfund.model.AgentPOJO;

public class AgentAdapter extends RecyclerView.Adapter<AgentAdapter.AdavanceReportHolder> {

    private Context context;
    private ArrayList<AgentPOJO> agentPOJOArrayList;

    public AgentAdapter(Context context, ArrayList<AgentPOJO> agentPOJOArrayList) {
        this.context = context;
        this.agentPOJOArrayList = agentPOJOArrayList;
    }

    @Override
    public AdavanceReportHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_advance_report, parent, false);
        return new AdavanceReportHolder(view);
    }

    @Override
    public void onBindViewHolder(AdavanceReportHolder holder, int position) {
        AgentPOJO agentPOJO = agentPOJOArrayList.get(position);

//        holder.ticketNo.setText(agentPOJO.getTicket_no());
//        holder.memberName.setText(agentPOJO.getMember_name());
//        holder.collectionType.setText(agentPOJO.getCollection_type());
//        holder.amount.setText(agentPOJO.getAmount());
//        holder.receiptNo.setText(agentPOJO.getReceipt_no());
//        holder.receiptDate.setText(agentPOJO.getReceipt_date());

    }

    @Override
    public int getItemCount() {
        return agentPOJOArrayList.size();
    }

    public class AdavanceReportHolder extends RecyclerView.ViewHolder {

        private TextView ticketNo, memberName, collectionType, amount, receiptNo, receiptDate;

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

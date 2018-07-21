package chitfund.wayzontech.chitfund.chitfund.adapter.agentReportAdapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import chitfund.wayzontech.chitfund.chitfund.R;
import chitfund.wayzontech.chitfund.chitfund.model.agentReport.AdvanceReport;

public class AdvanceAgentReportAdapter extends RecyclerView.Adapter<AdvanceAgentReportAdapter.AgentAdapterViewHolder> {

    private ArrayList<AdvanceReport> advanceReportArrayList;

    public AdvanceAgentReportAdapter(ArrayList<AdvanceReport> advanceReportArrayList) {
        this.advanceReportArrayList = advanceReportArrayList;
    }

    @NonNull
    @Override
    public AgentAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_advance_agent_report, parent, false);
        return new AgentAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AgentAdapterViewHolder holder, int position) {

        AdvanceReport advanceReport = advanceReportArrayList.get(position);
        holder.memberName.setText(advanceReport.getMemberName());
        holder.groupName.setText(advanceReport.getGroupName());
        //holder.collectionType.setText(advanceReport.getCollectionType());
        holder.dateTime.setText(advanceReport.getDateTime());
        holder.amount.setText(advanceReport.getAmount());

    }

    @Override
    public int getItemCount() {
        return advanceReportArrayList.size();
    }

    public class AgentAdapterViewHolder extends RecyclerView.ViewHolder {

        private TextView memberName, groupName, collectionType, amount, dateTime;

        public AgentAdapterViewHolder(View itemView) {
            super(itemView);

            memberName = itemView.findViewById(R.id.textViewMemberNameAgentReport);
            //collectionType = itemView.findViewById(R.id.textViewCollectionTypeAgentReport);
            amount = itemView.findViewById(R.id.textViewAmountAgentReport);
            groupName = itemView.findViewById(R.id.textViewGroupNameAgentReport);
            dateTime = itemView.findViewById(R.id.textViewDateTimeAgentReport);
        }
    }
}

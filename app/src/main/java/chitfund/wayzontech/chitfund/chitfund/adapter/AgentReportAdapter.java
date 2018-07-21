package chitfund.wayzontech.chitfund.chitfund.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import chitfund.wayzontech.chitfund.chitfund.R;
import chitfund.wayzontech.chitfund.chitfund.model.AgentReport;

public class AgentReportAdapter extends RecyclerView.Adapter<AgentReportAdapter.AgentReportHolder> {

    private Context context;
    private ArrayList<AgentReport> agentReportArrayList;

    public AgentReportAdapter(Context context, ArrayList<AgentReport> agentReportArrayList) {
        this.context = context;
        this.agentReportArrayList = agentReportArrayList;
    }

    @Override
    public AgentReportHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_agent_report, parent, false);
        return new AgentReportHolder(view);
    }

    @Override
    public void onBindViewHolder(AgentReportHolder holder, int position) {
        AgentReport AgentReport = agentReportArrayList.get(position);

        holder.memberName.setText(AgentReport.getMemberName());
        holder.groupName.setText(AgentReport.getGroupName());
        holder.collectionType.setText(AgentReport.getCollectionType());
        holder.dateTime.setText(AgentReport.getDateTime());
        holder.amount.setText(AgentReport.getAmount());

    }

    @Override
    public int getItemCount() {
        return agentReportArrayList.size();
    }

    public class AgentReportHolder extends RecyclerView.ViewHolder {

        private TextView memberName, groupName, collectionType, amount, dateTime;

        public AgentReportHolder(View itemView) {
            super(itemView);

            memberName = itemView.findViewById(R.id.textViewMemberNameAgentReport);
            collectionType = itemView.findViewById(R.id.textViewCollectionTypeAgentReport);
            amount = itemView.findViewById(R.id.textViewAmountAgentReport);
            groupName = itemView.findViewById(R.id.textViewGroupNameAgentReport);
            dateTime = itemView.findViewById(R.id.textViewDateTimeAgentReport);
        }
    }
}

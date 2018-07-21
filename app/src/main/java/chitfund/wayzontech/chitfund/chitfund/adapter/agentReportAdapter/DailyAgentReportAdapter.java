package chitfund.wayzontech.chitfund.chitfund.adapter.agentReportAdapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import chitfund.wayzontech.chitfund.chitfund.R;
import chitfund.wayzontech.chitfund.chitfund.model.agentReport.DailyReport;

public class DailyAgentReportAdapter extends RecyclerView.Adapter<DailyAgentReportAdapter.AgentAdapterViewHolder> {

    private ArrayList<DailyReport> dailyReportArrayList;

    public DailyAgentReportAdapter(ArrayList<DailyReport> dailyReportArrayList) {
        this.dailyReportArrayList = dailyReportArrayList;
    }

    @NonNull
    @Override
    public AgentAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_daily_agent_report, parent, false);
        return new AgentAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AgentAdapterViewHolder holder, int position) {

        DailyReport dailyReport = dailyReportArrayList.get(position);

        holder.memberName.setText(dailyReport.getMemberName());
        //holder.groupName.setText(dailyReport.getGroupName());
        holder.collectionType.setText(dailyReport.getCollectionType());
        //holder.dateTime.setText(dailyReport.getDateTime());
        holder.amount.setText(dailyReport.getAmount());

    }

    @Override
    public int getItemCount() {
        return dailyReportArrayList.size();
    }

    public class AgentAdapterViewHolder extends RecyclerView.ViewHolder {

        private TextView memberName, groupName, collectionType, amount, dateTime;

        public AgentAdapterViewHolder(View itemView) {
            super(itemView);
            memberName = itemView.findViewById(R.id.textViewMemberNameAgentDailyReport);
            collectionType = itemView.findViewById(R.id.textViewGroupNameAgentDailyReport);
            amount = itemView.findViewById(R.id.textViewAmountAgentDailyReport);
            //groupName = itemView.findViewById(R.id.textViewGroupNameAgentReport);
            //dateTime = itemView.findViewById(R.id.textViewDateTimeAgentReport);
        }


    }
}

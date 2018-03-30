package chitfund.wayzontech.chitfund.chitfund.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import chitfund.wayzontech.chitfund.chitfund.R;
import chitfund.wayzontech.chitfund.chitfund.model.MemberReport;

public class MemberReportAdapter extends RecyclerView.Adapter<MemberReportAdapter.MemberReportHolder>
{
    private Context context;
    private ArrayList<MemberReport> memberReportArrayList;

    public MemberReportAdapter(Context context, ArrayList<MemberReport> memberReportArrayList) {
        this.context = context;
        this.memberReportArrayList = memberReportArrayList;
    }

    @Override
    public MemberReportHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_member_report,parent,false);

        return new MemberReportHolder(view);
    }

    @Override
    public void onBindViewHolder(MemberReportHolder holder, int position) {

        MemberReport memberReport = memberReportArrayList.get(position);
        holder.memeberId.setText(memberReport.getMember_id());
        holder.memberName.setText(memberReport.getMember_name());
        holder.memberMobile.setText(memberReport.getMember_mobile());
    }

    @Override
    public int getItemCount() {
        return memberReportArrayList.size();
    }

    public class MemberReportHolder extends RecyclerView.ViewHolder {

        private TextView memeberId,memberName,memberMobile;

        public MemberReportHolder(View itemView) {
            super(itemView);
            memeberId = itemView.findViewById(R.id.textViewMemberId);
            memberName = itemView.findViewById(R.id.textViewMemberName);
            memberMobile = itemView.findViewById(R.id.textViewMemberMobile);
        }
    }
}

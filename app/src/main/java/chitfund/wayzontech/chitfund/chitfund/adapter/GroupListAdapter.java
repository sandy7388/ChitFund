package chitfund.wayzontech.chitfund.chitfund.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import chitfund.wayzontech.chitfund.chitfund.R;
import chitfund.wayzontech.chitfund.chitfund.activity.GroupInfoActivity;
import chitfund.wayzontech.chitfund.chitfund.model.MemberName;

/**
 * Created by sandy on 12/3/18.
 */

public class GroupListAdapter extends RecyclerView.Adapter<GroupListAdapter.GroupListViewHolder> {
    private Context context;
    private ArrayList<MemberName> memberNameArrayList;

    public GroupListAdapter(Context context, ArrayList<MemberName> memberName) {

    }

    public GroupListAdapter(ArrayList<MemberName> memberNameArrayList, Context context) {
        this.context = context;
        this.memberNameArrayList = memberNameArrayList;
    }

    @Override
    public GroupListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        GroupListViewHolder groupListViewHolder =null;
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.adapter_group_list,parent,false);
        groupListViewHolder = new GroupListViewHolder(view);

        return groupListViewHolder;
    }

    @Override
    public void onBindViewHolder(GroupListViewHolder holder, final int position) {

        MemberName memberName = memberNameArrayList.get(position);
        holder.memberName.setText(memberName.getName());
    }

    @Override
    public int getItemCount() {
        return memberNameArrayList.size();
    }

    public class GroupListViewHolder extends RecyclerView.ViewHolder{
        private TextView memberName;
        public GroupListViewHolder(View itemView) {
            super(itemView);

            memberName = itemView.findViewById(R.id.member_name);
        }
    }


}

package chitfund.wayzontech.chitfund.chitfund.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import chitfund.wayzontech.chitfund.chitfund.R;
import chitfund.wayzontech.chitfund.chitfund.model.JoinedGroup;

/**
 * Created by sandy on 24/3/18.
 */

public class JoinedGroupAdapter extends RecyclerView.Adapter<JoinedGroupAdapter.JoinedGroupHolder> {

    private Context context;
    private ArrayList<JoinedGroup> joinedGroupArrayList;

    public JoinedGroupAdapter(Context context, ArrayList<JoinedGroup> joinedGroupArrayList) {
        this.context = context;
        this.joinedGroupArrayList = joinedGroupArrayList;
    }

    @Override
    public JoinedGroupHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_joined_group,parent,false);
        return new JoinedGroupHolder(view);
    }

    @Override
    public void onBindViewHolder(JoinedGroupHolder holder, int position) {

        JoinedGroup joinedGroup = joinedGroupArrayList.get(position);
        holder.textViewId.setText(joinedGroup.getGroup_id());
        holder.textViewName.setText(joinedGroup.getGroup_name());
        holder.textViewNextDate.setText(joinedGroup.getNext_date());
        holder.textViewAmount.setText(joinedGroup.getAmount());
    }

    @Override
    public int getItemCount() {
        return joinedGroupArrayList.size();
    }

    public class JoinedGroupHolder extends RecyclerView.ViewHolder {

        private TextView textViewId,textViewName,textViewNextDate,textViewAmount;
        public JoinedGroupHolder(View itemView) {
            super(itemView);

            textViewId = itemView.findViewById(R.id.groupId);
            textViewName = itemView.findViewById(R.id.groupName);
            textViewNextDate = itemView.findViewById(R.id.nxt_auction);
            textViewAmount = itemView.findViewById(R.id.amount);
        }
    }
}

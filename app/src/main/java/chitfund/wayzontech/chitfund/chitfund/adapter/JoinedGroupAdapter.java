package chitfund.wayzontech.chitfund.chitfund.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import chitfund.wayzontech.chitfund.chitfund.R;
import chitfund.wayzontech.chitfund.chitfund.model.JoinedGroup;

/**
 * Created by sandy on 24/3/18.
 */

public class JoinedGroupAdapter extends RecyclerView.Adapter<JoinedGroupAdapter.JoinedGroupHolder> {

    private Context context;
    private ArrayList<JoinedGroup> joinedGroupArrayList;
    private String strRemainingDays,inputDateString;
    //private int strRemainingDays;
    public JoinedGroupAdapter(Context context, ArrayList<JoinedGroup> joinedGroupArrayList) {
        this.context = context;
        this.joinedGroupArrayList = joinedGroupArrayList;
    }

    @Override
    public JoinedGroupHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_joined_group,parent,false);

        inputDateString = "03/29/2018";
        Calendar calCurr = Calendar.getInstance();
        Calendar day = Calendar.getInstance();
        try {
            day.setTime(new SimpleDateFormat("MM/dd/yyyy").parse(inputDateString));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(day.after(calCurr)){
            strRemainingDays = String.valueOf((day.get(Calendar.DAY_OF_MONTH) -(calCurr.get(Calendar.DAY_OF_MONTH))));
            System.out.println("Days Left: " + strRemainingDays );
        }
        return new JoinedGroupHolder(view);
    }

    @Override
    public void onBindViewHolder(JoinedGroupHolder holder, int position) {

        JoinedGroup joinedGroup = joinedGroupArrayList.get(position);
        holder.textViewId.setText(joinedGroup.getGroup_id());
        holder.textViewName.setText(joinedGroup.getGroup_name());
        holder.textViewNextDate.setText(joinedGroup.getNext_date());
        holder.textViewAmount.setText(joinedGroup.getAmount());
        holder.textViewRemainingDays.setText(strRemainingDays + " Days");
    }

    @Override
    public int getItemCount() {
        return joinedGroupArrayList.size();
    }

    public class JoinedGroupHolder extends RecyclerView.ViewHolder {

        private TextView textViewId,textViewName,textViewNextDate,
                textViewAmount,textViewRemainingDays;
        public JoinedGroupHolder(View itemView) {
            super(itemView);

            textViewId = itemView.findViewById(R.id.groupId);
            textViewName = itemView.findViewById(R.id.groupName);
            textViewNextDate = itemView.findViewById(R.id.nxt_auction);
            textViewAmount = itemView.findViewById(R.id.amount);
            textViewRemainingDays = itemView.findViewById(R.id.remainingDays);
        }
    }
}

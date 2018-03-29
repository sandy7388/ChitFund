package chitfund.wayzontech.chitfund.chitfund.adapter;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import chitfund.wayzontech.chitfund.chitfund.R;
import chitfund.wayzontech.chitfund.chitfund.model.JoinedGroup;

/**
 * Created by sandy on 24/3/18.
 */

public class JoinedGroupAdapter extends RecyclerView.Adapter<JoinedGroupAdapter.JoinedGroupHolder> {

    private Calendar start_calendar = Calendar.getInstance();
    private Calendar end_calendar = Calendar.getInstance();
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
                .inflate(R.layout.adapter_new_joined_group,parent,false);

//        inputDateString = "29/03/2018";
//        Calendar calCurr = Calendar.getInstance();
//        Calendar day = Calendar.getInstance();
//        try {
//            day.setTime(new SimpleDateFormat("dd/MM/yyyy").parse(inputDateString));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        if(day.after(calCurr)){
//            strRemainingDays = String.valueOf((day.get(Calendar.DAY_OF_MONTH) -(calCurr.get(Calendar.DAY_OF_MONTH))));
//            System.out.println("Days Left: " + strRemainingDays );
//        }
        return new JoinedGroupHolder(view);
    }

    @Override
    public void onBindViewHolder(JoinedGroupHolder holder, int position) {
        JoinedGroup joinedGroup = joinedGroupArrayList.get(position);
        holder.textViewId.setText(joinedGroup.getGroup_id());
        holder.textViewName.setText(joinedGroup.getGroup_name());
        holder.textViewNextDate.setText(joinedGroup.getNext_date());
        holder.textViewAmount.setText(joinedGroup.getAmount());
        try {
            end_calendar.setTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(joinedGroup.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        countdown(holder.textViewRemainingDays);
    }

    @Override
    public int getItemCount() {
        return joinedGroupArrayList.size();
    }

    public class JoinedGroupHolder extends RecyclerView.ViewHolder {

        private TextView textViewId,textViewName,textViewNextDate,
                textViewAmount,textViewRemainingDays;
        private JoinedGroupHolder(View itemView) {
            super(itemView);

            textViewId = itemView.findViewById(R.id.groupId);
            textViewName = itemView.findViewById(R.id.groupName);
            textViewNextDate = itemView.findViewById(R.id.nxt_auction);
            textViewAmount = itemView.findViewById(R.id.amount);
            textViewRemainingDays = itemView.findViewById(R.id.remainingDays);
        }
    }
   private void countdown(final TextView textview)
    {
        //end_calendar.set();

        long start_millis = start_calendar.getTimeInMillis(); //get the start time in milliseconds
        long end_millis = end_calendar.getTimeInMillis(); //get the end time in milliseconds
        long total_millis = (end_millis - start_millis); //total time in milliseconds

        //1000 = 1 second interval
        CountDownTimer cdt = new CountDownTimer(total_millis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long days = TimeUnit.MILLISECONDS.toDays(millisUntilFinished);
                millisUntilFinished -= TimeUnit.DAYS.toMillis(days);

                long hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished);
                millisUntilFinished -= TimeUnit.HOURS.toMillis(hours);

                long minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished);
                millisUntilFinished -= TimeUnit.MINUTES.toMillis(minutes);

                long seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished);

                textview.setText(days + ":" + hours + ":" + minutes + ":" + seconds); //You can compute the millisUntilFinished on hours/minutes/seconds
            }

            @Override
            public void onFinish() {
                textview.setText("Finish!");
            }
        };
        cdt.start();
    }
}

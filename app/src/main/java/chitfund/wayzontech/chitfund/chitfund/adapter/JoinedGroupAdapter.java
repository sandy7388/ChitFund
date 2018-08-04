package chitfund.wayzontech.chitfund.chitfund.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import chitfund.wayzontech.chitfund.chitfund.R;
import chitfund.wayzontech.chitfund.chitfund.activity.AuctionActivity;
import chitfund.wayzontech.chitfund.chitfund.model.JoinedGroup;

/**
 * Created by sandy on 24/3/18.
 */

public class JoinedGroupAdapter extends RecyclerView.Adapter<JoinedGroupAdapter.JoinedGroupHolder> {

    private Calendar start_calendar = Calendar.getInstance();
    private Calendar start_calendar1 = Calendar.getInstance();
    private Calendar start_calendar2 = Calendar.getInstance();
    private Calendar end_calendar = Calendar.getInstance();
    private Calendar end_calendar1 = Calendar.getInstance();
    private Calendar end_calendar2 = Calendar.getInstance();
    private Date d, d1, d2;
    private DateFormat date1, time1, date2, time2, date3, time3;
    private Context context;
    private ArrayList<JoinedGroup> joinedGroupArrayList;
    private String strRemainingDays, inputDateString;
    private TextView textView;

    //private int strRemainingDays;
    public JoinedGroupAdapter(Context context, ArrayList<JoinedGroup> joinedGroupArrayList) {
        this.context = context;
        this.joinedGroupArrayList = joinedGroupArrayList;
    }

    @Override
    public JoinedGroupHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_new_joined_group, parent, false);

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
    public void onBindViewHolder(JoinedGroupHolder holder, final int position) {
        JoinedGroup joinedGroup = joinedGroupArrayList.get(position);
        holder.textViewId.setText(joinedGroup.getGroup_id());
        holder.textViewName.setText(joinedGroup.getGroup_name());
        holder.textViewNextDate.setText(joinedGroup.getNext_date());
        holder.textViewAmount.setText(joinedGroup.getAmount());
        holder.groupAuctionType.setText(joinedGroup.getType());
        //blink(position);
        try {

            if (joinedGroup.getType().equals("Monthly")) {
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
                d = dateFormat.parse(joinedGroup.getTime());
                date1 = new SimpleDateFormat("dd-MM-yyyy");
                time1 = new SimpleDateFormat("hh:mm:ss a");
                end_calendar = Calendar.getInstance();
                end_calendar.setTime(d);
                end_calendar.add(Calendar.MONTH, +1);
                d = end_calendar.getTime();
                countdown(holder.textViewRemainingDays, position);
                //blink(position);
            }

            if (joinedGroup.getType().equals("Daily")) {
                DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
                d2 = dateFormat2.parse(joinedGroup.getTime());
                date2 = new SimpleDateFormat("dd-MM-yyyy");
                time2 = new SimpleDateFormat("hh:mm:ss a");
                end_calendar1 = Calendar.getInstance();
                end_calendar1.setTime(d2);
                end_calendar1.add(Calendar.HOUR_OF_DAY, +24);
                d2 = end_calendar1.getTime();
                countdown1(holder.textViewRemainingDays, position);
                //blink(position);
            }

            if (joinedGroup.getType().equals("Weekly")) {
                DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd EEEE hh:mm:ss a");
                d1 = dateFormat1.parse(joinedGroup.getTime());
                date3 = new SimpleDateFormat("dd-MM-yyyy");
                time3 = new SimpleDateFormat("hh:mm:ss a");
                end_calendar2 = Calendar.getInstance();
                end_calendar2.setTime(d1);
                end_calendar2.add(Calendar.DAY_OF_WEEK, +7);
                d1 = end_calendar2.getTime();
                countdown2(holder.textViewRemainingDays, position);
                //blink(position);
            }



        } catch (ParseException e) {
            e.printStackTrace();
        }

        Animation();


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //textView.setEnabled(false);
                startAuction(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return joinedGroupArrayList.size();
    }

    private void countdown(final TextView textview, final int position) {
        //end_calendar.set();

        long start_millis = start_calendar.getTimeInMillis(); //get the start time in milliseconds
        long end_millis = end_calendar.getTimeInMillis(); //get the end time in milliseconds
        long total_millis = (end_millis - start_millis); //total time in milliseconds

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

                textview.setText(days + "d" + ":" + hours + "h" + ":" + minutes + "m" + ":" + seconds + "s"); //You can compute the millisUntilFinished on hours/minutes/seconds

            }

            @Override
            public void onFinish() {
                //textView.setClickable(false);
                textview.setText("Auction Started");
                //startAuction(position);

            }
        };
        cdt.start();
    }

    private void countdown1(final TextView textview, final int position) {

        //end_calendar.set();

        long start_millis = start_calendar1.getTimeInMillis(); //get the start time in milliseconds
        long end_millis = end_calendar1.getTimeInMillis(); //get the end time in milliseconds
        long total_millis = (end_millis - start_millis); //total time in milliseconds

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

                textview.setText(days + "d" + ":" + hours + "h" + ":" + minutes + "m" + ":" + seconds + "s"); //You can compute the millisUntilFinished on hours/minutes/seconds
            }

            @Override
            public void onFinish() {
                //textview.setEnabled(true);
                //textview.setText("Auction Started");
                //startAuction(position);

            }
        };
        cdt.start();
    }

    private void countdown2(final TextView textview, final int position) {

        //end_calendar.set();

        long start_millis = start_calendar2.getTimeInMillis(); //get the start time in milliseconds
        long end_millis = end_calendar2.getTimeInMillis(); //get the end time in milliseconds
        long total_millis = (end_millis - start_millis); //total time in milliseconds

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

                textview.setText(days + "d" + ":" + hours + "h" + ":" + minutes + "m" + ":" + seconds + "s"); //You can compute the millisUntilFinished on hours/minutes/seconds
            }

            @Override
            public void onFinish() {
                //textview.setEnabled(true);
                //textview.setText("Auction Started");
                //startAuction(position);

            }
        };
        cdt.start();
    }

    private void startAuction(int position) {
        JoinedGroup joinedGroup = new JoinedGroup();

        joinedGroup = joinedGroupArrayList.get(position);

        Intent intent = new Intent(context, AuctionActivity.class);
        intent.putExtra("KEY_AMOUNT", joinedGroup.getAmount());
        intent.putExtra("KEY_ID", joinedGroup.getGroup_id());

        context.startActivity(intent);
    }

    private void blink(final int position) {
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                int timeToBlink = 500;    //in milissegunds
                try {
                    Thread.sleep(timeToBlink);
                } catch (Exception e) {
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        if (textView.getVisibility() == View.VISIBLE) {
                            textView.setVisibility(View.INVISIBLE);
                        } else {
                            textView.setVisibility(View.VISIBLE);
                        }
                        blink(position);
                        //Animation();
                    }
                });
            }
        }).start();
    }

    private void Animation() {
        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(300); //You can manage the blinking time with this parameter
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // if you want to change color at start of animation
                //textView.settextcolor("your color");
                textView.setTextColor(Color.parseColor("#FFFFFF"));
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // if you want to change color at end of animation
                //textView.settextcolor("your color");
                textView.setTextColor(Color.parseColor("#0c0cf4"));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

                //textView.setTextColor(Color.parseColor("#0c0cf4"));
            }
        });
        textView.startAnimation(anim);
    }

    public class JoinedGroupHolder extends RecyclerView.ViewHolder {

        private TextView textViewId, textViewName, textViewNextDate,
                textViewAmount, textViewRemainingDays, textViewStartAuction, groupAuctionType;
        private Button textViewRemainingDays1;

        public JoinedGroupHolder(View itemView) {
            super(itemView);

            textViewId = itemView.findViewById(R.id.groupId);
            textViewName = itemView.findViewById(R.id.groupName);
            textViewNextDate = itemView.findViewById(R.id.nxt_auction);
            textViewAmount = itemView.findViewById(R.id.amount);
            textViewRemainingDays = itemView.findViewById(R.id.remainingDays);
            textView = itemView.findViewById(R.id.start_auction);
            groupAuctionType = itemView.findViewById(R.id.groupAuctionType);
            textView.setClickable(false);
        }
    }
}
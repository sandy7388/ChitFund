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
    private Calendar end_calendar = Calendar.getInstance();
    private Date d;
    private DateFormat date, time;
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
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            d = dateFormat.parse(joinedGroup.getTime());
            //end_calendar.setTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(joinedGroup.getTime()));
            date = new SimpleDateFormat("dd-MM-yyyy");
            time = new SimpleDateFormat("hh:mm:ss");
            end_calendar = Calendar.getInstance();
            end_calendar.setTime(d);

            end_calendar.add(Calendar.MONTH, +1);
            d = end_calendar.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        countdown(holder.textViewRemainingDays);

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startAuction(position);
//            }
//        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAuction(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return joinedGroupArrayList.size();
    }

    private void countdown(final TextView textview) {
        //end_calendar.set();

        long start_millis = start_calendar.getTimeInMillis(); //get the start time in milliseconds
        long end_millis = end_calendar.getTimeInMillis(); //get the end time in milliseconds
        long total_millis = (end_millis - start_millis); //total time in milliseconds

        //1000 = 1 second interval
        //textview.setEnabled(false);
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
                textview.setText("Start");

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

    private void blink() {
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
                        blink();
                        Animation();
                    }
                });
            }
        }).start();
    }

    private void Animation() {
        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(500); //You can manage the blinking time with this parameter
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
                textViewAmount, textViewRemainingDays, textViewStartAuction;
        private Button textViewRemainingDays1;

        public JoinedGroupHolder(View itemView) {
            super(itemView);

            textViewId = itemView.findViewById(R.id.groupId);
            textViewName = itemView.findViewById(R.id.groupName);
            textViewNextDate = itemView.findViewById(R.id.nxt_auction);
            textViewAmount = itemView.findViewById(R.id.amount);
            textViewRemainingDays = itemView.findViewById(R.id.remainingDays);
            textView = itemView.findViewById(R.id.start_auction);

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
                            blink();

                        }
                    });
                }
            }).start();
        }
    }
}
package chitfund.wayzontech.chitfund.chitfund.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import chitfund.wayzontech.chitfund.chitfund.R;

import static android.content.ContentValues.TAG;


public class HomeFragment extends Fragment implements View.OnClickListener {

    private TextView name,date,auction,lastAuction,notifictions,
            groupList,joinedGroup,profile;
    private String strDate;
    private Calendar calendar;
    private int date_Year,date_Month,date_Day;
    private LinearLayout linearLayout_auction,linearLayout_group,
                        linearLayout_joined,linearLayout_last,
                        linearLayout_notification,linearLayout_profile;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        name = view.findViewById(R.id.textName);
        date = view.findViewById(R.id.textDate);
        linearLayout_auction = view.findViewById(R.id.linear_auction);
        linearLayout_group = view.findViewById(R.id.lnr_groupList);
        linearLayout_joined = view.findViewById(R.id.lnr_joinedGroup);
        linearLayout_last = view.findViewById(R.id.lnr_last_auction);
        linearLayout_notification = view.findViewById(R.id.lnr_notificaion);
        linearLayout_profile = view.findViewById(R.id.lnr_profile);

        linearLayout_auction.setOnClickListener(this);
        linearLayout_group.setOnClickListener(this);
        linearLayout_joined.setOnClickListener(this);
        linearLayout_last.setOnClickListener(this);
        linearLayout_notification.setOnClickListener(this);
        linearLayout_profile.setOnClickListener(this);

        // Calender instance
        calendar = Calendar.getInstance();
        date_Year = calendar.get(Calendar.YEAR);
        date_Month = calendar.get(Calendar.MONTH);
        date_Day = calendar.get(Calendar.DAY_OF_MONTH);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E dd/MM/yyyy");
        date.setText(simpleDateFormat.format(calendar.getTime()));

        return view;
    }


    public void setFragments(Fragment targetFragment) {
        try {
            Fragment fragment = targetFragment;
            if (fragment != null) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content, fragment).commit();
            } else {
                // error in creating fragment
                Log.e(TAG, "Error in creating fragment");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.linear_auction:
                setFragments(new AuctionFragment());
                break;
            case R.id.lnr_groupList:
                setFragments(new GroupListFragment());
                break;
            case R.id.lnr_joinedGroup:
                setFragments(new JoinedGroupFragment());
                break;
            case R.id.lnr_last_auction:
                setFragments(new LastAuctionFragment());
                break;
            case R.id.lnr_notificaion:
                setFragments(new CollectionReportFragment());
                break;
            case R.id.lnr_profile:
                setFragments(new ProfileFragment());
                break;
        }
    }
}

package chitfund.wayzontech.chitfund.chitfund.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import chitfund.wayzontech.chitfund.chitfund.R;
import chitfund.wayzontech.chitfund.chitfund.activity.CollectionReportActivity;
import chitfund.wayzontech.chitfund.chitfund.activity.MemberReportActivity;
import chitfund.wayzontech.chitfund.chitfund.adapter.ViewPagerMemberAdapter;

import static android.content.ContentValues.TAG;

public class ReportFragment extends Fragment implements View.OnClickListener, TabLayout.OnTabSelectedListener {
    private TextView textViewCollectionReport,textViewMemberReport,
            textViewUpcomingReport,textViewAdvanceReport;

    TabLayout tabLayout;
    ViewPager viewPager;
    public ReportFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reports,
                container,false);
        initController(view);
        getActivity().setTitle("Reports");
        return view;
    }

    void initController(View view)
    {
        textViewCollectionReport = view.findViewById(R.id.textViewCollectionReport);
        textViewMemberReport = view.findViewById(R.id.textViewMemberReport);
        textViewUpcomingReport = view.findViewById(R.id.textViewUpcomingReport);
        textViewAdvanceReport = view.findViewById(R.id.textViewAdvanceReport);

        textViewCollectionReport.setOnClickListener(this);
        textViewMemberReport.setOnClickListener(this);
        textViewUpcomingReport.setOnClickListener(this);
        textViewAdvanceReport.setOnClickListener(this);

        tabLayout = view.findViewById(R.id.tabLayoutMember);
        viewPager = view.findViewById(R.id.viewPagerMember);

        tabLayout.addTab(tabLayout.newTab().setText("Collection Report"));
        tabLayout.addTab(tabLayout.newTab().setText("Member Report"));
        //tabLayout.addTab(tabLayout.newTab().setText("Movie"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPagerMemberAdapter adapter = new ViewPagerMemberAdapter(getContext(), getChildFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        tabLayout.addOnTabSelectedListener(this);

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
            case R.id.textViewCollectionReport:
                startActivity(new Intent(getActivity(), CollectionReportActivity.class));
                //setFragments(new CollectionReportActivity());
                break;

            case R.id.textViewMemberReport:
                startActivity(new Intent(getActivity(), MemberReportActivity.class));
                //setFragments(new MemberReportActivity());
                break;

            case R.id.textViewUpcomingReport:
                //startActivity(new Intent(getActivity(), UpcomingReportActivity.class));
                break;
            case R.id.textViewAdvanceReport:
                //startActivity(new Intent(getActivity(), AdvanceReportActivity.class));

        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}

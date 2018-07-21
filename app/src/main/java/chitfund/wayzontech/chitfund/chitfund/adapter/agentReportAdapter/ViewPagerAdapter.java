package chitfund.wayzontech.chitfund.chitfund.adapter.agentReportAdapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import chitfund.wayzontech.chitfund.chitfund.fragment.report.AdvanceReportFragment;
import chitfund.wayzontech.chitfund.chitfund.fragment.report.DailyReportFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    int totalTabs;
    private Context myContext;

    public ViewPagerAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                AdvanceReportFragment advanceReportFragment = new AdvanceReportFragment();
                return advanceReportFragment;
            case 1:
                DailyReportFragment dailyReportFragment = new DailyReportFragment();
                return dailyReportFragment;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}

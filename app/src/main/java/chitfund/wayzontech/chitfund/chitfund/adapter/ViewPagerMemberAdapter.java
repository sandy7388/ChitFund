package chitfund.wayzontech.chitfund.chitfund.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import chitfund.wayzontech.chitfund.chitfund.fragment.CollectionReportFragment;
import chitfund.wayzontech.chitfund.chitfund.fragment.MemberReportFragment;


public class ViewPagerMemberAdapter extends FragmentPagerAdapter {

    int totalTabs;
    private Context myContext;

    public ViewPagerMemberAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                CollectionReportFragment homeFragment = new CollectionReportFragment();
                return homeFragment;
            case 1:
                MemberReportFragment sportFragment = new MemberReportFragment();
                return sportFragment;

            default:
                return null;
        }
    }

    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }
}

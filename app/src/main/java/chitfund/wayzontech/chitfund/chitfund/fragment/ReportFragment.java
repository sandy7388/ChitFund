package chitfund.wayzontech.chitfund.chitfund.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import chitfund.wayzontech.chitfund.chitfund.R;
import chitfund.wayzontech.chitfund.chitfund.activity.UpcomingInstallmentActivity;

import static android.content.ContentValues.TAG;

public class ReportFragment extends Fragment implements View.OnClickListener {
    private TextView textViewCollectionReport,textViewMemberReport,
            textViewUpcomingReport;
    public ReportFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reports,container,false);
        initController(view);
        return view;
    }

    void initController(View view)
    {
        textViewCollectionReport = view.findViewById(R.id.textViewCollectionReport);
        textViewMemberReport = view.findViewById(R.id.textViewMemberReport);
        textViewUpcomingReport = view.findViewById(R.id.textViewUpcomingReport);

        textViewCollectionReport.setOnClickListener(this);
        textViewMemberReport.setOnClickListener(this);
        textViewUpcomingReport.setOnClickListener(this);

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
                startActivity(new Intent(getActivity(), CollectionReportFragment.class));
                //setFragments(new CollectionReportFragment());
                break;

            case R.id.textViewMemberReport:
                startActivity(new Intent(getActivity(), MemberReportFragment.class));
                //setFragments(new MemberReportFragment());
                break;

            case R.id.textViewUpcomingReport:
                startActivity(new Intent(getActivity(), UpcomingInstallmentActivity.class));
                break;

        }
    }
}

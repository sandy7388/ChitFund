package chitfund.wayzontech.chitfund.chitfund.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import chitfund.wayzontech.chitfund.chitfund.R;

public class ReportFragment extends Fragment
{
    public ReportFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report,container,false);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}

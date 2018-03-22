package chitfund.wayzontech.chitfund.chitfund.fragment;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import chitfund.wayzontech.chitfund.chitfund.R;

/**
 * Created by sandy on 12/3/18.
 */

public class AuctionFragment extends Fragment {

    String strDate1,strDate2;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_auction, container, false);

        return view;
    }
}

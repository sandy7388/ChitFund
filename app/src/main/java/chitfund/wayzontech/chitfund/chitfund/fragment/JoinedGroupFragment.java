package chitfund.wayzontech.chitfund.chitfund.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import chitfund.wayzontech.chitfund.chitfund.R;


public class JoinedGroupFragment extends Fragment {

    public JoinedGroupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_joined_group, container, false);

        return view;
    }



}

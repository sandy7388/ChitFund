package chitfund.wayzontech.chitfund.chitfund.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import chitfund.wayzontech.chitfund.chitfund.R;

/**
 * Created by sandy on 24/3/18.
 */

public class EditProfileFragment extends Fragment {

    public EditProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile,
                container, false);
        return view;
    }
}

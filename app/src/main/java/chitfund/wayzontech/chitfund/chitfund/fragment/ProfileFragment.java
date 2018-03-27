package chitfund.wayzontech.chitfund.chitfund.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import chitfund.wayzontech.chitfund.chitfund.R;

import static android.content.ContentValues.TAG;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    private ImageView imageView;
    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile,
                container, false);
        imageView = view.findViewById(R.id.imageEdit);
        imageView.setOnClickListener(this);
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
            case R.id.imageEdit:
                setFragments(new EditProfileFragment());
                break;
        }
    }
}

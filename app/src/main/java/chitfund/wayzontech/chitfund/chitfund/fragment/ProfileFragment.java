package chitfund.wayzontech.chitfund.chitfund.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import chitfund.wayzontech.chitfund.chitfund.R;
import chitfund.wayzontech.chitfund.chitfund.httpHelper.URLs;
import chitfund.wayzontech.chitfund.chitfund.session.SessionManager;
import chitfund.wayzontech.chitfund.chitfund.volley.VolleySingleton;

import static android.content.ContentValues.TAG;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    private EditText editTextName,editTextEmail,
                    editTextAddress,editTextBirthday,editTextMobile;
    private ImageView imageView;
    private Button buttonDelete;
    private SessionManager session;
    private String strName,strMobile,strEmail,strAddress,strBirthday;
    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile,
                container, false);

        initController(view);
        return view;
    }

    void initController(View view)
    {
        session = new SessionManager(getContext());
        imageView = view.findViewById(R.id.imageEdit);
        editTextName = view.findViewById(R.id.profileName);
        editTextAddress = view.findViewById(R.id.profileAddress);
        editTextEmail = view.findViewById(R.id.profileEmail);
        editTextBirthday = view.findViewById(R.id.profileBirthday);
        editTextMobile = view.findViewById(R.id.profileMobile);
        buttonDelete = view.findViewById(R.id.btnDeleteAccount);
        //buttonDelete.setOnClickListener(this);
        imageView.setOnClickListener(this);

        getProfile();

    }

    private void getProfile()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.PROFILE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            JSONArray jsonArray = jsonObject.getJSONArray("profile_info");
                            for (int i=0;i<jsonArray.length();i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                if (jsonObject.getString("success").equals("1")) {
                                    strName = object.getString("member_name");
                                    strMobile = object.getString("member_mobile");
                                    strEmail = object.getString("email");
                                    strBirthday = object.getString("birth_date");
                                    strAddress = object.getString("member_address");

                                    editTextName.setText(strName);
                                    editTextMobile.setText(strMobile);
                                    editTextEmail.setText(strEmail);
                                    editTextBirthday.setText(strBirthday);
                                    editTextAddress.setText(strAddress);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<>();
                //map.put("userid",session.getUserID());
                map.put("memberid",session.getMemberID());
                return map;
            }
        };
        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
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
            case R.id.btnDeleteAccount:
                deleteAccount();
                break;
        }
    }

    private void deleteAccount()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.DELETE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userid",session.getUserID());
                params.put("memberid",session.getMemberID());
                return params;
            }
        };
        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }
}

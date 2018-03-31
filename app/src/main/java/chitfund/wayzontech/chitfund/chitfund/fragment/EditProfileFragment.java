package chitfund.wayzontech.chitfund.chitfund.fragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import chitfund.wayzontech.chitfund.chitfund.R;
import chitfund.wayzontech.chitfund.chitfund.activity.RegistrationActivity;
import chitfund.wayzontech.chitfund.chitfund.httpHelper.URLs;
import chitfund.wayzontech.chitfund.chitfund.session.SessionManager;
import chitfund.wayzontech.chitfund.chitfund.volley.VolleySingleton;

import static android.content.ContentValues.TAG;

/**
 * Created by sandy on 24/3/18.
 */

public class EditProfileFragment extends Fragment
        implements View.OnClickListener,DatePickerDialog.OnDateSetListener {

    private Button buttonSave;
    private EditText editTextName,editTextMobile,editTextAddress,
                    editTextBirthday,editTextEmail;
    private String strName,strMobile,strAddress,strBirthday,strEmail;
    private int date_Year,date_Month,date_Day;
    private Calendar calendar;
    private ProgressDialog progressDialog;
    private SessionManager sessionManager;
    public EditProfileFragment()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_edit_profile,
                container, false);
        getActivity().setTitle("Edit Profile");
        initController(view);
        return view;
    }

    private void initController(View view) {

        editTextName = view.findViewById(R.id.editName);
        editTextMobile = view.findViewById(R.id.editMobile);
        editTextEmail = view.findViewById(R.id.editEmail);
        editTextBirthday = view.findViewById(R.id.editDob);
        editTextAddress = view.findViewById(R.id.editAddress);
        sessionManager = new SessionManager(getContext());
        buttonSave = view.findViewById(R.id.btnEditSave);
        editTextBirthday.setOnClickListener(this);
        buttonSave.setOnClickListener(this);

        // Calender instance
        calendar = Calendar.getInstance();
        date_Year = calendar.get(Calendar.YEAR);
        date_Month = calendar.get(Calendar.MONTH);
        date_Day = calendar.get(Calendar.DAY_OF_MONTH);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        editTextBirthday.setText(simpleDateFormat.format(calendar.getTime()));
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
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btnEditSave:
                editProfileSave();
                break;
            case R.id.editDob:
                getDate();
                break;
        }

    }

    private void editProfileSave()
    {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        progressDialog.setMessage("Please wait....!");
        progressDialog.setCancelable(true);
        strName = editTextName.getText().toString();
        strAddress = editTextAddress.getText().toString();
        strBirthday = editTextBirthday.getText().toString();
        strEmail = editTextEmail.getText().toString();
        strMobile = editTextMobile.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.UPDATE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("success").equals("1"))
                            {
                                progressDialog.dismiss();
                                Toast.makeText(getContext(),jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                                setFragments(new ProfileFragment());
                            }
                            else
                                progressDialog.dismiss();
                                Toast.makeText(getContext(),jsonObject.getString("message"),Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            progressDialog.dismiss();
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                error.printStackTrace();

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("userid",sessionManager.getUserID());
                params.put("name",strName);
                params.put("mobile",strMobile);
                params.put("email",strEmail);
                params.put("birthday",strBirthday);
                params.put("address",strAddress);
                return params;
            }
        };
        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        month = month + 1;
        editTextBirthday.setText(year + "-" + month + "-" + dayOfMonth);
    }
    private void getDate()
    {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                this,date_Year,date_Month,date_Day);
        datePickerDialog.show();
    }
}

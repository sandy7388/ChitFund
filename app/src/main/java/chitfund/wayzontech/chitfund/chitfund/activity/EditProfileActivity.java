package chitfund.wayzontech.chitfund.chitfund.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
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
import chitfund.wayzontech.chitfund.chitfund.fragment.ProfileFragment;
import chitfund.wayzontech.chitfund.chitfund.httpHelper.URLs;
import chitfund.wayzontech.chitfund.chitfund.session.SessionManager;
import chitfund.wayzontech.chitfund.chitfund.volley.VolleySingleton;

import static android.content.ContentValues.TAG;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener,DatePickerDialog.OnDateSetListener {

    private Button buttonSave;
    private TextView editTextBirthday;
    private EditText editTextName,editTextMobile,editTextAddress,
            editTextEmail;
    private String strName,strMobile,strAddress,strBirthday,strEmail;
    private int date_Year,date_Month,date_Day;
    private Calendar calendar;
    private ProgressDialog progressDialog;
    private SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        if (getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Edit Profile");
        }
        initController();

        controller();
    }

    private void controller()
    {
        Intent intent = this.getIntent();
        String name = intent.getExtras().getString("KEY_NAME");
        String mobile = intent.getExtras().getString("KEY_MOBILE");
        String email = intent.getExtras().getString("KEY_EMAIL");
        String address = intent.getExtras().getString("KEY_ADDRESS");
        String birthday = intent.getExtras().getString("KEY_BIRTHDAY");

        editTextName.setText(name);
        editTextMobile.setText(mobile);
        editTextEmail.setText(email);
        editTextBirthday.setText(birthday);
        editTextAddress.setText(address);
    }

    private void initController() {

        editTextName = findViewById(R.id.editName);
        editTextMobile = findViewById(R.id.editMobile);
        editTextEmail = findViewById(R.id.editEmail);
        editTextBirthday = findViewById(R.id.editDob);
        editTextAddress = findViewById(R.id.editAddress);
        sessionManager = new SessionManager(this);
        buttonSave = findViewById(R.id.btnEditSave);
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

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        month = month + 1;
        editTextBirthday.setText(year + "-" + month + "-" + dayOfMonth);
    }
    private void getDate()
    {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                this,date_Year,date_Month,date_Day);
        datePickerDialog.show();
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


    public void setFragments(Fragment targetFragment) {
        try {
            Fragment fragment = targetFragment;
            if (fragment != null) {
                FragmentManager fragmentManager = this.getSupportFragmentManager();
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

    private void editProfileSave()
    {
        progressDialog = new ProgressDialog(this);
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
                                Toast.makeText(EditProfileActivity.this,jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                                setFragments(new ProfileFragment());
                            }
                            else
                                progressDialog.dismiss();
                                Toast.makeText(EditProfileActivity.this,jsonObject.getString("message"),Toast.LENGTH_SHORT).show();

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
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}

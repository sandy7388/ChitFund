package chitfund.wayzontech.chitfund.chitfund.activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import chitfund.wayzontech.chitfund.chitfund.R;
import chitfund.wayzontech.chitfund.chitfund.httpHelper.URLs;
import chitfund.wayzontech.chitfund.chitfund.volley.VolleySingleton;

public class RegistrationActivity extends AppCompatActivity
        implements View.OnClickListener , DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener, RadioGroup.OnCheckedChangeListener {

    private TextView textView_dob,textView_reg;
    private Button register;
    private int date_Year,date_Month,date_Day;
    private Calendar calendar;
    private Spinner spinner_guarantor_1,spinner_guarantor_2,spinner_groupList;
    private ArrayList<String> guarantor_1List;
    private ArrayList<String> guarantor_2List;
    private ArrayList<String> groupList;
    private String strSpinner1,strSpinner2,strSpinnerGroup;
    private JSONArray jsonArrayGuarantor1,jsonArrayGuarantor2,jsonArrayGroupList;
    private JSONObject jsonObjectGroupList,jsonObject;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private EditText edtName,edtMobile,edtEmail,
            edtAddress,edtNomineeName,edtNomineeMobile,
            edtNomineeRelation,
            edtNomineeAge,edtNomineeAddress;
    private String strDOB,strREG,strName,strMobile,strEmail,
            strAddress,strNomineeName,strNomineeMobile,
            strNomineeRelation,
            strNomineeAge,strNomineeAddress,
            guarantorName1,guarantorId1,guarantorName2,guarantorId2,groupName,groupId;
    private String gender="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        init();
        if (getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Registration");
        }
        getURLForSpinner();
    }
    @SuppressLint("SimpleDateFormat")
    private void init()
    {
        register = findViewById(R.id.btn_registration);
        textView_dob = findViewById(R.id.txt_dob_date);
        textView_reg = findViewById(R.id.txt_reg_date);
        spinner_guarantor_1 = findViewById(R.id.spinner_guarantor1);
        spinner_guarantor_2 = findViewById(R.id.spinner_guarantor2);
        spinner_groupList = findViewById(R.id.spinner_groupList);
        // Guarantor Arraylist
        guarantor_1List = new ArrayList<>();
        guarantor_2List = new ArrayList<>();
        groupList = new ArrayList<>();

        // Gender
        radioGroup = findViewById(R.id.radioGroup);
//
        // EditText
        edtName = findViewById(R.id.editText_name_reg);
        edtMobile = findViewById(R.id.editText_mobile_reg);
        edtEmail = findViewById(R.id.editText_email);
        edtAddress = findViewById(R.id.editText_address_reg);
        edtNomineeName = findViewById(R.id.editText_nominee_name);
        edtNomineeMobile = findViewById(R.id.editText_nominee_mobile);
        edtNomineeRelation = findViewById(R.id.editText_nominee_relation);
        edtNomineeAge = findViewById(R.id.editText_nominee_age);
        edtNomineeAddress = findViewById(R.id.editText_nominee_address);



        // Calender instance
        calendar = Calendar.getInstance();
        date_Year = calendar.get(Calendar.YEAR);
        date_Month = calendar.get(Calendar.MONTH);
        date_Day = calendar.get(Calendar.DAY_OF_MONTH);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        textView_dob.setText(simpleDateFormat.format(calendar.getTime()));
        textView_reg.setText(simpleDateFormat.format(calendar.getTime()));

        //textView_reg.setOnClickListener(this);
        textView_dob.setOnClickListener(this);
        register.setOnClickListener(this);
        radioGroup.setOnCheckedChangeListener(this);
    }

    void getURLForSpinner()
    {
        getGuarantor1(URLs.GUARANTOR_URL);
        getGuarantor2(URLs.GUARANTOR_URL);
        getGroupList(URLs.GROUP_URL);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_registration:
                userRegistration();
                break;
            case R.id.txt_dob_date:
                getDate();
                break;
            case R.id.txt_reg_date:
                getDate();
                break;
        }
    }

    private void userRegistration()
    {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setMessage("Please wait....!");
        strName = edtName.getText().toString();
        strMobile = edtMobile.getText().toString();
        strEmail = edtEmail.getText().toString();
        strAddress = edtAddress.getText().toString();
        strNomineeName = edtNomineeName.getText().toString();
        strNomineeMobile = edtNomineeMobile.getText().toString();
        strNomineeAddress = edtNomineeAddress.getText().toString();
        strNomineeAge = edtNomineeAge.getText().toString();
        strNomineeRelation = edtNomineeRelation.getText().toString();

        strDOB = textView_dob.getText().toString();
        strREG = textView_reg.getText().toString();

        //System.out.println("Gender" +gender);

        validation();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.REGISTRATION_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("success").equals("0"))
                            {
                                progressDialog.dismiss();
                                Toast.makeText(RegistrationActivity.this,jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegistrationActivity.this,LoginActivity.class));
                                finish();
                            }
                            else
                                progressDialog.dismiss();
                                Toast.makeText(RegistrationActivity.this,jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegistrationActivity.this,LoginActivity.class));

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                progressDialog.dismiss();
                error.printStackTrace();

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name",strName);
                params.put("mobile",strMobile);
                params.put("email",strEmail);
                params.put("gender",gender);
                params.put("dob",strDOB);
                params.put("address",strAddress);
                params.put("guarantor1",guarantorId1);
                params.put("guarantor2",guarantorId2);
                params.put("registrationdate",strREG);
                params.put("nomineename",strNomineeName);
                params.put("nomineemobile",strNomineeMobile);
                params.put("nomineerelation",strNomineeRelation);
                params.put("nomineeage",strNomineeAge);
                params.put("nomineeaddress",strNomineeAddress);
                params.put("groupid",groupId);
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        month = month + 1;
        textView_dob.setText(year + "-" + month + "-" + dayOfMonth);
    }

    private void getDate()
    {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                RegistrationActivity.this,date_Year,date_Month,date_Day);
        datePickerDialog.show();
    }

    private void getGuarantor1(final String gurantor_1)
    {
        StringRequest stringRequest = new StringRequest(gurantor_1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try
                {
                    jsonArrayGuarantor1 = new JSONArray(response);
                    for (int i = 0;i<jsonArrayGuarantor1.length();i++)
                    {
                        JSONObject jsonObject = jsonArrayGuarantor1.getJSONObject(i);

                        String guarantor = jsonObject.getString("member_name");

                        guarantor_1List.add(guarantor);
                    }

                    spinner_guarantor_1.setAdapter(new ArrayAdapter<String>(RegistrationActivity.this, android.R.layout.simple_spinner_dropdown_item,guarantor_1List));
                    spinner_guarantor_1.setOnItemSelectedListener(RegistrationActivity.this);

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
            }
        });

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
    private void getGuarantor2(final String gurantor_2)
    {
        StringRequest stringRequest = new StringRequest(gurantor_2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try
                {
                    jsonArrayGuarantor2 = new JSONArray(response);
                    for (int k = 0;k<jsonArrayGuarantor2.length();k++)
                    {
                        JSONObject jsonObject = jsonArrayGuarantor2.getJSONObject(k);

                        String guarantor = jsonObject.getString("member_name");

                        guarantor_2List.add(guarantor);
                    }

                    spinner_guarantor_2.setAdapter(new ArrayAdapter<String>(RegistrationActivity.this, android.R.layout.simple_spinner_dropdown_item,guarantor_2List));
                    spinner_guarantor_2.setOnItemSelectedListener(RegistrationActivity.this);

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
    private void getGroupList(String groupUrl)
    {

        StringRequest stringRequest = new StringRequest(groupUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            jsonObjectGroupList = new JSONObject(response);
                            jsonArrayGroupList = jsonObjectGroupList.getJSONArray("group_info");
                            for (int i=0;i<jsonArrayGroupList.length();i++)
                            {
                                jsonObject = jsonArrayGroupList.getJSONObject(i);
                                String groupName = jsonObject.getString("group_name");

                                groupList.add(groupName);
                            }

                            spinner_groupList.setAdapter(new ArrayAdapter<String>(RegistrationActivity.this, android.R.layout.simple_spinner_dropdown_item,groupList));
                            spinner_groupList.setOnItemSelectedListener(RegistrationActivity.this);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) parent;
        guarantorName1 = spinner.getAdapter().getItem(position).toString();
        if(spinner.getId()==R.id.spinner_guarantor1) {
            for (int i = 0; i < jsonArrayGuarantor1.length(); i++) {
                try {
                    if (jsonArrayGuarantor1.getJSONObject(i).getString("member_name").equals(guarantorName1)) {
                        guarantorId1 = jsonArrayGuarantor1.getJSONObject(i).getString("member_id");
                        System.out.println(" 1" + guarantorId1);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        else if(spinner.getId()==R.id.spinner_guarantor2) {
            guarantorName2 = spinner.getAdapter().getItem(position).toString();
            for (int i = 0; i < jsonArrayGuarantor2.length(); i++) {
                try {
                    if (jsonArrayGuarantor2.getJSONObject(i).getString("member_name").equals(guarantorName2)) {
                        guarantorId2 = jsonArrayGuarantor2.getJSONObject(i).getString("member_id");
                        System.out.println("2 " + guarantorId2);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        groupName = spinner.getAdapter().getItem(position).toString();
        if(spinner.getId()==R.id.spinner_groupList) {
            for (int j = 0; j < jsonArrayGroupList.length(); j++) {
                try {
                    if (jsonArrayGroupList.getJSONObject(j).getString("group_name").equals(groupName)) {
                        groupId = jsonArrayGroupList.getJSONObject(j).getString("group_id");
                        System.out.println("G " + groupId);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void validation()
    {
        try {

            if(gender.equals(""))
            {
                Toast.makeText(RegistrationActivity.this,"Please select gender",Toast.LENGTH_SHORT).show();

                return;
            }
            if (strName.equals("")) {
                edtName.setError("required");
                return;
            }
            if (strMobile.equals(""))
            {
                edtMobile.setError("required");
                return;
            }
            if (strEmail.equals(""))
            {
                edtEmail.setError("required");
                return;
            }

            if (strDOB.equals("")) {
                return;
            }
            if (strREG.equals(""))
            {
                return;
            }
            if (strAddress.equals(""))
            {
                edtAddress.setError("Address is required");
                return;
            }

            if (guarantorId1.equals("")) {

                return;
            }
            if (strNomineeName.equals(""))
            {
                edtNomineeName.setError("Nominee name is required");
                return;
            }
            if (strNomineeMobile.equals(""))
            {
                edtNomineeMobile.setError("Mobile is required");
                return;
            }
            if (strNomineeAddress.equals("")) {
                edtNomineeAddress.setError("Address is required");
                return;
            }
            if (strNomineeRelation.equals(""))
            {
                edtNomineeRelation.setError("Relation is required");
                return;
            }
            if (strNomineeAge.equals(""))
            {
                edtNomineeAge.setError("Age is reguired");
                return;
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId)
    {
        // This will get the radiobutton that has changed in its check state
        RadioButton checkedRadioButton = (RadioButton)group.findViewById(checkedId);
        // This puts the value (true/false) into the variable
        boolean isChecked = checkedRadioButton.isChecked();
        // If the radiobutton that has changed in check state is now checked...
        if (isChecked)
        {
            // Changes the textview's text to "Checked: example radiobutton text"
            System.out.println("Checked:" + checkedRadioButton.getText());
            gender=checkedRadioButton.getText().toString();

        }

    }
}

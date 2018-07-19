package chitfund.wayzontech.chitfund.chitfund.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import chitfund.wayzontech.chitfund.chitfund.R;
import chitfund.wayzontech.chitfund.chitfund.httpHelper.AgentURL;
import chitfund.wayzontech.chitfund.chitfund.session.AgentSession;
import chitfund.wayzontech.chitfund.chitfund.volley.VolleySingleton;

public class AgentActivity extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener, View.OnClickListener,
        RadioGroup.OnCheckedChangeListener {

    private AgentSession agentSession;
    Date date = new Date();
    private Spinner spinnerGroupName, spinnerMemberName, spinnerBankName, spinnerCollectionType;
    private String strGroupNameJSON, strGroupName, strGroupIdJSON,
            strGroupId, strMemberName, strMemberId, strMemberNameJSON,
            strMemberIdJSON, strBankName, strBankId, strBankNameJSON, strBankIdJSON, strCollectionType;
    private ArrayList<String> groupName, memberName, bankName;
    private JSONArray groupArray, memberArray, bankArray;
    private ProgressDialog progressDialog;
    private JSONObject serverResponse, groupNameObject, serverResponseMember,
            memberNameObject, serverResponseBank, bankNameObject;
    private String strRoleId = String.valueOf(4), strDate, strReceipt, strAmount, strChequeNumber;
    private Button buttonGetDetails, buttonCollection;
    private EditText editTextChequeNumber, editTextAmount;
    private LinearLayout linearLayout;
    private RadioButton radioButtonCash, radioButtonCheque;
    private RadioGroup radioGroup;
    private String paymentMode = "";
    private int date_Year, date_Month, date_Day;
    private Calendar calendar;
    private String[] country = {"Select One", "Advance", "Daily", "Regular"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent);
        controller();

    }

    void controller() {
        spinnerGroupName = findViewById(R.id.spinnerGrpNameAgent);
        spinnerMemberName = findViewById(R.id.spinnerMbrNameAgent);
        spinnerBankName = findViewById(R.id.spinnerBankNameAgent);
        spinnerCollectionType = findViewById(R.id.spinnerSubTypeAgent);
        buttonCollection = findViewById(R.id.buttonCollectPayment);
        radioGroup = findViewById(R.id.radioGroupAgent);
        editTextChequeNumber = findViewById(R.id.editTextChequeNo);
        editTextAmount = findViewById(R.id.editTextAmountAgent);
        linearLayout = findViewById(R.id.linearLayoutAgent);
        agentSession = new AgentSession(this);
        spinnerGroupName.setOnItemSelectedListener(this);
        spinnerMemberName.setOnItemSelectedListener(this);
        spinnerBankName.setOnItemSelectedListener(this);
        spinnerCollectionType.setOnItemSelectedListener(this);
        buttonCollection.setOnClickListener(this);

        radioGroup.setOnCheckedChangeListener(this);
        groupName = new ArrayList<>();
        memberName = new ArrayList<>();
        bankName = new ArrayList<>();

        if (!AgentSession.getInstance(this).isLoggedIn()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

        try {
            getGroupName();
            getBankName();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ArrayAdapter aa = new ArrayAdapter(this, R.layout.spinner_layout, country);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinnerCollectionType.setAdapter(aa);

        calendar = Calendar.getInstance();
        date_Year = calendar.get(Calendar.YEAR);
        date_Month = calendar.get(Calendar.MONTH);
        date_Day = calendar.get(Calendar.DAY_OF_MONTH);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat receiptNo = new SimpleDateFormat("yyyyMMddhhmmss");
        strDate = simpleDateFormat.format(date);
        strReceipt = receiptNo.format(date);
    }

    void getGroupName() {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, AgentURL.AGENT_GROUP_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            serverResponse = new JSONObject(response);

                            if (serverResponse.getString("success").equals("1")) {
                                groupArray = serverResponse.getJSONArray("Agent_info");

                                for (int i = 0; i < groupArray.length(); i++) {
                                    groupNameObject = groupArray.getJSONObject(i);

                                    strGroupNameJSON = groupNameObject.getString("group_name");
                                    strGroupIdJSON = groupNameObject.getString("group_id");

                                    groupName.add(strGroupNameJSON);
                                }

                                spinnerGroupName.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_layout, groupName));

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> param = new HashMap<>();
                param.put("agent_id", agentSession.getUserID());
                param.put("role_id", strRoleId);
                return param;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }


    void getBankName() {


        StringRequest stringRequest = new StringRequest(Request.Method.GET, AgentURL.AGENT_BANK_DETAILS_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            serverResponseBank = new JSONObject(response);

                            if (serverResponseBank.getString("success").equals("1")) {
                                bankArray = serverResponseBank.getJSONArray("Bank_info");

                                for (int i = 0; i < bankArray.length(); i++) {
                                    bankNameObject = bankArray.getJSONObject(i);

                                    strBankNameJSON = bankNameObject.getString("bank_name");
                                    strBankIdJSON = bankNameObject.getString("bl_id");

                                    bankName.add(strBankNameJSON);
                                }

                                spinnerBankName.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_layout, bankName));

                            }
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
        switch (spinner.getId()) {
            case R.id.spinnerGrpNameAgent:
                strGroupName = spinner.getAdapter().getItem(position).toString();
                for (int i = 0; i < groupArray.length(); i++) {
                    try {

                        if (strGroupNameJSON.equals(strGroupName)) {
                            strGroupId = strGroupIdJSON;
                            System.out.println("strGroupId" + strGroupId);

                            getMemberName();

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            case R.id.spinnerMbrNameAgent:

                strMemberName = spinner.getAdapter().getItem(position).toString();
                try {
                    memberArray = serverResponseMember.getJSONArray("Members_info");
                    for (int j = 0; j < memberArray.length(); j++) {
                        if (memberArray.getJSONObject(j).getString("member_name").equals(strMemberName)) {
                            strMemberId = memberArray.getJSONObject(j).getString("member_id");
                            System.out.println("strMemberId" + strMemberId);
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.spinnerBankNameAgent:

                strBankName = spinner.getAdapter().getItem(position).toString();
                try {
                    bankArray = serverResponseBank.getJSONArray("Bank_info");
                    for (int k = 0; k < bankArray.length(); k++) {


                        if (bankArray.getJSONObject(k).getString("bank_name").equals(strBankName)) {
                            strBankId = bankArray.getJSONObject(k).getString("bl_id");
                            System.out.println("strBankId" + strBankId);

                            getMemberName();

                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case R.id.spinnerSubTypeAgent:
                strCollectionType = spinner.getAdapter().getItem(position).toString();
                System.out.println("strCollectionType" + strCollectionType);

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_logout:
                AgentSession.getInstance(AgentActivity.this).logout();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    void getMemberName() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait.....!");
        progressDialog.show();
        progressDialog.setCancelable(true);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AgentURL.AGENT_MEMBER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            serverResponseMember = new JSONObject(response);
                            if (serverResponseMember.getString("success").equals("1")) {
                                memberArray = serverResponseMember.getJSONArray("Members_info");

                                for (int j = 0; j < memberArray.length(); j++) {
                                    memberNameObject = memberArray.getJSONObject(j);

                                    strMemberNameJSON = memberNameObject.getString("member_name");
                                    strMemberIdJSON = memberNameObject.getString("member_id");
                                    memberName.add(strMemberNameJSON);
                                }

                                progressDialog.dismiss();
                                spinnerMemberName.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_layout, memberName));

                            }
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
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("agent_id", agentSession.getUserID());
                map.put("role_id", strRoleId);
                map.put("group_id", strGroupId);
                return map;

            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonCollectPayment:
                makeCollection();
                break;
        }

    }

    private void makeCollection() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait.....!");
        progressDialog.show();
        progressDialog.setCancelable(true);

        strAmount = editTextAmount.getText().toString();
        strChequeNumber = editTextChequeNumber.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AgentURL.AGENT_MEMBER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if (jsonObject.getString("success").equals("1")) {
                                progressDialog.dismiss();

                                Toast.makeText(AgentActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            } else
                                progressDialog.dismiss();

                            Toast.makeText(AgentActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

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
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("agent_id", agentSession.getUserID());
                map.put("role_id", strRoleId);
                map.put("group_id", strGroupId);
                map.put("member_id", strMemberId);
                map.put("bl_id", strBankId);
                map.put("payment_mode", paymentMode);
                map.put("amount", strAmount);
                map.put("cheque_no", strChequeNumber);
                map.put("collection_type", strCollectionType);
                map.put("date", strDate);
                map.put("receipt_no", strReceipt);

                return map;

            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        // This will get the radiobutton that has changed in its check state
        RadioButton checkedRadioButton = group.findViewById(checkedId);
        // This puts the value (true/false) into the variable
        boolean isChecked = checkedRadioButton.isChecked();
        // If the radiobutton that has changed in check state is now checked...
        if (checkedId == R.id.radioCash) {
            // Changes the textview's text to "Checked: example radiobutton text"

            linearLayout.setVisibility(View.GONE);
            paymentMode = checkedRadioButton.getText().toString();
            System.out.println("Checked:" + paymentMode);

        }
        if (checkedId == R.id.radioCheque) {
            linearLayout.setVisibility(View.VISIBLE);

            paymentMode = checkedRadioButton.getText().toString();

            System.out.println("Checked Cheque:" + paymentMode);
        }

    }
}


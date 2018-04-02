package chitfund.wayzontech.chitfund.chitfund.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import chitfund.wayzontech.chitfund.chitfund.R;
import chitfund.wayzontech.chitfund.chitfund.httpHelper.URLs;
import chitfund.wayzontech.chitfund.chitfund.session.SessionManager;
import chitfund.wayzontech.chitfund.chitfund.volley.VolleySingleton;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtOldPassword,edtNewPassword,edtConfirmPassword;
    private Button btnChangePassword;
    private ProgressDialog progressDialog;
    private SessionManager sessionManager;
    private String strUsername,strOldPassword,strNewPassword,strConfirmPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        if (getSupportActionBar()!=null)
        {
            //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Change Password");
        }
        initController();
    }

    private void initController()
    {
        edtOldPassword = findViewById(R.id.oldPassword);
        edtNewPassword =findViewById(R.id.newPassword);
        edtConfirmPassword = findViewById(R.id.confirmPassword);
        sessionManager = new SessionManager(this);

        btnChangePassword = findViewById(R.id.btnChangePassword);

        btnChangePassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnChangePassword:
                changeOneTimePassword();
                break;
        }
    }
    private void changeOneTimePassword()
    {
        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setMessage("Please wait....!");
        progressDialog.setCancelable(true);

        strOldPassword = edtOldPassword.getText().toString();
        strNewPassword = edtNewPassword.getText().toString();
        strConfirmPassword = edtConfirmPassword.getText().toString();

        if (strOldPassword.equals(""))
        {
            Toast.makeText(ChangePasswordActivity.this,"Please enter old password",Toast.LENGTH_SHORT).show();
            return;
        }
        if (strNewPassword.equals(""))
        {
            Toast.makeText(ChangePasswordActivity.this,"Please enter new password",Toast.LENGTH_SHORT).show();
            return;
        }
        if (strConfirmPassword.equals(""))
        {
            Toast.makeText(ChangePasswordActivity.this,"Please enter confirm password",Toast.LENGTH_SHORT).show();
            return;
        }
        if (!strNewPassword.equals(strConfirmPassword))
        {
            Toast.makeText(ChangePasswordActivity.this,"Password does not matched",Toast.LENGTH_SHORT).show();
            edtNewPassword.setText("");
            edtConfirmPassword.setText("");
            edtNewPassword.requestFocus();
            edtConfirmPassword.requestFocus();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.CHANGE_PASS_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("success").equals("1"))
                            {
                                progressDialog.dismiss();
                                Toast.makeText(ChangePasswordActivity.this,jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ChangePasswordActivity.this,LoginActivity.class));
                                finish();
                            }
                            else
                                progressDialog.dismiss();
                                Toast.makeText(ChangePasswordActivity.this,jsonObject.getString("message"),Toast.LENGTH_SHORT).show();


                        } catch (JSONException e)
                        {
                            progressDialog.dismiss();
                            e.printStackTrace();
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
                Map<String, String> map = new HashMap<>();
                map.put("userid",sessionManager.getUserID());
                map.put("oldpassword",strOldPassword);
                map.put("newpassword",strNewPassword);
                return map;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}

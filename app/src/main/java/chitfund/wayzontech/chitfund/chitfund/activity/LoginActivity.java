package chitfund.wayzontech.chitfund.chitfund.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
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
import chitfund.wayzontech.chitfund.chitfund.model.UserLogin;
import chitfund.wayzontech.chitfund.chitfund.session.SessionManager;
import chitfund.wayzontech.chitfund.chitfund.volley.VolleySingleton;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button_register,button_login;
    private EditText editText_username,editText_password;
    private String userid,strUsername,strPassword,member_id;
    private ProgressDialog progressDialog;
    private SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initLogin();
    }

    private void initLogin()
    {
        button_login = findViewById(R.id.btn_login);
        button_register = findViewById(R.id.btn_register);
        editText_username = findViewById(R.id.edt_userid);
        editText_password = findViewById(R.id.edt_password);
        sessionManager = new SessionManager(this);
        button_register.setOnClickListener(this);
        button_login.setOnClickListener(this);
        if (SessionManager.getInstance(LoginActivity.this).isLoggedIn()) {
            finish();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_login:
                login();
                break;
            case R.id.btn_register:
                startActivity(new Intent(this,RegistrationActivity.class));
                break;
        }
    }

    private void login() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait.....!");
        progressDialog.show();
        progressDialog.setCancelable(true);
        strUsername = editText_username.getText().toString();
        strPassword = editText_password.getText().toString();
        if (strUsername.equals(""))
        {
            editText_username.setError("Please enter your username");
            return;
        }
        if (strPassword.equals(""))
        {
            editText_password.setError("Please enter your password");
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("success").equals("1"))
                            {
                                if (jsonObject.getString("password_change_status").equals("1"))
                                {
                                    progressDialog.dismiss();
                                    //Toast.makeText(LoginActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                    userid = jsonObject.getString("user_id");
                                    member_id = jsonObject.getString("member_id");
                                    UserLogin userLogin = new UserLogin(userid, strUsername, strPassword,member_id);
                                    sessionManager.userLogin(userLogin);
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    //finish();
                                }
                                if (jsonObject.getString("password_change_status").equals("0"))
                                {
                                    progressDialog.dismiss();
                                    //Toast.makeText(LoginActivity.this, jsonObject.getString("message1"), Toast.LENGTH_SHORT).show();
                                    userid = jsonObject.getString("user_id");
                                    UserLogin userLogin = new UserLogin(userid);
                                    sessionManager.setUserId(userLogin);
                                    startActivity(new Intent(LoginActivity.this, ChangePasswordActivity.class));
                                    //finish();
                                }
                            }

                            if (jsonObject.getString("success").equals("0"))
                            {
                                progressDialog.dismiss();
                                Toast.makeText(LoginActivity.this,jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                                editText_username.setText("");
                                editText_password.setText("");

                            }
                            if (jsonObject.getString("success").equals("2"))
                            {
                                progressDialog.dismiss();
                                Toast.makeText(LoginActivity.this,jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                                editText_username.setText("");
                                editText_password.setText("");
                            }
                            else
                                progressDialog.dismiss();
                                Toast.makeText(LoginActivity.this,jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                                editText_username.setText("");
                                editText_password.setText("");

                        } catch (JSONException e) {
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
                Map<String, String> params = new HashMap<>();
                params.put("username",strUsername);
                params.put("password",strPassword);
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    // onBack pressed
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click back again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}

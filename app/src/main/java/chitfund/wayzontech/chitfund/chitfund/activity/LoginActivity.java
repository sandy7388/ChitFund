package chitfund.wayzontech.chitfund.chitfund.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
import chitfund.wayzontech.chitfund.chitfund.model.AgentLogin;
import chitfund.wayzontech.chitfund.chitfund.model.MemberLogin;
import chitfund.wayzontech.chitfund.chitfund.session.AgentSession;
import chitfund.wayzontech.chitfund.chitfund.session.MemberSession;
import chitfund.wayzontech.chitfund.chitfund.volley.VolleySingleton;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button_register,button_login;
    private EditText editText_username,editText_password;
    private String userid,strUsername,strPassword,member_id,strId,strName,strSubDomain;
    private ProgressDialog progressDialog;
    private MemberSession memberSession;
    private AgentSession agentSession;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (getSupportActionBar()!=null)
        {
            getSupportActionBar().setTitle("Login");
        }
        initLogin();
        //checkLoginFromServerSide();
    }

    private void initLogin()
    {
        button_login = findViewById(R.id.btn_login);
        button_register = findViewById(R.id.btn_register);
        editText_username = findViewById(R.id.edt_userid);
        editText_password = findViewById(R.id.edt_password);
        memberSession = new MemberSession(this);
        agentSession = new AgentSession(this);
        button_register.setOnClickListener(this);
        button_login.setOnClickListener(this);

        // Check either login or not
        if(MemberSession.getInstance(this).isLoggedIn())
        {
            finish();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            return;
        }
        if (AgentSession.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(LoginActivity.this, AgentActivity.class));
            return;
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
                                if (jsonObject.getString("role_id").equals("3"))
                                {
                                    strId = jsonObject.getString("user_id");
                                    strName = jsonObject.getString("uname");
                                    strSubDomain = jsonObject.getString("subdomain");
                                    MemberLogin memberLogin = new MemberLogin();
                                    memberLogin.setId(strId);
                                    memberLogin.setUsername(strName);
                                    memberLogin.setPassword(strPassword);
                                    memberLogin.setSubdomain(strSubDomain);
                                    memberSession.userLogin(memberLogin);
                                    URLs urLs = new URLs();
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    finish();
                                }

                                if (jsonObject.getString("role_id").equals("4"))
                                {
                                    strId = jsonObject.getString("user_id");
                                    strName = jsonObject.getString("uname");
                                    strSubDomain = jsonObject.getString("subdomain");
                                    AgentLogin agentLogin = new AgentLogin();
                                    agentLogin.setId(strId);
                                    agentLogin.setUsername(strName);
                                    agentLogin.setPassword(strPassword);
                                    agentLogin.setSubdomain(strSubDomain);
                                    agentSession.userLogin(agentLogin);
                                    startActivity(new Intent(LoginActivity.this, AgentActivity.class));
                                    finish();
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
            protected Map<String, String> getParams() {
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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.register, menu);
//
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        switch (id) {
//            case R.id.action_register:
//                startActivity(new Intent(this,RegistrationActivity.class));
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }

}

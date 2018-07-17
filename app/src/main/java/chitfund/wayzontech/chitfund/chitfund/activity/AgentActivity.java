package chitfund.wayzontech.chitfund.chitfund.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import chitfund.wayzontech.chitfund.chitfund.R;
import chitfund.wayzontech.chitfund.chitfund.httpHelper.URLs;
import chitfund.wayzontech.chitfund.chitfund.session.AgentSession;
import chitfund.wayzontech.chitfund.chitfund.volley.VolleySingleton;

public class AgentActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private AgentSession agentSession;
    private Spinner spinnerGroupName, spinnerMemberName;
    private String strGroupName, strMemberName;
    private ArrayList<String> groupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent);
        controller();

    }

    void controller() {
        spinnerGroupName = findViewById(R.id.spinnerGrpNameAgent);
        spinnerMemberName = findViewById(R.id.spinnerMbrNameAgent);
        agentSession = new AgentSession(this);
        spinnerGroupName.setOnItemSelectedListener(this);
        spinnerGroupName.setOnItemSelectedListener(this);
        groupName = new ArrayList<>();
        if (!AgentSession.getInstance(this).isLoggedIn()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }

    void getGroupName() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.GROUP_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

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
                return param;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

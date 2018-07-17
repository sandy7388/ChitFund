package chitfund.wayzontech.chitfund.chitfund.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import chitfund.wayzontech.chitfund.chitfund.R;
import chitfund.wayzontech.chitfund.chitfund.session.AgentSession;

public class AgentActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private AgentSession agentSession;
    private Spinner spinnerGroupName, spinnerMemberName;
    private String strGroupNameJSON, strGroupName, strGroupIdJSON, strGroupId, strMemberName, strMemberId;
    private ArrayList<String> groupName;
    private JSONArray groupArray;
    private JSONObject serverResponse, groupNameObject;
    private ProgressDialog progressDialog;
    private String strRoleId = String.valueOf(4);

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

        try {
            //getGroupName();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

//    void getGroupName() {
//
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.AGENT_GROUP_URL,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//
//                            if (jsonObject.getString("success").equals("1"))
//                            {
//                                JSONArray jsonArray = jsonObject.getJSONArray("Agent_info");
//
//                                for (int i=0;i<jsonArray.length();i++)
//                                {
//                                    JSONObject object = jsonArray.getJSONObject(i);
//
//                                    strGroupNameJSON = object.getString("group_name");
//                                    strGroupIdJSON = object.getString("group_id");
//
//                                    groupName.add(strGroupNameJSON);
//                                }
//
//                                spinnerGroupName.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, groupName));
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        })
//        {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> param = new HashMap<>();
//                param.put("agent_id",agentSession.getUserID());
//                param.put("role_id",strRoleId);
//                return param;
//            }
//        };
//
//        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
//
//    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

//        Spinner spinner = (Spinner) parent;
//        strGroupName = spinner.getAdapter().getItem(position).toString();
//
//        switch (spinner.getId()) {
//            case R.id.spinnerGrpNameAgent:
//
//        }
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
                //androidLogout();
                AgentSession.getInstance(AgentActivity.this).logout();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

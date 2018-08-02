package chitfund.wayzontech.chitfund.chitfund.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import chitfund.wayzontech.chitfund.chitfund.R;
import chitfund.wayzontech.chitfund.chitfund.adapter.SubdomainAdapter;
import chitfund.wayzontech.chitfund.chitfund.model.UserProfile;
import chitfund.wayzontech.chitfund.chitfund.other.MyDividerItemDecoration;
import chitfund.wayzontech.chitfund.chitfund.session.MemberSession;
import chitfund.wayzontech.chitfund.chitfund.session.SubdomainSession;
import chitfund.wayzontech.chitfund.chitfund.sqliteHelper.DatabaseHelper;

public class SubdomainActivity extends AppCompatActivity {

    CustomDialogClass customDialogClass;
    private SubdomainAdapter mAdapter;
    private List<UserProfile> userProfileArrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private DatabaseHelper db;
    private SubdomainSession subdomainSession;
    private DatabaseHelper databaseHelper;
    private Button yes, no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subdomain);
        //gridView = findViewById(R.id.gridView);
        db = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.recyclerViewSubdomain);
        initialization();

        if (SubdomainSession.getInstance(SubdomainActivity.this).isLoggedIn()) {
            finish();
            startActivity(new Intent(SubdomainActivity.this, MainActivity.class));

        }
        databaseHelper = new DatabaseHelper(this);
        customDialogClass = new CustomDialogClass(this);
    }

    void initialization() {
        subdomainSession = new SubdomainSession(this);
        userProfileArrayList.addAll(db.getSubdomain());
        mAdapter = new SubdomainAdapter(userProfileArrayList, this, subdomainSession);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, GridLayoutManager.VERTICAL, 16));
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.subdomain_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_logout:
//                SessionManager.getInstance(this).logout();
//                finish();
                //alertForLogout();
                customDialogClass.show();
                break;
//            case R.id.action_marathi:
//                setLocale("mr");
//                break;
//            case R.id.action_english:
//                setLocale("en");
//                break;
//            case R.id.action_other_subdomain:
//                SubdomainSession.getInstance(this).logout();
//                finish();
//                break;

        }

        return super.onOptionsItemSelected(item);
    }

    void alertForLogout() {
        AlertDialog.Builder aBuilder = new AlertDialog.Builder(this);

        aBuilder.setMessage("Are you sure, You want to Logout");
        aBuilder.setTitle("Logout Alert");
        aBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MemberSession.getInstance(SubdomainActivity.this).logout();
                SubdomainSession.getInstance(SubdomainActivity.this).clearSession();
                databaseHelper.deleteAll();
                finish();
            }
        });
        aBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = aBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    private class CustomDialogClass extends Dialog implements View.OnClickListener {

        public CustomDialogClass(Context context) {
            super(context);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.custom_dialog);
            yes = findViewById(R.id.btn_yes);
            no = findViewById(R.id.btn_no);
            yes.setOnClickListener(this);
            no.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_yes:
                    MemberSession.getInstance(SubdomainActivity.this).logout();
                    SubdomainSession.getInstance(SubdomainActivity.this).clearSession();
                    databaseHelper.deleteAll();
                    finish();
                    break;
                case R.id.btn_no:
                    dismiss();
                    break;
                default:
                    break;
            }
            dismiss();
        }
    }
}

package chitfund.wayzontech.chitfund.chitfund.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import chitfund.wayzontech.chitfund.chitfund.R;

public class GroupInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_info);
        if (getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}

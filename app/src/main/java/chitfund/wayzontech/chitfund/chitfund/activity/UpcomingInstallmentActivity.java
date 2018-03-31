package chitfund.wayzontech.chitfund.chitfund.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import chitfund.wayzontech.chitfund.chitfund.R;

public class UpcomingInstallmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_installment);

        if (getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Upcoming Installment Report");
        }
    }
}

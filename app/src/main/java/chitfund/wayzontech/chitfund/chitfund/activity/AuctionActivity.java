package chitfund.wayzontech.chitfund.chitfund.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import chitfund.wayzontech.chitfund.chitfund.R;
import chitfund.wayzontech.chitfund.chitfund.httpHelper.URLs;
import chitfund.wayzontech.chitfund.chitfund.session.SubdomainSession;

public class AuctionActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txtAmount,txtGroupId;
    private Button buttonAuction;
    private String strAmount,strGroupId;
    public static final String WEBVIEW_URL = "realtimeauction/webviewTimer";
    private WebView webView;
    private SubdomainSession session;
    //private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auction);

        txtAmount = findViewById(R.id.textViewAuctionAmount);
        txtGroupId = findViewById(R.id.textViewAuctionId);
        webView = findViewById(R.id.webView);
        buttonAuction = findViewById(R.id.buttonAuction);
        //swipeRefreshLayout = findViewById(R.id.swipeToRefreshAuction);

        if (getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        session = new SubdomainSession(this);

        Intent intent = this.getIntent();
        strAmount = intent.getExtras().getString("KEY_AMOUNT");
        strGroupId = intent.getExtras().getString("KEY_ID");

        txtAmount.setText(strAmount);
        txtGroupId.setText(strGroupId);

        buttonAuction.setOnClickListener(this);

        //swipeRefreshLayout.setOnRefreshListener(this);
        controller();
        //handler.post(timedTask);
    }

    private void controller()
    {
        //webView = view.findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.loadUrl("http://" + session.getSubDomain() + URLs.BASE_URL + WEBVIEW_URL + "?groupid=" + strGroupId + "&user_id=" + session.getUserID());
        webView.setWebViewClient(new MyWebViewClient());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.buttonAuction:
                //realTimeAuction();
                controller();
                //handler.post(timedTask);
                break;
        }
    }


    private class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return false;
        }
    }

//    Handler handler = new Handler();
//    Runnable timedTask = new Runnable(){
//
//        @Override
//        public void run() {
//            webView.reload();
//            handler.postDelayed(timedTask, 15000);
//        }
//    };

//    private void timer()
//    {
//        new CountDownTimer(1800000, 1000) { // adjust the milli seconds here
//
//            public void onTick(long millisUntilFinished) {
//                textView.setEnabled(true);
//                //_tv.setText(""+String.format("%d min, %d sec",
//                long minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished);
//                millisUntilFinished -= TimeUnit.MINUTES.toMillis(minutes);
//
//                long seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished);
//                //textview.setText(minutes + "m" + ":" + seconds + "s"); //You can compute the millisUntilFinished on hours/minutes/seconds
//            }
//
//            public void onFinish() {
//                //_tv.setText("done!");
//                textview.setText("Auction Completed");
//            }
//        }.start();
//    }

    long timer = 1800000;

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu, menu);

        final MenuItem counter = menu.findItem(R.id.counter);
        new CountDownTimer(timer, 1000) {

            public void onTick(long millisUntilFinished) {
                long millis = millisUntilFinished;
                String hms = (TimeUnit.MILLISECONDS.toHours(millis)) + ":" + (TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis))) + ":" + (TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));

                counter.setTitle(hms);
                timer = millis;

            }

            public void onFinish() {
                counter.setTitle("done!");
            }
        }.start();

        return true;

    }



}

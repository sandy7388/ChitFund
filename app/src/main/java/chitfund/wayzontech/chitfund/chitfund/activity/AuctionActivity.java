package chitfund.wayzontech.chitfund.chitfund.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

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

}

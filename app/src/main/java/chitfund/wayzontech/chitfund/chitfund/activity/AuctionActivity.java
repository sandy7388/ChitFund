package chitfund.wayzontech.chitfund.chitfund.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import chitfund.wayzontech.chitfund.chitfund.R;
import chitfund.wayzontech.chitfund.chitfund.httpHelper.URLs;
import chitfund.wayzontech.chitfund.chitfund.session.MemberSession;

public class AuctionActivity extends AppCompatActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private TextView txtAmount,txtGroupId;
    private Button buttonAuction;
    private String strAmount,strGroupId;
    private MemberSession session;
    private WebView webView;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auction);

        txtAmount = findViewById(R.id.textViewAuctionAmount);
        txtGroupId = findViewById(R.id.textViewAuctionId);
        webView = findViewById(R.id.webView);
        buttonAuction = findViewById(R.id.buttonAuction);
        swipeRefreshLayout = findViewById(R.id.swipeToRefreshAuction);

        if (getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        session = new MemberSession(this);

        Intent intent = this.getIntent();
        strAmount = intent.getExtras().getString("KEY_AMOUNT");
        strGroupId = intent.getExtras().getString("KEY_ID");

        txtAmount.setText(strAmount);
        txtGroupId.setText(strGroupId);

        buttonAuction.setOnClickListener(this);

        swipeRefreshLayout.setOnRefreshListener(this);
        controller();
        //handler.post(timedTask);
    }

    private void controller()
    {
        //webView = view.findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.loadUrl(URLs.WEBVIEW_URL+"?groupid="+strGroupId +"&user_id="+session.getUserID());
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

    @Override
    public void onRefresh() {

        webView.reload();
        swipeRefreshLayout.setRefreshing(false);

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

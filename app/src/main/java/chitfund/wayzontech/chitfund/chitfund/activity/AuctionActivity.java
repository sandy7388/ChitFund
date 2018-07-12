package chitfund.wayzontech.chitfund.chitfund.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import chitfund.wayzontech.chitfund.chitfund.R;
import chitfund.wayzontech.chitfund.chitfund.fragment.AuctionFragment;
import chitfund.wayzontech.chitfund.chitfund.httpHelper.URLs;
import chitfund.wayzontech.chitfund.chitfund.session.SessionManager;
import chitfund.wayzontech.chitfund.chitfund.volley.VolleySingleton;

public class AuctionActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txtAmount,txtGroupId;
    private Button buttonAuction;
    private String strAmount,strGroupId;
    private SessionManager session;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auction);

        txtAmount = findViewById(R.id.textViewAuctionAmount);
        txtGroupId = findViewById(R.id.textViewAuctionId);
        webView = findViewById(R.id.webView);
        buttonAuction = findViewById(R.id.buttonAuction);

        if (getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        session = new SessionManager(this);

        Intent intent = this.getIntent();
        strAmount = intent.getExtras().getString("KEY_AMOUNT");
        strGroupId = intent.getExtras().getString("KEY_ID");

        txtAmount.setText(strAmount);
        txtGroupId.setText(strGroupId);

        buttonAuction.setOnClickListener(this);
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

}

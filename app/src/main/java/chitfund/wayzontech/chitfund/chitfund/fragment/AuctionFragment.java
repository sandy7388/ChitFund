package chitfund.wayzontech.chitfund.chitfund.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import chitfund.wayzontech.chitfund.chitfund.R;
import chitfund.wayzontech.chitfund.chitfund.httpHelper.URLs;
import chitfund.wayzontech.chitfund.chitfund.session.SubdomainSession;

/**
 * Created by sandy on 12/3/18.
 */

public class AuctionFragment extends Fragment  {
    public static final String WEBVIEW_URL = "realtimeauction/webviewTimer";
    private WebView webView;
    private TextView txtAmount, txtGroupId;
    private String strAmount, strGroupId;
    private SubdomainSession session;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_auction,
                container, false);
        getActivity().setTitle("Auction");
        controller(view);
        return view;

    }

    private void controller(View view)
    {

        webView = view.findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.loadUrl("http://" + session.getSubDomain() + URLs.BASE_URL + WEBVIEW_URL + "?groupid=" + strGroupId + "&user_id=" + session.getUserID());

        webView.setWebViewClient(new MyWebViewClient());
    }

    private class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return false;
        }
    }
}

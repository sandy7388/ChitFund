package chitfund.wayzontech.chitfund.chitfund.application;

import android.app.Application;

import chitfund.wayzontech.chitfund.chitfund.receiverNservices.ConnectivityReceiver;

public class MyApplication extends Application {

    private static MyApplication mInstance;

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }
}

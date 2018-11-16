package net.gree.unitywebview;

import com.unity3d.player.*;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;

import im.delight.android.webview.AdvancedWebView;

public class CUnityPlayerActivity
    extends UnityPlayerActivity
{
    public static ValueCallback uploadMsg;
    public static final int FILECHOOSER_RESULTCODE = 100;
    public static final int REQUEST_SELECT_FILE = 101;
    private WVListener wvListener;

    @Override
    public void onCreate(Bundle bundle) {
        requestWindowFeature(1);
        super.onCreate(bundle);
        getWindow().setFormat(2);
        mUnityPlayer = new CUnityPlayer(this);
        setContentView(mUnityPlayer);
        mUnityPlayer.requestFocus();
    }

    @SuppressLint("NewApi")
    @Override
    public void onResume() {
        super.onResume();
        if(wvListener != null)
            wvListener.onResume();
    }

    @SuppressLint("NewApi")
    @Override
    public void onPause() {
        if(wvListener != null)
            wvListener.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        if(wvListener != null)
            wvListener.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.w("unity-weview","onActivityResult");
		if(wvListener != null)
            wvListener.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }


    public void setCustomListeners(WVListener l) {
		Log.w("unity-weview","setCustomListeners");
        wvListener = l;
    }
}

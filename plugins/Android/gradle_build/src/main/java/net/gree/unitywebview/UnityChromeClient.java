package net.gree.unitywebview;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.unity3d.player.UnityPlayer;

public class UnityChromeClient extends WebChromeClient {
    View videoView;
    private int progress;
    private FrameLayout layout;

    public UnityChromeClient(FrameLayout layout){
        super();
        this.layout = layout;
    }

    protected void openFileChooser(ValueCallback<Uri> uploadMsg)
    {
        CUnityPlayerActivity.uploadMsg = uploadMsg;
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("*/*");
        UnityPlayer.currentActivity.startActivityForResult(Intent.createChooser(i, "File Chooser"), CUnityPlayerActivity.FILECHOOSER_RESULTCODE);
    }

    // For Lollipop 5.0+ Devices
    public boolean onShowFileChooser(WebView mWebView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
        Log.d("unity-webview", "onShowFileChooser");

        if (CUnityPlayerActivity.uploadMsg != null) {
            CUnityPlayerActivity.uploadMsg.onReceiveValue(null);
            CUnityPlayerActivity.uploadMsg = null;
        }

        CUnityPlayerActivity.uploadMsg = filePathCallback;
        Intent intent = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            intent = fileChooserParams.createIntent();
        }

        try {
            Log.d("unity-webview", "trying to launch file selection activity");
            UnityPlayer.currentActivity.startActivityForResult(intent, CUnityPlayerActivity.REQUEST_SELECT_FILE);
        } catch (ActivityNotFoundException e) {
            CUnityPlayerActivity.uploadMsg = null;
            Toast.makeText(UnityPlayer.currentActivity.getApplicationContext(), "Cannot Open File Chooser", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        progress = newProgress;
    }

    @Override
    public void onShowCustomView(View view, CustomViewCallback callback) {
        super.onShowCustomView(view, callback);
        if (layout != null) {
            videoView = view;
            layout.setBackgroundColor(0xff000000);
            layout.addView(videoView);
        }
    }

    @Override
    public void onHideCustomView() {
        super.onHideCustomView();
        if (layout != null) {
            layout.removeView(videoView);
            layout.setBackgroundColor(0x00000000);
            videoView = null;
        }
    }
}

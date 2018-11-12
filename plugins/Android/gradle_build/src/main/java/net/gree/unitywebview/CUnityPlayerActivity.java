package net.gree.unitywebview;

import com.unity3d.player.*;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;

public class CUnityPlayerActivity
    extends UnityPlayerActivity
{
    public static ValueCallback uploadMsg;
    public static final int FILECHOOSER_RESULTCODE = 100;
    public static final int REQUEST_SELECT_FILE = 101;

    @Override
    public void onCreate(Bundle bundle) {
        requestWindowFeature(1);
        super.onCreate(bundle);
        getWindow().setFormat(2);
        mUnityPlayer = new CUnityPlayer(this);
        setContentView(mUnityPlayer);
        mUnityPlayer.requestFocus();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("unity-webview", "onActivityResult "+resultCode);

        if (requestCode == REQUEST_SELECT_FILE) {
            if (uploadMsg == null) return;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Log.d("unity-webview", "calling onReceiveValue (1)");
                uploadMsg.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode, data));
            }
            uploadMsg = null;
        } else if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == uploadMsg)
                return;
            // Use MainActivity.RESULT_OK if you're implementing WebView inside Fragment
            // Use RESULT_OK only if you're implementing WebView inside an Activity
            Uri result = data == null || resultCode != CUnityPlayerActivity.RESULT_OK ? null : data.getData();
            Log.d("unity-webview", "calling onReceiveValue: "+result.toString());
            uploadMsg.onReceiveValue(result);
            uploadMsg = null;
        }
    }













}

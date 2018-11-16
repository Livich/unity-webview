package net.gree.unitywebview;

import android.content.Intent;

interface WVListener {
    public void onActivityResult(int requestCode, int resultCode, Intent intent);
    public void onPause();
    public void onResume();
    public void onDestroy();
}

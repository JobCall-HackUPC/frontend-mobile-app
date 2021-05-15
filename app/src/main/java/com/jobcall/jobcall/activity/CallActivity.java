package com.jobcall.jobcall.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JsResult;
import android.webkit.PermissionRequest;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jobcall.jobcall.R;

public class CallActivity extends AppCompatActivity {
    WebView webView;
    private PermissionRequest myRequest;
    private static final int MY_PERMISSIONS_REQUEST_RECORD_AUDIO = 101;
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 102;

    FloatingActionButton button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        webView = findViewById(R.id.webView);
        button = findViewById(R.id.answerButton);
        button.setOnClickListener((view) -> joinCall());
    }
    protected class SSLTolerentWebViewClient extends WebViewClient {
        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed(); // Ignore SSL certificate errors
        }
    }
    private void joinCall(){
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.setWebViewClient(new WebViewClient());

        webView.getSettings().setSaveFormData(true);
        webView.getSettings().setSupportZoom(false);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                result.confirm();
                return true;

            }

            @Override
            public void onPermissionRequest(final PermissionRequest request) {
                myRequest = request;
                for (String permission : request.getResources()) {
                    switch (permission) {
                        case "android.webkit.resource.AUDIO_CAPTURE": {
                            askForPermission(request.getOrigin().toString(), Manifest.permission.RECORD_AUDIO, MY_PERMISSIONS_REQUEST_RECORD_AUDIO);
                            break;
                        }
                        case "android.webkit.resource.VIDEO_CAPTURE": {
                            askForPermission(request.getOrigin().toString(), Manifest.permission.CAMERA, MY_PERMISSIONS_REQUEST_CAMERA);
                            break;
                        }
                    }
                }
            }
        });
        webView.loadUrl("https://latra:8080/");

    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_RECORD_AUDIO: {
                Log.d("WebView", "PERMISSION FOR AUDIO");
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    myRequest.grant(myRequest.getResources());
                    webView.loadUrl("https://latra:8080/");

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
            }
            // other 'case' lines to check for other
            // permissions this app might rezquest
        }
    }

    public void askForPermission(String origin, String permission, int requestCode) {
        Log.d("WebView", "inside askForPermission for" + origin + "with" + permission);

        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                permission)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(CallActivity.this,
                    permission)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(CallActivity.this,
                        new String[]{permission},
                        requestCode);
            }
        } else {
            myRequest.grant(myRequest.getResources());
        }
    }
}
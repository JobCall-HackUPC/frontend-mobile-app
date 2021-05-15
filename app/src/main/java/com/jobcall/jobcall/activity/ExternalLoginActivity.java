package com.jobcall.jobcall.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jobcall.jobcall.R;
import com.jobcall.jobcall.utils.Constants;

public class ExternalLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stack);
        WebView webView = findViewById(R.id.loginWebView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setInitialScale(1);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setMinimumFontSize(40);
        webView.clearCache(true);
        /*CookieSyncManager.createInstance(this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();*/
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.setWebViewClient( new WebViewClient() {
                    @Override
                    public void onPageFinished(WebView view, String url) {
                        Log.d("URL", url);
                        if (url.contains(Constants.SERVER_URL)){

                        }
                    }
                }
        );
        webView.loadUrl(this.getIntent().getStringExtra("url"));
    }
}
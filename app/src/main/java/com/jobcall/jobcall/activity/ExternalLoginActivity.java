package com.jobcall.jobcall.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
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
        // Clear all the cookies
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.setWebViewClient(new WebViewClient() {
                                     @Override
                                     public void onPageStarted(WebView view, String url, Bitmap favicon) {
                                         super.onPageStarted(view, url, favicon);
                                         Log.d("URL", url);
                                         if (url.contains(Constants.SERVER_URL + Constants.STACK_SIGNUP) ||
                                                 url.contains(Constants.SERVER_URL + Constants.GIT_SIGNUP)) {
                                             Log.d("END", url);
                                             finish();
                                         }
                                     }
                                 }
        );
        webView.loadUrl(this.getIntent().getStringExtra("url"));
    }
}
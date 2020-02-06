package com.f2mate.fbdownloader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.nio.file.WatchEvent;

public class WebActivity extends AppCompatActivity {
    private SwipeRefreshLayout recyclerLayout;
    private WebView webo;
    private ProgressBar progress;
    String URL = "https://www.f2mate.com";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        webo = findViewById(R.id.webViewBrowser);
        progress = findViewById(R.id.progressBarBrowser);
        progress.setVisibility(View.GONE);
        
        browser();
        initComponents();
        
        
        }
    @Override
    public void onBackPressed() {
        if(webo.canGoBack())
        {
            webo.goBack();
        }else {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }
    }
    public void setValue(int progress) {
       this.progress.setProgress(progress);
        if (progress>=100) // code to be added
           this.progress.setVisibility(View.GONE);
    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater=getMenuInflater();
//        inflater.inflate(R.menu.menu_main, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item)
//    {
//       if(item.getItemId()==R.id.action_refresh){
//           initComponents();
//       }
//        return true;
//    }

    @SuppressLint("JavascriptInterface")
    public void browser(){
        webo.getSettings().setJavaScriptEnabled(true);
        webo.addJavascriptInterface(this, "F2mate");
        webo.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
               progress.setVisibility(View.VISIBLE);
               WebActivity.this.setValue(newProgress);
                super.onProgressChanged(view, newProgress);
            }
        });
        webo.setWebViewClient(new WebViewClient() {



            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                progress.setVisibility(View.VISIBLE);
//                MainActivity.this.progress.setProgress(0);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                WebActivity.this.webo.loadUrl("javascript:(function() { var el = document.querySelectorAll('div[data-sigil]');for(var i=0;i<el.length; i++){var sigil = el[i].dataset.sigil;if(sigil.indexOf('inlineVideo') > -1){delete el[i].dataset.sigil;var jsonData = JSON.parse(el[i].dataset.store);el[i].setAttribute('onClick', 'F2mate.processVideo(\"'+jsonData['src']+'\");');}}})()");
                Log.e("WEBVIEWFIN", url);
                progress.setVisibility(View.GONE);
//                MainActivity.this.progress.setProgress(100);
                super.onPageFinished(view, url);
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                WebActivity.this.webo.loadUrl("javascript:(function prepareVideo() { var el = document.querySelectorAll('div[data-sigil]');for(var i=0;i<el.length; i++){var sigil = el[i].dataset.sigil;if(sigil.indexOf('inlineVideo') > -1){delete el[i].dataset.sigil;console.log(i);var jsonData = JSON.parse(el[i].dataset.store);el[i].setAttribute('onClick', 'F2mate.processVideo(\"'+jsonData['src']+'\",\"'+jsonData['videoID']+'\");');}}})()");
                WebActivity.this.webo.loadUrl("javascript:( window.onload=prepareVideo;)()");
            }
        });
        CookieManager cookieManager = CookieManager.getInstance();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            CookieSyncManager.createInstance(this);
        }
        cookieManager.setAcceptCookie(true);
        webo.loadUrl(URL);
    }
    private void initComponents() {

        recyclerLayout = (SwipeRefreshLayout) findViewById(R.id.browserSwipeRefresh);
        recyclerLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recyclerLayout.setRefreshing(true);
                browser();
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        recyclerLayout.setRefreshing(false);
                        Toast.makeText(WebActivity.this, "Refreshed!", Toast.LENGTH_SHORT).show();
                    }
                }, 2000);

            }
        });
    }

    }


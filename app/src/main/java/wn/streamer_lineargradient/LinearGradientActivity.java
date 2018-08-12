package wn.streamer_lineargradient;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AbsoluteLayout;

public class LinearGradientActivity extends AppCompatActivity {

    private WebView webView ;
    private LinearGradientView linearGradientView ;
    private AbsoluteLayout absoluteLayout ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        webView = (WebView)findViewById(R.id.web_view);
        webView.loadUrl("https://www.cnblogs.com/huolongluo/p/6071103.html");

        absoluteLayout = new AbsoluteLayout(this);
        AbsoluteLayout.LayoutParams lp = new AbsoluteLayout.LayoutParams(
                AbsoluteLayout.LayoutParams.MATCH_PARENT,
                AbsoluteLayout.LayoutParams.MATCH_PARENT,
                0,
                0);
        AbsoluteLayout.LayoutParams lpView = new AbsoluteLayout.LayoutParams(
                AbsoluteLayout.LayoutParams.WRAP_CONTENT,
                AbsoluteLayout.LayoutParams.WRAP_CONTENT,
                width/2-150,
                height/2-48);
        linearGradientView = new LinearGradientView(this);
        linearGradientView.setLayoutParams(lpView);
        absoluteLayout.addView(linearGradientView);
        absoluteLayout.setBackgroundColor(Color.BLACK);
        absoluteLayout.setLayoutParams(lp);
        webView.addView(absoluteLayout);
        initWebViewOtherConfigution();


    }

    private void initWebViewOtherConfigution(){
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                //newProgress不是匀速从1增加到100，onProgressChanged调用次数未知
                if (newProgress >= 100){
                    absoluteLayout.setVisibility(View.INVISIBLE);
                    //absoluteLayout.setVisibility(View.GONE);
                    linearGradientView.stopAnimation();
                    webView.removeView(absoluteLayout);
                }
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public void onShowCustomView(View view, CustomViewCallback callback) {
                super.onShowCustomView(view, callback);
            }
        });
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

            }



            @Override
            public void onPageFinished(WebView view, String url) {
                //网页渲染2-3之后才会被调用
                absoluteLayout.setVisibility(View.INVISIBLE);
                //absoluteLayout.setVisibility(View.GONE);
                linearGradientView.stopAnimation();
                webView.removeView(absoluteLayout);
                super.onPageFinished(view, url);


            }

            @Nullable
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                return super.shouldInterceptRequest(view, request);
            }

        });
    }

    private void initWebViewConfigution(){
        //声明WebSettings子类
        final WebSettings webSettings = webView.getSettings();

        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
        // 若加载的 html 里有JS 在执行动画等操作，会造成资源浪费（CPU、电量）
        // 在 onStop 和 onResume 里分别把 setJavaScriptEnabled() 给设置成 false 和 true 即可

        //支持插件
        //webSettings.setPluginState();
        //webSettings.setPluginsEnabled(true);

        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        //缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

        //其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
    }
}

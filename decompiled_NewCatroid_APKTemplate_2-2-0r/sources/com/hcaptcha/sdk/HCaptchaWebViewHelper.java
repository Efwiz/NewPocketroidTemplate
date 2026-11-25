package com.hcaptcha.sdk;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.fasterxml.jackson.databind.ObjectMapper;

/* JADX INFO: compiled from: r8-map-id-071b77f92f575d77028d4852bce4d6e5f9d65cb60a14a6342bd357f80a49e8cf */
/* JADX INFO: loaded from: classes10.dex */
final class HCaptchaWebViewHelper {
    private final IHCaptchaVerifier captchaVerifier;
    private final HCaptchaConfig config;
    private final Context context;
    private final IHCaptchaHtmlProvider htmlProvider;
    private final HCaptchaWebView webView;

    /* JADX INFO: compiled from: r8-map-id-071b77f92f575d77028d4852bce4d6e5f9d65cb60a14a6342bd357f80a49e8cf */
    public class HCaptchaWebChromeClient extends WebChromeClient {
        private HCaptchaWebChromeClient() {
        }

        @Override // android.webkit.WebChromeClient
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            HCaptchaLog.d("[webview] onConsoleMessage " + consoleMessage.message());
            return true;
        }

        @Override // android.webkit.WebChromeClient
        public boolean onCreateWindow(WebView webView, boolean z, boolean z2, Message message) {
            if (z2) {
                try {
                    WebView.HitTestResult hitTestResult = webView.getHitTestResult();
                    if (hitTestResult.getType() == 7) {
                        Uri uri = Uri.parse(hitTestResult.getExtra());
                        Intent intent = new Intent("android.intent.action.VIEW", uri);
                        intent.addFlags(268435456);
                        HCaptchaWebViewHelper.this.context.startActivity(intent);
                        HCaptchaLog.d("[webview] opened target=_blank link in browser: " + uri);
                        return true;
                    }
                } catch (Exception e) {
                    HCaptchaLog.w("[webview] failed to open target=_blank link in browser: " + e.getMessage());
                }
            }
            return super.onCreateWindow(webView, z, z2, message);
        }

        @Override // android.webkit.WebChromeClient
        public void onProgressChanged(WebView webView, int i) {
            HCaptchaLog.d("[webview] onProgressChanged %d%%", Integer.valueOf(i));
        }
    }

    /* JADX INFO: compiled from: r8-map-id-071b77f92f575d77028d4852bce4d6e5f9d65cb60a14a6342bd357f80a49e8cf */
    public class HCaptchaWebClient extends WebViewClient {
        private final Handler handler;

        public HCaptchaWebClient(Handler handler) {
            if (handler == null) {
                throw new NullPointerException("handler is marked non-null but is null");
            }
            this.handler = handler;
        }

        private String stripUrl(String str) {
            return str.split("[?#]")[0] + "...";
        }

        /* JADX INFO: renamed from: lambda$shouldInterceptRequest$0$com-hcaptcha-sdk-HCaptchaWebViewHelper$HCaptchaWebClient, reason: not valid java name */
        /* synthetic */ void m2670x3e21ddd7(Uri uri) {
            HCaptchaWebViewHelper.this.webView.removeJavascriptInterface(HCaptchaJSInterface.JS_INTERFACE_TAG);
            HCaptchaWebViewHelper.this.webView.removeJavascriptInterface(HCaptchaDebugInfo.JS_INTERFACE_TAG);
            HCaptchaWebViewHelper.this.captchaVerifier.onFailure(new HCaptchaException(HCaptchaError.INSECURE_HTTP_REQUEST_ERROR, "Insecure resource " + uri + " requested"));
        }

        @Override // android.webkit.WebViewClient
        public void onLoadResource(WebView webView, String str) {
            HCaptchaLog.d("[webview] onLoadResource " + stripUrl(str));
        }

        @Override // android.webkit.WebViewClient
        public void onPageFinished(WebView webView, String str) {
            HCaptchaLog.d("[webview] onPageFinished " + stripUrl(str));
        }

        @Override // android.webkit.WebViewClient
        public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            HCaptchaLog.d("[webview] onPageStarted " + stripUrl(str));
        }

        @Override // android.webkit.WebViewClient
        public void onReceivedError(WebView webView, int i, String str, String str2) {
            super.onReceivedError(webView, i, str, str2);
            HCaptchaLog.d("[webview] onReceivedError \"%s\" (%d)", str, Integer.valueOf(i));
        }

        @Override // android.webkit.WebViewClient
        public WebResourceResponse shouldInterceptRequest(WebView webView, WebResourceRequest webResourceRequest) {
            final Uri url = webResourceRequest.getUrl();
            if (url != null && url.getScheme() != null && url.getScheme().equals("http")) {
                this.handler.post(new Runnable() { // from class: com.hcaptcha.sdk.HCaptchaWebViewHelper$HCaptchaWebClient$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m2670x3e21ddd7(url);
                    }
                });
            }
            return super.shouldInterceptRequest(webView, webResourceRequest);
        }

        @Override // android.webkit.WebViewClient
        public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest webResourceRequest) {
            String string = webResourceRequest.getUrl().toString();
            if (!string.startsWith("sms:")) {
                return false;
            }
            try {
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(string));
                intent.addFlags(268435456);
                HCaptchaWebViewHelper.this.context.startActivity(intent);
                HCaptchaLog.d("[webview] messaging app launched");
                return true;
            } catch (Exception e) {
                HCaptchaLog.w("[webview] messaging app launch failed: " + e.getMessage());
                HCaptchaWebViewHelper.this.captchaVerifier.onFailure(new HCaptchaException(HCaptchaError.INTERNAL_ERROR, "Messaging app cannot be launched"));
                return true;
            }
        }
    }

    public HCaptchaWebViewHelper(Handler handler, Context context, HCaptchaConfig hCaptchaConfig, HCaptchaInternalConfig hCaptchaInternalConfig, IHCaptchaVerifier iHCaptchaVerifier, HCaptchaWebView hCaptchaWebView) {
        if (handler == null) {
            throw new NullPointerException("handler is marked non-null but is null");
        }
        if (context == null) {
            throw new NullPointerException("context is marked non-null but is null");
        }
        if (hCaptchaConfig == null) {
            throw new NullPointerException("config is marked non-null but is null");
        }
        if (hCaptchaInternalConfig == null) {
            throw new NullPointerException("internalConfig is marked non-null but is null");
        }
        if (iHCaptchaVerifier == null) {
            throw new NullPointerException("captchaVerifier is marked non-null but is null");
        }
        if (hCaptchaWebView == null) {
            throw new NullPointerException("webView is marked non-null but is null");
        }
        this.context = context;
        this.config = hCaptchaConfig;
        this.captchaVerifier = iHCaptchaVerifier;
        this.webView = hCaptchaWebView;
        this.htmlProvider = hCaptchaInternalConfig.getHtmlProvider();
        setupWebView(handler);
    }

    private void setupWebView(Handler handler) {
        if (handler == null) {
            throw new NullPointerException("handler is marked non-null but is null");
        }
        HCaptchaLog.d("WebViewHelper.setupWebView");
        this.webView.setId(R.id.webView);
        HCaptchaJSInterface hCaptchaJSInterface = new HCaptchaJSInterface(handler, this.config, this.captchaVerifier);
        HCaptchaDebugInfo hCaptchaDebugInfo = new HCaptchaDebugInfo(this.context);
        WebSettings settings = this.webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setCacheMode(-1);
        settings.setGeolocationEnabled(false);
        settings.setAllowFileAccess(false);
        settings.setAllowContentAccess(false);
        settings.setSupportMultipleWindows(true);
        this.webView.setWebViewClient(new HCaptchaWebClient(handler));
        this.webView.setWebChromeClient(new HCaptchaWebChromeClient());
        this.webView.setBackgroundColor(0);
        if (this.config.getDisableHardwareAcceleration().booleanValue()) {
            this.webView.setLayerType(1, null);
        }
        this.webView.addJavascriptInterface(hCaptchaJSInterface, HCaptchaJSInterface.JS_INTERFACE_TAG);
        this.webView.addJavascriptInterface(hCaptchaDebugInfo, HCaptchaDebugInfo.JS_INTERFACE_TAG);
        this.webView.loadDataWithBaseURL(this.config.getBaseUrl(), this.htmlProvider.getHtml(), "text/html", "UTF-8", null);
        HCaptchaLog.d("WebViewHelper.loadData. Hardware acceleration enabled: %b", Boolean.valueOf(this.webView.isHardwareAccelerated()));
    }

    public void destroy() {
        HCaptchaLog.d("WebViewHelper.destroy");
        this.webView.removeJavascriptInterface(HCaptchaJSInterface.JS_INTERFACE_TAG);
        this.webView.removeJavascriptInterface(HCaptchaDebugInfo.JS_INTERFACE_TAG);
        ViewParent parent = this.webView.getParent();
        if (parent instanceof ViewGroup) {
            ((ViewGroup) parent).removeView(this.webView);
        } else {
            HCaptchaLog.w("webView.getParent() is null or not a ViewGroup instance");
        }
        this.webView.destroy();
    }

    public void execute() {
        try {
            this.webView.loadUrl("javascript:execute();");
        } catch (Exception e) {
            HCaptchaLog.w("Failed to call javascript:execute(): " + e.getMessage());
        }
    }

    public HCaptchaConfig getConfig() {
        return this.config;
    }

    public HCaptchaWebView getWebView() {
        return this.webView;
    }

    public void reset() {
        try {
            this.webView.loadUrl("javascript:reset();");
        } catch (Exception e) {
            HCaptchaLog.w("Failed to call javascript:reset(): " + e.getMessage());
        }
    }

    public void resetAndExecute(HCaptchaVerifyParams hCaptchaVerifyParams) {
        reset();
        setVerifyParams(hCaptchaVerifyParams);
        execute();
    }

    public void setVerifyParams(HCaptchaVerifyParams hCaptchaVerifyParams) {
        String rqdata;
        if (hCaptchaVerifyParams == null) {
            hCaptchaVerifyParams = new HCaptchaVerifyParams();
        }
        String rqdata2 = hCaptchaVerifyParams.getRqdata();
        if ((rqdata2 == null || rqdata2.isEmpty()) && (rqdata = this.config.getRqdata()) != null && !rqdata.isEmpty()) {
            hCaptchaVerifyParams.setRqdata(rqdata);
        }
        if (hCaptchaVerifyParams.equals(new HCaptchaVerifyParams())) {
            return;
        }
        try {
            this.webView.loadUrl("javascript:setData(" + new ObjectMapper().writeValueAsString(hCaptchaVerifyParams) + ");");
        } catch (Exception e) {
            HCaptchaLog.w("Failed to call javascript:setData(): " + e.getMessage());
        }
    }

    public boolean shouldRetry(HCaptchaException hCaptchaException) {
        return this.config.getRetryPredicate().shouldRetry(this.config, hCaptchaException);
    }
}

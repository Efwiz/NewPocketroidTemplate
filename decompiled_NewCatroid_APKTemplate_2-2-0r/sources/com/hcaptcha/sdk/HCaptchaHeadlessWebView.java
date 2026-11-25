package com.hcaptcha.sdk;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.view.ViewGroup;

/* JADX INFO: compiled from: r8-map-id-071b77f92f575d77028d4852bce4d6e5f9d65cb60a14a6342bd357f80a49e8cf */
/* JADX INFO: loaded from: classes10.dex */
final class HCaptchaHeadlessWebView implements IHCaptchaVerifier {
    private final HCaptchaConfig config;
    private final HCaptchaStateListener listener;
    private boolean shouldExecuteOnLoad;
    private boolean shouldResetOnLoad;
    private HCaptchaVerifyParams verifyParams;
    private final HCaptchaWebViewHelper webViewHelper;
    private boolean webViewLoaded;

    public HCaptchaHeadlessWebView(Activity activity, HCaptchaConfig hCaptchaConfig, HCaptchaInternalConfig hCaptchaInternalConfig, HCaptchaStateListener hCaptchaStateListener) {
        if (activity == null) {
            throw new NullPointerException("activity is marked non-null but is null");
        }
        if (hCaptchaConfig == null) {
            throw new NullPointerException("config is marked non-null but is null");
        }
        if (hCaptchaInternalConfig == null) {
            throw new NullPointerException("internalConfig is marked non-null but is null");
        }
        if (hCaptchaStateListener == null) {
            throw new NullPointerException("listener is marked non-null but is null");
        }
        HCaptchaLog.d("HeadlessWebView.init");
        this.config = hCaptchaConfig;
        this.listener = hCaptchaStateListener;
        HCaptchaWebView hCaptchaWebView = new HCaptchaWebView(activity);
        hCaptchaWebView.setId(R.id.webView);
        hCaptchaWebView.setVisibility(8);
        if (hCaptchaWebView.getParent() == null) {
            ((ViewGroup) activity.getWindow().getDecorView().getRootView()).addView(hCaptchaWebView);
        }
        this.webViewHelper = new HCaptchaWebViewHelper(new Handler(Looper.getMainLooper()), activity, hCaptchaConfig, hCaptchaInternalConfig, this, hCaptchaWebView);
    }

    @Override // com.hcaptcha.sdk.IHCaptchaVerifier
    public void destroy() {
        this.webViewHelper.destroy();
    }

    public HCaptchaConfig getConfig() {
        return this.config;
    }

    @Override // com.hcaptcha.sdk.tasks.OnFailureListener
    public void onFailure(HCaptchaException hCaptchaException) {
        if (hCaptchaException == null) {
            throw new NullPointerException("exception is marked non-null but is null");
        }
        if (this.webViewHelper.shouldRetry(hCaptchaException)) {
            this.webViewHelper.resetAndExecute(this.verifyParams);
        } else {
            this.listener.onFailure(hCaptchaException);
        }
    }

    @Override // com.hcaptcha.sdk.tasks.OnLoadedListener
    public void onLoaded() {
        this.listener.onLoaded();
        this.webViewLoaded = true;
        if (this.shouldResetOnLoad) {
            this.shouldResetOnLoad = false;
            reset();
        } else if (this.shouldExecuteOnLoad) {
            this.shouldExecuteOnLoad = false;
            this.webViewHelper.resetAndExecute(this.verifyParams);
        }
    }

    @Override // com.hcaptcha.sdk.tasks.OnOpenListener
    public void onOpen() {
        this.listener.onOpen();
    }

    @Override // com.hcaptcha.sdk.IHCaptchaVerifier
    public void reset() {
        if (!this.webViewLoaded) {
            this.shouldResetOnLoad = true;
            return;
        }
        this.webViewHelper.reset();
        HCaptchaWebView webView = this.webViewHelper.getWebView();
        if (webView.getParent() != null) {
            ((ViewGroup) webView.getParent()).removeView(webView);
        }
    }

    @Override // com.hcaptcha.sdk.IHCaptchaVerifier
    public void startVerification(Activity activity, HCaptchaVerifyParams hCaptchaVerifyParams) {
        if (activity == null) {
            throw new NullPointerException("activity is marked non-null but is null");
        }
        this.verifyParams = hCaptchaVerifyParams;
        if (this.webViewLoaded) {
            this.webViewHelper.resetAndExecute(hCaptchaVerifyParams);
        } else {
            this.shouldExecuteOnLoad = true;
        }
    }

    @Override // com.hcaptcha.sdk.tasks.OnSuccessListener
    public void onSuccess(String str) {
        this.listener.onSuccess(str);
    }
}

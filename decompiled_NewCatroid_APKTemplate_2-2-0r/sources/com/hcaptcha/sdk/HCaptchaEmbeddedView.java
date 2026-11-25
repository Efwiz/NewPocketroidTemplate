package com.hcaptcha.sdk;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.view.ViewGroup;
import android.view.ViewParent;

/* JADX INFO: compiled from: r8-map-id-071b77f92f575d77028d4852bce4d6e5f9d65cb60a14a6342bd357f80a49e8cf */
/* JADX INFO: loaded from: classes10.dex */
final class HCaptchaEmbeddedView implements IHCaptchaVerifier {
    private final ViewGroup challengeContainer;
    private final HCaptchaStateListener listener;
    private boolean readyForInteraction = false;
    private HCaptchaVerifyParams verifyParams;
    private final HCaptchaWebViewHelper webViewHelper;

    public HCaptchaEmbeddedView(Activity activity, HCaptchaConfig hCaptchaConfig, HCaptchaInternalConfig hCaptchaInternalConfig, HCaptchaStateListener hCaptchaStateListener, ViewGroup viewGroup) {
        this.listener = hCaptchaStateListener;
        this.challengeContainer = viewGroup;
        this.webViewHelper = new HCaptchaWebViewHelper(new Handler(Looper.getMainLooper()), activity, hCaptchaConfig, hCaptchaInternalConfig, this, new HCaptchaWebView(activity));
    }

    private void attachWebView() {
        HCaptchaWebView webView = this.webViewHelper.getWebView();
        ViewParent parent = webView.getParent();
        if ((parent instanceof ViewGroup) && parent != this.challengeContainer) {
            ((ViewGroup) parent).removeView(webView);
        }
        if (webView.getParent() == null) {
            this.challengeContainer.addView(webView, new ViewGroup.LayoutParams(-1, -1));
        }
    }

    private void detachWebView() {
        HCaptchaWebView webView = this.webViewHelper.getWebView();
        ViewParent parent = webView.getParent();
        if (parent instanceof ViewGroup) {
            ((ViewGroup) parent).removeView(webView);
        }
    }

    @Override // com.hcaptcha.sdk.IHCaptchaVerifier
    public void destroy() {
        this.readyForInteraction = false;
        this.verifyParams = null;
        this.webViewHelper.destroy();
    }

    @Override // com.hcaptcha.sdk.tasks.OnFailureListener
    public void onFailure(HCaptchaException hCaptchaException) {
        if (this.webViewHelper.shouldRetry(hCaptchaException)) {
            this.webViewHelper.resetAndExecute(this.verifyParams);
        } else {
            this.listener.onFailure(hCaptchaException);
        }
    }

    @Override // com.hcaptcha.sdk.tasks.OnLoadedListener
    public void onLoaded() {
        this.listener.onLoaded();
        HCaptchaConfig config = this.webViewHelper.getConfig();
        boolean z = config.getSize() == HCaptchaSize.INVISIBLE;
        if (!z) {
            this.readyForInteraction = true;
        }
        if (!z || config.isHeadlessMode()) {
            this.webViewHelper.setVerifyParams(this.verifyParams);
        } else {
            this.webViewHelper.resetAndExecute(this.verifyParams);
        }
    }

    @Override // com.hcaptcha.sdk.tasks.OnOpenListener
    public void onOpen() {
        this.readyForInteraction = true;
        this.listener.onOpen();
    }

    @Override // com.hcaptcha.sdk.IHCaptchaVerifier
    public void reset() {
        this.readyForInteraction = false;
        this.verifyParams = null;
        this.webViewHelper.reset();
        detachWebView();
    }

    @Override // com.hcaptcha.sdk.IHCaptchaVerifier
    public void startVerification(Activity activity, HCaptchaVerifyParams hCaptchaVerifyParams) {
        this.verifyParams = hCaptchaVerifyParams;
        if (this.readyForInteraction) {
            this.webViewHelper.setVerifyParams(hCaptchaVerifyParams);
        }
        attachWebView();
    }

    @Override // com.hcaptcha.sdk.tasks.OnSuccessListener
    public void onSuccess(String str) {
        this.listener.onSuccess(str);
    }
}

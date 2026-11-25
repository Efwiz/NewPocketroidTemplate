package com.hcaptcha.sdk;

import android.os.Handler;
import android.util.Log;
import android.webkit.JavascriptInterface;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.Serializable;
import java.util.Objects;

/* JADX INFO: compiled from: r8-map-id-071b77f92f575d77028d4852bce4d6e5f9d65cb60a14a6342bd357f80a49e8cf */
/* JADX INFO: loaded from: classes10.dex */
class HCaptchaJSInterface implements Serializable {
    public static final String JS_INTERFACE_TAG = "JSInterface";
    private final transient IHCaptchaVerifier captchaVerifier;
    private final String config;
    private final transient Handler handler;

    public HCaptchaJSInterface(Handler handler, HCaptchaConfig hCaptchaConfig, IHCaptchaVerifier iHCaptchaVerifier) {
        String strWriteValueAsString;
        if (handler == null) {
            throw new NullPointerException("handler is marked non-null but is null");
        }
        if (hCaptchaConfig == null) {
            throw new NullPointerException("config is marked non-null but is null");
        }
        if (iHCaptchaVerifier == null) {
            throw new NullPointerException("captchaVerifier is marked non-null but is null");
        }
        this.handler = handler;
        this.captchaVerifier = iHCaptchaVerifier;
        try {
            strWriteValueAsString = new ObjectMapper().writeValueAsString(hCaptchaConfig);
        } catch (JsonProcessingException unused) {
            Log.w(JS_INTERFACE_TAG, "Cannot prepare config for passing to WebView. A fallback config will be used");
            strWriteValueAsString = null;
        }
        this.config = strWriteValueAsString;
    }

    @JavascriptInterface
    public String getConfig() {
        return this.config;
    }

    /* JADX INFO: renamed from: lambda$onError$1$com-hcaptcha-sdk-HCaptchaJSInterface, reason: not valid java name */
    /* synthetic */ void m2668lambda$onError$1$comhcaptchasdkHCaptchaJSInterface(HCaptchaError hCaptchaError) {
        this.captchaVerifier.onFailure(new HCaptchaException(hCaptchaError));
    }

    /* JADX INFO: renamed from: lambda$onPass$0$com-hcaptcha-sdk-HCaptchaJSInterface, reason: not valid java name */
    /* synthetic */ void m2669lambda$onPass$0$comhcaptchasdkHCaptchaJSInterface(String str) {
        this.captchaVerifier.onSuccess(str);
    }

    @JavascriptInterface
    public void onError(int i) {
        HCaptchaLog.d("JSInterface.onError %d", Integer.valueOf(i));
        final HCaptchaError hCaptchaErrorFromId = HCaptchaError.fromId(i);
        this.handler.post(new Runnable() { // from class: com.hcaptcha.sdk.HCaptchaJSInterface$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m2668lambda$onError$1$comhcaptchasdkHCaptchaJSInterface(hCaptchaErrorFromId);
            }
        });
    }

    @JavascriptInterface
    public void onLoaded() {
        HCaptchaLog.d("JSInterface.onLoaded");
        Handler handler = this.handler;
        final IHCaptchaVerifier iHCaptchaVerifier = this.captchaVerifier;
        Objects.requireNonNull(iHCaptchaVerifier);
        handler.post(new Runnable() { // from class: com.hcaptcha.sdk.HCaptchaJSInterface$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                iHCaptchaVerifier.onLoaded();
            }
        });
    }

    @JavascriptInterface
    public void onOpen() {
        HCaptchaLog.d("JSInterface.onOpen");
        Handler handler = this.handler;
        final IHCaptchaVerifier iHCaptchaVerifier = this.captchaVerifier;
        Objects.requireNonNull(iHCaptchaVerifier);
        handler.post(new Runnable() { // from class: com.hcaptcha.sdk.HCaptchaJSInterface$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                iHCaptchaVerifier.onOpen();
            }
        });
    }

    @JavascriptInterface
    public void onPass(final String str) {
        HCaptchaLog.d("JSInterface.onPass");
        this.handler.post(new Runnable() { // from class: com.hcaptcha.sdk.HCaptchaJSInterface$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m2669lambda$onPass$0$comhcaptchasdkHCaptchaJSInterface(str);
            }
        });
    }
}

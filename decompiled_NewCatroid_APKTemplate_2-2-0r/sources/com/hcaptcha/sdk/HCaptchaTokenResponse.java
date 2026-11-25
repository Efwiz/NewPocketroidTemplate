package com.hcaptcha.sdk;

import android.os.Handler;

/* JADX INFO: compiled from: r8-map-id-071b77f92f575d77028d4852bce4d6e5f9d65cb60a14a6342bd357f80a49e8cf */
/* JADX INFO: loaded from: classes10.dex */
public class HCaptchaTokenResponse {
    private final Handler handler;
    private final String tokenResult;

    public HCaptchaTokenResponse(String str, Handler handler) {
        this.tokenResult = str;
        this.handler = handler;
    }

    public String getTokenResult() {
        return this.tokenResult;
    }

    public void markUsed() {
        this.handler.removeCallbacksAndMessages(null);
    }
}

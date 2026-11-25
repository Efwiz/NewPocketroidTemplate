package com.hcaptcha.sdk;

import android.app.Activity;
import com.hcaptcha.sdk.tasks.OnFailureListener;
import com.hcaptcha.sdk.tasks.OnLoadedListener;
import com.hcaptcha.sdk.tasks.OnOpenListener;
import com.hcaptcha.sdk.tasks.OnSuccessListener;

/* JADX INFO: compiled from: r8-map-id-071b77f92f575d77028d4852bce4d6e5f9d65cb60a14a6342bd357f80a49e8cf */
/* JADX INFO: loaded from: classes10.dex */
interface IHCaptchaVerifier extends OnLoadedListener, OnOpenListener, OnSuccessListener<String>, OnFailureListener {
    void destroy();

    void reset();

    void startVerification(Activity activity, HCaptchaVerifyParams hCaptchaVerifyParams);
}

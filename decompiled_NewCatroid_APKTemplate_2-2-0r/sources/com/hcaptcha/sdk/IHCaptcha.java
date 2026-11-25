package com.hcaptcha.sdk;

import android.view.ViewGroup;

/* JADX INFO: compiled from: r8-map-id-071b77f92f575d77028d4852bce4d6e5f9d65cb60a14a6342bd357f80a49e8cf */
/* JADX INFO: loaded from: classes10.dex */
public interface IHCaptcha {
    void destroy();

    void reset();

    IHCaptcha setEmbeddedContainer(ViewGroup viewGroup);

    IHCaptcha setup();

    IHCaptcha setup(HCaptchaConfig hCaptchaConfig);

    IHCaptcha setup(String str);

    void stopEvents();

    IHCaptcha verifyWithHCaptcha();

    IHCaptcha verifyWithHCaptcha(HCaptchaConfig hCaptchaConfig);

    IHCaptcha verifyWithHCaptcha(HCaptchaConfig hCaptchaConfig, HCaptchaVerifyParams hCaptchaVerifyParams);

    IHCaptcha verifyWithHCaptcha(HCaptchaVerifyParams hCaptchaVerifyParams);

    IHCaptcha verifyWithHCaptcha(String str);
}

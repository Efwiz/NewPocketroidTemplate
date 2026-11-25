package com.hcaptcha.sdk;

import java.io.Serializable;

/* JADX INFO: compiled from: r8-map-id-071b77f92f575d77028d4852bce4d6e5f9d65cb60a14a6342bd357f80a49e8cf */
/* JADX INFO: loaded from: classes10.dex */
@FunctionalInterface
public interface IHCaptchaRetryPredicate extends Serializable {
    boolean shouldRetry(HCaptchaConfig hCaptchaConfig, HCaptchaException hCaptchaException);
}

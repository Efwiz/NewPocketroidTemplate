package com.hcaptcha.sdk;

import java.io.Serializable;

/* JADX INFO: compiled from: D8$$SyntheticClass */
/* JADX INFO: loaded from: classes10.dex */
public final /* synthetic */ class HCaptchaConfig$$ExternalSyntheticLambda0 implements IHCaptchaRetryPredicate, Serializable {
    @Override // com.hcaptcha.sdk.IHCaptchaRetryPredicate
    public final boolean shouldRetry(HCaptchaConfig hCaptchaConfig, HCaptchaException hCaptchaException) {
        return HCaptchaConfig.lambda$$default$retryPredicate$41a513e9$1(hCaptchaConfig, hCaptchaException);
    }
}

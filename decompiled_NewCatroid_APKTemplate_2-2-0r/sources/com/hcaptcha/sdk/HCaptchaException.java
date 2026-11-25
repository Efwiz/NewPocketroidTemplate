package com.hcaptcha.sdk;

import com.huawei.hms.ml.language.common.utils.Constant;

/* JADX INFO: compiled from: r8-map-id-071b77f92f575d77028d4852bce4d6e5f9d65cb60a14a6342bd357f80a49e8cf */
/* JADX INFO: loaded from: classes10.dex */
public class HCaptchaException extends Exception {
    private final HCaptchaError hCaptchaError;
    private final String message;

    public HCaptchaException(HCaptchaError hCaptchaError, String str) {
        if (hCaptchaError == null) {
            throw new NullPointerException("hCaptchaError is marked non-null but is null");
        }
        this.hCaptchaError = hCaptchaError;
        this.message = str;
    }

    public boolean canEqual(Object obj) {
        return obj instanceof HCaptchaException;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof HCaptchaException)) {
            return false;
        }
        HCaptchaException hCaptchaException = (HCaptchaException) obj;
        if (!hCaptchaException.canEqual(this) || !super.equals(obj)) {
            return false;
        }
        HCaptchaError hCaptchaError = getHCaptchaError();
        HCaptchaError hCaptchaError2 = hCaptchaException.getHCaptchaError();
        if (hCaptchaError != null ? !hCaptchaError.equals(hCaptchaError2) : hCaptchaError2 != null) {
            return false;
        }
        String message = getMessage();
        String message2 = hCaptchaException.getMessage();
        return message != null ? message.equals(message2) : message2 == null;
    }

    public HCaptchaError getHCaptchaError() {
        return this.hCaptchaError;
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        String str = this.message;
        return str == null ? this.hCaptchaError.getMessage() : str;
    }

    public int getStatusCode() {
        return this.hCaptchaError.getErrorId();
    }

    public int hashCode() {
        int iHashCode = super.hashCode();
        HCaptchaError hCaptchaError = getHCaptchaError();
        int iHashCode2 = (iHashCode * 59) + (hCaptchaError == null ? 43 : hCaptchaError.hashCode());
        String message = getMessage();
        return (iHashCode2 * 59) + (message != null ? message.hashCode() : 43);
    }

    @Override // java.lang.Throwable
    public String toString() {
        return "HCaptchaException(hCaptchaError=" + getHCaptchaError() + ", message=" + getMessage() + Constant.AFTER_QUTO;
    }

    public HCaptchaException(HCaptchaError hCaptchaError) {
        this(hCaptchaError, null);
    }
}

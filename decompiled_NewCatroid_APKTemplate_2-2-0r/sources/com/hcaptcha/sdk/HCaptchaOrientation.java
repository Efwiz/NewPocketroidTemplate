package com.hcaptcha.sdk;

import com.fasterxml.jackson.annotation.JsonValue;
import java.io.Serializable;

/* JADX INFO: compiled from: r8-map-id-071b77f92f575d77028d4852bce4d6e5f9d65cb60a14a6342bd357f80a49e8cf */
/* JADX INFO: loaded from: classes10.dex */
public enum HCaptchaOrientation implements Serializable {
    PORTRAIT("portrait"),
    LANDSCAPE("landscape");

    private final String orientation;

    HCaptchaOrientation(String str) {
        this.orientation = str;
    }

    public String getOrientation() {
        return this.orientation;
    }

    @Override // java.lang.Enum
    @JsonValue
    public String toString() {
        return this.orientation;
    }
}

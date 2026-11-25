package com.hcaptcha.sdk;

import com.fasterxml.jackson.annotation.JsonValue;
import java.io.Serializable;

/* JADX INFO: compiled from: r8-map-id-071b77f92f575d77028d4852bce4d6e5f9d65cb60a14a6342bd357f80a49e8cf */
/* JADX INFO: loaded from: classes10.dex */
public enum HCaptchaSize implements Serializable {
    INVISIBLE("invisible"),
    NORMAL("normal"),
    COMPACT("compact");

    private final String size;

    HCaptchaSize(String str) {
        this.size = str;
    }

    public String getSize() {
        return this.size;
    }

    @Override // java.lang.Enum
    @JsonValue
    public String toString() {
        return this.size;
    }
}

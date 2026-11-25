package com.hcaptcha.sdk;

import com.fasterxml.jackson.annotation.JsonValue;
import java.io.Serializable;

/* JADX INFO: compiled from: r8-map-id-071b77f92f575d77028d4852bce4d6e5f9d65cb60a14a6342bd357f80a49e8cf */
/* JADX INFO: loaded from: classes10.dex */
public enum HCaptchaTheme implements Serializable {
    DARK("dark"),
    LIGHT("light"),
    CONTRAST("contrast");

    private final String theme;

    HCaptchaTheme(String str) {
        this.theme = str;
    }

    public String getTheme() {
        return this.theme;
    }

    @Override // java.lang.Enum
    @JsonValue
    public String toString() {
        return this.theme;
    }
}

package com.huawei.hms.mlsdk.text;

import com.huawei.hms.mlsdk.internal.client.Objects;

/* JADX INFO: loaded from: classes10.dex */
public class TextLanguage {
    private final String language;

    public TextLanguage(String str) {
        this.language = str;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof TextLanguage)) {
            return false;
        }
        TextLanguage textLanguage = (TextLanguage) obj;
        String str = this.language;
        if (str == null) {
            return textLanguage.language == null;
        }
        return str.equals(textLanguage.language);
    }

    public String getLanguage() {
        return this.language;
    }

    public int hashCode() {
        return Objects.hashCode(this.language);
    }
}

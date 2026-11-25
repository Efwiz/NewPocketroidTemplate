package com.koushikdutta.async.http;

import android.text.TextUtils;
import com.huawei.hms.framework.common.ContainerUtils;

/* JADX INFO: loaded from: classes10.dex */
public class BasicNameValuePair implements NameValuePair, Cloneable {

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private final String f213name;
    private final String value;

    public BasicNameValuePair(String str, String str2) {
        if (str == null) {
            throw new IllegalArgumentException("Name may not be null");
        }
        this.f213name = str;
        this.value = str2;
    }

    @Override // com.koushikdutta.async.http.NameValuePair
    public String getName() {
        return this.f213name;
    }

    @Override // com.koushikdutta.async.http.NameValuePair
    public String getValue() {
        return this.value;
    }

    public String toString() {
        return this.f213name + ContainerUtils.KEY_VALUE_DELIMITER + this.value;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof NameValuePair)) {
            return false;
        }
        BasicNameValuePair basicNameValuePair = (BasicNameValuePair) obj;
        return this.f213name.equals(basicNameValuePair.f213name) && TextUtils.equals(this.value, basicNameValuePair.value);
    }

    public int hashCode() {
        return this.f213name.hashCode() ^ this.value.hashCode();
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

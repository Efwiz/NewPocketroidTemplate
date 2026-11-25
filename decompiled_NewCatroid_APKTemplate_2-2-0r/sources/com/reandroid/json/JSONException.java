package com.reandroid.json;

/* JADX INFO: loaded from: classes11.dex */
public class JSONException extends IllegalArgumentException {
    private static final long serialVersionUID = 0;

    public JSONException(String str) {
        super(str);
    }

    public JSONException(String str, Throwable th) {
        super(str, th);
    }

    public JSONException(Throwable th) {
        super(th.getMessage(), th);
    }
}

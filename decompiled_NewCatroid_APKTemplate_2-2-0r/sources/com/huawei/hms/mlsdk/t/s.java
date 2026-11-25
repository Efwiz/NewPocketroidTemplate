package com.huawei.hms.mlsdk.t;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: TtsErrorResponse.java */
/* JADX INFO: loaded from: classes10.dex */
public class s implements p {
    private String a;
    private String b;

    public String a() {
        return this.a;
    }

    public String b() {
        return this.b;
    }

    public void a(JSONObject jSONObject) throws JSONException {
        this.a = jSONObject.getString("retCode");
        this.b = jSONObject.getString("retMsg");
    }
}

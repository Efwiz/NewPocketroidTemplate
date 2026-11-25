package com.huawei.hms.mlsdk.t;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: RequestTtsInput.java */
/* JADX INFO: loaded from: classes10.dex */
public class r implements p {
    private String a;
    private String b;

    public r(String str, String str2) {
        this.a = str;
        this.b = str2;
    }

    public JSONObject a() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        String str = this.a;
        if (str == null) {
            str = "";
        }
        jSONObject.put("text", str);
        String str2 = this.b;
        jSONObject.put("language", str2 != null ? str2 : "");
        return jSONObject;
    }

    public String toString() {
        return a.a(a.a("TtsInput{text='"), this.a, '\'', ", language='").append(this.b).append('\'').append('}').toString();
    }
}

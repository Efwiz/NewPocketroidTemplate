package com.huawei.hms.mlsdk.t;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: TtsRequest.java */
/* JADX INFO: loaded from: classes10.dex */
public class t implements p {
    private r a;
    private q b;

    public t(r rVar, q qVar) {
        this.a = rVar;
        this.b = qVar;
    }

    public JSONObject a() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        r rVar = this.a;
        jSONObject.put("data", rVar == null ? "" : rVar.a());
        q qVar = this.b;
        jSONObject.put("config", qVar != null ? qVar.a() : "");
        return jSONObject;
    }

    public String toString() {
        return a.a("TtsRequest{ttsInput=").append(this.a).append(", ttsConfig=").append(this.b).append('}').toString();
    }
}

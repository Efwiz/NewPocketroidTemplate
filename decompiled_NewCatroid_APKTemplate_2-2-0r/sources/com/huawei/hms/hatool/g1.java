package com.huawei.hms.hatool;

import com.google.firebase.database.core.ServerValues;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes10.dex */
public class g1 extends o {
    public String g = "";

    @Override // com.huawei.hms.hatool.s
    public JSONObject a() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("protocol_version", SchemaSymbols.ATTVAL_TRUE_1);
        jSONObject.put("compress_mode", SchemaSymbols.ATTVAL_TRUE_1);
        jSONObject.put("serviceid", this.d);
        jSONObject.put("appid", this.a);
        jSONObject.put("hmac", this.g);
        jSONObject.put("chifer", this.f);
        jSONObject.put(ServerValues.NAME_OP_TIMESTAMP, this.b);
        jSONObject.put("servicetag", this.c);
        jSONObject.put("requestid", this.e);
        return jSONObject;
    }

    public void g(String str) {
        this.g = str;
    }
}

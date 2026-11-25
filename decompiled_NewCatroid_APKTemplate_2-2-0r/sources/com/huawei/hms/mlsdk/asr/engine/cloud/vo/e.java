package com.huawei.hms.mlsdk.asr.engine.cloud.vo;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.Serializable;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: AsrResponse.java */
/* JADX INFO: loaded from: classes10.dex */
public class e implements Serializable {
    private String a;
    private String b;
    private JSONObject c;

    public String a() {
        return this.a;
    }

    public JSONObject b() {
        return this.c;
    }

    public String c() {
        return this.b;
    }

    public static e a(String str) throws JSONException {
        e eVar = new e();
        JSONObject jSONObject = new JSONObject(str);
        if (jSONObject.has("command")) {
            eVar.a = jSONObject.getString("command");
        }
        if (jSONObject.has("retCode")) {
            eVar.b = jSONObject.getString("retCode");
        }
        if (jSONObject.has("retMsg")) {
            jSONObject.getString("retMsg");
        }
        if ("DATA".equalsIgnoreCase(eVar.a) || "STATUS".equalsIgnoreCase(eVar.a)) {
            eVar.c = jSONObject.getJSONObject("result");
        }
        return eVar;
    }

    public static c a(JSONObject jSONObject) throws JSONException {
        c cVar = new c();
        if (jSONObject != null) {
            if (jSONObject.has("text")) {
                cVar.a(jSONObject.getString("text"));
            }
            if (jSONObject.has(FirebaseAnalytics.Param.SCORE)) {
                Double.valueOf(jSONObject.getDouble(FirebaseAnalytics.Param.SCORE));
            }
            if (jSONObject.has("endTimeOffset")) {
                Integer.valueOf(jSONObject.getInt("endTimeOffset"));
            }
            if (jSONObject.has("final")) {
                cVar.a(jSONObject.getBoolean("final"));
            }
            if (jSONObject.has("isPartialFinal")) {
                cVar.b(jSONObject.getBoolean("isPartialFinal"));
            }
            if (jSONObject.has("words")) {
                cVar.b(a(jSONObject.getJSONArray("words")));
            }
            if (jSONObject.has("sentences")) {
                cVar.a(a(jSONObject.getJSONArray("sentences")));
            }
        }
        return cVar;
    }

    private static ArrayList<RttSegment> a(JSONArray jSONArray) throws JSONException {
        ArrayList<RttSegment> arrayList = new ArrayList<>();
        if (jSONArray.length() > 0) {
            for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                String string = "";
                String string2 = jSONObject.has("startTime") ? jSONObject.getString("startTime") : "";
                String string3 = jSONObject.has("endTime") ? jSONObject.getString("endTime") : "";
                if (jSONObject.has("text")) {
                    string = jSONObject.getString("text");
                }
                arrayList.add(new RttSegment(string2, string3, string));
            }
        }
        return arrayList;
    }
}

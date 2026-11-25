package com.huawei.hms.mlsdk.t;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.text.TextUtils;
import android.util.Pair;
import com.huawei.hms.mlsdk.common.MLApplication;
import com.huawei.hms.mlsdk.tts.MLTtsConstants;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: FileUtils.java */
/* JADX INFO: loaded from: classes10.dex */
public final class g0 {
    public static JSONArray a(JSONObject jSONObject, String str) {
        if (jSONObject != null && !TextUtils.isEmpty(str)) {
            try {
                if (jSONObject.has(str)) {
                    return jSONObject.getJSONArray(str);
                }
            } catch (JSONException unused) {
                i0.b("JsonUtil", "GetJsonArray JSONException fieldName=" + str);
            }
        }
        return null;
    }

    public static String b(JSONObject jSONObject, String str) {
        if (jSONObject != null && str != null) {
            try {
                if (jSONObject.has(str)) {
                    return jSONObject.getString(str);
                }
            } catch (JSONException unused) {
                i0.b("JsonUtil", "getJsonFiled failed.JSONException");
            }
        }
        return "";
    }

    public static boolean c(String str) {
        return (TextUtils.equals(str, MLTtsConstants.TTS_SPEAKER_FEMALE_ZH_1) || TextUtils.equals(str, MLTtsConstants.TTS_SPEAKER_MALE_ZH_1) || TextUtils.equals(str, MLTtsConstants.TTS_SPEAKER_FEMALE_EN_1) || TextUtils.equals(str, MLTtsConstants.TTS_SPEAKER_MALE_EN_1) || TextUtils.equals(str, MLTtsConstants.TTS_SPEAKER_FEMALE_ZH_HQ) || TextUtils.equals(str, MLTtsConstants.TTS_SPEAKER_FEMALE_EN_HQ) || TextUtils.equals(str, MLTtsConstants.TTS_SPEAKER_MALE_ZH_HQ) || TextUtils.equals(str, MLTtsConstants.TTS_SPEAKER_MALE_EN_HQ)) ? false : true;
    }

    public static JSONObject d(String str) {
        try {
            return new JSONObject(str);
        } catch (JSONException unused) {
            i0.b("JsonUtil", "parseJson failed.JSONException");
            return null;
        }
    }

    public static JSONArray b(String str) {
        try {
            return new JSONArray(str);
        } catch (JSONException unused) {
            i0.b("JsonUtil", "parseJson failed.JSONException");
            return null;
        }
    }

    public static int a(String str) {
        com.huawei.hms.mlsdk.tts.engine.common.b.e().d().contains(str);
        return 9;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static boolean a() {
        String str = "HmsAdapter";
        List<ResolveInfo> listQueryIntentServices = MLApplication.getInstance().getAppContext().getPackageManager().queryIntentServices(new Intent("com.huawei.hms.core.aidlservice"), 128);
        Pair pair = null;
        if (listQueryIntentServices.size() != 0) {
            Iterator<ResolveInfo> it = listQueryIntentServices.iterator();
            if (it.hasNext()) {
                ResolveInfo next = it.next();
                pair = new Pair(next.serviceInfo.applicationInfo.packageName, next);
            }
        }
        boolean z = false;
        if (pair == null) {
            return false;
        }
        String str2 = (String) pair.first;
        try {
            Context appContext = MLApplication.getInstance().getAppContext();
            if (appContext == null) {
                i0.b("HmsAdapter", "Context not initialized");
                str = str;
            } else {
                int i = appContext.getPackageManager().getPackageInfo(str2, 0).applicationInfo.flags & 1;
                str = i;
                if (i != 0) {
                    z = true;
                    str = i;
                }
            }
        } catch (Exception e) {
            i0.d(str, "isPackageInternal Exception e: " + e);
        }
        return z;
    }
}

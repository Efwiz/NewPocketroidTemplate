package com.reandroid.json;

import com.huawei.hms.framework.common.ContainerUtils;

/* JADX INFO: loaded from: classes11.dex */
public class CookieList {
    public static JSONObject toJSONObject(String str) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        JSONTokener jSONTokener = new JSONTokener(str);
        while (jSONTokener.more()) {
            String strUnescape = Cookie.unescape(jSONTokener.nextTo('='));
            jSONTokener.next('=');
            jSONObject.put(strUnescape, Cookie.unescape(jSONTokener.nextTo(';')));
            jSONTokener.next();
        }
        return jSONObject;
    }

    public static String toString(JSONObject jSONObject) throws JSONException {
        StringBuilder sb = new StringBuilder();
        boolean z = false;
        for (String str : jSONObject.keySet()) {
            Object objOpt = jSONObject.opt(str);
            if (!JSONObject.NULL.equals(objOpt)) {
                if (z) {
                    sb.append(';');
                }
                sb.append(Cookie.escape(str));
                sb.append(ContainerUtils.KEY_VALUE_DELIMITER);
                sb.append(Cookie.escape(objOpt.toString()));
                z = true;
            }
        }
        return sb.toString();
    }
}

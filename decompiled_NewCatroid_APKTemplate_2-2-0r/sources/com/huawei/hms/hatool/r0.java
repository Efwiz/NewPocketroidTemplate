package com.huawei.hms.hatool;

import android.content.Context;
import android.text.TextUtils;
import java.io.File;

/* JADX INFO: loaded from: classes10.dex */
public class r0 {
    public static boolean a(Context context) {
        return System.currentTimeMillis() - h0.a(context, "Privacy_MY", "flashKeyTime", -1L) > 43200000;
    }

    public static boolean a(Context context, String str) {
        if (context == null) {
            return true;
        }
        if (context.checkSelfPermission(str) == 0) {
            return false;
        }
        z.f("hmsSdk", "not have read phone permission!");
        return true;
    }

    public static boolean a(Context context, String str, int i) {
        long length = new File(context.getFilesDir(), "../shared_prefs/" + (h0.c(context, str) + ".xml")).length();
        if (length <= i) {
            return false;
        }
        z.c("hmsSdk", String.format("reach local file limited size - file len: %d limitedSize: %d", Long.valueOf(length), Integer.valueOf(i)));
        return true;
    }

    public static boolean a(String str, long j, long j2) {
        if (TextUtils.isEmpty(str)) {
            return true;
        }
        try {
            return j - Long.parseLong(str) > j2;
        } catch (NumberFormatException unused) {
            z.f("hmsSdk", "isTimeExpired(): Data type conversion error : number format !");
            return true;
        }
    }
}

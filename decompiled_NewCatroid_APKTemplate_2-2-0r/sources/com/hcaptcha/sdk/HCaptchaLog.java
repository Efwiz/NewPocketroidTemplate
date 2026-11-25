package com.hcaptcha.sdk;

import android.util.Log;
import java.util.Locale;

/* JADX INFO: compiled from: r8-map-id-071b77f92f575d77028d4852bce4d6e5f9d65cb60a14a6342bd357f80a49e8cf */
/* JADX INFO: loaded from: classes10.dex */
public final class HCaptchaLog {
    private static final String TAG = "hcaptcha";
    static boolean sDiagnosticsLogEnabled = false;

    private HCaptchaLog() {
    }

    public static void d(String str) {
        if (sDiagnosticsLogEnabled) {
            Log.d(TAG, str);
        }
    }

    public static void w(String str) {
        Log.w(TAG, str);
    }

    public static void d(String str, Object... objArr) {
        if (sDiagnosticsLogEnabled) {
            Log.d(TAG, String.format(Locale.getDefault(), str, objArr));
        }
    }
}

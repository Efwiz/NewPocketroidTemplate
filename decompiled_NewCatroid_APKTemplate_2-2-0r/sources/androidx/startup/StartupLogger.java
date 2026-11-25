package androidx.startup;

import android.util.Log;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/startup-runtime-1.1.1.aar:classes.jar:androidx/startup/StartupLogger.class
  classes.dex
 */
/* JADX INFO: loaded from: classes.dex */
public final class StartupLogger {
    static final boolean DEBUG = false;
    private static final String TAG = "StartupLogger";

    private StartupLogger() {
    }

    public static void i(String str) {
        Log.i(TAG, str);
    }

    public static void w(String str) {
        Log.w(TAG, str);
    }

    public static void e(String str, Throwable th) {
        Log.e(TAG, str, th);
    }
}

package androidx.core.provider;

import android.os.Handler;
import android.os.Looper;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/core-1.8.0.aar:classes.jar:androidx/core/provider/CalleeHandler.class
  classes.dex
 */
/* JADX INFO: loaded from: classes.dex */
class CalleeHandler {
    private CalleeHandler() {
    }

    static Handler create() {
        if (Looper.myLooper() == null) {
            return new Handler(Looper.getMainLooper());
        }
        return new Handler();
    }
}

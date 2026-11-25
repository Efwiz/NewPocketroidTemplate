package androidx.core.database;

import android.database.CursorWindow;
import android.os.Build;
import androidx.annotation.DoNotInline;
import androidx.annotation.RequiresApi;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/core-1.8.0.aar:classes.jar:androidx/core/database/CursorWindowCompat.class
  NewCatroid_APKTemplate_2-2-0r.apk:assets/core.jar:androidx/core/database/CursorWindowCompat.class
  classes.dex
 */
/* JADX INFO: loaded from: classes.dex */
public final class CursorWindowCompat {
    private CursorWindowCompat() {
    }

    public static CursorWindow create(String str, long j) {
        if (Build.VERSION.SDK_INT >= 28) {
            return Api28Impl.createCursorWindow(str, j);
        }
        return new CursorWindow(str);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/core-1.8.0.aar:classes.jar:androidx/core/database/CursorWindowCompat$Api28Impl.class
      classes.dex
     */
    static class Api28Impl {
        private Api28Impl() {
        }

        static CursorWindow createCursorWindow(String str, long j) {
            return new CursorWindow(str, j);
        }
    }

    /* JADX INFO: loaded from: NewCatroid_APKTemplate_2-2-0r.apk:assets/core-1.8.0.aar:classes.jar:androidx/core/database/CursorWindowCompat$Api15Impl.class */
    @RequiresApi(15)
    static class Api15Impl {
        private Api15Impl() {
        }

        @DoNotInline
        static CursorWindow createCursorWindow(String name2) {
            return new CursorWindow(name2);
        }
    }
}

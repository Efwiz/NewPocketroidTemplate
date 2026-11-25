package androidx.core.os;

import android.os.Environment;
import androidx.annotation.DoNotInline;
import androidx.annotation.RequiresApi;
import java.io.File;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/core-1.8.0.aar:classes.jar:androidx/core/os/EnvironmentCompat.class
  NewCatroid_APKTemplate_2-2-0r.apk:assets/core.jar:androidx/core/os/EnvironmentCompat.class
  classes.dex
 */
/* JADX INFO: loaded from: classes.dex */
public final class EnvironmentCompat {

    @Deprecated
    public static final String MEDIA_UNKNOWN = "unknown";

    public static String getStorageState(File file) {
        return Api21Impl.getExternalStorageState(file);
    }

    private EnvironmentCompat() {
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/core-1.8.0.aar:classes.jar:androidx/core/os/EnvironmentCompat$Api21Impl.class
      classes.dex
     */
    static class Api21Impl {
        private Api21Impl() {
        }

        static String getExternalStorageState(File file) {
            return Environment.getExternalStorageState(file);
        }
    }

    /* JADX INFO: loaded from: NewCatroid_APKTemplate_2-2-0r.apk:assets/core-1.8.0.aar:classes.jar:androidx/core/os/EnvironmentCompat$Api19Impl.class */
    @RequiresApi(19)
    static class Api19Impl {
        private Api19Impl() {
        }

        @DoNotInline
        static String getStorageState(File path) {
            return Environment.getStorageState(path);
        }
    }
}

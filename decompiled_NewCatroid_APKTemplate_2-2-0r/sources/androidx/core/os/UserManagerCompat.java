package androidx.core.os;

import android.content.Context;
import android.os.UserManager;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/core-1.8.0.aar:classes.jar:androidx/core/os/UserManagerCompat.class
  NewCatroid_APKTemplate_2-2-0r.apk:assets/core.jar:androidx/core/os/UserManagerCompat.class
  classes.dex
 */
/* JADX INFO: loaded from: classes.dex */
public class UserManagerCompat {
    private UserManagerCompat() {
    }

    public static boolean isUserUnlocked(Context context) {
        return Api24Impl.isUserUnlocked(context);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/core-1.8.0.aar:classes.jar:androidx/core/os/UserManagerCompat$Api24Impl.class
      classes.dex
     */
    static class Api24Impl {
        private Api24Impl() {
        }

        static boolean isUserUnlocked(Context context) {
            return ((UserManager) context.getSystemService(UserManager.class)).isUserUnlocked();
        }
    }
}

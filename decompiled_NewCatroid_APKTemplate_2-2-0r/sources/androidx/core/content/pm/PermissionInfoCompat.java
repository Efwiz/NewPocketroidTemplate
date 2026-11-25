package androidx.core.content.pm;

import android.content.pm.PermissionInfo;
import android.os.Build;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/core-1.8.0.aar:classes.jar:androidx/core/content/pm/PermissionInfoCompat.class
  NewCatroid_APKTemplate_2-2-0r.apk:assets/core.jar:androidx/core/content/pm/PermissionInfoCompat.class
  classes.dex
 */
/* JADX INFO: loaded from: classes.dex */
public final class PermissionInfoCompat {

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/core-1.8.0.aar:classes.jar:androidx/core/content/pm/PermissionInfoCompat$Protection.class
      NewCatroid_APKTemplate_2-2-0r.apk:assets/core.jar:androidx/core/content/pm/PermissionInfoCompat$Protection.class
      classes.dex
     */
    @Retention(RetentionPolicy.SOURCE)
    public @interface Protection {
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/core-1.8.0.aar:classes.jar:androidx/core/content/pm/PermissionInfoCompat$ProtectionFlags.class
      NewCatroid_APKTemplate_2-2-0r.apk:assets/core.jar:androidx/core/content/pm/PermissionInfoCompat$ProtectionFlags.class
      classes.dex
     */
    @Retention(RetentionPolicy.SOURCE)
    public @interface ProtectionFlags {
    }

    private PermissionInfoCompat() {
    }

    public static int getProtection(PermissionInfo permissionInfo) {
        if (Build.VERSION.SDK_INT >= 28) {
            return Api28Impl.getProtection(permissionInfo);
        }
        return permissionInfo.protectionLevel & 15;
    }

    public static int getProtectionFlags(PermissionInfo permissionInfo) {
        if (Build.VERSION.SDK_INT >= 28) {
            return Api28Impl.getProtectionFlags(permissionInfo);
        }
        return permissionInfo.protectionLevel & (-16);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/core-1.8.0.aar:classes.jar:androidx/core/content/pm/PermissionInfoCompat$Api28Impl.class
      classes.dex
     */
    static class Api28Impl {
        private Api28Impl() {
        }

        static int getProtection(PermissionInfo permissionInfo) {
            return permissionInfo.getProtection();
        }

        static int getProtectionFlags(PermissionInfo permissionInfo) {
            return permissionInfo.getProtectionFlags();
        }
    }
}

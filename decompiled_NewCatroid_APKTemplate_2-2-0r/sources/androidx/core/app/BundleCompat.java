package androidx.core.app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import androidx.annotation.DoNotInline;
import androidx.annotation.RequiresApi;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/core-1.8.0.aar:classes.jar:androidx/core/app/BundleCompat.class
  NewCatroid_APKTemplate_2-2-0r.apk:assets/core.jar:androidx/core/app/BundleCompat.class
  classes.dex
 */
/* JADX INFO: loaded from: classes.dex */
@Deprecated
public final class BundleCompat {
    private BundleCompat() {
    }

    /* JADX INFO: loaded from: NewCatroid_APKTemplate_2-2-0r.apk:assets/core.jar:androidx/core/app/BundleCompat$BundleCompatBaseImpl.class */
    static class BundleCompatBaseImpl {
        private static final String TAG = "BundleCompatBaseImpl";
        private static Method sGetIBinderMethod;
        private static boolean sGetIBinderMethodFetched;
        private static Method sPutIBinderMethod;
        private static boolean sPutIBinderMethodFetched;

        private BundleCompatBaseImpl() {
        }

        public static IBinder getBinder(Bundle bundle, String key) {
            if (!sGetIBinderMethodFetched) {
                try {
                    sGetIBinderMethod = Bundle.class.getMethod("getIBinder", String.class);
                    sGetIBinderMethod.setAccessible(true);
                } catch (NoSuchMethodException e) {
                    Log.i(TAG, "Failed to retrieve getIBinder method", e);
                }
                sGetIBinderMethodFetched = true;
            }
            if (sGetIBinderMethod != null) {
                try {
                    return (IBinder) sGetIBinderMethod.invoke(bundle, key);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e2) {
                    Log.i(TAG, "Failed to invoke getIBinder via reflection", e2);
                    sGetIBinderMethod = null;
                    return null;
                }
            }
            return null;
        }

        public static void putBinder(Bundle bundle, String key, IBinder binder) {
            if (!sPutIBinderMethodFetched) {
                try {
                    sPutIBinderMethod = Bundle.class.getMethod("putIBinder", String.class, IBinder.class);
                    sPutIBinderMethod.setAccessible(true);
                } catch (NoSuchMethodException e) {
                    Log.i(TAG, "Failed to retrieve putIBinder method", e);
                }
                sPutIBinderMethodFetched = true;
            }
            if (sPutIBinderMethod != null) {
                try {
                    sPutIBinderMethod.invoke(bundle, key, binder);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e2) {
                    Log.i(TAG, "Failed to invoke putIBinder via reflection", e2);
                    sPutIBinderMethod = null;
                }
            }
        }
    }

    public static IBinder getBinder(Bundle bundle, String str) {
        return bundle.getBinder(str);
    }

    /* JADX INFO: loaded from: NewCatroid_APKTemplate_2-2-0r.apk:assets/core-1.8.0.aar:classes.jar:androidx/core/app/BundleCompat$BeforeApi18Impl.class */
    @SuppressLint({"BanUncheckedReflection"})
    static class BeforeApi18Impl {
        private static final String TAG = "BundleCompatBaseImpl";
        private static Method sGetIBinderMethod;
        private static boolean sGetIBinderMethodFetched;
        private static Method sPutIBinderMethod;
        private static boolean sPutIBinderMethodFetched;

        private BeforeApi18Impl() {
        }

        public static IBinder getBinder(Bundle bundle, String key) {
            if (!sGetIBinderMethodFetched) {
                try {
                    sGetIBinderMethod = Bundle.class.getMethod("getIBinder", String.class);
                    sGetIBinderMethod.setAccessible(true);
                } catch (NoSuchMethodException e) {
                    Log.i(TAG, "Failed to retrieve getIBinder method", e);
                }
                sGetIBinderMethodFetched = true;
            }
            if (sGetIBinderMethod != null) {
                try {
                    return (IBinder) sGetIBinderMethod.invoke(bundle, key);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e2) {
                    Log.i(TAG, "Failed to invoke getIBinder via reflection", e2);
                    sGetIBinderMethod = null;
                    return null;
                }
            }
            return null;
        }

        public static void putBinder(Bundle bundle, String key, IBinder binder) {
            if (!sPutIBinderMethodFetched) {
                try {
                    sPutIBinderMethod = Bundle.class.getMethod("putIBinder", String.class, IBinder.class);
                    sPutIBinderMethod.setAccessible(true);
                } catch (NoSuchMethodException e) {
                    Log.i(TAG, "Failed to retrieve putIBinder method", e);
                }
                sPutIBinderMethodFetched = true;
            }
            if (sPutIBinderMethod != null) {
                try {
                    sPutIBinderMethod.invoke(bundle, key, binder);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e2) {
                    Log.i(TAG, "Failed to invoke putIBinder via reflection", e2);
                    sPutIBinderMethod = null;
                }
            }
        }
    }

    public static void putBinder(Bundle bundle, String str, IBinder iBinder) {
        bundle.putBinder(str, iBinder);
    }

    /* JADX INFO: loaded from: NewCatroid_APKTemplate_2-2-0r.apk:assets/core-1.8.0.aar:classes.jar:androidx/core/app/BundleCompat$Api18Impl.class */
    @RequiresApi(18)
    static class Api18Impl {
        private Api18Impl() {
        }

        @DoNotInline
        static IBinder getBinder(Bundle bundle, String key) {
            return bundle.getBinder(key);
        }

        @DoNotInline
        static void putBinder(Bundle bundle, String key, IBinder value) {
            bundle.putBinder(key, value);
        }
    }
}

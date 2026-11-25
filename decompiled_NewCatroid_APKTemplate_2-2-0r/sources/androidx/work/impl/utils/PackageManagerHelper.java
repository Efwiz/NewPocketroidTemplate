package androidx.work.impl.utils;

import android.content.ComponentName;
import android.content.Context;
import androidx.work.Logger;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;

/* JADX INFO: loaded from: classes.dex */
public class PackageManagerHelper {
    private static final String TAG = Logger.tagWithPrefix("PackageManagerHelper");

    private PackageManagerHelper() {
    }

    public static void setComponentEnabled(Context context, Class<?> klazz, boolean enabled) {
        String str = CompilerOptions.ENABLED;
        try {
            context.getPackageManager().setComponentEnabledSetting(new ComponentName(context, klazz.getName()), enabled ? 1 : 2, 1);
            Logger.get().debug(TAG, String.format("%s %s", klazz.getName(), enabled ? CompilerOptions.ENABLED : CompilerOptions.DISABLED), new Throwable[0]);
        } catch (Exception e) {
            Logger logger = Logger.get();
            String str2 = TAG;
            String name2 = klazz.getName();
            if (!enabled) {
                str = CompilerOptions.DISABLED;
            }
            logger.debug(str2, String.format("%s could not be %s", name2, str), e);
        }
    }

    public static boolean isComponentExplicitlyEnabled(Context context, Class<?> klazz) {
        return isComponentExplicitlyEnabled(context, klazz.getName());
    }

    public static boolean isComponentExplicitlyEnabled(Context context, String className) {
        return context.getPackageManager().getComponentEnabledSetting(new ComponentName(context, className)) == 1;
    }
}

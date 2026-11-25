package androidx.core.content.res;

import android.content.res.Resources;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/core-1.8.0.aar:classes.jar:androidx/core/content/res/ConfigurationHelper.class
  NewCatroid_APKTemplate_2-2-0r.apk:assets/core.jar:androidx/core/content/res/ConfigurationHelper.class
  classes.dex
 */
/* JADX INFO: loaded from: classes.dex */
public final class ConfigurationHelper {
    private ConfigurationHelper() {
    }

    public static int getDensityDpi(Resources resources) {
        return resources.getConfiguration().densityDpi;
    }
}

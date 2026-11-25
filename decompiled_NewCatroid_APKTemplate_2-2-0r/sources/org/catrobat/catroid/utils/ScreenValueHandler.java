package org.catrobat.catroid.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import org.catrobat.catroid.common.ScreenValues;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/ScreenValueHandler.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class ScreenValueHandler {
    private ScreenValueHandler() {
        throw new AssertionError();
    }

    public static void updateScreenWidthAndHeight(Context context) {
        if (context != null) {
            WindowManager windowManager = (WindowManager) context.getSystemService(ConfigConstants.CONFIG_KEY_WINDOW);
            DisplayMetrics displayMetrics = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
            ScreenValues.currentScreenResolution = new Resolution(displayMetrics.widthPixels, displayMetrics.heightPixels);
            return;
        }
        ScreenValues.setToDefaultScreenSize();
    }
}

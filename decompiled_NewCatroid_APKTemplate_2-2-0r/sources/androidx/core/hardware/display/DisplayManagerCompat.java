package androidx.core.hardware.display;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.view.Display;
import androidx.annotation.DoNotInline;
import androidx.annotation.RequiresApi;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/core-1.8.0.aar:classes.jar:androidx/core/hardware/display/DisplayManagerCompat.class
  NewCatroid_APKTemplate_2-2-0r.apk:assets/core.jar:androidx/core/hardware/display/DisplayManagerCompat.class
  classes.dex
 */
/* JADX INFO: loaded from: classes.dex */
public final class DisplayManagerCompat {
    public static final String DISPLAY_CATEGORY_PRESENTATION = "android.hardware.display.category.PRESENTATION";
    private final Context mContext;

    private DisplayManagerCompat(Context context) {
        this.mContext = context;
    }

    public static DisplayManagerCompat getInstance(Context context) {
        return new DisplayManagerCompat(context);
    }

    public Display getDisplay(int i) {
        return ((DisplayManager) this.mContext.getSystemService("display")).getDisplay(i);
    }

    public Display[] getDisplays() {
        return ((DisplayManager) this.mContext.getSystemService("display")).getDisplays();
    }

    public Display[] getDisplays(String str) {
        return ((DisplayManager) this.mContext.getSystemService("display")).getDisplays();
    }

    /* JADX INFO: loaded from: NewCatroid_APKTemplate_2-2-0r.apk:assets/core-1.8.0.aar:classes.jar:androidx/core/hardware/display/DisplayManagerCompat$Api17Impl.class */
    @RequiresApi(17)
    static class Api17Impl {
        private Api17Impl() {
        }

        @DoNotInline
        static Display getDisplay(DisplayManager displayManager, int displayId) {
            return displayManager.getDisplay(displayId);
        }

        @DoNotInline
        static Display[] getDisplays(DisplayManager displayManager) {
            return displayManager.getDisplays();
        }
    }
}

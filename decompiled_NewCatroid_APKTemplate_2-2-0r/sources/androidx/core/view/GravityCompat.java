package androidx.core.view;

import android.graphics.Rect;
import android.view.Gravity;
import androidx.annotation.DoNotInline;
import androidx.annotation.RequiresApi;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/core-1.8.0.aar:classes.jar:androidx/core/view/GravityCompat.class
  NewCatroid_APKTemplate_2-2-0r.apk:assets/core.jar:androidx/core/view/GravityCompat.class
  classes.dex
 */
/* JADX INFO: loaded from: classes.dex */
public final class GravityCompat {
    public static final int END = 8388613;
    public static final int RELATIVE_HORIZONTAL_GRAVITY_MASK = 8388615;
    public static final int RELATIVE_LAYOUT_DIRECTION = 8388608;
    public static final int START = 8388611;

    public static void apply(int i, int i2, int i3, Rect rect, Rect rect2, int i4) {
        Gravity.apply(i, i2, i3, rect, rect2, i4);
    }

    public static void apply(int i, int i2, int i3, Rect rect, int i4, int i5, Rect rect2, int i6) {
        Gravity.apply(i, i2, i3, rect, i4, i5, rect2, i6);
    }

    public static void applyDisplay(int i, Rect rect, Rect rect2, int i2) {
        Gravity.applyDisplay(i, rect, rect2, i2);
    }

    public static int getAbsoluteGravity(int i, int i2) {
        return Gravity.getAbsoluteGravity(i, i2);
    }

    private GravityCompat() {
    }

    /* JADX INFO: loaded from: NewCatroid_APKTemplate_2-2-0r.apk:assets/core-1.8.0.aar:classes.jar:androidx/core/view/GravityCompat$Api17Impl.class */
    @RequiresApi(17)
    static class Api17Impl {
        private Api17Impl() {
        }

        @DoNotInline
        static void apply(int gravity, int w, int h, Rect container, Rect outRect, int layoutDirection) {
            Gravity.apply(gravity, w, h, container, outRect, layoutDirection);
        }

        @DoNotInline
        static void apply(int gravity, int w, int h, Rect container, int xAdj, int yAdj, Rect outRect, int layoutDirection) {
            Gravity.apply(gravity, w, h, container, xAdj, yAdj, outRect, layoutDirection);
        }

        @DoNotInline
        static void applyDisplay(int gravity, Rect display, Rect inoutObj, int layoutDirection) {
            Gravity.applyDisplay(gravity, display, inoutObj, layoutDirection);
        }
    }
}

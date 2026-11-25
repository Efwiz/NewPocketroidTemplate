package androidx.core.view;

import android.view.ViewGroup;
import androidx.annotation.DoNotInline;
import androidx.annotation.RequiresApi;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/core-1.8.0.aar:classes.jar:androidx/core/view/MarginLayoutParamsCompat.class
  NewCatroid_APKTemplate_2-2-0r.apk:assets/core.jar:androidx/core/view/MarginLayoutParamsCompat.class
  classes.dex
 */
/* JADX INFO: loaded from: classes.dex */
@Deprecated
public final class MarginLayoutParamsCompat {
    @Deprecated
    public static int getMarginStart(ViewGroup.MarginLayoutParams marginLayoutParams) {
        return marginLayoutParams.getMarginStart();
    }

    @Deprecated
    public static int getMarginEnd(ViewGroup.MarginLayoutParams marginLayoutParams) {
        return marginLayoutParams.getMarginEnd();
    }

    @Deprecated
    public static void setMarginStart(ViewGroup.MarginLayoutParams marginLayoutParams, int i) {
        marginLayoutParams.setMarginStart(i);
    }

    @Deprecated
    public static void setMarginEnd(ViewGroup.MarginLayoutParams marginLayoutParams, int i) {
        marginLayoutParams.setMarginEnd(i);
    }

    @Deprecated
    public static boolean isMarginRelative(ViewGroup.MarginLayoutParams marginLayoutParams) {
        return marginLayoutParams.isMarginRelative();
    }

    @Deprecated
    public static int getLayoutDirection(ViewGroup.MarginLayoutParams marginLayoutParams) {
        int layoutDirection = marginLayoutParams.getLayoutDirection();
        if (layoutDirection == 0 || layoutDirection == 1) {
            return layoutDirection;
        }
        return 0;
    }

    @Deprecated
    public static void setLayoutDirection(ViewGroup.MarginLayoutParams marginLayoutParams, int i) {
        marginLayoutParams.setLayoutDirection(i);
    }

    @Deprecated
    public static void resolveLayoutDirection(ViewGroup.MarginLayoutParams marginLayoutParams, int i) {
        marginLayoutParams.resolveLayoutDirection(i);
    }

    private MarginLayoutParamsCompat() {
    }

    /* JADX INFO: loaded from: NewCatroid_APKTemplate_2-2-0r.apk:assets/core-1.8.0.aar:classes.jar:androidx/core/view/MarginLayoutParamsCompat$Api17Impl.class */
    @RequiresApi(17)
    static class Api17Impl {
        private Api17Impl() {
        }

        @DoNotInline
        static int getMarginStart(ViewGroup.MarginLayoutParams marginLayoutParams) {
            return marginLayoutParams.getMarginStart();
        }

        @DoNotInline
        static int getMarginEnd(ViewGroup.MarginLayoutParams marginLayoutParams) {
            return marginLayoutParams.getMarginEnd();
        }

        @DoNotInline
        static void setMarginStart(ViewGroup.MarginLayoutParams marginLayoutParams, int start) {
            marginLayoutParams.setMarginStart(start);
        }

        @DoNotInline
        static void setMarginEnd(ViewGroup.MarginLayoutParams marginLayoutParams, int end) {
            marginLayoutParams.setMarginEnd(end);
        }

        @DoNotInline
        static boolean isMarginRelative(ViewGroup.MarginLayoutParams marginLayoutParams) {
            return marginLayoutParams.isMarginRelative();
        }

        @DoNotInline
        static int getLayoutDirection(ViewGroup.MarginLayoutParams marginLayoutParams) {
            return marginLayoutParams.getLayoutDirection();
        }

        @DoNotInline
        static void setLayoutDirection(ViewGroup.MarginLayoutParams marginLayoutParams, int layoutDirection) {
            marginLayoutParams.setLayoutDirection(layoutDirection);
        }

        @DoNotInline
        static void resolveLayoutDirection(ViewGroup.MarginLayoutParams marginLayoutParams, int layoutDirection) {
            marginLayoutParams.resolveLayoutDirection(layoutDirection);
        }
    }
}

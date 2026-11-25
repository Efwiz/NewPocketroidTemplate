package androidx.core.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.CheckedTextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import java.lang.reflect.Field;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/core-1.8.0.aar:classes.jar:androidx/core/widget/CheckedTextViewCompat.class
  classes.dex
 */
/* JADX INFO: loaded from: classes.dex */
public final class CheckedTextViewCompat {
    private CheckedTextViewCompat() {
    }

    public static void setCheckMarkTintList(CheckedTextView checkedTextView, ColorStateList colorStateList) {
        Api21Impl.setCheckMarkTintList(checkedTextView, colorStateList);
    }

    public static ColorStateList getCheckMarkTintList(CheckedTextView checkedTextView) {
        return Api21Impl.getCheckMarkTintList(checkedTextView);
    }

    public static void setCheckMarkTintMode(CheckedTextView checkedTextView, PorterDuff.Mode mode) {
        Api21Impl.setCheckMarkTintMode(checkedTextView, mode);
    }

    public static PorterDuff.Mode getCheckMarkTintMode(CheckedTextView checkedTextView) {
        return Api21Impl.getCheckMarkTintMode(checkedTextView);
    }

    public static Drawable getCheckMarkDrawable(CheckedTextView checkedTextView) {
        return checkedTextView.getCheckMarkDrawable();
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/core-1.8.0.aar:classes.jar:androidx/core/widget/CheckedTextViewCompat$Api21Impl.class
      classes.dex
     */
    private static class Api21Impl {
        private Api21Impl() {
        }

        static void setCheckMarkTintList(CheckedTextView checkedTextView, ColorStateList colorStateList) {
            checkedTextView.setCheckMarkTintList(colorStateList);
        }

        static ColorStateList getCheckMarkTintList(CheckedTextView checkedTextView) {
            return checkedTextView.getCheckMarkTintList();
        }

        static void setCheckMarkTintMode(CheckedTextView checkedTextView, PorterDuff.Mode mode) {
            checkedTextView.setCheckMarkTintMode(mode);
        }

        static PorterDuff.Mode getCheckMarkTintMode(CheckedTextView checkedTextView) {
            return checkedTextView.getCheckMarkTintMode();
        }
    }

    /* JADX INFO: loaded from: NewCatroid_APKTemplate_2-2-0r.apk:assets/core-1.8.0.aar:classes.jar:androidx/core/widget/CheckedTextViewCompat$Api16Impl.class */
    @RequiresApi(16)
    private static class Api16Impl {
        private Api16Impl() {
        }

        @Nullable
        static Drawable getCheckMarkDrawable(@NonNull CheckedTextView textView) {
            return textView.getCheckMarkDrawable();
        }
    }

    /* JADX INFO: loaded from: NewCatroid_APKTemplate_2-2-0r.apk:assets/core-1.8.0.aar:classes.jar:androidx/core/widget/CheckedTextViewCompat$Api14Impl.class */
    private static class Api14Impl {
        private static Field sCheckMarkDrawableField;
        private static boolean sResolved;

        private Api14Impl() {
        }

        @Nullable
        static Drawable getCheckMarkDrawable(@NonNull CheckedTextView textView) {
            if (!sResolved) {
                try {
                    sCheckMarkDrawableField = CheckedTextView.class.getDeclaredField("mCheckMarkDrawable");
                    sCheckMarkDrawableField.setAccessible(true);
                } catch (NoSuchFieldException e) {
                    Log.i("CheckedTextViewCompat", "Failed to retrieve mCheckMarkDrawable field", e);
                }
                sResolved = true;
            }
            if (sCheckMarkDrawableField != null) {
                try {
                    return (Drawable) sCheckMarkDrawableField.get(textView);
                } catch (IllegalAccessException e2) {
                    Log.i("CheckedTextViewCompat", "Failed to get check mark drawable via reflection", e2);
                    sCheckMarkDrawableField = null;
                    return null;
                }
            }
            return null;
        }
    }
}

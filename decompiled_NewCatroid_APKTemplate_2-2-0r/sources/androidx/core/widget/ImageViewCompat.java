package androidx.core.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.widget.ImageView;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/core-1.8.0.aar:classes.jar:androidx/core/widget/ImageViewCompat.class
  NewCatroid_APKTemplate_2-2-0r.apk:assets/core.jar:androidx/core/widget/ImageViewCompat.class
  classes.dex
 */
/* JADX INFO: loaded from: classes.dex */
public class ImageViewCompat {
    public static ColorStateList getImageTintList(ImageView imageView) {
        return Api21Impl.getImageTintList(imageView);
    }

    public static void setImageTintList(ImageView imageView, ColorStateList colorStateList) {
        Api21Impl.setImageTintList(imageView, colorStateList);
    }

    public static PorterDuff.Mode getImageTintMode(ImageView imageView) {
        return Api21Impl.getImageTintMode(imageView);
    }

    public static void setImageTintMode(ImageView imageView, PorterDuff.Mode mode) {
        Api21Impl.setImageTintMode(imageView, mode);
    }

    private ImageViewCompat() {
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/core-1.8.0.aar:classes.jar:androidx/core/widget/ImageViewCompat$Api21Impl.class
      classes.dex
     */
    static class Api21Impl {
        private Api21Impl() {
        }

        static ColorStateList getImageTintList(ImageView imageView) {
            return imageView.getImageTintList();
        }

        static void setImageTintList(ImageView imageView, ColorStateList colorStateList) {
            imageView.setImageTintList(colorStateList);
        }

        static PorterDuff.Mode getImageTintMode(ImageView imageView) {
            return imageView.getImageTintMode();
        }

        static void setImageTintMode(ImageView imageView, PorterDuff.Mode mode) {
            imageView.setImageTintMode(mode);
        }
    }
}

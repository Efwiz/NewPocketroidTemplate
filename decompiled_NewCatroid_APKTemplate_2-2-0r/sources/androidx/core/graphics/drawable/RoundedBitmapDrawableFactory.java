package androidx.core.graphics.drawable;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.Log;
import androidx.core.graphics.BitmapCompat;
import androidx.core.view.GravityCompat;
import java.io.InputStream;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/core-1.8.0.aar:classes.jar:androidx/core/graphics/drawable/RoundedBitmapDrawableFactory.class
  NewCatroid_APKTemplate_2-2-0r.apk:assets/core.jar:androidx/core/graphics/drawable/RoundedBitmapDrawableFactory.class
  classes.dex
 */
/* JADX INFO: loaded from: classes.dex */
public final class RoundedBitmapDrawableFactory {
    private static final String TAG = "RoundedBitmapDrawableFa";

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/core-1.8.0.aar:classes.jar:androidx/core/graphics/drawable/RoundedBitmapDrawableFactory$DefaultRoundedBitmapDrawable.class
      NewCatroid_APKTemplate_2-2-0r.apk:assets/core.jar:androidx/core/graphics/drawable/RoundedBitmapDrawableFactory$DefaultRoundedBitmapDrawable.class
      classes.dex
     */
    private static class DefaultRoundedBitmapDrawable extends RoundedBitmapDrawable {
        DefaultRoundedBitmapDrawable(Resources resources, Bitmap bitmap) {
            super(resources, bitmap);
        }

        @Override // androidx.core.graphics.drawable.RoundedBitmapDrawable
        public void setMipMap(boolean z) {
            if (this.mBitmap != null) {
                BitmapCompat.setHasMipMap(this.mBitmap, z);
                invalidateSelf();
            }
        }

        @Override // androidx.core.graphics.drawable.RoundedBitmapDrawable
        public boolean hasMipMap() {
            return this.mBitmap != null && BitmapCompat.hasMipMap(this.mBitmap);
        }

        @Override // androidx.core.graphics.drawable.RoundedBitmapDrawable
        void gravityCompatApply(int i, int i2, int i3, Rect rect, Rect rect2) {
            GravityCompat.apply(i, i2, i3, rect, rect2, 0);
        }
    }

    public static RoundedBitmapDrawable create(Resources resources, Bitmap bitmap) {
        return new RoundedBitmapDrawable21(resources, bitmap);
    }

    public static RoundedBitmapDrawable create(Resources resources, String str) {
        RoundedBitmapDrawable roundedBitmapDrawableCreate = create(resources, BitmapFactory.decodeFile(str));
        if (roundedBitmapDrawableCreate.getBitmap() == null) {
            Log.w(TAG, "RoundedBitmapDrawable cannot decode " + str);
        }
        return roundedBitmapDrawableCreate;
    }

    public static RoundedBitmapDrawable create(Resources resources, InputStream inputStream) {
        RoundedBitmapDrawable roundedBitmapDrawableCreate = create(resources, BitmapFactory.decodeStream(inputStream));
        if (roundedBitmapDrawableCreate.getBitmap() == null) {
            Log.w(TAG, "RoundedBitmapDrawable cannot decode " + inputStream);
        }
        return roundedBitmapDrawableCreate;
    }

    private RoundedBitmapDrawableFactory() {
    }
}

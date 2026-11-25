package androidx.core.util;

import android.util.SizeF;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/core-1.8.0.aar:classes.jar:androidx/core/util/SizeFCompat.class
  classes.dex
 */
/* JADX INFO: loaded from: classes.dex */
public final class SizeFCompat {
    private final float mHeight;
    private final float mWidth;

    public SizeFCompat(float f, float f2) {
        this.mWidth = Preconditions.checkArgumentFinite(f, "width");
        this.mHeight = Preconditions.checkArgumentFinite(f2, "height");
    }

    public float getWidth() {
        return this.mWidth;
    }

    public float getHeight() {
        return this.mHeight;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SizeFCompat)) {
            return false;
        }
        SizeFCompat sizeFCompat = (SizeFCompat) obj;
        return sizeFCompat.mWidth == this.mWidth && sizeFCompat.mHeight == this.mHeight;
    }

    public int hashCode() {
        return Float.floatToIntBits(this.mWidth) ^ Float.floatToIntBits(this.mHeight);
    }

    public String toString() {
        return this.mWidth + "x" + this.mHeight;
    }

    public SizeF toSizeF() {
        return Api21Impl.toSizeF(this);
    }

    public static SizeFCompat toSizeFCompat(SizeF sizeF) {
        return Api21Impl.toSizeFCompat(sizeF);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/core-1.8.0.aar:classes.jar:androidx/core/util/SizeFCompat$Api21Impl.class
      classes.dex
     */
    private static final class Api21Impl {
        private Api21Impl() {
        }

        static SizeFCompat toSizeFCompat(SizeF sizeF) {
            Preconditions.checkNotNull(sizeF);
            return new SizeFCompat(sizeF.getWidth(), sizeF.getHeight());
        }

        static SizeF toSizeF(SizeFCompat sizeFCompat) {
            Preconditions.checkNotNull(sizeFCompat);
            return new SizeF(sizeFCompat.getWidth(), sizeFCompat.getHeight());
        }
    }
}

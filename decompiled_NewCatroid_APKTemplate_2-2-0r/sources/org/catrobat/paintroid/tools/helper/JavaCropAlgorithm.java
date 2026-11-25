package org.catrobat.paintroid.tools.helper;

import android.graphics.Bitmap;
import android.graphics.Rect;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX INFO: compiled from: JavaCropAlgorithm.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0014\u0010\u0007\u001a\u0004\u0018\u00010\u00062\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004H\u0016J\r\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\u0002\u0010\nJ\r\u0010\u000b\u001a\u0004\u0018\u00010\t¢\u0006\u0002\u0010\nJ\r\u0010\f\u001a\u0004\u0018\u00010\t¢\u0006\u0002\u0010\nJ\r\u0010\r\u001a\u0004\u0018\u00010\t¢\u0006\u0002\u0010\nJ\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0003\u001a\u00020\u0004J\u0018\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\tH\u0002J\f\u0010\u0014\u001a\u00020\u0015*\u00020\u0006H\u0002J\f\u0010\u0016\u001a\u00020\u0015*\u00020\u0006H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lorg/catrobat/paintroid/tools/helper/JavaCropAlgorithm;", "Lorg/catrobat/paintroid/tools/helper/CropAlgorithm;", "()V", "bitmap", "Landroid/graphics/Bitmap;", "bounds", "Landroid/graphics/Rect;", "crop", "getBottommostCellWithOpaquePixel", "", "()Ljava/lang/Integer;", "getLeftmostCellWithOpaquePixel", "getRightmostCellWithOpaquePixel", "getTopmostCellWithOpaquePixel", ConfigConstants.CONFIG_INIT_SECTION, "", "isOpaquePixel", "", "x", "y", "xRange", "Lkotlin/ranges/IntRange;", "yRange", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class JavaCropAlgorithm implements CropAlgorithm {
    private Bitmap bitmap;
    private Rect bounds;

    private final IntRange yRange(Rect rect) {
        return new IntRange(rect.top, rect.bottom);
    }

    private final IntRange xRange(Rect rect) {
        return new IntRange(rect.left, rect.right);
    }

    private final boolean isOpaquePixel(int x, int y) {
        Bitmap bitmap = this.bitmap;
        if (bitmap == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bitmap");
            bitmap = null;
        }
        return bitmap.getPixel(x, y) != 0;
    }

    public final Integer getTopmostCellWithOpaquePixel() {
        Integer next;
        Rect rect = this.bounds;
        Integer num = null;
        if (rect == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bounds");
            rect = null;
        }
        for (Integer num2 : yRange(rect)) {
            int iIntValue = num2.intValue();
            Rect rect2 = this.bounds;
            if (rect2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("bounds");
                rect2 = null;
            }
            Iterator<Integer> it = xRange(rect2).iterator();
            do {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
            } while (!isOpaquePixel(next.intValue(), iIntValue));
            if (next != null) {
                num = num2;
                break;
            }
        }
        return num;
    }

    public final Integer getBottommostCellWithOpaquePixel() {
        Integer next;
        Rect rect = this.bounds;
        if (rect == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bounds");
            rect = null;
        }
        Integer num = null;
        for (Integer num2 : yRange(rect)) {
            int iIntValue = num2.intValue();
            Rect rect2 = this.bounds;
            if (rect2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("bounds");
                rect2 = null;
            }
            Iterator<Integer> it = xRange(rect2).iterator();
            do {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
            } while (!isOpaquePixel(next.intValue(), iIntValue));
            if (next != null) {
                num = num2;
            }
        }
        return num;
    }

    public final Integer getLeftmostCellWithOpaquePixel() {
        Integer next;
        Rect rect = this.bounds;
        Integer num = null;
        if (rect == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bounds");
            rect = null;
        }
        for (Integer num2 : xRange(rect)) {
            int iIntValue = num2.intValue();
            Rect rect2 = this.bounds;
            if (rect2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("bounds");
                rect2 = null;
            }
            Iterator<Integer> it = yRange(rect2).iterator();
            do {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
            } while (!isOpaquePixel(iIntValue, next.intValue()));
            if (next != null) {
                num = num2;
                break;
            }
        }
        return num;
    }

    public final Integer getRightmostCellWithOpaquePixel() {
        Integer next;
        Rect rect = this.bounds;
        if (rect == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bounds");
            rect = null;
        }
        Integer num = null;
        for (Integer num2 : xRange(rect)) {
            int iIntValue = num2.intValue();
            Rect rect2 = this.bounds;
            if (rect2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("bounds");
                rect2 = null;
            }
            Iterator<Integer> it = yRange(rect2).iterator();
            do {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
            } while (!isOpaquePixel(iIntValue, next.intValue()));
            if (next != null) {
                num = num2;
            }
        }
        return num;
    }

    public final void init(Bitmap bitmap) {
        Intrinsics.checkNotNullParameter(bitmap, "bitmap");
        this.bitmap = bitmap;
        this.bounds = new Rect(0, 0, bitmap.getWidth() - 1, bitmap.getHeight() - 1);
    }

    @Override // org.catrobat.paintroid.tools.helper.CropAlgorithm
    public Rect crop(Bitmap bitmap) {
        int iIntValue;
        int iIntValue2;
        int iIntValue3;
        if (bitmap == null) {
            return null;
        }
        this.bitmap = bitmap;
        Rect rect = new Rect(0, 0, bitmap.getWidth() - 1, bitmap.getHeight() - 1);
        this.bounds = rect;
        Integer topmostCellWithOpaquePixel = getTopmostCellWithOpaquePixel();
        if (topmostCellWithOpaquePixel == null) {
            return null;
        }
        rect.top = topmostCellWithOpaquePixel.intValue();
        Rect rect2 = this.bounds;
        if (rect2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bounds");
            rect2 = null;
        }
        Integer bottommostCellWithOpaquePixel = getBottommostCellWithOpaquePixel();
        if (bottommostCellWithOpaquePixel == null) {
            Rect rect3 = this.bounds;
            if (rect3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("bounds");
                rect3 = null;
            }
            iIntValue = rect3.top - 1;
        } else {
            iIntValue = bottommostCellWithOpaquePixel.intValue();
        }
        rect2.bottom = iIntValue;
        Rect rect4 = this.bounds;
        if (rect4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bounds");
            rect4 = null;
        }
        Integer leftmostCellWithOpaquePixel = getLeftmostCellWithOpaquePixel();
        if (leftmostCellWithOpaquePixel == null) {
            Rect rect5 = this.bounds;
            if (rect5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("bounds");
                rect5 = null;
            }
            iIntValue2 = rect5.right + 1;
        } else {
            iIntValue2 = leftmostCellWithOpaquePixel.intValue();
        }
        rect4.left = iIntValue2;
        Rect rect6 = this.bounds;
        if (rect6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bounds");
            rect6 = null;
        }
        Integer rightmostCellWithOpaquePixel = getRightmostCellWithOpaquePixel();
        if (rightmostCellWithOpaquePixel == null) {
            Rect rect7 = this.bounds;
            if (rect7 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("bounds");
                rect7 = null;
            }
            iIntValue3 = rect7.left - 1;
        } else {
            iIntValue3 = rightmostCellWithOpaquePixel.intValue();
        }
        rect6.right = iIntValue3;
        Rect rect8 = this.bounds;
        if (rect8 != null) {
            return rect8;
        }
        Intrinsics.throwUninitializedPropertyAccessException("bounds");
        return null;
    }
}

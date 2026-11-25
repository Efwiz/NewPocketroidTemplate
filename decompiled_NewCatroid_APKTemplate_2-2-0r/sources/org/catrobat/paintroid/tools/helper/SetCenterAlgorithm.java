package org.catrobat.paintroid.tools.helper;

import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.Rect;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: SetCenterAlgorithm.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\f\u001a\u00020\rH\u0002J\u001c\u0010\u000e\u001a\u0004\u0018\u00010\b2\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\u0010\n\u001a\u0004\u0018\u00010\u000bJ\b\u0010\u000f\u001a\u00020\u0010H\u0002J\b\u0010\u0011\u001a\u00020\u0010H\u0002J\b\u0010\u0012\u001a\u00020\u0010H\u0002J\b\u0010\u0013\u001a\u00020\u0010H\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082.¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lorg/catrobat/paintroid/tools/helper/SetCenterAlgorithm;", "", "javaCropAlgorithm", "Lorg/catrobat/paintroid/tools/helper/JavaCropAlgorithm;", "(Lorg/catrobat/paintroid/tools/helper/JavaCropAlgorithm;)V", "bitmap", "Landroid/graphics/Bitmap;", "bounds", "Landroid/graphics/Rect;", "cropAlgorithm", "position", "Landroid/graphics/PointF;", "checkIfNothingFound", "", "crop", "getDistanceBottom", "", "getDistanceLeft", "getDistanceRight", "getDistanceTop", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class SetCenterAlgorithm {
    private Bitmap bitmap;
    private Rect bounds;
    private JavaCropAlgorithm cropAlgorithm;
    private PointF position;

    public SetCenterAlgorithm(JavaCropAlgorithm javaCropAlgorithm) {
        Intrinsics.checkNotNullParameter(javaCropAlgorithm, "javaCropAlgorithm");
        this.cropAlgorithm = javaCropAlgorithm;
    }

    private final int getDistanceLeft() {
        Integer leftmostCellWithOpaquePixel = this.cropAlgorithm.getLeftmostCellWithOpaquePixel();
        if (leftmostCellWithOpaquePixel == null) {
            return Integer.MIN_VALUE;
        }
        int iIntValue = leftmostCellWithOpaquePixel.intValue();
        PointF pointF = this.position;
        if (pointF == null) {
            Intrinsics.throwUninitializedPropertyAccessException("position");
            pointF = null;
        }
        return ((int) pointF.x) - iIntValue;
    }

    private final int getDistanceRight() {
        Integer rightmostCellWithOpaquePixel = this.cropAlgorithm.getRightmostCellWithOpaquePixel();
        if (rightmostCellWithOpaquePixel == null) {
            return Integer.MIN_VALUE;
        }
        int iIntValue = rightmostCellWithOpaquePixel.intValue();
        PointF pointF = this.position;
        if (pointF == null) {
            Intrinsics.throwUninitializedPropertyAccessException("position");
            pointF = null;
        }
        return iIntValue - ((int) pointF.x);
    }

    private final int getDistanceTop() {
        Integer topmostCellWithOpaquePixel = this.cropAlgorithm.getTopmostCellWithOpaquePixel();
        if (topmostCellWithOpaquePixel == null) {
            return Integer.MIN_VALUE;
        }
        int iIntValue = topmostCellWithOpaquePixel.intValue();
        PointF pointF = this.position;
        if (pointF == null) {
            Intrinsics.throwUninitializedPropertyAccessException("position");
            pointF = null;
        }
        return ((int) pointF.y) - iIntValue;
    }

    private final int getDistanceBottom() {
        Integer bottommostCellWithOpaquePixel = this.cropAlgorithm.getBottommostCellWithOpaquePixel();
        if (bottommostCellWithOpaquePixel == null) {
            return Integer.MIN_VALUE;
        }
        int iIntValue = bottommostCellWithOpaquePixel.intValue();
        PointF pointF = this.position;
        if (pointF == null) {
            Intrinsics.throwUninitializedPropertyAccessException("position");
            pointF = null;
        }
        return iIntValue - ((int) pointF.y);
    }

    private final boolean checkIfNothingFound() {
        return this.cropAlgorithm.getLeftmostCellWithOpaquePixel() == null;
    }

    public final Rect crop(Bitmap bitmap, PointF position) {
        if (bitmap == null) {
            return null;
        }
        this.bitmap = bitmap;
        if (position == null) {
            return null;
        }
        this.position = position;
        this.cropAlgorithm.init(bitmap);
        if (checkIfNothingFound()) {
            return null;
        }
        int distanceLeft = getDistanceLeft();
        int distanceRight = getDistanceRight();
        int distanceTop = getDistanceTop();
        int distanceBottom = getDistanceBottom();
        Rect rect = new Rect(0, 0, 0, 0);
        this.bounds = rect;
        if (distanceTop <= distanceBottom) {
            distanceTop = distanceBottom;
        }
        if (distanceLeft <= distanceRight) {
            distanceLeft = distanceRight;
        }
        rect.top = ((int) position.y) - distanceTop;
        Rect rect2 = this.bounds;
        if (rect2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bounds");
            rect2 = null;
        }
        rect2.bottom = ((int) position.y) + distanceTop;
        Rect rect3 = this.bounds;
        if (rect3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bounds");
            rect3 = null;
        }
        rect3.left = ((int) position.x) - distanceLeft;
        Rect rect4 = this.bounds;
        if (rect4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bounds");
            rect4 = null;
        }
        rect4.right = ((int) position.x) + distanceLeft;
        Rect rect5 = this.bounds;
        if (rect5 != null) {
            return rect5;
        }
        Intrinsics.throwUninitializedPropertyAccessException("bounds");
        return null;
    }
}

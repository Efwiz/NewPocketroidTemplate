package org.catrobat.catroid.utils;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/Resolution.class
  classes14.dex
 */
/* JADX INFO: compiled from: Resolution.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000b\n\u0002\b\t\u0018\u00002\u00020\u0001B+\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003¢\u0006\u0002\u0010\u0007J\u0006\u0010\r\u001a\u00020\u000eJ\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0000H\u0002J\u000e\u0010\u0012\u001a\u00020\u00002\u0006\u0010\u0011\u001a\u00020\u0000J\u0006\u0010\u0013\u001a\u00020\u0000J\u0006\u0010\u0014\u001a\u00020\u0000J\u0006\u0010\u0015\u001a\u00020\u0010J\u0006\u0010\u0016\u001a\u00020\u0010J\u000e\u0010\u0017\u001a\u00020\u00002\u0006\u0010\u0011\u001a\u00020\u0000J\u000e\u0010\u0018\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0000R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\t¨\u0006\u0019"}, d2 = {"Lorg/catrobat/catroid/utils/Resolution;", "", "width", "", "height", "offsetX", "offsetY", "(IIII)V", "getHeight", "()I", "getOffsetX", "getOffsetY", "getWidth", "aspectRatio", "", "doesOrientationMatch", "", "target", "flipToFit", "flipToLandscape", "flipToPortrait", "isInLandscapeMode", "isInPortraitMode", "resizeToFit", "sameRatioOrMeasurements", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Resolution {
    private final int height;
    private final int offsetX;
    private final int offsetY;
    private final int width;

    public Resolution(int i, int i2) {
        this(i, i2, 0, 0, 12, null);
    }

    public Resolution(int i, int i2, int i3) {
        this(i, i2, i3, 0, 8, null);
    }

    public Resolution(int i, int i2, int i3, int i4) {
        this.width = i;
        this.height = i2;
        this.offsetX = i3;
        this.offsetY = i4;
        if (i == 0) {
            throw new IllegalArgumentException("Screen width may not be 0;");
        }
        if (i2 == 0) {
            throw new IllegalArgumentException("Screen height may not be 0;");
        }
    }

    public /* synthetic */ Resolution(int i, int i2, int i3, int i4, int i5, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, i2, (i5 & 4) != 0 ? 0 : i3, (i5 & 8) != 0 ? 0 : i4);
    }

    public final int getWidth() {
        return this.width;
    }

    public final int getHeight() {
        return this.height;
    }

    public final int getOffsetX() {
        return this.offsetX;
    }

    public final int getOffsetY() {
        return this.offsetY;
    }

    public final boolean isInPortraitMode() {
        return this.width < this.height;
    }

    public final boolean isInLandscapeMode() {
        return !isInPortraitMode();
    }

    public final float aspectRatio() {
        return this.width / this.height;
    }

    public final Resolution flipToFit(Resolution target) {
        Intrinsics.checkNotNullParameter(target, "target");
        return doesOrientationMatch(target) ? new Resolution(this.width, this.height, this.offsetX, this.offsetY) : new Resolution(this.height, this.width, this.offsetY, this.offsetX);
    }

    public final Resolution flipToPortrait() {
        return flipToFit(new Resolution(1, 2, 0, 0, 12, null));
    }

    public final Resolution flipToLandscape() {
        return flipToFit(new Resolution(2, 1, 0, 0, 12, null));
    }

    private final boolean doesOrientationMatch(Resolution target) {
        return (target.height > target.width && isInPortraitMode()) || (target.height < target.width && isInLandscapeMode());
    }

    public final Resolution resizeToFit(Resolution target) {
        Intrinsics.checkNotNullParameter(target, "target");
        float f = target.height / this.height;
        float f2 = target.width / this.width;
        if (aspectRatio() < target.aspectRatio()) {
            float f3 = f / f2;
            int i = target.width;
            int i2 = (int) (i * f3);
            return new Resolution(i2, target.height, (int) (((double) (i - i2)) / 2.0d), 0);
        }
        if (aspectRatio() > target.aspectRatio()) {
            float f4 = f2 / f;
            int i3 = target.height;
            int i4 = (int) (i3 * f4);
            return new Resolution(target.width, i4, 0, (int) (((double) (i3 - i4)) / 2.0d));
        }
        return new Resolution(target.width, target.height, 0, 0);
    }

    public final boolean sameRatioOrMeasurements(Resolution target) {
        Intrinsics.checkNotNullParameter(target, "target");
        return (this.width == target.width && this.height == target.height) || ((double) Math.abs(aspectRatio() - target.aspectRatio())) < 1.0E-7d;
    }
}

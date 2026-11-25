package org.catrobat.paintroid.tools.drawable;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: DrawableFactory.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lorg/catrobat/paintroid/tools/drawable/DrawableFactory;", "", "()V", "createDrawable", "Lorg/catrobat/paintroid/tools/drawable/ShapeDrawable;", "shape", "Lorg/catrobat/paintroid/tools/drawable/DrawableShape;", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class DrawableFactory {

    /* JADX INFO: compiled from: DrawableFactory.kt */
    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[DrawableShape.values().length];
            iArr[DrawableShape.RECTANGLE.ordinal()] = 1;
            iArr[DrawableShape.OVAL.ordinal()] = 2;
            iArr[DrawableShape.HEART.ordinal()] = 3;
            iArr[DrawableShape.STAR.ordinal()] = 4;
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public final ShapeDrawable createDrawable(DrawableShape shape) {
        Intrinsics.checkNotNullParameter(shape, "shape");
        int i = WhenMappings.$EnumSwitchMapping$0[shape.ordinal()];
        if (i == 1) {
            return new RectangleDrawable();
        }
        if (i == 2) {
            return new OvalDrawable();
        }
        if (i == 3) {
            return new HeartDrawable();
        }
        if (i == 4) {
            return new StarDrawable();
        }
        throw new NoWhenBranchMatchedException();
    }
}

package org.catrobat.catroid.sensing;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/sensing/BoundingRectangle.class
  classes14.dex
 */
/* JADX INFO: compiled from: ConditionMatcherRunner.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u000f\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\u0018\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003*\b\u0012\u0004\u0012\u00020\u00040\u0003H\u0002J\u0018\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003*\b\u0012\u0004\u0012\u00020\u00040\u0003H\u0002R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\n\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\tR\u0011\u0010\f\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\tR\u0011\u0010\u000e\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\tR\u0011\u0010\u0010\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\tR\u0011\u0010\u0012\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\t¨\u0006\u0016"}, d2 = {"Lorg/catrobat/catroid/sensing/BoundingRectangle;", "", "vertices", "", "", "(Ljava/util/List;)V", "bottom", "", "getBottom", "()I", "height", "getHeight", "left", "getLeft", "right", "getRight", "top", "getTop", "width", "getWidth", "getXVertices", "getYVertices", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BoundingRectangle {
    private final int bottom;
    private final int height;
    private final int left;
    private final int right;
    private final int top;
    private final int width;

    public BoundingRectangle(List<Float> vertices) {
        Intrinsics.checkNotNullParameter(vertices, "vertices");
        Float fMinOrNull = CollectionsKt.minOrNull((Iterable<? extends Float>) getYVertices(vertices));
        int iFloatValue = fMinOrNull != null ? (int) fMinOrNull.floatValue() : 0;
        this.top = iFloatValue;
        Float fMaxOrNull = CollectionsKt.maxOrNull((Iterable<? extends Float>) getYVertices(vertices));
        int iFloatValue2 = fMaxOrNull != null ? (int) fMaxOrNull.floatValue() : 0;
        this.bottom = iFloatValue2;
        Float fMinOrNull2 = CollectionsKt.minOrNull((Iterable<? extends Float>) getXVertices(vertices));
        int iFloatValue3 = fMinOrNull2 != null ? (int) fMinOrNull2.floatValue() : 0;
        this.left = iFloatValue3;
        Float fMaxOrNull2 = CollectionsKt.maxOrNull((Iterable<? extends Float>) getXVertices(vertices));
        int iFloatValue4 = fMaxOrNull2 != null ? (int) fMaxOrNull2.floatValue() : 0;
        this.right = iFloatValue4;
        this.width = iFloatValue4 - iFloatValue3;
        this.height = iFloatValue2 - iFloatValue;
    }

    public final int getTop() {
        return this.top;
    }

    public final int getBottom() {
        return this.bottom;
    }

    public final int getLeft() {
        return this.left;
    }

    public final int getRight() {
        return this.right;
    }

    public final int getWidth() {
        return this.width;
    }

    public final int getHeight() {
        return this.height;
    }

    private final List<Float> getYVertices(List<Float> list) {
        ArrayList arrayList = new ArrayList();
        int i = 0;
        for (Object obj : list) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            ((Number) obj).floatValue();
            if (ConditionMatcherRunnerKt.isOdd(i)) {
                arrayList.add(obj);
            }
            i = i2;
        }
        return arrayList;
    }

    private final List<Float> getXVertices(List<Float> list) {
        ArrayList arrayList = new ArrayList();
        int i = 0;
        for (Object obj : list) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            ((Number) obj).floatValue();
            if (!ConditionMatcherRunnerKt.isOdd(i)) {
                arrayList.add(obj);
            }
            i = i2;
        }
        return arrayList;
    }
}

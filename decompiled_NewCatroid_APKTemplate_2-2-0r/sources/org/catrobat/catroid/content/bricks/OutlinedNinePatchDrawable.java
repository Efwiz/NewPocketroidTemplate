package org.catrobat.catroid.content.bricks;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import com.reandroid.arsc.value.ResConfigBase;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/OutlinedNinePatchDrawable.class
  classes14.dex
 */
/* JADX INFO: compiled from: SubCategoryHeaderBrick.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B5\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0004\u0012\u0006\u0010\u0007\u001a\u00020\u0004\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\b\u0010\u0015\u001a\u00020\u0004H\u0016J\u0010\u0010\u0016\u001a\u00020\u00122\u0006\u0010\u0017\u001a\u00020\u0018H\u0014J\u0010\u0010\u0019\u001a\u00020\u00122\u0006\u0010\u001a\u001a\u00020\u0004H\u0016J\u0012\u0010\u001b\u001a\u00020\u00122\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0016R6\u0010\u000b\u001a*\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\r0\fj\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\r`\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001e"}, d2 = {"Lorg/catrobat/catroid/content/bricks/OutlinedNinePatchDrawable;", "Landroid/graphics/drawable/Drawable;", "original", "outlineColor", "", "outlineSizePx", "safetyMarginHorizontal", "safetyMarginVertical", ResConfigBase.NAME_density, "", "(Landroid/graphics/drawable/Drawable;IIIIF)V", "offsets", "Ljava/util/ArrayList;", "Lkotlin/Pair;", "Lkotlin/collections/ArrayList;", "whiteFilter", "Landroid/graphics/PorterDuffColorFilter;", "draw", "", "canvas", "Landroid/graphics/Canvas;", "getOpacity", "onBoundsChange", "bounds", "Landroid/graphics/Rect;", "setAlpha", "alpha", "setColorFilter", "colorFilter", "Landroid/graphics/ColorFilter;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class OutlinedNinePatchDrawable extends Drawable {
    private final ArrayList<Pair<Integer, Integer>> offsets;
    private final Drawable original;
    private final int outlineColor;
    private final int outlineSizePx;
    private final int safetyMarginHorizontal;
    private final int safetyMarginVertical;
    private final PorterDuffColorFilter whiteFilter;

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
    }

    public OutlinedNinePatchDrawable(Drawable original, int i, int i2, int i3, int i4, float f) {
        Intrinsics.checkNotNullParameter(original, "original");
        this.original = original;
        this.outlineColor = i;
        this.outlineSizePx = i2;
        this.safetyMarginHorizontal = i3;
        this.safetyMarginVertical = i4;
        this.whiteFilter = new PorterDuffColorFilter(i, PorterDuff.Mode.SRC_IN);
        this.offsets = new ArrayList<>();
        for (int i5 = 0; i5 < 12; i5++) {
            double d = (((double) (i5 * 2)) * 3.141592653589793d) / ((double) 12);
            this.offsets.add(TuplesKt.to(Integer.valueOf((int) (((double) this.outlineSizePx) * Math.cos(d))), Integer.valueOf((int) (((double) this.outlineSizePx) * Math.sin(d)))));
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Rect bounds = getBounds();
        Intrinsics.checkNotNullExpressionValue(bounds, "getBounds(...)");
        Rect rect = new Rect(bounds.left + this.safetyMarginHorizontal, bounds.top + this.safetyMarginVertical, bounds.right - this.safetyMarginHorizontal, bounds.bottom - this.safetyMarginVertical);
        this.original.setColorFilter(this.whiteFilter);
        for (Pair<Integer, Integer> pair : this.offsets) {
            int iIntValue = pair.component1().intValue();
            int iIntValue2 = pair.component2().intValue();
            this.original.setBounds(rect.left + iIntValue, rect.top + iIntValue2, rect.right + iIntValue, rect.bottom + iIntValue2);
            this.original.draw(canvas);
        }
        this.original.setBounds(rect.left, rect.top, rect.right, rect.bottom);
        this.original.draw(canvas);
        this.original.setBounds(rect);
        this.original.setColorFilter(null);
        this.original.draw(canvas);
        this.original.setBounds(bounds);
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int alpha) {
        this.original.setAlpha(alpha);
    }

    @Override // android.graphics.drawable.Drawable
    protected void onBoundsChange(Rect bounds) {
        Intrinsics.checkNotNullParameter(bounds, "bounds");
        super.onBoundsChange(bounds);
        this.original.setBounds(bounds);
    }
}

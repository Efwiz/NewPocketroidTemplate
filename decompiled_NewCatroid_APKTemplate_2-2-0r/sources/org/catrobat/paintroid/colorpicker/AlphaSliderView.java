package org.catrobat.paintroid.colorpicker;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AlphaSliderView.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u0000 \u00182\u00020\u0001:\u0001\u0018B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u0019\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u0007B!\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014J\u000e\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\tR\u001a\u0010\u000b\u001a\u00020\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\u00020\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\r\"\u0004\b\u0012\u0010\u000f¨\u0006\u0019"}, d2 = {"Lorg/catrobat/paintroid/colorpicker/AlphaSliderView;", "Landroid/widget/LinearLayout;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "isCatroid", "", "()Z", "setCatroid", "(Z)V", "openedFromFormulaEditorInCatroidFlag", "getOpenedFromFormulaEditorInCatroidFlag", "setOpenedFromFormulaEditorInCatroidFlag", "getAlphaSlider", "Lorg/catrobat/paintroid/colorpicker/AlphaSlider;", "setSelectedColor", "", TypedValues.Custom.S_COLOR, "Companion", "colorpicker_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class AlphaSliderView extends LinearLayout {
    private static AlphaSlider alphaSlider;
    private boolean isCatroid;
    private boolean openedFromFormulaEditorInCatroidFlag;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AlphaSliderView(Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        Context context2 = getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "context");
        AlphaSlider alphaSlider2 = new AlphaSlider(context2);
        alphaSlider = alphaSlider2;
        alphaSlider2.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        addView(alphaSlider);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AlphaSliderView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Intrinsics.checkNotNullParameter(context, "context");
        Context context2 = getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "context");
        AlphaSlider alphaSlider2 = new AlphaSlider(context2);
        alphaSlider = alphaSlider2;
        alphaSlider2.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        addView(alphaSlider);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AlphaSliderView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Intrinsics.checkNotNullParameter(context, "context");
        Context context2 = getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "context");
        AlphaSlider alphaSlider2 = new AlphaSlider(context2);
        alphaSlider = alphaSlider2;
        alphaSlider2.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        addView(alphaSlider);
    }

    /* JADX INFO: renamed from: isCatroid, reason: from getter */
    public final boolean getIsCatroid() {
        return this.isCatroid;
    }

    public final void setCatroid(boolean z) {
        this.isCatroid = z;
    }

    public final boolean getOpenedFromFormulaEditorInCatroidFlag() {
        return this.openedFromFormulaEditorInCatroidFlag;
    }

    public final void setOpenedFromFormulaEditorInCatroidFlag(boolean z) {
        this.openedFromFormulaEditorInCatroidFlag = z;
    }

    public final AlphaSlider getAlphaSlider() {
        return alphaSlider;
    }

    public final void setSelectedColor(int color) {
        AlphaSlider alphaSlider2 = alphaSlider;
        if (alphaSlider2 == null) {
            return;
        }
        alphaSlider2.setSelectedColor(color);
    }
}

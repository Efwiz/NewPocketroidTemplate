package org.catrobat.paintroid.colorpicker;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: HSVSelectorView.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u0019\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u0007J\u000e\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fR\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0010"}, d2 = {"Lorg/catrobat/paintroid/colorpicker/HSVSelectorView;", "Landroidx/appcompat/widget/LinearLayoutCompat;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "hsvColorPickerView", "Lorg/catrobat/paintroid/colorpicker/HSVColorPickerView;", "getHsvColorPickerView", "()Lorg/catrobat/paintroid/colorpicker/HSVColorPickerView;", "setSelectedColor", "", TypedValues.Custom.S_COLOR, "", "colorpicker_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class HSVSelectorView extends LinearLayoutCompat {
    private final HSVColorPickerView hsvColorPickerView;

    public final HSVColorPickerView getHsvColorPickerView() {
        return this.hsvColorPickerView;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public HSVSelectorView(Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        HSVColorPickerView hSVColorPickerView = new HSVColorPickerView(getContext());
        hSVColorPickerView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        Unit unit = Unit.INSTANCE;
        this.hsvColorPickerView = hSVColorPickerView;
        addView(hSVColorPickerView);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public HSVSelectorView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Intrinsics.checkNotNullParameter(context, "context");
        HSVColorPickerView hSVColorPickerView = new HSVColorPickerView(getContext());
        hSVColorPickerView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        Unit unit = Unit.INSTANCE;
        this.hsvColorPickerView = hSVColorPickerView;
        addView(hSVColorPickerView);
    }

    public final void setSelectedColor(int color) {
        this.hsvColorPickerView.setSelectedColor(color);
    }
}

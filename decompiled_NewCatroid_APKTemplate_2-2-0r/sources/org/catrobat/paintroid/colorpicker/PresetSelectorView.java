package org.catrobat.paintroid.colorpicker;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: PresetSelectorView.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001:\u0001\u0015B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u0019\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u0007J\b\u0010\u000e\u001a\u00020\u000bH\u0002J\b\u0010\u000f\u001a\u00020\u0010H\u0002J\u0010\u0010\u0011\u001a\u00020\u00102\b\u0010\u0012\u001a\u0004\u0018\u00010\tJ\u000e\u0010\u0013\u001a\u00020\u00102\u0006\u0010\u0014\u001a\u00020\u000bR\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lorg/catrobat/paintroid/colorpicker/PresetSelectorView;", "Landroid/widget/LinearLayout;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "onColorChangedListener", "Lorg/catrobat/paintroid/colorpicker/PresetSelectorView$OnColorChangedListener;", "selectedColor", "", "tableLayout", "Landroid/widget/TableLayout;", "getSelectedColor", "onColorChanged", "", "setOnColorChangedListener", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "setSelectedColor", TypedValues.Custom.S_COLOR, "OnColorChangedListener", "colorpicker_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class PresetSelectorView extends LinearLayout {
    private OnColorChangedListener onColorChangedListener;
    private int selectedColor;
    private TableLayout tableLayout;

    /* JADX INFO: compiled from: PresetSelectorView.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\bæ\u0080\u0001\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&¨\u0006\u0006"}, d2 = {"Lorg/catrobat/paintroid/colorpicker/PresetSelectorView$OnColorChangedListener;", "", "colorChanged", "", TypedValues.Custom.S_COLOR, "", "colorpicker_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public interface OnColorChangedListener {
        void colorChanged(int color);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public PresetSelectorView(Context context) {
        this(context, null);
        Intrinsics.checkNotNullParameter(context, "context");
        setWillNotDraw(false);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PresetSelectorView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Intrinsics.checkNotNullParameter(context, "context");
        setWillNotDraw(false);
        TableLayout tableLayout = new TableLayout(context, attributeSet);
        tableLayout.setGravity(48);
        tableLayout.setOrientation(1);
        tableLayout.setStretchAllColumns(true);
        tableLayout.setShrinkAllColumns(true);
        Unit unit = Unit.INSTANCE;
        this.tableLayout = tableLayout;
        View.OnClickListener onClickListener = new View.OnClickListener() { // from class: org.catrobat.paintroid.colorpicker.PresetSelectorView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PresetSelectorView.m5908_init_$lambda1(this.f$0, view);
            }
        };
        TypedArray typedArrayObtainTypedArray = getResources().obtainTypedArray(R.array.pocketpaint_color_picker_preset_colors);
        Intrinsics.checkNotNullExpressionValue(typedArrayObtainTypedArray, "resources.obtainTypedArr…lor_picker_preset_colors)");
        TableRow tableRow = new TableRow(context);
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams();
        layoutParams.setMargins(2, 2, 2, 2);
        int length = typedArrayObtainTypedArray.length();
        if (length > 0) {
            int i = 0;
            while (true) {
                int i2 = i + 1;
                ColorPickerPresetColorButton colorPickerPresetColorButton = new ColorPickerPresetColorButton(context, typedArrayObtainTypedArray.getColor(i, 0));
                colorPickerPresetColorButton.setOnClickListener(onClickListener);
                tableRow.addView(colorPickerPresetColorButton, layoutParams);
                if (i2 % 4 == 0) {
                    this.tableLayout.addView(tableRow);
                    tableRow = new TableRow(context);
                }
                if (i2 >= length) {
                    break;
                } else {
                    i = i2;
                }
            }
        }
        typedArrayObtainTypedArray.recycle();
        addView(this.tableLayout);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: _init_$lambda-1, reason: not valid java name */
    public static final void m5908_init_$lambda1(PresetSelectorView this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (view != null) {
            this$0.selectedColor = ((ColorPickerPresetColorButton) view).getColor();
            this$0.onColorChanged();
            return;
        }
        throw new NullPointerException("null cannot be cast to non-null type org.catrobat.paintroid.colorpicker.ColorPickerPresetColorButton");
    }

    private final int getSelectedColor() {
        return this.selectedColor;
    }

    public final void setSelectedColor(int color) {
        this.selectedColor = color;
    }

    private final void onColorChanged() {
        OnColorChangedListener onColorChangedListener = this.onColorChangedListener;
        if (onColorChangedListener == null) {
            return;
        }
        onColorChangedListener.colorChanged(getSelectedColor());
    }

    public final void setOnColorChangedListener(OnColorChangedListener listener) {
        this.onColorChangedListener = listener;
    }
}

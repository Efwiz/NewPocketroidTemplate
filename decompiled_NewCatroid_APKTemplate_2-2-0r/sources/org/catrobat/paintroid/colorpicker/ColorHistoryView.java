package org.catrobat.paintroid.colorpicker;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ColorHistoryView.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u0019\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u0007J\b\u0010\u0016\u001a\u00020\u0017H\u0002J\b\u0010\u0018\u001a\u00020\u0017H\u0002J\u0010\u0010\u0019\u001a\u00020\u00172\b\u0010\u001a\u001a\u0004\u0018\u00010\u0011J\u000e\u0010\u001b\u001a\u00020\u00172\u0006\u0010\u0015\u001a\u00020\u0001J\u001e\u0010\u001c\u001a\u00020\u00172\u0016\u0010\u001d\u001a\u0012\u0012\u0004\u0012\u00020\n0\tj\b\u0012\u0004\u0012\u00020\n`\u000bR*\u0010\b\u001a\u0012\u0012\u0004\u0012\u00020\n0\tj\b\u0012\u0004\u0012\u00020\n`\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u0001X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001e"}, d2 = {"Lorg/catrobat/paintroid/colorpicker/ColorHistoryView;", "Landroid/widget/LinearLayout;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "colorHistory", "Ljava/util/ArrayList;", "", "Lkotlin/collections/ArrayList;", "getColorHistory", "()Ljava/util/ArrayList;", "setColorHistory", "(Ljava/util/ArrayList;)V", "onColorChangedListener", "Lorg/catrobat/paintroid/colorpicker/OnColorInHistoryChangedListener;", "selectedColor", "tableLayout", "Landroid/widget/TableLayout;", "textLayout", "drawHistory", "", "onColorChanged", "setOnColorInHistoryChangedListener", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "setTextLayout", "updateColorHistory", "updatedHistory", "colorpicker_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class ColorHistoryView extends LinearLayout {
    private ArrayList<Integer> colorHistory;
    private OnColorInHistoryChangedListener onColorChangedListener;
    private int selectedColor;
    private TableLayout tableLayout;
    private LinearLayout textLayout;

    public final ArrayList<Integer> getColorHistory() {
        return this.colorHistory;
    }

    public final void setColorHistory(ArrayList<Integer> arrayList) {
        Intrinsics.checkNotNullParameter(arrayList, "<set-?>");
        this.colorHistory = arrayList;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ColorHistoryView(Context context) {
        this(context, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ColorHistoryView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Intrinsics.checkNotNullParameter(context, "context");
        this.colorHistory = new ArrayList<>();
        setWillNotDraw(false);
        TableLayout tableLayout = new TableLayout(context, attributeSet);
        tableLayout.setGravity(48);
        tableLayout.setOrientation(1);
        tableLayout.setShrinkAllColumns(true);
        Unit unit = Unit.INSTANCE;
        this.tableLayout = tableLayout;
        tableLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        drawHistory();
        addView(this.tableLayout);
    }

    private final void onColorChanged() {
        OnColorInHistoryChangedListener onColorInHistoryChangedListener = this.onColorChangedListener;
        if (onColorInHistoryChangedListener == null) {
            return;
        }
        onColorInHistoryChangedListener.colorInHistoryChanged(this.selectedColor);
    }

    public final void setOnColorInHistoryChangedListener(OnColorInHistoryChangedListener listener) {
        this.onColorChangedListener = listener;
    }

    public final void setTextLayout(LinearLayout textLayout) {
        Intrinsics.checkNotNullParameter(textLayout, "textLayout");
        this.textLayout = textLayout;
    }

    private final void drawHistory() {
        View.OnClickListener onClickListener = new View.OnClickListener() { // from class: org.catrobat.paintroid.colorpicker.ColorHistoryView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ColorHistoryView.m5894drawHistory$lambda1(this.f$0, view);
            }
        };
        TableRow tableRow = new TableRow(getContext());
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams();
        layoutParams.setMargins(2, 2, 2, 2);
        layoutParams.width = 0;
        layoutParams.height = -2;
        layoutParams.weight = 0.25f;
        Iterator it = CollectionsKt.reversed(this.colorHistory).iterator();
        while (it.hasNext()) {
            int iIntValue = ((Number) it.next()).intValue();
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "context");
            ColorPickerPresetColorButton colorPickerPresetColorButton = new ColorPickerPresetColorButton(context, iIntValue);
            colorPickerPresetColorButton.setOnClickListener(onClickListener);
            if (Color.red(iIntValue) > 240 && Color.green(iIntValue) > 240 && Color.blue(iIntValue) > 240) {
                colorPickerPresetColorButton.setElevation(1.5f);
            }
            tableRow.addView(colorPickerPresetColorButton, layoutParams);
        }
        if (!this.colorHistory.isEmpty()) {
            int size = 4 - this.colorHistory.size();
            for (int i = 0; i < size; i++) {
                Context context2 = getContext();
                Intrinsics.checkNotNullExpressionValue(context2, "context");
                tableRow.addView(new ColorPickerPresetColorButton(context2, -1), layoutParams);
            }
            LinearLayout linearLayout = this.textLayout;
            if (linearLayout != null) {
                linearLayout.setVisibility(0);
            }
        }
        this.tableLayout.removeAllViews();
        this.tableLayout.addView(tableRow);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: drawHistory$lambda-1, reason: not valid java name */
    public static final void m5894drawHistory$lambda1(ColorHistoryView this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (view != null) {
            this$0.selectedColor = ((ColorPickerPresetColorButton) view).getColor();
            this$0.onColorChanged();
            return;
        }
        throw new NullPointerException("null cannot be cast to non-null type org.catrobat.paintroid.colorpicker.ColorPickerPresetColorButton");
    }

    public final void updateColorHistory(ArrayList<Integer> updatedHistory) {
        Intrinsics.checkNotNullParameter(updatedHistory, "updatedHistory");
        this.colorHistory = updatedHistory;
        drawHistory();
    }
}

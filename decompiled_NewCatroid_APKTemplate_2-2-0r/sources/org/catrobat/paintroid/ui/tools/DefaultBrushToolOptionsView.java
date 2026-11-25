package org.catrobat.paintroid.ui.tools;

import android.graphics.Paint;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SeekBar;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import java.util.Arrays;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import org.catrobat.paintroid.R;
import org.catrobat.paintroid.tools.helper.DefaultNumberRangeFilter;
import org.catrobat.paintroid.tools.options.BrushToolOptionsView;
import org.catrobat.paintroid.tools.options.BrushToolPreview;

/* JADX INFO: compiled from: DefaultBrushToolOptionsView.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0003\u0018\u0000 .2\u00020\u0001:\u0002./B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010\u0016\u001a\u00020\u0017J\b\u0010\u0018\u001a\u00020\u0006H\u0016J\b\u0010\u0019\u001a\u00020\u0006H\u0016J\b\u0010\u001a\u001a\u00020\u0017H\u0016J\b\u0010\u001b\u001a\u00020\u0017H\u0016J\b\u0010\u001c\u001a\u00020\u0017H\u0002J\b\u0010\u001d\u001a\u00020\u0017H\u0002J\u0010\u0010\u001e\u001a\u00020\u00172\u0006\u0010\u001f\u001a\u00020\bH\u0016J\u0010\u0010 \u001a\u00020\u00172\u0006\u0010!\u001a\u00020\"H\u0016J\u0010\u0010#\u001a\u00020\u00172\u0006\u0010$\u001a\u00020%H\u0016J\u0010\u0010&\u001a\u00020\u00172\u0006\u0010'\u001a\u00020(H\u0016J\u0010\u0010)\u001a\u00020\u00172\u0006\u0010*\u001a\u00020(H\u0002J\u0010\u0010+\u001a\u00020\u00172\u0006\u0010,\u001a\u00020-H\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00060"}, d2 = {"Lorg/catrobat/paintroid/ui/tools/DefaultBrushToolOptionsView;", "Lorg/catrobat/paintroid/tools/options/BrushToolOptionsView;", "rootView", "Landroid/view/ViewGroup;", "(Landroid/view/ViewGroup;)V", "bottomLayout", "Landroid/view/View;", "brushChangedListener", "Lorg/catrobat/paintroid/tools/options/BrushToolOptionsView$OnBrushChangedListener;", "brushSizeText", "Landroid/widget/EditText;", "brushToolPreview", "Lorg/catrobat/paintroid/tools/options/BrushToolPreview;", "brushWidthSeekBar", "Landroid/widget/SeekBar;", "buttonCircle", "Lcom/google/android/material/chip/Chip;", "buttonRect", "currentView", "strokeCapButtonsGroup", "Lcom/google/android/material/chip/ChipGroup;", "topLayout", "adjustOptionsView", "", "getBottomToolOptions", "getTopToolOptions", "hideCaps", "invalidate", "onCircleButtonClicked", "onRectButtonClicked", "setBrushChangedListener", "onBrushChangedListener", "setBrushPreviewListener", "onBrushPreviewListener", "Lorg/catrobat/paintroid/tools/options/BrushToolOptionsView$OnBrushPreviewListener;", "setCurrentPaint", "paint", "Landroid/graphics/Paint;", "setStrokeCapButtonChecked", "strokeCap", "Landroid/graphics/Paint$Cap;", "updateStrokeCap", "cap", "updateStrokeWidthChange", "strokeWidth", "", "Companion", "OnBrushChangedWidthSeekBarListener", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class DefaultBrushToolOptionsView implements BrushToolOptionsView {
    private static final String TAG = "DefaultBrushToolOptionsView";
    private final View bottomLayout;
    private BrushToolOptionsView.OnBrushChangedListener brushChangedListener;
    private EditText brushSizeText;
    private BrushToolPreview brushToolPreview;
    private SeekBar brushWidthSeekBar;
    private Chip buttonCircle;
    private final Chip buttonRect;
    private ViewGroup currentView;
    private ChipGroup strokeCapButtonsGroup;
    private final View topLayout;

    /* JADX INFO: compiled from: DefaultBrushToolOptionsView.kt */
    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Paint.Cap.values().length];
            iArr[Paint.Cap.ROUND.ordinal()] = 1;
            iArr[Paint.Cap.SQUARE.ordinal()] = 2;
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public DefaultBrushToolOptionsView(ViewGroup rootView) {
        Intrinsics.checkNotNullParameter(rootView, "rootView");
        this.currentView = rootView;
        View viewInflate = LayoutInflater.from(rootView.getContext()).inflate(R.layout.dialog_pocketpaint_stroke, rootView, true);
        View viewFindViewById = viewInflate.findViewById(R.id.pocketpaint_stroke_types);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.pocketpaint_stroke_types)");
        this.strokeCapButtonsGroup = (ChipGroup) viewFindViewById;
        View viewFindViewById2 = viewInflate.findViewById(R.id.pocketpaint_stroke_ibtn_circle);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(R.id.pocketpaint_stroke_ibtn_circle)");
        this.buttonCircle = (Chip) viewFindViewById2;
        View viewFindViewById3 = viewInflate.findViewById(R.id.pocketpaint_stroke_ibtn_rect);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(R.id.pocketpaint_stroke_ibtn_rect)");
        Chip chip = (Chip) viewFindViewById3;
        this.buttonRect = chip;
        View viewFindViewById4 = viewInflate.findViewById(R.id.pocketpaint_stroke_width_seek_bar);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "findViewById(R.id.pocket…nt_stroke_width_seek_bar)");
        SeekBar seekBar = (SeekBar) viewFindViewById4;
        this.brushWidthSeekBar = seekBar;
        seekBar.setOnSeekBarChangeListener(new OnBrushChangedWidthSeekBarListener(this));
        View viewFindViewById5 = viewInflate.findViewById(R.id.pocketpaint_stroke_width_width_text);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById5, "findViewById(R.id.pocket…_stroke_width_width_text)");
        this.brushSizeText = (EditText) viewFindViewById5;
        KeyEvent.Callback callbackFindViewById = viewInflate.findViewById(R.id.pocketpaint_brush_tool_preview);
        Intrinsics.checkNotNullExpressionValue(callbackFindViewById, "findViewById(R.id.pocketpaint_brush_tool_preview)");
        this.brushToolPreview = (BrushToolPreview) callbackFindViewById;
        View viewFindViewById6 = viewInflate.findViewById(R.id.pocketpaint_stroke_top_layout);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById6, "findViewById(R.id.pocketpaint_stroke_top_layout)");
        this.topLayout = viewFindViewById6;
        View viewFindViewById7 = viewInflate.findViewById(R.id.pocketpaint_stroke_bottom_layout);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById7, "findViewById(R.id.pocket…int_stroke_bottom_layout)");
        this.bottomLayout = viewFindViewById7;
        this.brushSizeText.setFilters(new InputFilter[]{new DefaultNumberRangeFilter(1, 100)});
        this.buttonCircle.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.paintroid.ui.tools.DefaultBrushToolOptionsView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DefaultBrushToolOptionsView.m5981_init_$lambda1(this.f$0, view);
            }
        });
        chip.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.paintroid.ui.tools.DefaultBrushToolOptionsView$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DefaultBrushToolOptionsView.m5982_init_$lambda2(this.f$0, view);
            }
        });
        this.brushSizeText.addTextChangedListener(new TextWatcher() { // from class: org.catrobat.paintroid.ui.tools.DefaultBrushToolOptionsView.4
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Intrinsics.checkNotNullParameter(charSequence, "charSequence");
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Intrinsics.checkNotNullParameter(charSequence, "charSequence");
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                int i;
                Intrinsics.checkNotNullParameter(editable, "editable");
                try {
                    i = Integer.parseInt(DefaultBrushToolOptionsView.this.brushSizeText.getText().toString());
                } catch (NumberFormatException e) {
                    String localizedMessage = e.getLocalizedMessage();
                    if (localizedMessage != null) {
                        Log.d(DefaultBrushToolOptionsView.TAG, localizedMessage);
                    }
                    i = 1;
                }
                DefaultBrushToolOptionsView.this.brushWidthSeekBar.setProgress(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: _init_$lambda-1, reason: not valid java name */
    public static final void m5981_init_$lambda1(DefaultBrushToolOptionsView this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.onCircleButtonClicked();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: _init_$lambda-2, reason: not valid java name */
    public static final void m5982_init_$lambda2(DefaultBrushToolOptionsView this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.onRectButtonClicked();
    }

    private final void onRectButtonClicked() {
        updateStrokeCap(Paint.Cap.SQUARE);
        this.buttonRect.setChecked(true);
        this.buttonCircle.setChecked(false);
        invalidate();
    }

    @Override // org.catrobat.paintroid.tools.options.BrushToolOptionsView
    public void hideCaps() {
        this.strokeCapButtonsGroup.setVisibility(8);
    }

    private final void onCircleButtonClicked() {
        updateStrokeCap(Paint.Cap.ROUND);
        this.buttonCircle.setChecked(true);
        this.buttonRect.setChecked(false);
        invalidate();
    }

    public final void adjustOptionsView() {
        View viewInflate = LayoutInflater.from(this.currentView.getContext()).inflate(R.layout.dialog_pocketpaint_stroke, this.currentView, true);
        View viewFindViewById = viewInflate.findViewById(R.id.pocketpaint_stroke_width_seek_bar);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.pocket…nt_stroke_width_seek_bar)");
        SeekBar seekBar = (SeekBar) viewFindViewById;
        this.brushWidthSeekBar = seekBar;
        seekBar.setOnSeekBarChangeListener(new OnBrushChangedWidthSeekBarListener(this));
        View viewFindViewById2 = viewInflate.findViewById(R.id.pocketpaint_stroke_width_width_text);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(R.id.pocket…_stroke_width_width_text)");
        this.brushSizeText = (EditText) viewFindViewById2;
        KeyEvent.Callback callbackFindViewById = viewInflate.findViewById(R.id.pocketpaint_brush_tool_preview);
        Intrinsics.checkNotNullExpressionValue(callbackFindViewById, "findViewById(R.id.pocketpaint_brush_tool_preview)");
        this.brushToolPreview = (BrushToolPreview) callbackFindViewById;
    }

    @Override // org.catrobat.paintroid.tools.options.BrushToolOptionsView
    public void setCurrentPaint(Paint paint) {
        Intrinsics.checkNotNullParameter(paint, "paint");
        if (paint.getStrokeCap() == Paint.Cap.ROUND) {
            this.buttonCircle.setSelected(true);
            this.buttonRect.setSelected(false);
        } else {
            this.buttonCircle.setSelected(false);
            this.buttonRect.setSelected(true);
        }
        this.brushWidthSeekBar.setProgress((int) paint.getStrokeWidth());
        EditText editText = this.brushSizeText;
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String str = String.format(Locale.getDefault(), "%d", Arrays.copyOf(new Object[]{Integer.valueOf((int) paint.getStrokeWidth())}, 1));
        Intrinsics.checkNotNullExpressionValue(str, "java.lang.String.format(locale, format, *args)");
        editText.setText(str);
    }

    @Override // org.catrobat.paintroid.tools.options.BrushToolOptionsView
    public void setStrokeCapButtonChecked(Paint.Cap strokeCap) {
        Intrinsics.checkNotNullParameter(strokeCap, "strokeCap");
        int i = WhenMappings.$EnumSwitchMapping$0[strokeCap.ordinal()];
        if (i == 1) {
            this.strokeCapButtonsGroup.check(this.buttonCircle.getId());
        } else {
            if (i != 2) {
                return;
            }
            this.strokeCapButtonsGroup.check(this.buttonRect.getId());
        }
    }

    @Override // org.catrobat.paintroid.tools.options.BrushToolOptionsView
    public void setBrushChangedListener(BrushToolOptionsView.OnBrushChangedListener onBrushChangedListener) {
        Intrinsics.checkNotNullParameter(onBrushChangedListener, "onBrushChangedListener");
        this.brushChangedListener = onBrushChangedListener;
    }

    @Override // org.catrobat.paintroid.tools.options.BrushToolOptionsView
    public void setBrushPreviewListener(BrushToolOptionsView.OnBrushPreviewListener onBrushPreviewListener) {
        Intrinsics.checkNotNullParameter(onBrushPreviewListener, "onBrushPreviewListener");
        this.brushToolPreview.setListener(onBrushPreviewListener);
        this.brushToolPreview.invalidate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateStrokeWidthChange(int strokeWidth) {
        BrushToolOptionsView.OnBrushChangedListener onBrushChangedListener = this.brushChangedListener;
        if (onBrushChangedListener == null) {
            return;
        }
        onBrushChangedListener.setStrokeWidth(strokeWidth);
    }

    private final void updateStrokeCap(Paint.Cap cap) {
        BrushToolOptionsView.OnBrushChangedListener onBrushChangedListener = this.brushChangedListener;
        if (onBrushChangedListener == null) {
            return;
        }
        onBrushChangedListener.setCap(cap);
    }

    @Override // org.catrobat.paintroid.tools.options.BrushToolOptionsView
    public void invalidate() {
        this.brushToolPreview.invalidate();
    }

    /* JADX INFO: compiled from: DefaultBrushToolOptionsView.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0010\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\f\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\r"}, d2 = {"Lorg/catrobat/paintroid/ui/tools/DefaultBrushToolOptionsView$OnBrushChangedWidthSeekBarListener;", "Landroid/widget/SeekBar$OnSeekBarChangeListener;", "(Lorg/catrobat/paintroid/ui/tools/DefaultBrushToolOptionsView;)V", "onProgressChanged", "", "seekBar", "Landroid/widget/SeekBar;", "progress", "", "fromUser", "", "onStartTrackingTouch", "onStopTrackingTouch", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public final class OnBrushChangedWidthSeekBarListener implements SeekBar.OnSeekBarChangeListener {
        final /* synthetic */ DefaultBrushToolOptionsView this$0;

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onStartTrackingTouch(SeekBar seekBar) {
            Intrinsics.checkNotNullParameter(seekBar, "seekBar");
        }

        public OnBrushChangedWidthSeekBarListener(DefaultBrushToolOptionsView this$0) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this.this$0 = this$0;
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            Intrinsics.checkNotNullParameter(seekBar, "seekBar");
            if (progress < 1) {
                seekBar.setProgress(1);
                progress = 1;
            }
            this.this$0.updateStrokeWidthChange(progress);
            if (fromUser) {
                EditText editText = this.this$0.brushSizeText;
                StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
                String str = String.format(Locale.getDefault(), "%d", Arrays.copyOf(new Object[]{Integer.valueOf(progress)}, 1));
                Intrinsics.checkNotNullExpressionValue(str, "java.lang.String.format(locale, format, *args)");
                editText.setText(str);
            }
            this.this$0.brushToolPreview.invalidate();
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onStopTrackingTouch(SeekBar seekBar) {
            Intrinsics.checkNotNullParameter(seekBar, "seekBar");
            EditText editText = this.this$0.brushSizeText;
            StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
            String str = String.format(Locale.getDefault(), "%d", Arrays.copyOf(new Object[]{Integer.valueOf(seekBar.getProgress())}, 1));
            Intrinsics.checkNotNullExpressionValue(str, "java.lang.String.format(locale, format, *args)");
            editText.setText(str);
        }
    }

    @Override // org.catrobat.paintroid.tools.options.BrushToolOptionsView
    /* JADX INFO: renamed from: getTopToolOptions, reason: from getter */
    public View getTopLayout() {
        return this.topLayout;
    }

    @Override // org.catrobat.paintroid.tools.options.BrushToolOptionsView
    /* JADX INFO: renamed from: getBottomToolOptions, reason: from getter */
    public View getBottomLayout() {
        return this.bottomLayout;
    }
}

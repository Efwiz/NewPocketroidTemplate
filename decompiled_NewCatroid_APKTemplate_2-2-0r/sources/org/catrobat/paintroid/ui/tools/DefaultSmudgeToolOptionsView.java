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
import org.catrobat.paintroid.tools.options.SmudgeToolOptionsView;

/* JADX INFO: compiled from: DefaultSmudgeToolOptionsView.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\r\u0018\u0000 ;2\u00020\u0001:\u0004;<=>B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u001b\u001a\u00020\u0006H\u0016J\b\u0010\u001c\u001a\u00020\u0006H\u0016J\b\u0010\u001d\u001a\u00020\u001eH\u0016J\b\u0010\u001f\u001a\u00020\u001eH\u0016J\b\u0010 \u001a\u00020\u001eH\u0002J\b\u0010!\u001a\u00020\u001eH\u0002J\u0010\u0010\"\u001a\u00020\u001e2\u0006\u0010#\u001a\u00020\bH\u0016J\u0010\u0010$\u001a\u00020\u001e2\u0006\u0010%\u001a\u00020&H\u0016J\u0010\u0010'\u001a\u00020\u001e2\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\u0010\u0010(\u001a\u00020\u001e2\u0006\u0010)\u001a\u00020*H\u0016J\u0010\u0010+\u001a\u00020\u001e2\u0006\u0010,\u001a\u00020-H\u0002J\u0010\u0010.\u001a\u00020\u001e2\u0006\u0010/\u001a\u00020-H\u0002J\u0010\u00100\u001a\u00020\u001e2\u0006\u00101\u001a\u000202H\u0016J\u0010\u00103\u001a\u00020\u001e2\u0006\u00104\u001a\u00020-H\u0002J\u0010\u00105\u001a\u00020\u001e2\u0006\u00106\u001a\u00020-H\u0002J\u0010\u00107\u001a\u00020\u001e2\u0006\u00108\u001a\u000202H\u0002J\u0010\u00109\u001a\u00020\u001e2\u0006\u0010:\u001a\u00020-H\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006?"}, d2 = {"Lorg/catrobat/paintroid/ui/tools/DefaultSmudgeToolOptionsView;", "Lorg/catrobat/paintroid/tools/options/SmudgeToolOptionsView;", "rootView", "Landroid/view/ViewGroup;", "(Landroid/view/ViewGroup;)V", "bottomLayout", "Landroid/view/View;", "brushChangedListener", "Lorg/catrobat/paintroid/tools/options/BrushToolOptionsView$OnBrushChangedListener;", "brushSizeText", "Landroid/widget/EditText;", "brushToolPreview", "Lorg/catrobat/paintroid/tools/options/BrushToolPreview;", "brushWidthSeekBar", "Landroid/widget/SeekBar;", "buttonCircle", "Lcom/google/android/material/chip/Chip;", "buttonRect", "callback", "Lorg/catrobat/paintroid/tools/options/SmudgeToolOptionsView$Callback;", "dragSeekBar", "dragText", "pressureSeekBar", "pressureText", "strokeButtonsGroup", "Lcom/google/android/material/chip/ChipGroup;", "topLayout", "getBottomToolOptions", "getTopToolOptions", "hideCaps", "", "invalidate", "onCircleButtonClicked", "onRectButtonClicked", "setBrushChangedListener", "onBrushChangedListener", "setBrushPreviewListener", "onBrushPreviewListener", "Lorg/catrobat/paintroid/tools/options/BrushToolOptionsView$OnBrushPreviewListener;", "setCallback", "setCurrentPaint", "paint", "Landroid/graphics/Paint;", "setDragText", "dragInPercent", "", "setPressureText", "pressureInPercent", "setStrokeCapButtonChecked", "strokeCap", "Landroid/graphics/Paint$Cap;", "updateDrag", "drag", "updatePressure", "pressure", "updateStrokeCap", "cap", "updateStrokeWidthChange", "strokeWidth", "Companion", "OnBrushChangedWidthSeekBarListener", "OnDragChangedSeekBarListener", "OnPressureChangedSeekBarListener", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class DefaultSmudgeToolOptionsView implements SmudgeToolOptionsView {
    private static final String TAG = "DefaultSmudgeToolOptionsView";
    private final View bottomLayout;
    private BrushToolOptionsView.OnBrushChangedListener brushChangedListener;
    private final EditText brushSizeText;
    private final BrushToolPreview brushToolPreview;
    private final SeekBar brushWidthSeekBar;
    private final Chip buttonCircle;
    private final Chip buttonRect;
    private SmudgeToolOptionsView.Callback callback;
    private final SeekBar dragSeekBar;
    private final EditText dragText;
    private final SeekBar pressureSeekBar;
    private final EditText pressureText;
    private ChipGroup strokeButtonsGroup;
    private final View topLayout;

    /* JADX INFO: compiled from: DefaultSmudgeToolOptionsView.kt */
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

    @Override // org.catrobat.paintroid.tools.options.BrushToolOptionsView
    public void hideCaps() {
    }

    public DefaultSmudgeToolOptionsView(ViewGroup rootView) {
        Intrinsics.checkNotNullParameter(rootView, "rootView");
        View viewInflate = LayoutInflater.from(rootView.getContext()).inflate(R.layout.dialog_pocketpaint_smudge_tool, rootView, true);
        View viewFindViewById = viewInflate.findViewById(R.id.pocketpaint_stroke_types);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.pocketpaint_stroke_types)");
        this.strokeButtonsGroup = (ChipGroup) viewFindViewById;
        View viewFindViewById2 = viewInflate.findViewById(R.id.pocketpaint_stroke_ibtn_circle);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(R.id.pocketpaint_stroke_ibtn_circle)");
        Chip chip = (Chip) viewFindViewById2;
        this.buttonCircle = chip;
        View viewFindViewById3 = viewInflate.findViewById(R.id.pocketpaint_stroke_ibtn_rect);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(R.id.pocketpaint_stroke_ibtn_rect)");
        Chip chip2 = (Chip) viewFindViewById3;
        this.buttonRect = chip2;
        View viewFindViewById4 = viewInflate.findViewById(R.id.pocketpaint_stroke_width_seek_bar);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "findViewById(R.id.pocket…nt_stroke_width_seek_bar)");
        SeekBar seekBar = (SeekBar) viewFindViewById4;
        this.brushWidthSeekBar = seekBar;
        seekBar.setOnSeekBarChangeListener(new OnBrushChangedWidthSeekBarListener(this));
        View viewFindViewById5 = viewInflate.findViewById(R.id.pocketpaint_stroke_width_width_text);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById5, "findViewById(R.id.pocket…_stroke_width_width_text)");
        EditText editText = (EditText) viewFindViewById5;
        this.brushSizeText = editText;
        KeyEvent.Callback callbackFindViewById = viewInflate.findViewById(R.id.pocketpaint_brush_tool_preview);
        Intrinsics.checkNotNullExpressionValue(callbackFindViewById, "findViewById(R.id.pocketpaint_brush_tool_preview)");
        this.brushToolPreview = (BrushToolPreview) callbackFindViewById;
        View viewFindViewById6 = viewInflate.findViewById(R.id.pocketpaint_smudge_tool_dialog_pressure_input);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById6, "findViewById(R.id.pocket…ol_dialog_pressure_input)");
        EditText editText2 = (EditText) viewFindViewById6;
        this.pressureText = editText2;
        View viewFindViewById7 = viewInflate.findViewById(R.id.pocketpaint_pressure_seek_bar);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById7, "findViewById(R.id.pocketpaint_pressure_seek_bar)");
        SeekBar seekBar2 = (SeekBar) viewFindViewById7;
        this.pressureSeekBar = seekBar2;
        seekBar2.setOnSeekBarChangeListener(new OnPressureChangedSeekBarListener(this));
        View viewFindViewById8 = viewInflate.findViewById(R.id.pocketpaint_smudge_tool_dialog_drag_input);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById8, "findViewById(R.id.pocket…e_tool_dialog_drag_input)");
        EditText editText3 = (EditText) viewFindViewById8;
        this.dragText = editText3;
        View viewFindViewById9 = viewInflate.findViewById(R.id.pocketpaint_drag_seek_bar);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById9, "findViewById(R.id.pocketpaint_drag_seek_bar)");
        SeekBar seekBar3 = (SeekBar) viewFindViewById9;
        this.dragSeekBar = seekBar3;
        seekBar3.setOnSeekBarChangeListener(new OnDragChangedSeekBarListener(this));
        View viewFindViewById10 = viewInflate.findViewById(R.id.pocketpaint_smudge_top_layout);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById10, "findViewById(R.id.pocketpaint_smudge_top_layout)");
        this.topLayout = viewFindViewById10;
        View viewFindViewById11 = viewInflate.findViewById(R.id.pocketpaint_smudge_bottom_layout);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById11, "findViewById(R.id.pocket…int_smudge_bottom_layout)");
        this.bottomLayout = viewFindViewById11;
        editText.setFilters(new InputFilter[]{new DefaultNumberRangeFilter(1, 100)});
        editText2.setFilters(new InputFilter[]{new DefaultNumberRangeFilter(1, 100)});
        editText3.setFilters(new InputFilter[]{new DefaultNumberRangeFilter(1, 100)});
        chip.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.paintroid.ui.tools.DefaultSmudgeToolOptionsView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DefaultSmudgeToolOptionsView.m5998_init_$lambda1(this.f$0, view);
            }
        });
        chip2.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.paintroid.ui.tools.DefaultSmudgeToolOptionsView$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DefaultSmudgeToolOptionsView.m5999_init_$lambda2(this.f$0, view);
            }
        });
        editText.addTextChangedListener(new TextWatcher() { // from class: org.catrobat.paintroid.ui.tools.DefaultSmudgeToolOptionsView.4
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
                    i = Integer.parseInt(DefaultSmudgeToolOptionsView.this.brushSizeText.getText().toString());
                } catch (NumberFormatException e) {
                    String localizedMessage = e.getLocalizedMessage();
                    if (localizedMessage != null) {
                        Log.d(DefaultSmudgeToolOptionsView.TAG, localizedMessage);
                    }
                    i = 1;
                }
                DefaultSmudgeToolOptionsView.this.brushWidthSeekBar.setProgress(i);
            }
        });
        editText2.addTextChangedListener(new TextWatcher() { // from class: org.catrobat.paintroid.ui.tools.DefaultSmudgeToolOptionsView.5
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
                    i = Integer.parseInt(DefaultSmudgeToolOptionsView.this.pressureText.getText().toString());
                } catch (NumberFormatException e) {
                    String localizedMessage = e.getLocalizedMessage();
                    if (localizedMessage != null) {
                        Log.d(DefaultSmudgeToolOptionsView.TAG, localizedMessage);
                    }
                    i = 1;
                }
                DefaultSmudgeToolOptionsView.this.pressureSeekBar.setProgress(i);
            }
        });
        editText3.addTextChangedListener(new TextWatcher() { // from class: org.catrobat.paintroid.ui.tools.DefaultSmudgeToolOptionsView.6
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
                    i = Integer.parseInt(DefaultSmudgeToolOptionsView.this.dragText.getText().toString());
                } catch (NumberFormatException e) {
                    String localizedMessage = e.getLocalizedMessage();
                    if (localizedMessage != null) {
                        Log.d(DefaultSmudgeToolOptionsView.TAG, localizedMessage);
                    }
                    i = 1;
                }
                DefaultSmudgeToolOptionsView.this.dragSeekBar.setProgress(i);
            }
        });
        setPressureText(50);
        setDragText(50);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: _init_$lambda-1, reason: not valid java name */
    public static final void m5998_init_$lambda1(DefaultSmudgeToolOptionsView this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.onCircleButtonClicked();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: _init_$lambda-2, reason: not valid java name */
    public static final void m5999_init_$lambda2(DefaultSmudgeToolOptionsView this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.onRectButtonClicked();
    }

    private final void onRectButtonClicked() {
        updateStrokeCap(Paint.Cap.SQUARE);
        this.buttonRect.setChecked(true);
        this.buttonCircle.setChecked(false);
        invalidate();
    }

    private final void onCircleButtonClicked() {
        updateStrokeCap(Paint.Cap.ROUND);
        this.buttonCircle.setChecked(true);
        this.buttonRect.setChecked(false);
        invalidate();
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
            this.strokeButtonsGroup.check(this.buttonCircle.getId());
        } else {
            if (i != 2) {
                return;
            }
            this.strokeButtonsGroup.check(this.buttonRect.getId());
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

    @Override // org.catrobat.paintroid.tools.options.SmudgeToolOptionsView
    public void setCallback(SmudgeToolOptionsView.Callback callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        this.callback = callback;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updatePressure(int pressure) {
        SmudgeToolOptionsView.Callback callback = this.callback;
        if (callback == null) {
            return;
        }
        callback.onPressureChanged(pressure);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateDrag(int drag) {
        SmudgeToolOptionsView.Callback callback = this.callback;
        if (callback == null) {
            return;
        }
        callback.onDragChanged(drag);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setPressureText(int pressureInPercent) {
        EditText editText = this.pressureText;
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String str = String.format(Locale.getDefault(), "%d", Arrays.copyOf(new Object[]{Integer.valueOf(pressureInPercent)}, 1));
        Intrinsics.checkNotNullExpressionValue(str, "java.lang.String.format(locale, format, *args)");
        editText.setText(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setDragText(int dragInPercent) {
        EditText editText = this.dragText;
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String str = String.format(Locale.getDefault(), "%d", Arrays.copyOf(new Object[]{Integer.valueOf(dragInPercent)}, 1));
        Intrinsics.checkNotNullExpressionValue(str, "java.lang.String.format(locale, format, *args)");
        editText.setText(str);
    }

    /* JADX INFO: compiled from: DefaultSmudgeToolOptionsView.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0010\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\f\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\r"}, d2 = {"Lorg/catrobat/paintroid/ui/tools/DefaultSmudgeToolOptionsView$OnBrushChangedWidthSeekBarListener;", "Landroid/widget/SeekBar$OnSeekBarChangeListener;", "(Lorg/catrobat/paintroid/ui/tools/DefaultSmudgeToolOptionsView;)V", "onProgressChanged", "", "seekBar", "Landroid/widget/SeekBar;", "progress", "", "fromUser", "", "onStartTrackingTouch", "onStopTrackingTouch", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public final class OnBrushChangedWidthSeekBarListener implements SeekBar.OnSeekBarChangeListener {
        final /* synthetic */ DefaultSmudgeToolOptionsView this$0;

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onStartTrackingTouch(SeekBar seekBar) {
            Intrinsics.checkNotNullParameter(seekBar, "seekBar");
        }

        public OnBrushChangedWidthSeekBarListener(DefaultSmudgeToolOptionsView this$0) {
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

    /* JADX INFO: compiled from: DefaultSmudgeToolOptionsView.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0010\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\f\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\r"}, d2 = {"Lorg/catrobat/paintroid/ui/tools/DefaultSmudgeToolOptionsView$OnPressureChangedSeekBarListener;", "Landroid/widget/SeekBar$OnSeekBarChangeListener;", "(Lorg/catrobat/paintroid/ui/tools/DefaultSmudgeToolOptionsView;)V", "onProgressChanged", "", "seekBar", "Landroid/widget/SeekBar;", "progress", "", "fromUser", "", "onStartTrackingTouch", "onStopTrackingTouch", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public final class OnPressureChangedSeekBarListener implements SeekBar.OnSeekBarChangeListener {
        final /* synthetic */ DefaultSmudgeToolOptionsView this$0;

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onStartTrackingTouch(SeekBar seekBar) {
            Intrinsics.checkNotNullParameter(seekBar, "seekBar");
        }

        public OnPressureChangedSeekBarListener(DefaultSmudgeToolOptionsView this$0) {
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
            if (fromUser) {
                this.this$0.setPressureText(progress);
            }
            this.this$0.updatePressure(progress);
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onStopTrackingTouch(SeekBar seekBar) {
            Intrinsics.checkNotNullParameter(seekBar, "seekBar");
            this.this$0.setPressureText(seekBar.getProgress());
        }
    }

    /* JADX INFO: compiled from: DefaultSmudgeToolOptionsView.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0010\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\f\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\r"}, d2 = {"Lorg/catrobat/paintroid/ui/tools/DefaultSmudgeToolOptionsView$OnDragChangedSeekBarListener;", "Landroid/widget/SeekBar$OnSeekBarChangeListener;", "(Lorg/catrobat/paintroid/ui/tools/DefaultSmudgeToolOptionsView;)V", "onProgressChanged", "", "seekBar", "Landroid/widget/SeekBar;", "progress", "", "fromUser", "", "onStartTrackingTouch", "onStopTrackingTouch", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public final class OnDragChangedSeekBarListener implements SeekBar.OnSeekBarChangeListener {
        final /* synthetic */ DefaultSmudgeToolOptionsView this$0;

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onStartTrackingTouch(SeekBar seekBar) {
            Intrinsics.checkNotNullParameter(seekBar, "seekBar");
        }

        public OnDragChangedSeekBarListener(DefaultSmudgeToolOptionsView this$0) {
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
            if (fromUser) {
                this.this$0.setDragText(progress);
            }
            this.this$0.updateDrag(progress);
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onStopTrackingTouch(SeekBar seekBar) {
            Intrinsics.checkNotNullParameter(seekBar, "seekBar");
            this.this$0.setDragText(seekBar.getProgress());
        }
    }

    @Override // org.catrobat.paintroid.tools.options.BrushToolOptionsView
    /* JADX INFO: renamed from: getBottomToolOptions, reason: from getter */
    public View getBottomLayout() {
        return this.bottomLayout;
    }

    @Override // org.catrobat.paintroid.tools.options.BrushToolOptionsView
    /* JADX INFO: renamed from: getTopToolOptions, reason: from getter */
    public View getTopLayout() {
        return this.topLayout;
    }
}

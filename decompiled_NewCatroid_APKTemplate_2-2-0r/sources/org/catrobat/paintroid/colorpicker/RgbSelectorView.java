package org.catrobat.paintroid.colorpicker;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.SeekBar;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import java.util.Arrays;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;

/* JADX INFO: compiled from: RgbSelectorView.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\b\u0018\u00002\u00020\u0001:\u00013B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u0019\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u0007J\b\u0010\"\u001a\u00020#H\u0014J\u0010\u0010$\u001a\u00020#2\u0006\u0010\u0016\u001a\u00020\u0017H\u0002J\b\u0010%\u001a\u00020#H\u0014J\u0010\u0010&\u001a\u00020\u00172\u0006\u0010'\u001a\u00020(H\u0002J\b\u0010)\u001a\u00020#H\u0002J\u0016\u0010*\u001a\u00020#2\u0006\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020,J\u0010\u0010.\u001a\u00020#2\b\u0010/\u001a\u0004\u0018\u00010\fJ\u0018\u0010\u001b\u001a\u00020#2\u0006\u0010\u0016\u001a\u00020\u00172\b\b\u0002\u00100\u001a\u00020,J\u0010\u00101\u001a\u00020#2\u0006\u0010\u0016\u001a\u00020\u0017H\u0002J\b\u00102\u001a\u00020#H\u0002R\u000e\u0010\b\u001a\u00020\u0001X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\r\u001a\u00020\u000eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0013\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R$\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u0016\u001a\u00020\u00178F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\u000e\u0010\u001d\u001a\u00020\u001eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u001eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u001eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\u001eX\u0082\u000e¢\u0006\u0002\n\u0000¨\u00064"}, d2 = {"Lorg/catrobat/paintroid/colorpicker/RgbSelectorView;", "Landroidx/appcompat/widget/LinearLayoutCompat;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "alphaRow", "editTextHex", "Landroidx/appcompat/widget/AppCompatEditText;", "onColorChangedListener", "Lorg/catrobat/paintroid/colorpicker/RgbSelectorView$OnColorChangedListener;", "seekBarAlpha", "Landroid/widget/SeekBar;", "getSeekBarAlpha", "()Landroid/widget/SeekBar;", "setSeekBarAlpha", "(Landroid/widget/SeekBar;)V", "seekBarBlue", "seekBarGreen", "seekBarRed", TypedValues.Custom.S_COLOR, "", "selectedColor", "getSelectedColor", "()I", "setSelectedColor", "(I)V", "textViewAlpha", "Landroidx/appcompat/widget/AppCompatTextView;", "textViewBlue", "textViewGreen", "textViewRed", "onAttachedToWindow", "", "onColorChanged", "onDetachedFromWindow", "parseInputToCheckIfHEX", "newText", "", "resetTextColor", "setAlphaRow", "catroidFlag", "", "openedFromFormulaEditorInCatroidFlag", "setOnColorChangedListener", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "isOpenedFromFormulaEditorInCatroid", "setSelectedColorText", "setTextColorToRed", "OnColorChangedListener", "colorpicker_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class RgbSelectorView extends LinearLayoutCompat {
    private LinearLayoutCompat alphaRow;
    private AppCompatEditText editTextHex;
    private OnColorChangedListener onColorChangedListener;
    private SeekBar seekBarAlpha;
    private SeekBar seekBarBlue;
    private SeekBar seekBarGreen;
    private SeekBar seekBarRed;
    private AppCompatTextView textViewAlpha;
    private AppCompatTextView textViewBlue;
    private AppCompatTextView textViewGreen;
    private AppCompatTextView textViewRed;

    /* JADX INFO: compiled from: RgbSelectorView.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\bæ\u0080\u0001\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&¨\u0006\u0006"}, d2 = {"Lorg/catrobat/paintroid/colorpicker/RgbSelectorView$OnColorChangedListener;", "", "colorChanged", "", TypedValues.Custom.S_COLOR, "", "colorpicker_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public interface OnColorChangedListener {
        void colorChanged(int color);
    }

    public final SeekBar getSeekBarAlpha() {
        return this.seekBarAlpha;
    }

    public final void setSeekBarAlpha(SeekBar seekBar) {
        Intrinsics.checkNotNullParameter(seekBar, "<set-?>");
        this.seekBarAlpha = seekBar;
    }

    public final int getSelectedColor() {
        return Color.argb(this.seekBarAlpha.getProgress(), this.seekBarRed.getProgress(), this.seekBarGreen.getProgress(), this.seekBarBlue.getProgress());
    }

    public final void setSelectedColor(int i) {
        String str;
        int iRed = Color.red(i);
        int iGreen = Color.green(i);
        int iBlue = Color.blue(i);
        int iAlpha = Color.alpha(i);
        this.seekBarAlpha.setProgress(iAlpha);
        this.seekBarRed.setProgress(iRed);
        this.seekBarGreen.setProgress(iGreen);
        this.seekBarBlue.setProgress(iBlue);
        int selectionStart = this.editTextHex.getSelectionStart();
        this.editTextHex.setTag("changed programmatically");
        if (this.alphaRow.getVisibility() == 0) {
            StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
            str = String.format("#%02X%02X%02X%02X", Arrays.copyOf(new Object[]{Integer.valueOf(iAlpha), Integer.valueOf(iRed), Integer.valueOf(iGreen), Integer.valueOf(iBlue)}, 4));
            Intrinsics.checkNotNullExpressionValue(str, "java.lang.String.format(format, *args)");
        } else {
            StringCompanionObject stringCompanionObject2 = StringCompanionObject.INSTANCE;
            str = String.format("#%02X%02X%02X", Arrays.copyOf(new Object[]{Integer.valueOf(iRed), Integer.valueOf(iGreen), Integer.valueOf(iBlue)}, 3));
            Intrinsics.checkNotNullExpressionValue(str, "java.lang.String.format(format, *args)");
        }
        this.editTextHex.setText(str);
        this.editTextHex.setTag(null);
        this.editTextHex.setSelection(Math.min(selectionStart, String.valueOf(this.editTextHex.getText()).length()));
        setSelectedColorText(i);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RgbSelectorView(Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        View viewInflate = LinearLayoutCompat.inflate(getContext(), R.layout.color_picker_layout_rgbview, null);
        addView(viewInflate);
        View viewFindViewById = viewInflate.findViewById(R.id.color_picker_color_rgb_seekbar_red);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.color_…er_color_rgb_seekbar_red)");
        this.seekBarRed = (SeekBar) viewFindViewById;
        View viewFindViewById2 = viewInflate.findViewById(R.id.color_picker_color_rgb_seekbar_green);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(R.id.color_…_color_rgb_seekbar_green)");
        this.seekBarGreen = (SeekBar) viewFindViewById2;
        View viewFindViewById3 = viewInflate.findViewById(R.id.color_picker_color_rgb_seekbar_blue);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(R.id.color_…r_color_rgb_seekbar_blue)");
        this.seekBarBlue = (SeekBar) viewFindViewById3;
        View viewFindViewById4 = viewInflate.findViewById(R.id.color_picker_color_rgb_seekbar_alpha);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "findViewById(R.id.color_…_color_rgb_seekbar_alpha)");
        setSeekBarAlpha((SeekBar) viewFindViewById4);
        View viewFindViewById5 = viewInflate.findViewById(R.id.color_picker_rgb_red_value);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById5, "findViewById(R.id.color_picker_rgb_red_value)");
        this.textViewRed = (AppCompatTextView) viewFindViewById5;
        View viewFindViewById6 = viewInflate.findViewById(R.id.color_picker_rgb_blue_value);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById6, "findViewById(R.id.color_picker_rgb_blue_value)");
        this.textViewBlue = (AppCompatTextView) viewFindViewById6;
        View viewFindViewById7 = viewInflate.findViewById(R.id.color_picker_color_rgb_hex);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById7, "findViewById(R.id.color_picker_color_rgb_hex)");
        this.editTextHex = (AppCompatEditText) viewFindViewById7;
        View viewFindViewById8 = viewInflate.findViewById(R.id.color_picker_rgb_green_value);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById8, "findViewById(R.id.color_picker_rgb_green_value)");
        this.textViewGreen = (AppCompatTextView) viewFindViewById8;
        View viewFindViewById9 = viewInflate.findViewById(R.id.color_picker_rgb_alpha_value);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById9, "findViewById(R.id.color_picker_rgb_alpha_value)");
        this.textViewAlpha = (AppCompatTextView) viewFindViewById9;
        View viewFindViewById10 = viewInflate.findViewById(R.id.color_picker_alpha_row);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById10, "findViewById(R.id.color_picker_alpha_row)");
        this.alphaRow = (LinearLayoutCompat) viewFindViewById10;
        resetTextColor();
        setSelectedColor(ViewCompat.MEASURED_STATE_MASK);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RgbSelectorView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Intrinsics.checkNotNullParameter(context, "context");
        View viewInflate = LinearLayoutCompat.inflate(getContext(), R.layout.color_picker_layout_rgbview, null);
        addView(viewInflate);
        View viewFindViewById = viewInflate.findViewById(R.id.color_picker_color_rgb_seekbar_red);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.color_…er_color_rgb_seekbar_red)");
        this.seekBarRed = (SeekBar) viewFindViewById;
        View viewFindViewById2 = viewInflate.findViewById(R.id.color_picker_color_rgb_seekbar_green);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(R.id.color_…_color_rgb_seekbar_green)");
        this.seekBarGreen = (SeekBar) viewFindViewById2;
        View viewFindViewById3 = viewInflate.findViewById(R.id.color_picker_color_rgb_seekbar_blue);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(R.id.color_…r_color_rgb_seekbar_blue)");
        this.seekBarBlue = (SeekBar) viewFindViewById3;
        View viewFindViewById4 = viewInflate.findViewById(R.id.color_picker_color_rgb_seekbar_alpha);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "findViewById(R.id.color_…_color_rgb_seekbar_alpha)");
        setSeekBarAlpha((SeekBar) viewFindViewById4);
        View viewFindViewById5 = viewInflate.findViewById(R.id.color_picker_rgb_red_value);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById5, "findViewById(R.id.color_picker_rgb_red_value)");
        this.textViewRed = (AppCompatTextView) viewFindViewById5;
        View viewFindViewById6 = viewInflate.findViewById(R.id.color_picker_rgb_blue_value);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById6, "findViewById(R.id.color_picker_rgb_blue_value)");
        this.textViewBlue = (AppCompatTextView) viewFindViewById6;
        View viewFindViewById7 = viewInflate.findViewById(R.id.color_picker_color_rgb_hex);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById7, "findViewById(R.id.color_picker_color_rgb_hex)");
        this.editTextHex = (AppCompatEditText) viewFindViewById7;
        View viewFindViewById8 = viewInflate.findViewById(R.id.color_picker_rgb_green_value);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById8, "findViewById(R.id.color_picker_rgb_green_value)");
        this.textViewGreen = (AppCompatTextView) viewFindViewById8;
        View viewFindViewById9 = viewInflate.findViewById(R.id.color_picker_rgb_alpha_value);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById9, "findViewById(R.id.color_picker_rgb_alpha_value)");
        this.textViewAlpha = (AppCompatTextView) viewFindViewById9;
        View viewFindViewById10 = viewInflate.findViewById(R.id.color_picker_alpha_row);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById10, "findViewById(R.id.color_picker_alpha_row)");
        this.alphaRow = (LinearLayoutCompat) viewFindViewById10;
        resetTextColor();
        setSelectedColor(ViewCompat.MEASURED_STATE_MASK);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final int parseInputToCheckIfHEX(String newText) {
        if (newText.length() != (this.alphaRow.getVisibility() == 0 ? 9 : 7)) {
            return 20000000;
        }
        if (newText != null) {
            String strSubstring = newText.substring(0, 1);
            Intrinsics.checkNotNullExpressionValue(strSubstring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
            if (!Intrinsics.areEqual(strSubstring, "#")) {
                return 20000000;
            }
            try {
                return Color.parseColor(newText);
            } catch (IllegalArgumentException unused) {
                return 20000000;
            }
        }
        throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void resetTextColor() {
        this.editTextHex.setTextColor(ContextCompat.getColor(getContext(), R.color.pocketpaint_color_picker_hex_correct_black));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setTextColorToRed() {
        this.editTextHex.setTextColor(ContextCompat.getColor(getContext(), R.color.pocketpaint_color_picker_hex_wrong_value_red));
    }

    public static /* synthetic */ void setSelectedColor$default(RgbSelectorView rgbSelectorView, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = false;
        }
        rgbSelectorView.setSelectedColor(i, z);
    }

    public final void setSelectedColor(int color, boolean isOpenedFromFormulaEditorInCatroid) {
        String str;
        int iRed = Color.red(color);
        int iGreen = Color.green(color);
        int iBlue = Color.blue(color);
        int iAlpha = Color.alpha(color);
        this.seekBarAlpha.setProgress(iAlpha);
        this.seekBarRed.setProgress(iRed);
        this.seekBarGreen.setProgress(iGreen);
        this.seekBarBlue.setProgress(iBlue);
        int selectionStart = this.editTextHex.getSelectionStart();
        this.editTextHex.setTag("changed programmatically");
        if (!isOpenedFromFormulaEditorInCatroid) {
            StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
            str = String.format("#%02X%02X%02X%02X", Arrays.copyOf(new Object[]{Integer.valueOf(iAlpha), Integer.valueOf(iRed), Integer.valueOf(iGreen), Integer.valueOf(iBlue)}, 4));
            Intrinsics.checkNotNullExpressionValue(str, "java.lang.String.format(format, *args)");
        } else {
            StringCompanionObject stringCompanionObject2 = StringCompanionObject.INSTANCE;
            str = String.format("#%02X%02X%02X", Arrays.copyOf(new Object[]{Integer.valueOf(iRed), Integer.valueOf(iGreen), Integer.valueOf(iBlue)}, 3));
            Intrinsics.checkNotNullExpressionValue(str, "java.lang.String.format(format, *args)");
        }
        this.editTextHex.setText(str);
        this.editTextHex.setTag(null);
        this.editTextHex.setSelection(Math.min(selectionStart, String.valueOf(this.editTextHex.getText()).length()));
        setSelectedColorText(color);
    }

    private final void setSelectedColorText(int color) {
        int iRed = Color.red(color);
        int iGreen = Color.green(color);
        int iBlue = Color.blue(color);
        int iAlpha = (int) (Color.alpha(color) / 2.55f);
        AppCompatTextView appCompatTextView = this.textViewRed;
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String str = String.format(Locale.getDefault(), "%d", Arrays.copyOf(new Object[]{Integer.valueOf(iRed)}, 1));
        Intrinsics.checkNotNullExpressionValue(str, "java.lang.String.format(locale, format, *args)");
        appCompatTextView.setText(str);
        AppCompatTextView appCompatTextView2 = this.textViewGreen;
        StringCompanionObject stringCompanionObject2 = StringCompanionObject.INSTANCE;
        String str2 = String.format(Locale.getDefault(), "%d", Arrays.copyOf(new Object[]{Integer.valueOf(iGreen)}, 1));
        Intrinsics.checkNotNullExpressionValue(str2, "java.lang.String.format(locale, format, *args)");
        appCompatTextView2.setText(str2);
        AppCompatTextView appCompatTextView3 = this.textViewBlue;
        StringCompanionObject stringCompanionObject3 = StringCompanionObject.INSTANCE;
        String str3 = String.format(Locale.getDefault(), "%d", Arrays.copyOf(new Object[]{Integer.valueOf(iBlue)}, 1));
        Intrinsics.checkNotNullExpressionValue(str3, "java.lang.String.format(locale, format, *args)");
        appCompatTextView3.setText(str3);
        AppCompatTextView appCompatTextView4 = this.textViewAlpha;
        StringCompanionObject stringCompanionObject4 = StringCompanionObject.INSTANCE;
        String str4 = String.format(Locale.getDefault(), "%d", Arrays.copyOf(new Object[]{Integer.valueOf(iAlpha)}, 1));
        Intrinsics.checkNotNullExpressionValue(str4, "java.lang.String.format(locale, format, *args)");
        appCompatTextView4.setText(str4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onColorChanged(int color) {
        OnColorChangedListener onColorChangedListener = this.onColorChangedListener;
        if (onColorChangedListener == null) {
            return;
        }
        onColorChangedListener.colorChanged(color);
    }

    public final void setOnColorChangedListener(OnColorChangedListener listener) {
        this.onColorChangedListener = listener;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() { // from class: org.catrobat.paintroid.colorpicker.RgbSelectorView$onAttachedToWindow$seekBarListener$1
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
                Intrinsics.checkNotNullParameter(seekBar, "seekBar");
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                Intrinsics.checkNotNullParameter(seekBar, "seekBar");
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Intrinsics.checkNotNullParameter(seekBar, "seekBar");
                int selectedColor = this.this$0.getSelectedColor();
                this.this$0.setSelectedColor(selectedColor);
                if (fromUser) {
                    this.this$0.onColorChanged(selectedColor);
                }
            }
        };
        this.seekBarRed.setOnSeekBarChangeListener(onSeekBarChangeListener);
        this.seekBarGreen.setOnSeekBarChangeListener(onSeekBarChangeListener);
        this.seekBarBlue.setOnSeekBarChangeListener(onSeekBarChangeListener);
        this.seekBarAlpha.setOnSeekBarChangeListener(onSeekBarChangeListener);
        this.editTextHex.addTextChangedListener(new TextWatcher() { // from class: org.catrobat.paintroid.colorpicker.RgbSelectorView.onAttachedToWindow.1
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Intrinsics.checkNotNullParameter(s, "s");
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Intrinsics.checkNotNullParameter(s, "s");
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable newText) {
                Intrinsics.checkNotNullParameter(newText, "newText");
                if (RgbSelectorView.this.editTextHex.getTag() == null) {
                    int inputToCheckIfHEX = RgbSelectorView.this.parseInputToCheckIfHEX(newText.toString());
                    if (inputToCheckIfHEX == 20000000) {
                        RgbSelectorView.this.setTextColorToRed();
                        return;
                    }
                    RgbSelectorView.this.setSelectedColor(inputToCheckIfHEX);
                    RgbSelectorView.this.onColorChanged(inputToCheckIfHEX);
                    RgbSelectorView.this.resetTextColor();
                    return;
                }
                RgbSelectorView.this.resetTextColor();
            }
        });
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.seekBarRed.setOnSeekBarChangeListener(null);
        this.seekBarGreen.setOnSeekBarChangeListener(null);
        this.seekBarBlue.setOnSeekBarChangeListener(null);
        this.seekBarAlpha.setOnSeekBarChangeListener(null);
    }

    public final void setAlphaRow(boolean catroidFlag, boolean openedFromFormulaEditorInCatroidFlag) {
        this.alphaRow.setVisibility((catroidFlag && openedFromFormulaEditorInCatroidFlag) ? 8 : 0);
    }
}

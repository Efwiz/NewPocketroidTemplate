package org.catrobat.paintroid.ui.tools;

import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatSeekBar;
import java.util.Arrays;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import org.catrobat.paintroid.R;
import org.catrobat.paintroid.tools.helper.DefaultNumberRangeFilter;
import org.catrobat.paintroid.tools.options.FillToolOptionsView;

/* JADX INFO: compiled from: DefaultFillToolOptionsView.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\u0010\u0010\u0010\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00020\u000fH\u0002R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lorg/catrobat/paintroid/ui/tools/DefaultFillToolOptionsView;", "Lorg/catrobat/paintroid/tools/options/FillToolOptionsView;", "toolSpecificOptionsLayout", "Landroid/view/ViewGroup;", "(Landroid/view/ViewGroup;)V", "callback", "Lorg/catrobat/paintroid/tools/options/FillToolOptionsView$Callback;", "colorToleranceEditText", "Landroidx/appcompat/widget/AppCompatEditText;", "colorToleranceSeekBar", "Landroidx/appcompat/widget/AppCompatSeekBar;", "setCallback", "", "setColorToleranceText", "toleranceInPercent", "", "updateColorTolerance", "colorTolerance", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class DefaultFillToolOptionsView implements FillToolOptionsView {
    private FillToolOptionsView.Callback callback;
    private final AppCompatEditText colorToleranceEditText;
    private final AppCompatSeekBar colorToleranceSeekBar;

    public DefaultFillToolOptionsView(ViewGroup toolSpecificOptionsLayout) {
        Intrinsics.checkNotNullParameter(toolSpecificOptionsLayout, "toolSpecificOptionsLayout");
        View viewInflate = LayoutInflater.from(toolSpecificOptionsLayout.getContext()).inflate(R.layout.dialog_pocketpaint_fill_tool, toolSpecificOptionsLayout);
        View viewFindViewById = viewInflate.findViewById(R.id.pocketpaint_color_tolerance_seek_bar);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "fillToolOptionsView.find…color_tolerance_seek_bar)");
        AppCompatSeekBar appCompatSeekBar = (AppCompatSeekBar) viewFindViewById;
        this.colorToleranceSeekBar = appCompatSeekBar;
        View viewFindViewById2 = viewInflate.findViewById(R.id.pocketpaint_fill_tool_dialog_color_tolerance_input);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "fillToolOptionsView.find…og_color_tolerance_input)");
        AppCompatEditText appCompatEditText = (AppCompatEditText) viewFindViewById2;
        this.colorToleranceEditText = appCompatEditText;
        appCompatEditText.setFilters(new InputFilter[]{new DefaultNumberRangeFilter(0, 100)});
        appCompatSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: org.catrobat.paintroid.ui.tools.DefaultFillToolOptionsView.1
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
                if (fromUser) {
                    DefaultFillToolOptionsView.this.setColorToleranceText(progress);
                }
            }
        });
        appCompatEditText.addTextChangedListener(new TextWatcher() { // from class: org.catrobat.paintroid.ui.tools.DefaultFillToolOptionsView.2
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Intrinsics.checkNotNullParameter(s, "s");
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Intrinsics.checkNotNullParameter(s, "s");
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
                Intrinsics.checkNotNullParameter(s, "s");
                try {
                    int i = Integer.parseInt(s.toString());
                    DefaultFillToolOptionsView.this.colorToleranceSeekBar.setProgress(i);
                    DefaultFillToolOptionsView.this.updateColorTolerance(i);
                } catch (NumberFormatException unused) {
                    Log.e("Error parsing tolerance", "result was null");
                }
            }
        });
        setColorToleranceText(12);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateColorTolerance(int colorTolerance) {
        FillToolOptionsView.Callback callback = this.callback;
        if (callback == null) {
            return;
        }
        callback.onColorToleranceChanged(colorTolerance);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setColorToleranceText(int toleranceInPercent) {
        AppCompatEditText appCompatEditText = this.colorToleranceEditText;
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String str = String.format(Locale.getDefault(), "%d", Arrays.copyOf(new Object[]{Integer.valueOf(toleranceInPercent)}, 1));
        Intrinsics.checkNotNullExpressionValue(str, "java.lang.String.format(locale, format, *args)");
        appCompatEditText.setText(str);
    }

    @Override // org.catrobat.paintroid.tools.options.FillToolOptionsView
    public void setCallback(FillToolOptionsView.Callback callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        this.callback = callback;
    }
}

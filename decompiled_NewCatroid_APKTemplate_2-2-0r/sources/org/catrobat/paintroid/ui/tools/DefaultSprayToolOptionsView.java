package org.catrobat.paintroid.ui.tools;

import android.graphics.Paint;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import com.google.android.material.textfield.TextInputEditText;
import java.util.Arrays;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import org.catrobat.paintroid.R;
import org.catrobat.paintroid.tools.helper.DefaultNumberRangeFilter;
import org.catrobat.paintroid.tools.options.SprayToolOptionsView;

/* JADX INFO: compiled from: DefaultSprayToolOptionsView.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u0000 \u00162\u00020\u0001:\u0001\u0016B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u000b\u001a\u00020\fH\u0016J\b\u0010\r\u001a\u00020\u000eH\u0002J\u0012\u0010\u000f\u001a\u00020\u000e2\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0016J\u0010\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u0010\u0010\u0013\u001a\u00020\u000e2\u0006\u0010\u0014\u001a\u00020\u0015H\u0016R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lorg/catrobat/paintroid/ui/tools/DefaultSprayToolOptionsView;", "Lorg/catrobat/paintroid/tools/options/SprayToolOptionsView;", "rootView", "Landroid/view/ViewGroup;", "(Landroid/view/ViewGroup;)V", "callback", "Lorg/catrobat/paintroid/tools/options/SprayToolOptionsView$Callback;", "radiusSeekBar", "Landroid/widget/SeekBar;", "radiusText", "Lcom/google/android/material/textfield/TextInputEditText;", "getRadius", "", "initializeListeners", "", "setCallback", "setCurrentPaint", "paint", "Landroid/graphics/Paint;", "setRadius", "radius", "", "Companion", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class DefaultSprayToolOptionsView implements SprayToolOptionsView {
    private static final String TAG = "DefaultSprayToolOptionsView";
    private SprayToolOptionsView.Callback callback;
    private final SeekBar radiusSeekBar;
    private final TextInputEditText radiusText;

    public DefaultSprayToolOptionsView(ViewGroup rootView) {
        Intrinsics.checkNotNullParameter(rootView, "rootView");
        View viewInflate = LayoutInflater.from(rootView.getContext()).inflate(R.layout.dialog_pocketpaint_spray_tool, rootView);
        Intrinsics.checkNotNullExpressionValue(viewInflate, "inflater.inflate(R.layou…int_spray_tool, rootView)");
        View viewFindViewById = viewInflate.findViewById(R.id.pocketpaint_radius_text);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "sprayToolOptionsView.fin….pocketpaint_radius_text)");
        this.radiusText = (TextInputEditText) viewFindViewById;
        View viewFindViewById2 = viewInflate.findViewById(R.id.pocketpaint_spray_radius_seek_bar);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "sprayToolOptionsView.fin…nt_spray_radius_seek_bar)");
        this.radiusSeekBar = (SeekBar) viewFindViewById2;
        initializeListeners();
    }

    private final void initializeListeners() {
        this.radiusSeekBar.setProgress(5);
        this.radiusText.setText(String.valueOf(this.radiusSeekBar.getProgress()));
        this.radiusText.setFilters(new InputFilter[]{new DefaultNumberRangeFilter(1, 100)});
        this.radiusText.addTextChangedListener(new TextWatcher() { // from class: org.catrobat.paintroid.ui.tools.DefaultSprayToolOptionsView.initializeListeners.1
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
                int i;
                try {
                    i = Integer.parseInt(String.valueOf(DefaultSprayToolOptionsView.this.radiusText.getText()));
                } catch (NumberFormatException e) {
                    String localizedMessage = e.getLocalizedMessage();
                    if (localizedMessage != null) {
                        Log.d(DefaultSprayToolOptionsView.TAG, localizedMessage);
                    }
                    i = 1;
                }
                DefaultSprayToolOptionsView.this.radiusSeekBar.setProgress(i);
            }
        });
        this.radiusSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: org.catrobat.paintroid.ui.tools.DefaultSprayToolOptionsView.initializeListeners.2
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
                if (progress < 1) {
                    DefaultSprayToolOptionsView.this.radiusSeekBar.setProgress(1);
                    progress = 1;
                }
                if (fromUser) {
                    DefaultSprayToolOptionsView.this.radiusText.setText(String.valueOf(progress));
                }
                SprayToolOptionsView.Callback callback = DefaultSprayToolOptionsView.this.callback;
                if (callback == null) {
                    return;
                }
                callback.radiusChanged(progress);
            }
        });
    }

    @Override // org.catrobat.paintroid.tools.options.SprayToolOptionsView
    public void setCallback(SprayToolOptionsView.Callback callback) {
        this.callback = callback;
    }

    @Override // org.catrobat.paintroid.tools.options.SprayToolOptionsView
    public void setRadius(int radius) {
        this.radiusSeekBar.setProgress(radius);
        TextInputEditText textInputEditText = this.radiusText;
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String str = String.format(Locale.getDefault(), "%d", Arrays.copyOf(new Object[]{Integer.valueOf(radius)}, 1));
        Intrinsics.checkNotNullExpressionValue(str, "java.lang.String.format(locale, format, *args)");
        textInputEditText.setText(str);
    }

    @Override // org.catrobat.paintroid.tools.options.SprayToolOptionsView
    public void setCurrentPaint(Paint paint) {
        Intrinsics.checkNotNullParameter(paint, "paint");
        setRadius((int) paint.getStrokeWidth());
    }

    @Override // org.catrobat.paintroid.tools.options.SprayToolOptionsView
    public float getRadius() {
        return this.radiusSeekBar.getProgress();
    }
}

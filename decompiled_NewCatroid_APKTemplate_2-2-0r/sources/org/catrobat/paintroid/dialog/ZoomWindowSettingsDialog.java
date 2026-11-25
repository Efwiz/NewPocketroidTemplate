package org.catrobat.paintroid.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SwitchCompat;
import com.google.android.gms.analytics.ecommerce.Promotion;
import com.google.android.material.slider.LabelFormatter;
import com.google.android.material.slider.Slider;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.paintroid.R;
import org.catrobat.paintroid.UserPreferences;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleConstant;

/* JADX INFO: compiled from: ZoomWindowSettingsDialog.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0012\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0016J\u001a\u0010\u0013\u001a\u00020\f2\u0006\u0010\u0014\u001a\u00020\u00152\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0017R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lorg/catrobat/paintroid/dialog/ZoomWindowSettingsDialog;", "Lorg/catrobat/paintroid/dialog/MainActivityDialogFragment;", "sharedPreferences", "Lorg/catrobat/paintroid/UserPreferences;", "(Lorg/catrobat/paintroid/UserPreferences;)V", CompilerOptions.ENABLED, "", "initialEnabledValue", "initialPercentageValue", "", "percentage", "onCancel", "", "dialog", "Landroid/content/DialogInterface;", "onCreateDialog", "Landroid/app/Dialog;", "savedInstanceState", "Landroid/os/Bundle;", "onViewCreated", Promotion.ACTION_VIEW, "Landroid/view/View;", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class ZoomWindowSettingsDialog extends MainActivityDialogFragment {
    private boolean enabled;
    private final boolean initialEnabledValue;
    private final int initialPercentageValue;
    private int percentage;
    private final UserPreferences sharedPreferences;

    public ZoomWindowSettingsDialog(UserPreferences sharedPreferences) {
        Intrinsics.checkNotNullParameter(sharedPreferences, "sharedPreferences");
        this.sharedPreferences = sharedPreferences;
        this.initialEnabledValue = sharedPreferences.getPreferenceZoomWindowEnabled();
        this.initialPercentageValue = sharedPreferences.getPreferenceZoomWindowZoomPercentage();
        this.enabled = sharedPreferences.getPreferenceZoomWindowEnabled();
        this.percentage = sharedPreferences.getPreferenceZoomWindowZoomPercentage();
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(view, "view");
        super.onViewCreated(view, savedInstanceState);
        SwitchCompat switchCompat = (SwitchCompat) view.findViewById(R.id.pocketpaint_zoom_window_enabled);
        Slider slider = (Slider) view.findViewById(R.id.pocketpaint_zoom_window_slider);
        final TextView textView = (TextView) view.findViewById(R.id.pocketpaint_zoom_window_slider_progress);
        switchCompat.setChecked(this.initialEnabledValue);
        textView.setText(new StringBuilder().append(this.initialPercentageValue).append(MangleConstant.PLATFORM_FUNCTION_MARKER).toString());
        slider.setValue(this.initialPercentageValue);
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: org.catrobat.paintroid.dialog.ZoomWindowSettingsDialog$$ExternalSyntheticLambda0
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                ZoomWindowSettingsDialog.m5969onViewCreated$lambda0(this.f$0, compoundButton, z);
            }
        });
        slider.addOnChangeListener(new Slider.OnChangeListener() { // from class: org.catrobat.paintroid.dialog.ZoomWindowSettingsDialog$$ExternalSyntheticLambda1
            @Override // com.google.android.material.slider.BaseOnChangeListener
            public final void onValueChange(Slider slider2, float f, boolean z) {
                ZoomWindowSettingsDialog.m5970onViewCreated$lambda1(textView, this, slider2, f, z);
            }
        });
        slider.setLabelFormatter(new LabelFormatter() { // from class: org.catrobat.paintroid.dialog.ZoomWindowSettingsDialog$$ExternalSyntheticLambda2
            @Override // com.google.android.material.slider.LabelFormatter
            public final String getFormattedValue(float f) {
                return ZoomWindowSettingsDialog.m5971onViewCreated$lambda2(f);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: onViewCreated$lambda-0, reason: not valid java name */
    public static final void m5969onViewCreated$lambda0(ZoomWindowSettingsDialog this$0, CompoundButton compoundButton, boolean z) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.enabled = z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: onViewCreated$lambda-1, reason: not valid java name */
    public static final void m5970onViewCreated$lambda1(TextView textView, ZoomWindowSettingsDialog this$0, Slider noName_0, float f, boolean z) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(noName_0, "$noName_0");
        int i = (int) f;
        textView.setText(Intrinsics.stringPlus(String.valueOf(i), "%"));
        this$0.percentage = i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: onViewCreated$lambda-2, reason: not valid java name */
    public static final String m5971onViewCreated$lambda2(float f) {
        return new StringBuilder().append((int) f).append(MangleConstant.PLATFORM_FUNCTION_MARKER).toString();
    }

    @Override // androidx.appcompat.app.AppCompatDialogFragment, androidx.fragment.app.DialogFragment
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater layoutInflater = requireActivity().getLayoutInflater();
        Intrinsics.checkNotNullExpressionValue(layoutInflater, "requireActivity().layoutInflater");
        View layout = layoutInflater.inflate(R.layout.dialog_pocketpaint_zoomwindow_settings, (ViewGroup) null);
        Intrinsics.checkNotNullExpressionValue(layout, "layout");
        onViewCreated(layout, savedInstanceState);
        AlertDialog alertDialogCreate = new AlertDialog.Builder(requireContext(), R.style.PocketPaintAlertDialog).setTitle(R.string.menu_zoom_settings).setView(layout).setPositiveButton(R.string.pocketpaint_ok, new DialogInterface.OnClickListener() { // from class: org.catrobat.paintroid.dialog.ZoomWindowSettingsDialog$$ExternalSyntheticLambda3
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                ZoomWindowSettingsDialog.m5967onCreateDialog$lambda3(this.f$0, dialogInterface, i);
            }
        }).setNegativeButton(R.string.cancel_button_text, new DialogInterface.OnClickListener() { // from class: org.catrobat.paintroid.dialog.ZoomWindowSettingsDialog$$ExternalSyntheticLambda4
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                ZoomWindowSettingsDialog.m5968onCreateDialog$lambda4(this.f$0, dialogInterface, i);
            }
        }).create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "Builder(requireContext()…                .create()");
        return alertDialogCreate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: onCreateDialog$lambda-3, reason: not valid java name */
    public static final void m5967onCreateDialog$lambda3(ZoomWindowSettingsDialog this$0, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.sharedPreferences.setPreferenceZoomWindowEnabled(this$0.enabled);
        this$0.sharedPreferences.setPreferenceZoomWindowZoomPercentage(this$0.percentage);
        this$0.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: onCreateDialog$lambda-4, reason: not valid java name */
    public static final void m5968onCreateDialog$lambda4(ZoomWindowSettingsDialog this$0, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.sharedPreferences.setPreferenceZoomWindowEnabled(this$0.initialEnabledValue);
        this$0.sharedPreferences.setPreferenceZoomWindowZoomPercentage(this$0.initialPercentageValue);
        this$0.dismiss();
    }

    @Override // androidx.fragment.app.DialogFragment, android.content.DialogInterface.OnCancelListener
    public void onCancel(DialogInterface dialog) {
        Intrinsics.checkNotNullParameter(dialog, "dialog");
        this.sharedPreferences.setPreferenceZoomWindowEnabled(this.initialEnabledValue);
        this.sharedPreferences.setPreferenceZoomWindowZoomPercentage(this.initialPercentageValue);
        super.onCancel(dialog);
    }
}

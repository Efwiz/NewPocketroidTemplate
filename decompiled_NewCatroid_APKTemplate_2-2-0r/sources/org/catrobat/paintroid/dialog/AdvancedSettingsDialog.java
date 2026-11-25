package org.catrobat.paintroid.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SwitchCompat;
import com.google.android.gms.analytics.ecommerce.Promotion;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.paintroid.R;
import org.catrobat.paintroid.tools.helper.AdvancedSettingsAlgorithms;
import org.catrobat.paintroid.tools.implementation.DefaultToolPaint;

/* JADX INFO: compiled from: AdvancedSettingsDialog.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016J\u0012\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\rH\u0016J\u001a\u0010\u000e\u001a\u00020\u00072\u0006\u0010\u000f\u001a\u00020\u00102\b\u0010\f\u001a\u0004\u0018\u00010\rH\u0017R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lorg/catrobat/paintroid/dialog/AdvancedSettingsDialog;", "Lorg/catrobat/paintroid/dialog/MainActivityDialogFragment;", "()V", "initValueAntialiasing", "", "initValueSmoothing", "onCancel", "", "dialog", "Landroid/content/DialogInterface;", "onCreateDialog", "Landroid/app/Dialog;", "savedInstanceState", "Landroid/os/Bundle;", "onViewCreated", Promotion.ACTION_VIEW, "Landroid/view/View;", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class AdvancedSettingsDialog extends MainActivityDialogFragment {
    private boolean initValueAntialiasing = DefaultToolPaint.INSTANCE.getAntialiasing();
    private boolean initValueSmoothing = AdvancedSettingsAlgorithms.smoothing;

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(view, "view");
        super.onViewCreated(view, savedInstanceState);
        SwitchCompat switchCompat = (SwitchCompat) view.findViewById(R.id.pocketpaint_antialiasing);
        SwitchCompat switchCompat2 = (SwitchCompat) view.findViewById(R.id.pocketpaint_smoothing);
        switchCompat.setChecked(DefaultToolPaint.INSTANCE.getAntialiasing());
        switchCompat2.setChecked(AdvancedSettingsAlgorithms.smoothing);
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: org.catrobat.paintroid.dialog.AdvancedSettingsDialog$$ExternalSyntheticLambda0
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                AdvancedSettingsDialog.m5929onViewCreated$lambda0(compoundButton, z);
            }
        });
        if (switchCompat2 == null) {
            return;
        }
        switchCompat2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: org.catrobat.paintroid.dialog.AdvancedSettingsDialog$$ExternalSyntheticLambda1
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                AdvancedSettingsAlgorithms.smoothing = z;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: onViewCreated$lambda-0, reason: not valid java name */
    public static final void m5929onViewCreated$lambda0(CompoundButton compoundButton, boolean z) {
        DefaultToolPaint.INSTANCE.setAntialiasing(z);
    }

    @Override // androidx.appcompat.app.AppCompatDialogFragment, androidx.fragment.app.DialogFragment
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater layoutInflater = requireActivity().getLayoutInflater();
        Intrinsics.checkNotNullExpressionValue(layoutInflater, "requireActivity().layoutInflater");
        View layout = layoutInflater.inflate(R.layout.dialog_pocketpaint_advanced_settings, (ViewGroup) null);
        Intrinsics.checkNotNullExpressionValue(layout, "layout");
        onViewCreated(layout, savedInstanceState);
        AlertDialog alertDialogCreate = new AlertDialog.Builder(requireContext(), R.style.PocketPaintAlertDialog).setTitle(R.string.menu_advanced).setView(layout).setPositiveButton(R.string.pocketpaint_ok, new DialogInterface.OnClickListener() { // from class: org.catrobat.paintroid.dialog.AdvancedSettingsDialog$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                AdvancedSettingsDialog.m5927onCreateDialog$lambda2(this.f$0, dialogInterface, i);
            }
        }).setNegativeButton(R.string.cancel_button_text, new DialogInterface.OnClickListener() { // from class: org.catrobat.paintroid.dialog.AdvancedSettingsDialog$$ExternalSyntheticLambda3
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                AdvancedSettingsDialog.m5928onCreateDialog$lambda3(this.f$0, dialogInterface, i);
            }
        }).create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "Builder(requireContext()…  }\n            .create()");
        return alertDialogCreate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: onCreateDialog$lambda-2, reason: not valid java name */
    public static final void m5927onCreateDialog$lambda2(AdvancedSettingsDialog this$0, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getPresenter().setAntialiasingOnOkClicked();
        this$0.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: onCreateDialog$lambda-3, reason: not valid java name */
    public static final void m5928onCreateDialog$lambda3(AdvancedSettingsDialog this$0, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        DefaultToolPaint.INSTANCE.setAntialiasing(this$0.initValueAntialiasing);
        AdvancedSettingsAlgorithms.smoothing = this$0.initValueSmoothing;
        this$0.dismiss();
    }

    @Override // androidx.fragment.app.DialogFragment, android.content.DialogInterface.OnCancelListener
    public void onCancel(DialogInterface dialog) {
        Intrinsics.checkNotNullParameter(dialog, "dialog");
        DefaultToolPaint.INSTANCE.setAntialiasing(this.initValueAntialiasing);
        AdvancedSettingsAlgorithms.smoothing = this.initValueSmoothing;
        super.onCancel(dialog);
    }
}

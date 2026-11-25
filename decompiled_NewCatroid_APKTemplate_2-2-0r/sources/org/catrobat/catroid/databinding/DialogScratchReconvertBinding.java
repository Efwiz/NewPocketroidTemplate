package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/DialogScratchReconvertBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class DialogScratchReconvertBinding implements ViewBinding {
    public final RadioButton dialogScratchReconvertRadioDownload;
    public final RadioGroup dialogScratchReconvertRadioGroup;
    public final RadioButton dialogScratchReconvertRadioReconvert;
    private final ScrollView rootView;

    private DialogScratchReconvertBinding(ScrollView rootView, RadioButton dialogScratchReconvertRadioDownload, RadioGroup dialogScratchReconvertRadioGroup, RadioButton dialogScratchReconvertRadioReconvert) {
        this.rootView = rootView;
        this.dialogScratchReconvertRadioDownload = dialogScratchReconvertRadioDownload;
        this.dialogScratchReconvertRadioGroup = dialogScratchReconvertRadioGroup;
        this.dialogScratchReconvertRadioReconvert = dialogScratchReconvertRadioReconvert;
    }

    @Override // androidx.viewbinding.ViewBinding
    public ScrollView getRoot() {
        return this.rootView;
    }

    public static DialogScratchReconvertBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static DialogScratchReconvertBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.dialog_scratch_reconvert, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static DialogScratchReconvertBinding bind(View rootView) {
        int i = R.id.dialog_scratch_reconvert_radio_download;
        RadioButton radioButton = (RadioButton) ViewBindings.findChildViewById(rootView, R.id.dialog_scratch_reconvert_radio_download);
        if (radioButton != null) {
            i = R.id.dialog_scratch_reconvert_radio_group;
            RadioGroup radioGroup = (RadioGroup) ViewBindings.findChildViewById(rootView, R.id.dialog_scratch_reconvert_radio_group);
            if (radioGroup != null) {
                i = R.id.dialog_scratch_reconvert_radio_reconvert;
                RadioButton radioButton2 = (RadioButton) ViewBindings.findChildViewById(rootView, R.id.dialog_scratch_reconvert_radio_reconvert);
                if (radioButton2 != null) {
                    return new DialogScratchReconvertBinding((ScrollView) rootView, radioButton, radioGroup, radioButton2);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/DialogFormulaeditorComputeLandscapeBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class DialogFormulaeditorComputeLandscapeBinding implements ViewBinding {
    public final TextView formulaEditorComputeDialogTextviewLandscapeMode;
    private final RelativeLayout rootView;

    private DialogFormulaeditorComputeLandscapeBinding(RelativeLayout rootView, TextView formulaEditorComputeDialogTextviewLandscapeMode) {
        this.rootView = rootView;
        this.formulaEditorComputeDialogTextviewLandscapeMode = formulaEditorComputeDialogTextviewLandscapeMode;
    }

    @Override // androidx.viewbinding.ViewBinding
    public RelativeLayout getRoot() {
        return this.rootView;
    }

    public static DialogFormulaeditorComputeLandscapeBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static DialogFormulaeditorComputeLandscapeBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.dialog_formulaeditor_compute_landscape, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static DialogFormulaeditorComputeLandscapeBinding bind(View rootView) {
        TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.formula_editor_compute_dialog_textview_landscape_mode);
        if (textView != null) {
            return new DialogFormulaeditorComputeLandscapeBinding((RelativeLayout) rootView, textView);
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(R.id.formula_editor_compute_dialog_textview_landscape_mode)));
    }
}

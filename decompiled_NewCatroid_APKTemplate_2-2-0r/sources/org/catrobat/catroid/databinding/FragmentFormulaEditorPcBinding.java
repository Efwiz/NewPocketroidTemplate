package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.formulaeditor.FormulaEditorEditText;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/FragmentFormulaEditorPcBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class FragmentFormulaEditorPcBinding implements ViewBinding {
    public final LinearLayout formulaEditorBrickSpace;
    public final FormulaEditorEditText formulaEditorEditField;
    public final FormulaEditorKeyboardPcBinding formulaEditorKeyboardview;
    private final LinearLayout rootView;

    private FragmentFormulaEditorPcBinding(LinearLayout rootView, LinearLayout formulaEditorBrickSpace, FormulaEditorEditText formulaEditorEditField, FormulaEditorKeyboardPcBinding formulaEditorKeyboardview) {
        this.rootView = rootView;
        this.formulaEditorBrickSpace = formulaEditorBrickSpace;
        this.formulaEditorEditField = formulaEditorEditField;
        this.formulaEditorKeyboardview = formulaEditorKeyboardview;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static FragmentFormulaEditorPcBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static FragmentFormulaEditorPcBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.fragment_formula_editor_pc, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static FragmentFormulaEditorPcBinding bind(View rootView) {
        int i = R.id.formula_editor_brick_space;
        LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.formula_editor_brick_space);
        if (linearLayout != null) {
            i = R.id.formula_editor_edit_field;
            FormulaEditorEditText formulaEditorEditText = (FormulaEditorEditText) ViewBindings.findChildViewById(rootView, R.id.formula_editor_edit_field);
            if (formulaEditorEditText != null) {
                i = R.id.formula_editor_keyboardview;
                View viewFindChildViewById = ViewBindings.findChildViewById(rootView, R.id.formula_editor_keyboardview);
                if (viewFindChildViewById != null) {
                    return new FragmentFormulaEditorPcBinding((LinearLayout) rootView, linearLayout, formulaEditorEditText, FormulaEditorKeyboardPcBinding.bind(viewFindChildViewById));
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.formulaeditor.FormulaEditorEditText;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/FragmentFormulaEditorBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class FragmentFormulaEditorBinding implements ViewBinding {
    public final RelativeLayout formulaEditorBrickAndFormula;
    public final LinearLayout formulaEditorBrickSpace;
    public final FormulaEditorEditText formulaEditorEditField;
    public final FrameLayout formulaEditorFieldContainer;
    public final FormulaEditorKeyboardBinding formulaEditorKeyboardview;
    private final LinearLayout rootView;

    private FragmentFormulaEditorBinding(LinearLayout rootView, RelativeLayout formulaEditorBrickAndFormula, LinearLayout formulaEditorBrickSpace, FormulaEditorEditText formulaEditorEditField, FrameLayout formulaEditorFieldContainer, FormulaEditorKeyboardBinding formulaEditorKeyboardview) {
        this.rootView = rootView;
        this.formulaEditorBrickAndFormula = formulaEditorBrickAndFormula;
        this.formulaEditorBrickSpace = formulaEditorBrickSpace;
        this.formulaEditorEditField = formulaEditorEditField;
        this.formulaEditorFieldContainer = formulaEditorFieldContainer;
        this.formulaEditorKeyboardview = formulaEditorKeyboardview;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static FragmentFormulaEditorBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static FragmentFormulaEditorBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.fragment_formula_editor, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static FragmentFormulaEditorBinding bind(View rootView) {
        int i = R.id.formula_editor_brick_and_formula;
        RelativeLayout relativeLayout = (RelativeLayout) ViewBindings.findChildViewById(rootView, R.id.formula_editor_brick_and_formula);
        if (relativeLayout != null) {
            i = R.id.formula_editor_brick_space;
            LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.formula_editor_brick_space);
            if (linearLayout != null) {
                i = R.id.formula_editor_edit_field;
                FormulaEditorEditText formulaEditorEditText = (FormulaEditorEditText) ViewBindings.findChildViewById(rootView, R.id.formula_editor_edit_field);
                if (formulaEditorEditText != null) {
                    i = R.id.formula_editor_field_container;
                    FrameLayout frameLayout = (FrameLayout) ViewBindings.findChildViewById(rootView, R.id.formula_editor_field_container);
                    if (frameLayout != null) {
                        i = R.id.formula_editor_keyboardview;
                        View viewFindChildViewById = ViewBindings.findChildViewById(rootView, R.id.formula_editor_keyboardview);
                        if (viewFindChildViewById != null) {
                            return new FragmentFormulaEditorBinding((LinearLayout) rootView, relativeLayout, linearLayout, formulaEditorEditText, frameLayout, FormulaEditorKeyboardBinding.bind(viewFindChildViewById));
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

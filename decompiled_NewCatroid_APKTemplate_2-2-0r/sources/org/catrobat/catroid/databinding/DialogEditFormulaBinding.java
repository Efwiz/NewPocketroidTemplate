package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/DialogEditFormulaBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class DialogEditFormulaBinding implements ViewBinding {
    public final Button addParamButton;
    public final TextInputEditText formulaDisplayNameEdit;
    public final TextInputEditText formulaFunctionNameEdit;
    public final TextInputEditText formulaIdEdit;
    public final LinearLayout paramsContainer;
    private final ScrollView rootView;
    public final TextInputLayout textInputLayoutDisplayName;
    public final TextInputLayout textInputLayoutFunctionName;
    public final TextInputLayout textInputLayoutId;

    private DialogEditFormulaBinding(ScrollView rootView, Button addParamButton, TextInputEditText formulaDisplayNameEdit, TextInputEditText formulaFunctionNameEdit, TextInputEditText formulaIdEdit, LinearLayout paramsContainer, TextInputLayout textInputLayoutDisplayName, TextInputLayout textInputLayoutFunctionName, TextInputLayout textInputLayoutId) {
        this.rootView = rootView;
        this.addParamButton = addParamButton;
        this.formulaDisplayNameEdit = formulaDisplayNameEdit;
        this.formulaFunctionNameEdit = formulaFunctionNameEdit;
        this.formulaIdEdit = formulaIdEdit;
        this.paramsContainer = paramsContainer;
        this.textInputLayoutDisplayName = textInputLayoutDisplayName;
        this.textInputLayoutFunctionName = textInputLayoutFunctionName;
        this.textInputLayoutId = textInputLayoutId;
    }

    @Override // androidx.viewbinding.ViewBinding
    public ScrollView getRoot() {
        return this.rootView;
    }

    public static DialogEditFormulaBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static DialogEditFormulaBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.dialog_edit_formula, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static DialogEditFormulaBinding bind(View rootView) {
        int i = R.id.add_param_button;
        Button button = (Button) ViewBindings.findChildViewById(rootView, R.id.add_param_button);
        if (button != null) {
            i = R.id.formula_display_name_edit;
            TextInputEditText textInputEditText = (TextInputEditText) ViewBindings.findChildViewById(rootView, R.id.formula_display_name_edit);
            if (textInputEditText != null) {
                i = R.id.formula_function_name_edit;
                TextInputEditText textInputEditText2 = (TextInputEditText) ViewBindings.findChildViewById(rootView, R.id.formula_function_name_edit);
                if (textInputEditText2 != null) {
                    i = R.id.formula_id_edit;
                    TextInputEditText textInputEditText3 = (TextInputEditText) ViewBindings.findChildViewById(rootView, R.id.formula_id_edit);
                    if (textInputEditText3 != null) {
                        i = R.id.params_container;
                        LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.params_container);
                        if (linearLayout != null) {
                            i = R.id.text_input_layout_display_name;
                            TextInputLayout textInputLayout = (TextInputLayout) ViewBindings.findChildViewById(rootView, R.id.text_input_layout_display_name);
                            if (textInputLayout != null) {
                                i = R.id.text_input_layout_function_name;
                                TextInputLayout textInputLayout2 = (TextInputLayout) ViewBindings.findChildViewById(rootView, R.id.text_input_layout_function_name);
                                if (textInputLayout2 != null) {
                                    i = R.id.text_input_layout_id;
                                    TextInputLayout textInputLayout3 = (TextInputLayout) ViewBindings.findChildViewById(rootView, R.id.text_input_layout_id);
                                    if (textInputLayout3 != null) {
                                        return new DialogEditFormulaBinding((ScrollView) rootView, button, textInputEditText, textInputEditText2, textInputEditText3, linearLayout, textInputLayout, textInputLayout2, textInputLayout3);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

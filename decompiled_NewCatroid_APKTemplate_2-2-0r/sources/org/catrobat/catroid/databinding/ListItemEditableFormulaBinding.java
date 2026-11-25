package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.card.MaterialCardView;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/ListItemEditableFormulaBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class ListItemEditableFormulaBinding implements ViewBinding {
    public final TextView formulaDisplayName;
    public final TextView formulaFunctionName;
    public final TextView formulaId;
    private final MaterialCardView rootView;

    private ListItemEditableFormulaBinding(MaterialCardView rootView, TextView formulaDisplayName, TextView formulaFunctionName, TextView formulaId) {
        this.rootView = rootView;
        this.formulaDisplayName = formulaDisplayName;
        this.formulaFunctionName = formulaFunctionName;
        this.formulaId = formulaId;
    }

    @Override // androidx.viewbinding.ViewBinding
    public MaterialCardView getRoot() {
        return this.rootView;
    }

    public static ListItemEditableFormulaBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ListItemEditableFormulaBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.list_item_editable_formula, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ListItemEditableFormulaBinding bind(View rootView) {
        int i = R.id.formula_display_name;
        TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.formula_display_name);
        if (textView != null) {
            i = R.id.formula_function_name;
            TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.formula_function_name);
            if (textView2 != null) {
                i = R.id.formula_id;
                TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.formula_id);
                if (textView3 != null) {
                    return new ListItemEditableFormulaBinding((MaterialCardView) rootView, textView, textView2, textView3);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

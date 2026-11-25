package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/DialogImportRejectedBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class DialogImportRejectedBinding implements ViewBinding {
    public final TextView conflictingVariables;
    public final TextView importConflictingVariables;
    public final TextView importConflictingVariablesReason;
    public final TextView importConflictingVariablesTryAgain;
    private final LinearLayout rootView;

    private DialogImportRejectedBinding(LinearLayout rootView, TextView conflictingVariables, TextView importConflictingVariables, TextView importConflictingVariablesReason, TextView importConflictingVariablesTryAgain) {
        this.rootView = rootView;
        this.conflictingVariables = conflictingVariables;
        this.importConflictingVariables = importConflictingVariables;
        this.importConflictingVariablesReason = importConflictingVariablesReason;
        this.importConflictingVariablesTryAgain = importConflictingVariablesTryAgain;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static DialogImportRejectedBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static DialogImportRejectedBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.dialog_import_rejected, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static DialogImportRejectedBinding bind(View rootView) {
        int i = R.id.conflicting_variables;
        TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.conflicting_variables);
        if (textView != null) {
            i = R.id.import_conflicting_variables;
            TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.import_conflicting_variables);
            if (textView2 != null) {
                i = R.id.import_conflicting_variables_reason;
                TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.import_conflicting_variables_reason);
                if (textView3 != null) {
                    i = R.id.import_conflicting_variables_try_again;
                    TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.import_conflicting_variables_try_again);
                    if (textView4 != null) {
                        return new DialogImportRejectedBinding((LinearLayout) rootView, textView, textView2, textView3, textView4);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

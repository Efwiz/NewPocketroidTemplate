package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.utils.BorderTextView;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/DialogFormulaEditorIntroBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class DialogFormulaEditorIntroBinding implements ViewBinding {
    public final Button introDialogNextButton;
    public final Button introDialogSkipButton;
    public final BorderTextView introDialogSummary;
    public final BorderTextView introDialogTitle;
    private final LinearLayout rootView;

    private DialogFormulaEditorIntroBinding(LinearLayout rootView, Button introDialogNextButton, Button introDialogSkipButton, BorderTextView introDialogSummary, BorderTextView introDialogTitle) {
        this.rootView = rootView;
        this.introDialogNextButton = introDialogNextButton;
        this.introDialogSkipButton = introDialogSkipButton;
        this.introDialogSummary = introDialogSummary;
        this.introDialogTitle = introDialogTitle;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static DialogFormulaEditorIntroBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static DialogFormulaEditorIntroBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.dialog_formula_editor_intro, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static DialogFormulaEditorIntroBinding bind(View rootView) {
        int i = R.id.intro_dialog_next_button;
        Button button = (Button) ViewBindings.findChildViewById(rootView, R.id.intro_dialog_next_button);
        if (button != null) {
            i = R.id.intro_dialog_skip_button;
            Button button2 = (Button) ViewBindings.findChildViewById(rootView, R.id.intro_dialog_skip_button);
            if (button2 != null) {
                i = R.id.intro_dialog_summary;
                BorderTextView borderTextView = (BorderTextView) ViewBindings.findChildViewById(rootView, R.id.intro_dialog_summary);
                if (borderTextView != null) {
                    i = R.id.intro_dialog_title;
                    BorderTextView borderTextView2 = (BorderTextView) ViewBindings.findChildViewById(rootView, R.id.intro_dialog_title);
                    if (borderTextView2 != null) {
                        return new DialogFormulaEditorIntroBinding((LinearLayout) rootView, button, button2, borderTextView, borderTextView2);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

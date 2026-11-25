package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/DialogOverwriteProjectBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class DialogOverwriteProjectBinding implements ViewBinding {
    public final TextInputLayout input;
    public final TextInputEditText inputEditText;
    public final RadioGroup radioGroup;
    public final RadioButton rename;
    public final RadioButton replace;
    private final ScrollView rootView;
    public final TextView textView;

    private DialogOverwriteProjectBinding(ScrollView rootView, TextInputLayout input, TextInputEditText inputEditText, RadioGroup radioGroup, RadioButton rename, RadioButton replace, TextView textView) {
        this.rootView = rootView;
        this.input = input;
        this.inputEditText = inputEditText;
        this.radioGroup = radioGroup;
        this.rename = rename;
        this.replace = replace;
        this.textView = textView;
    }

    @Override // androidx.viewbinding.ViewBinding
    public ScrollView getRoot() {
        return this.rootView;
    }

    public static DialogOverwriteProjectBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static DialogOverwriteProjectBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.dialog_overwrite_project, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static DialogOverwriteProjectBinding bind(View rootView) {
        int i = R.id.input;
        TextInputLayout textInputLayout = (TextInputLayout) ViewBindings.findChildViewById(rootView, R.id.input);
        if (textInputLayout != null) {
            i = R.id.input_edit_text;
            TextInputEditText textInputEditText = (TextInputEditText) ViewBindings.findChildViewById(rootView, R.id.input_edit_text);
            if (textInputEditText != null) {
                i = R.id.radio_group;
                RadioGroup radioGroup = (RadioGroup) ViewBindings.findChildViewById(rootView, R.id.radio_group);
                if (radioGroup != null) {
                    i = R.id.rename;
                    RadioButton radioButton = (RadioButton) ViewBindings.findChildViewById(rootView, R.id.rename);
                    if (radioButton != null) {
                        i = R.id.replace;
                        RadioButton radioButton2 = (RadioButton) ViewBindings.findChildViewById(rootView, R.id.replace);
                        if (radioButton2 != null) {
                            i = R.id.textView;
                            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.textView);
                            if (textView != null) {
                                return new DialogOverwriteProjectBinding((ScrollView) rootView, textInputLayout, textInputEditText, radioGroup, radioButton, radioButton2, textView);
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

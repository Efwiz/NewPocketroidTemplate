package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/DialogRegexJsonExtractorBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class DialogRegexJsonExtractorBinding implements ViewBinding {
    public final TextInputLayout input;
    public final TextInputEditText inputEditText;
    private final LinearLayout rootView;

    private DialogRegexJsonExtractorBinding(LinearLayout rootView, TextInputLayout input, TextInputEditText inputEditText) {
        this.rootView = rootView;
        this.input = input;
        this.inputEditText = inputEditText;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static DialogRegexJsonExtractorBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static DialogRegexJsonExtractorBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.dialog_regex_json_extractor, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static DialogRegexJsonExtractorBinding bind(View rootView) {
        int i = R.id.input;
        TextInputLayout textInputLayout = (TextInputLayout) ViewBindings.findChildViewById(rootView, R.id.input);
        if (textInputLayout != null) {
            i = R.id.input_edit_text;
            TextInputEditText textInputEditText = (TextInputEditText) ViewBindings.findChildViewById(rootView, R.id.input_edit_text);
            if (textInputEditText != null) {
                return new DialogRegexJsonExtractorBinding((LinearLayout) rootView, textInputLayout, textInputEditText);
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

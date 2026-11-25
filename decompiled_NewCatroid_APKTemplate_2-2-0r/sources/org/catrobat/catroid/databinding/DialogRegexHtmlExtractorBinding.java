package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/DialogRegexHtmlExtractorBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class DialogRegexHtmlExtractorBinding implements ViewBinding {
    public final TextInputLayout input;
    public final TextInputLayout input1;
    public final TextInputEditText inputEditText;
    public final TextInputEditText inputEditText1;
    private final ScrollView rootView;

    private DialogRegexHtmlExtractorBinding(ScrollView rootView, TextInputLayout input, TextInputLayout input1, TextInputEditText inputEditText, TextInputEditText inputEditText1) {
        this.rootView = rootView;
        this.input = input;
        this.input1 = input1;
        this.inputEditText = inputEditText;
        this.inputEditText1 = inputEditText1;
    }

    @Override // androidx.viewbinding.ViewBinding
    public ScrollView getRoot() {
        return this.rootView;
    }

    public static DialogRegexHtmlExtractorBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static DialogRegexHtmlExtractorBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.dialog_regex_html_extractor, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static DialogRegexHtmlExtractorBinding bind(View rootView) {
        int i = R.id.input;
        TextInputLayout textInputLayout = (TextInputLayout) ViewBindings.findChildViewById(rootView, R.id.input);
        if (textInputLayout != null) {
            i = R.id.input1;
            TextInputLayout textInputLayout2 = (TextInputLayout) ViewBindings.findChildViewById(rootView, R.id.input1);
            if (textInputLayout2 != null) {
                i = R.id.input_edit_text;
                TextInputEditText textInputEditText = (TextInputEditText) ViewBindings.findChildViewById(rootView, R.id.input_edit_text);
                if (textInputEditText != null) {
                    i = R.id.input_edit_text1;
                    TextInputEditText textInputEditText2 = (TextInputEditText) ViewBindings.findChildViewById(rootView, R.id.input_edit_text1);
                    if (textInputEditText2 != null) {
                        return new DialogRegexHtmlExtractorBinding((ScrollView) rootView, textInputLayout, textInputLayout2, textInputEditText, textInputEditText2);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

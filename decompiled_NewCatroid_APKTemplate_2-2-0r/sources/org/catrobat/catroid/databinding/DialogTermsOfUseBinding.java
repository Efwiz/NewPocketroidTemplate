package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/DialogTermsOfUseBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class DialogTermsOfUseBinding implements ViewBinding {
    public final CheckBox dialogTermsOfUseCheckBoxAgreePermanently;
    public final TextView dialogTermsOfUseTextViewInfo;
    public final TextView dialogTermsOfUseTextViewUrl;
    private final ScrollView rootView;

    private DialogTermsOfUseBinding(ScrollView rootView, CheckBox dialogTermsOfUseCheckBoxAgreePermanently, TextView dialogTermsOfUseTextViewInfo, TextView dialogTermsOfUseTextViewUrl) {
        this.rootView = rootView;
        this.dialogTermsOfUseCheckBoxAgreePermanently = dialogTermsOfUseCheckBoxAgreePermanently;
        this.dialogTermsOfUseTextViewInfo = dialogTermsOfUseTextViewInfo;
        this.dialogTermsOfUseTextViewUrl = dialogTermsOfUseTextViewUrl;
    }

    @Override // androidx.viewbinding.ViewBinding
    public ScrollView getRoot() {
        return this.rootView;
    }

    public static DialogTermsOfUseBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static DialogTermsOfUseBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.dialog_terms_of_use, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static DialogTermsOfUseBinding bind(View rootView) {
        int i = R.id.dialog_terms_of_use_check_box_agree_permanently;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.dialog_terms_of_use_check_box_agree_permanently);
        if (checkBox != null) {
            i = R.id.dialog_terms_of_use_text_view_info;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.dialog_terms_of_use_text_view_info);
            if (textView != null) {
                i = R.id.dialog_terms_of_use_text_view_url;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.dialog_terms_of_use_text_view_url);
                if (textView2 != null) {
                    return new DialogTermsOfUseBinding((ScrollView) rootView, checkBox, textView, textView2);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

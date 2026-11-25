package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/PrivacyPolicyViewBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class PrivacyPolicyViewBinding implements ViewBinding {
    public final TextView dialogPrivacyPolicyDisclaimer;
    public final TextView dialogPrivacyPolicyTextViewUrl;
    private final ScrollView rootView;
    public final TextView welcome;

    private PrivacyPolicyViewBinding(ScrollView rootView, TextView dialogPrivacyPolicyDisclaimer, TextView dialogPrivacyPolicyTextViewUrl, TextView welcome) {
        this.rootView = rootView;
        this.dialogPrivacyPolicyDisclaimer = dialogPrivacyPolicyDisclaimer;
        this.dialogPrivacyPolicyTextViewUrl = dialogPrivacyPolicyTextViewUrl;
        this.welcome = welcome;
    }

    @Override // androidx.viewbinding.ViewBinding
    public ScrollView getRoot() {
        return this.rootView;
    }

    public static PrivacyPolicyViewBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static PrivacyPolicyViewBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.privacy_policy_view, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static PrivacyPolicyViewBinding bind(View rootView) {
        int i = R.id.dialog_privacy_policy_disclaimer;
        TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.dialog_privacy_policy_disclaimer);
        if (textView != null) {
            i = R.id.dialog_privacy_policy_text_view_url;
            TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.dialog_privacy_policy_text_view_url);
            if (textView2 != null) {
                i = R.id.welcome;
                TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.welcome);
                if (textView3 != null) {
                    return new PrivacyPolicyViewBinding((ScrollView) rootView, textView, textView2, textView3);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

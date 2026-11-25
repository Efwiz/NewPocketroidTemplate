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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/DialogAboutBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class DialogAboutBinding implements ViewBinding {
    public final TextView dialogAboutTextCatrobatUrl;
    public final TextView dialogAboutTextViewCatrobatVersionName;
    public final TextView dialogAboutTextViewUrl;
    public final TextView dialogAboutTextViewVersionName;
    private final ScrollView rootView;

    private DialogAboutBinding(ScrollView rootView, TextView dialogAboutTextCatrobatUrl, TextView dialogAboutTextViewCatrobatVersionName, TextView dialogAboutTextViewUrl, TextView dialogAboutTextViewVersionName) {
        this.rootView = rootView;
        this.dialogAboutTextCatrobatUrl = dialogAboutTextCatrobatUrl;
        this.dialogAboutTextViewCatrobatVersionName = dialogAboutTextViewCatrobatVersionName;
        this.dialogAboutTextViewUrl = dialogAboutTextViewUrl;
        this.dialogAboutTextViewVersionName = dialogAboutTextViewVersionName;
    }

    @Override // androidx.viewbinding.ViewBinding
    public ScrollView getRoot() {
        return this.rootView;
    }

    public static DialogAboutBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static DialogAboutBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.dialog_about, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static DialogAboutBinding bind(View rootView) {
        int i = R.id.dialog_about_text_catrobat_url;
        TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.dialog_about_text_catrobat_url);
        if (textView != null) {
            i = R.id.dialog_about_text_view_catrobat_version_name;
            TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.dialog_about_text_view_catrobat_version_name);
            if (textView2 != null) {
                i = R.id.dialog_about_text_view_url;
                TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.dialog_about_text_view_url);
                if (textView3 != null) {
                    i = R.id.dialog_about_text_view_version_name;
                    TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.dialog_about_text_view_version_name);
                    if (textView4 != null) {
                        return new DialogAboutBinding((ScrollView) rootView, textView, textView2, textView3, textView4);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

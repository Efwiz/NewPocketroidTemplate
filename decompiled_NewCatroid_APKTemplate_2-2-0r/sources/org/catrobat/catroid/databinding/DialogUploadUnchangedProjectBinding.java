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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/DialogUploadUnchangedProjectBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class DialogUploadUnchangedProjectBinding implements ViewBinding {
    private final LinearLayout rootView;
    public final TextView unchangedUploadInfo;
    public final TextView unchangedUploadUrl;
    public final TextView unchangedUploadWarning;

    private DialogUploadUnchangedProjectBinding(LinearLayout rootView, TextView unchangedUploadInfo, TextView unchangedUploadUrl, TextView unchangedUploadWarning) {
        this.rootView = rootView;
        this.unchangedUploadInfo = unchangedUploadInfo;
        this.unchangedUploadUrl = unchangedUploadUrl;
        this.unchangedUploadWarning = unchangedUploadWarning;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static DialogUploadUnchangedProjectBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static DialogUploadUnchangedProjectBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.dialog_upload_unchanged_project, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static DialogUploadUnchangedProjectBinding bind(View rootView) {
        int i = R.id.unchanged_upload_info;
        TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.unchanged_upload_info);
        if (textView != null) {
            i = R.id.unchanged_upload_url;
            TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.unchanged_upload_url);
            if (textView2 != null) {
                i = R.id.unchanged_upload_warning;
                TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.unchanged_upload_warning);
                if (textView3 != null) {
                    return new DialogUploadUnchangedProjectBinding((LinearLayout) rootView, textView, textView2, textView3);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

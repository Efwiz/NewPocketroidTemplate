package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/DialogUploadProjectProgressBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class DialogUploadProjectProgressBinding implements ViewBinding {
    public final TextView dialogUploadMessageFailed;
    public final TextView dialogUploadProgessMessage;
    public final ImageView dialogUploadProgressImage;
    public final ProgressBar dialogUploadProgressProgressbar;
    private final ScrollView rootView;

    private DialogUploadProjectProgressBinding(ScrollView rootView, TextView dialogUploadMessageFailed, TextView dialogUploadProgessMessage, ImageView dialogUploadProgressImage, ProgressBar dialogUploadProgressProgressbar) {
        this.rootView = rootView;
        this.dialogUploadMessageFailed = dialogUploadMessageFailed;
        this.dialogUploadProgessMessage = dialogUploadProgessMessage;
        this.dialogUploadProgressImage = dialogUploadProgressImage;
        this.dialogUploadProgressProgressbar = dialogUploadProgressProgressbar;
    }

    @Override // androidx.viewbinding.ViewBinding
    public ScrollView getRoot() {
        return this.rootView;
    }

    public static DialogUploadProjectProgressBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static DialogUploadProjectProgressBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.dialog_upload_project_progress, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static DialogUploadProjectProgressBinding bind(View rootView) {
        int i = R.id.dialog_upload_message_failed;
        TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.dialog_upload_message_failed);
        if (textView != null) {
            i = R.id.dialog_upload_progess_message;
            TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.dialog_upload_progess_message);
            if (textView2 != null) {
                i = R.id.dialog_upload_progress_image;
                ImageView imageView = (ImageView) ViewBindings.findChildViewById(rootView, R.id.dialog_upload_progress_image);
                if (imageView != null) {
                    i = R.id.dialog_upload_progress_progressbar;
                    ProgressBar progressBar = (ProgressBar) ViewBindings.findChildViewById(rootView, R.id.dialog_upload_progress_progressbar);
                    if (progressBar != null) {
                        return new DialogUploadProjectProgressBinding((ScrollView) rootView, textView, textView2, imageView, progressBar);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

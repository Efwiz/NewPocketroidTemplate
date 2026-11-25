package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/DialogProgressPfBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class DialogProgressPfBinding implements ViewBinding {
    public final ProgressBar progressBar;
    public final TextView progressText;
    public final TextView progressTitle;
    private final LinearLayout rootView;

    private DialogProgressPfBinding(LinearLayout rootView, ProgressBar progressBar, TextView progressText, TextView progressTitle) {
        this.rootView = rootView;
        this.progressBar = progressBar;
        this.progressText = progressText;
        this.progressTitle = progressTitle;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static DialogProgressPfBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static DialogProgressPfBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.dialog_progress_pf, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static DialogProgressPfBinding bind(View rootView) {
        int i = R.id.progressBar;
        ProgressBar progressBar = (ProgressBar) ViewBindings.findChildViewById(rootView, R.id.progressBar);
        if (progressBar != null) {
            i = R.id.progressText;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.progressText);
            if (textView != null) {
                i = R.id.progressTitle;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.progressTitle);
                if (textView2 != null) {
                    return new DialogProgressPfBinding((LinearLayout) rootView, progressBar, textView, textView2);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

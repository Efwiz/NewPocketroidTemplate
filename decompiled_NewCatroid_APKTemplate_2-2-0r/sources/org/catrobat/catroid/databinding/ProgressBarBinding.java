package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.viewbinding.ViewBinding;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/ProgressBarBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class ProgressBarBinding implements ViewBinding {
    public final LinearLayout progressBar;
    private final LinearLayout rootView;

    private ProgressBarBinding(LinearLayout rootView, LinearLayout progressBar) {
        this.rootView = rootView;
        this.progressBar = progressBar;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ProgressBarBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ProgressBarBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.progress_bar, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ProgressBarBinding bind(View rootView) {
        if (rootView == null) {
            throw new NullPointerException("rootView");
        }
        LinearLayout linearLayout = (LinearLayout) rootView;
        return new ProgressBarBinding(linearLayout, linearLayout);
    }
}

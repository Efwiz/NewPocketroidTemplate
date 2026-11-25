package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ui.WaveLoadingView;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/ActivityLoadingBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class ActivityLoadingBinding implements ViewBinding {
    public final TextView factTextView;
    public final ImageView loadingLogo;
    public final WaveLoadingView loadingProgressBar;
    private final ConstraintLayout rootView;

    private ActivityLoadingBinding(ConstraintLayout rootView, TextView factTextView, ImageView loadingLogo, WaveLoadingView loadingProgressBar) {
        this.rootView = rootView;
        this.factTextView = factTextView;
        this.loadingLogo = loadingLogo;
        this.loadingProgressBar = loadingProgressBar;
    }

    @Override // androidx.viewbinding.ViewBinding
    public ConstraintLayout getRoot() {
        return this.rootView;
    }

    public static ActivityLoadingBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivityLoadingBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.activity_loading, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ActivityLoadingBinding bind(View rootView) {
        int i = R.id.fact_text_view;
        TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.fact_text_view);
        if (textView != null) {
            i = R.id.loading_logo;
            ImageView imageView = (ImageView) ViewBindings.findChildViewById(rootView, R.id.loading_logo);
            if (imageView != null) {
                i = R.id.loading_progress_bar;
                WaveLoadingView waveLoadingView = (WaveLoadingView) ViewBindings.findChildViewById(rootView, R.id.loading_progress_bar);
                if (waveLoadingView != null) {
                    return new ActivityLoadingBinding((ConstraintLayout) rootView, textView, imageView, waveLoadingView);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/SingleSeekBarViewBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class SingleSeekBarViewBinding implements ViewBinding {
    public final LinearLayout motorBaseLayout;
    private final LinearLayout rootView;
    public final SeekBar singleSeekbarSeekbar;
    public final TextView singleSeekbarTitle;
    public final TextView singleSeekbarValue;

    private SingleSeekBarViewBinding(LinearLayout rootView, LinearLayout motorBaseLayout, SeekBar singleSeekbarSeekbar, TextView singleSeekbarTitle, TextView singleSeekbarValue) {
        this.rootView = rootView;
        this.motorBaseLayout = motorBaseLayout;
        this.singleSeekbarSeekbar = singleSeekbarSeekbar;
        this.singleSeekbarTitle = singleSeekbarTitle;
        this.singleSeekbarValue = singleSeekbarValue;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static SingleSeekBarViewBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static SingleSeekBarViewBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.single_seek_bar_view, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static SingleSeekBarViewBinding bind(View rootView) {
        LinearLayout linearLayout = (LinearLayout) rootView;
        int i = R.id.single_seekbar_seekbar;
        SeekBar seekBar = (SeekBar) ViewBindings.findChildViewById(rootView, R.id.single_seekbar_seekbar);
        if (seekBar != null) {
            i = R.id.single_seekbar_title;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.single_seekbar_title);
            if (textView != null) {
                i = R.id.single_seekbar_value;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.single_seekbar_value);
                if (textView2 != null) {
                    return new SingleSeekBarViewBinding(linearLayout, linearLayout, seekBar, textView, textView2);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

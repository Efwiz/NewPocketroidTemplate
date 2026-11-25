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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/InspectorSliderBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class InspectorSliderBinding implements ViewBinding {
    private final LinearLayout rootView;
    public final SeekBar seekbar;
    public final TextView textValue;

    private InspectorSliderBinding(LinearLayout rootView, SeekBar seekbar, TextView textValue) {
        this.rootView = rootView;
        this.seekbar = seekbar;
        this.textValue = textValue;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static InspectorSliderBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static InspectorSliderBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.inspector_slider, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static InspectorSliderBinding bind(View rootView) {
        int i = R.id.seekbar;
        SeekBar seekBar = (SeekBar) ViewBindings.findChildViewById(rootView, R.id.seekbar);
        if (seekBar != null) {
            i = R.id.text_value;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.text_value);
            if (textView != null) {
                return new InspectorSliderBinding((LinearLayout) rootView, seekBar, textView);
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

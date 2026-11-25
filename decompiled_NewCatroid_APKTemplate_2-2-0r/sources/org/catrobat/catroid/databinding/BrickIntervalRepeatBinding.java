package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ui.BrickLayout;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickIntervalRepeatBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickIntervalRepeatBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickIntervalRepeatEditTextCount;
    public final TextView brickIntervalRepeatEditTextInterval;
    public final BrickLayout brickIntervalRepeatLayout;
    private final LinearLayout rootView;

    private BrickIntervalRepeatBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickIntervalRepeatEditTextCount, TextView brickIntervalRepeatEditTextInterval, BrickLayout brickIntervalRepeatLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickIntervalRepeatEditTextCount = brickIntervalRepeatEditTextCount;
        this.brickIntervalRepeatEditTextInterval = brickIntervalRepeatEditTextInterval;
        this.brickIntervalRepeatLayout = brickIntervalRepeatLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickIntervalRepeatBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickIntervalRepeatBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_interval_repeat, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickIntervalRepeatBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_interval_repeat_edit_text_count;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_interval_repeat_edit_text_count);
            if (textView != null) {
                i = R.id.brick_interval_repeat_edit_text_interval;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_interval_repeat_edit_text_interval);
                if (textView2 != null) {
                    i = R.id.brick_interval_repeat_layout;
                    BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_interval_repeat_layout);
                    if (brickLayout != null) {
                        return new BrickIntervalRepeatBinding((LinearLayout) rootView, checkBox, textView, textView2, brickLayout);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

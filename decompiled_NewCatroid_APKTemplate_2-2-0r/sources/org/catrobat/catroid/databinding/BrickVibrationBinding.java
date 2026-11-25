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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickVibrationBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickVibrationBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickSecondsLabel;
    public final TextView brickVibrationEditText;
    public final BrickLayout brickVibrationLayout;
    private final LinearLayout rootView;

    private BrickVibrationBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickSecondsLabel, TextView brickVibrationEditText, BrickLayout brickVibrationLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickSecondsLabel = brickSecondsLabel;
        this.brickVibrationEditText = brickVibrationEditText;
        this.brickVibrationLayout = brickVibrationLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickVibrationBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickVibrationBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_vibration, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickVibrationBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_seconds_label;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_seconds_label);
            if (textView != null) {
                i = R.id.brick_vibration_edit_text;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_vibration_edit_text);
                if (textView2 != null) {
                    i = R.id.brick_vibration_layout;
                    BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_vibration_layout);
                    if (brickLayout != null) {
                        return new BrickVibrationBinding((LinearLayout) rootView, checkBox, textView, textView2, brickLayout);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

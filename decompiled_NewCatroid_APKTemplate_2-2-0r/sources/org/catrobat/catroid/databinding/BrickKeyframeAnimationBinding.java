package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ui.BrickLayout;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickKeyframeAnimationBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickKeyframeAnimationBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickKeyframeAnimId;
    public final BrickLayout brickKeyframeAnimLayout;
    public final Spinner brickKeyframeAnimSpinner;
    public final TextView brickKeyframeAnimTimeValue;
    public final LinearLayout layoutKeyframeTimeInput;
    private final LinearLayout rootView;

    private BrickKeyframeAnimationBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickKeyframeAnimId, BrickLayout brickKeyframeAnimLayout, Spinner brickKeyframeAnimSpinner, TextView brickKeyframeAnimTimeValue, LinearLayout layoutKeyframeTimeInput) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickKeyframeAnimId = brickKeyframeAnimId;
        this.brickKeyframeAnimLayout = brickKeyframeAnimLayout;
        this.brickKeyframeAnimSpinner = brickKeyframeAnimSpinner;
        this.brickKeyframeAnimTimeValue = brickKeyframeAnimTimeValue;
        this.layoutKeyframeTimeInput = layoutKeyframeTimeInput;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickKeyframeAnimationBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickKeyframeAnimationBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_keyframe_animation, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickKeyframeAnimationBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_keyframe_anim_id;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_keyframe_anim_id);
            if (textView != null) {
                i = R.id.brick_keyframe_anim_layout;
                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_keyframe_anim_layout);
                if (brickLayout != null) {
                    i = R.id.brick_keyframe_anim_spinner;
                    Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.brick_keyframe_anim_spinner);
                    if (spinner != null) {
                        i = R.id.brick_keyframe_anim_time_value;
                        TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_keyframe_anim_time_value);
                        if (textView2 != null) {
                            i = R.id.layout_keyframe_time_input;
                            LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.layout_keyframe_time_input);
                            if (linearLayout != null) {
                                return new BrickKeyframeAnimationBinding((LinearLayout) rootView, checkBox, textView, brickLayout, spinner, textView2, linearLayout);
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

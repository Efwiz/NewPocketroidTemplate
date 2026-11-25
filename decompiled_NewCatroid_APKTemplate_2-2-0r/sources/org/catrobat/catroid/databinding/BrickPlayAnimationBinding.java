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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickPlayAnimationBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickPlayAnimationBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final BrickLayout brickPlayAnimationLayout;
    public final TextView brickPlayAnimationLoopsValue;
    public final TextView brickPlayAnimationNameValue;
    public final TextView brickPlayAnimationObjectIdValue;
    public final TextView brickPlayAnimationSpeedValue;
    public final TextView brickPlayAnimationTransitionValue;
    private final LinearLayout rootView;

    private BrickPlayAnimationBinding(LinearLayout rootView, CheckBox brickCheckbox, BrickLayout brickPlayAnimationLayout, TextView brickPlayAnimationLoopsValue, TextView brickPlayAnimationNameValue, TextView brickPlayAnimationObjectIdValue, TextView brickPlayAnimationSpeedValue, TextView brickPlayAnimationTransitionValue) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickPlayAnimationLayout = brickPlayAnimationLayout;
        this.brickPlayAnimationLoopsValue = brickPlayAnimationLoopsValue;
        this.brickPlayAnimationNameValue = brickPlayAnimationNameValue;
        this.brickPlayAnimationObjectIdValue = brickPlayAnimationObjectIdValue;
        this.brickPlayAnimationSpeedValue = brickPlayAnimationSpeedValue;
        this.brickPlayAnimationTransitionValue = brickPlayAnimationTransitionValue;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickPlayAnimationBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickPlayAnimationBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_play_animation, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickPlayAnimationBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_play_animation_layout;
            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_play_animation_layout);
            if (brickLayout != null) {
                i = R.id.brick_play_animation_loops_value;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_play_animation_loops_value);
                if (textView != null) {
                    i = R.id.brick_play_animation_name_value;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_play_animation_name_value);
                    if (textView2 != null) {
                        i = R.id.brick_play_animation_object_id_value;
                        TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_play_animation_object_id_value);
                        if (textView3 != null) {
                            i = R.id.brick_play_animation_speed_value;
                            TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_play_animation_speed_value);
                            if (textView4 != null) {
                                i = R.id.brick_play_animation_transition_value;
                                TextView textView5 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_play_animation_transition_value);
                                if (textView5 != null) {
                                    return new BrickPlayAnimationBinding((LinearLayout) rootView, checkBox, brickLayout, textView, textView2, textView3, textView4, textView5);
                                }
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickJumpingSumoAnimationsBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickJumpingSumoAnimationsBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final BrickLayout brickJumpingSumoAnimationLayout;
    public final Spinner brickJumpingSumoAnimationSpinner;
    public final TextView brickJumpingSumoAnimationTextView;
    private final LinearLayout rootView;

    private BrickJumpingSumoAnimationsBinding(LinearLayout rootView, CheckBox brickCheckbox, BrickLayout brickJumpingSumoAnimationLayout, Spinner brickJumpingSumoAnimationSpinner, TextView brickJumpingSumoAnimationTextView) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickJumpingSumoAnimationLayout = brickJumpingSumoAnimationLayout;
        this.brickJumpingSumoAnimationSpinner = brickJumpingSumoAnimationSpinner;
        this.brickJumpingSumoAnimationTextView = brickJumpingSumoAnimationTextView;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickJumpingSumoAnimationsBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickJumpingSumoAnimationsBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_jumping_sumo_animations, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickJumpingSumoAnimationsBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_jumping_sumo_animation_layout;
            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_jumping_sumo_animation_layout);
            if (brickLayout != null) {
                i = R.id.brick_jumping_sumo_animation_spinner;
                Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.brick_jumping_sumo_animation_spinner);
                if (spinner != null) {
                    i = R.id.brick_jumping_sumo_animation_text_view;
                    TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_jumping_sumo_animation_text_view);
                    if (textView != null) {
                        return new BrickJumpingSumoAnimationsBinding((LinearLayout) rootView, checkBox, brickLayout, spinner, textView);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

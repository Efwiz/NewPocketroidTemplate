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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickJumpingSumoMoveBackwardBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickJumpingSumoMoveBackwardBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickJumpingSumoMoveBackwardEditTextPower;
    public final TextView brickJumpingSumoMoveBackwardEditTextSecond;
    public final TextView brickJumpingSumoMoveBackwardLabel;
    public final BrickLayout brickJumpingSumoMoveBackwardLayout;
    public final TextView brickJumpingSumoMoveBackwardSetPercent;
    public final TextView brickJumpingSumoMoveBackwardSetPower;
    public final TextView brickJumpingSumoMoveBackwardTextWith;
    public final TextView brickSecondsLabel;
    private final LinearLayout rootView;

    private BrickJumpingSumoMoveBackwardBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickJumpingSumoMoveBackwardEditTextPower, TextView brickJumpingSumoMoveBackwardEditTextSecond, TextView brickJumpingSumoMoveBackwardLabel, BrickLayout brickJumpingSumoMoveBackwardLayout, TextView brickJumpingSumoMoveBackwardSetPercent, TextView brickJumpingSumoMoveBackwardSetPower, TextView brickJumpingSumoMoveBackwardTextWith, TextView brickSecondsLabel) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickJumpingSumoMoveBackwardEditTextPower = brickJumpingSumoMoveBackwardEditTextPower;
        this.brickJumpingSumoMoveBackwardEditTextSecond = brickJumpingSumoMoveBackwardEditTextSecond;
        this.brickJumpingSumoMoveBackwardLabel = brickJumpingSumoMoveBackwardLabel;
        this.brickJumpingSumoMoveBackwardLayout = brickJumpingSumoMoveBackwardLayout;
        this.brickJumpingSumoMoveBackwardSetPercent = brickJumpingSumoMoveBackwardSetPercent;
        this.brickJumpingSumoMoveBackwardSetPower = brickJumpingSumoMoveBackwardSetPower;
        this.brickJumpingSumoMoveBackwardTextWith = brickJumpingSumoMoveBackwardTextWith;
        this.brickSecondsLabel = brickSecondsLabel;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickJumpingSumoMoveBackwardBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickJumpingSumoMoveBackwardBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_jumping_sumo_move_backward, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickJumpingSumoMoveBackwardBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_jumping_sumo_move_backward_edit_text_power;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_jumping_sumo_move_backward_edit_text_power);
            if (textView != null) {
                i = R.id.brick_jumping_sumo_move_backward_edit_text_second;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_jumping_sumo_move_backward_edit_text_second);
                if (textView2 != null) {
                    i = R.id.brick_jumping_sumo_move_backward_label;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_jumping_sumo_move_backward_label);
                    if (textView3 != null) {
                        i = R.id.brick_jumping_sumo_move_backward_layout;
                        BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_jumping_sumo_move_backward_layout);
                        if (brickLayout != null) {
                            i = R.id.brick_jumping_sumo_move_backward_set_percent;
                            TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_jumping_sumo_move_backward_set_percent);
                            if (textView4 != null) {
                                i = R.id.brick_jumping_sumo_move_backward_set_power;
                                TextView textView5 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_jumping_sumo_move_backward_set_power);
                                if (textView5 != null) {
                                    i = R.id.brick_jumping_sumo_move_backward_text_with;
                                    TextView textView6 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_jumping_sumo_move_backward_text_with);
                                    if (textView6 != null) {
                                        i = R.id.brick_seconds_label;
                                        TextView textView7 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_seconds_label);
                                        if (textView7 != null) {
                                            return new BrickJumpingSumoMoveBackwardBinding((LinearLayout) rootView, checkBox, textView, textView2, textView3, brickLayout, textView4, textView5, textView6, textView7);
                                        }
                                    }
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

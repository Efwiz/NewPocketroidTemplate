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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickDroneMoveForwardBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickDroneMoveForwardBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickDroneMoveForwardEditTextPower;
    public final TextView brickDroneMoveForwardEditTextSecond;
    public final TextView brickDroneMoveForwardLabel;
    public final BrickLayout brickDroneMoveForwardLayout;
    public final TextView brickDroneMoveForwardPercent;
    public final TextView brickDroneMoveForwardPower;
    public final TextView brickDroneMoveForwardTextWith;
    public final TextView brickSecondsLabel;
    private final LinearLayout rootView;

    private BrickDroneMoveForwardBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickDroneMoveForwardEditTextPower, TextView brickDroneMoveForwardEditTextSecond, TextView brickDroneMoveForwardLabel, BrickLayout brickDroneMoveForwardLayout, TextView brickDroneMoveForwardPercent, TextView brickDroneMoveForwardPower, TextView brickDroneMoveForwardTextWith, TextView brickSecondsLabel) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickDroneMoveForwardEditTextPower = brickDroneMoveForwardEditTextPower;
        this.brickDroneMoveForwardEditTextSecond = brickDroneMoveForwardEditTextSecond;
        this.brickDroneMoveForwardLabel = brickDroneMoveForwardLabel;
        this.brickDroneMoveForwardLayout = brickDroneMoveForwardLayout;
        this.brickDroneMoveForwardPercent = brickDroneMoveForwardPercent;
        this.brickDroneMoveForwardPower = brickDroneMoveForwardPower;
        this.brickDroneMoveForwardTextWith = brickDroneMoveForwardTextWith;
        this.brickSecondsLabel = brickSecondsLabel;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickDroneMoveForwardBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickDroneMoveForwardBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_drone_move_forward, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickDroneMoveForwardBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_drone_move_forward_edit_text_power;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_drone_move_forward_edit_text_power);
            if (textView != null) {
                i = R.id.brick_drone_move_forward_edit_text_second;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_drone_move_forward_edit_text_second);
                if (textView2 != null) {
                    i = R.id.brick_drone_move_forward_label;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_drone_move_forward_label);
                    if (textView3 != null) {
                        i = R.id.brick_drone_move_forward_layout;
                        BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_drone_move_forward_layout);
                        if (brickLayout != null) {
                            i = R.id.brick_drone_move_forward_percent;
                            TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_drone_move_forward_percent);
                            if (textView4 != null) {
                                i = R.id.brick_drone_move_forward_power;
                                TextView textView5 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_drone_move_forward_power);
                                if (textView5 != null) {
                                    i = R.id.brick_drone_move_forward_text_with;
                                    TextView textView6 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_drone_move_forward_text_with);
                                    if (textView6 != null) {
                                        i = R.id.brick_seconds_label;
                                        TextView textView7 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_seconds_label);
                                        if (textView7 != null) {
                                            return new BrickDroneMoveForwardBinding((LinearLayout) rootView, checkBox, textView, textView2, textView3, brickLayout, textView4, textView5, textView6, textView7);
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

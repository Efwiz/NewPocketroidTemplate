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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickDroneMoveDownBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickDroneMoveDownBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickDroneMoveDownEditTextPower;
    public final TextView brickDroneMoveDownEditTextSecond;
    public final TextView brickDroneMoveDownLabel;
    public final BrickLayout brickDroneMoveDownLayout;
    public final TextView brickDroneMoveDownPercent;
    public final TextView brickDroneMoveDownPower;
    public final TextView brickDroneMoveDownTextWith;
    public final TextView brickSecondsLabel;
    private final LinearLayout rootView;

    private BrickDroneMoveDownBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickDroneMoveDownEditTextPower, TextView brickDroneMoveDownEditTextSecond, TextView brickDroneMoveDownLabel, BrickLayout brickDroneMoveDownLayout, TextView brickDroneMoveDownPercent, TextView brickDroneMoveDownPower, TextView brickDroneMoveDownTextWith, TextView brickSecondsLabel) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickDroneMoveDownEditTextPower = brickDroneMoveDownEditTextPower;
        this.brickDroneMoveDownEditTextSecond = brickDroneMoveDownEditTextSecond;
        this.brickDroneMoveDownLabel = brickDroneMoveDownLabel;
        this.brickDroneMoveDownLayout = brickDroneMoveDownLayout;
        this.brickDroneMoveDownPercent = brickDroneMoveDownPercent;
        this.brickDroneMoveDownPower = brickDroneMoveDownPower;
        this.brickDroneMoveDownTextWith = brickDroneMoveDownTextWith;
        this.brickSecondsLabel = brickSecondsLabel;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickDroneMoveDownBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickDroneMoveDownBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_drone_move_down, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickDroneMoveDownBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_drone_move_down_edit_text_power;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_drone_move_down_edit_text_power);
            if (textView != null) {
                i = R.id.brick_drone_move_down_edit_text_second;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_drone_move_down_edit_text_second);
                if (textView2 != null) {
                    i = R.id.brick_drone_move_down_label;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_drone_move_down_label);
                    if (textView3 != null) {
                        i = R.id.brick_drone_move_down_layout;
                        BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_drone_move_down_layout);
                        if (brickLayout != null) {
                            i = R.id.brick_drone_move_down_percent;
                            TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_drone_move_down_percent);
                            if (textView4 != null) {
                                i = R.id.brick_drone_move_down_power;
                                TextView textView5 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_drone_move_down_power);
                                if (textView5 != null) {
                                    i = R.id.brick_drone_move_down_text_with;
                                    TextView textView6 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_drone_move_down_text_with);
                                    if (textView6 != null) {
                                        i = R.id.brick_seconds_label;
                                        TextView textView7 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_seconds_label);
                                        if (textView7 != null) {
                                            return new BrickDroneMoveDownBinding((LinearLayout) rootView, checkBox, textView, textView2, textView3, brickLayout, textView4, textView5, textView6, textView7);
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

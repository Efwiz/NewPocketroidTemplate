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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickEv3MotorMoveBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickEv3MotorMoveBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickEv3MotorMoveLabel;
    public final BrickLayout brickEv3MotorMoveLayout;
    public final TextView brickEv3MotorMovePercent;
    public final TextView brickEv3MotorMoveSpeedText;
    public final Spinner brickEv3MotorMoveSpinner;
    public final TextView ev3MotorMoveSpeedEditText;
    public final TextView legoMotorActionToText;
    private final LinearLayout rootView;

    private BrickEv3MotorMoveBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickEv3MotorMoveLabel, BrickLayout brickEv3MotorMoveLayout, TextView brickEv3MotorMovePercent, TextView brickEv3MotorMoveSpeedText, Spinner brickEv3MotorMoveSpinner, TextView ev3MotorMoveSpeedEditText, TextView legoMotorActionToText) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickEv3MotorMoveLabel = brickEv3MotorMoveLabel;
        this.brickEv3MotorMoveLayout = brickEv3MotorMoveLayout;
        this.brickEv3MotorMovePercent = brickEv3MotorMovePercent;
        this.brickEv3MotorMoveSpeedText = brickEv3MotorMoveSpeedText;
        this.brickEv3MotorMoveSpinner = brickEv3MotorMoveSpinner;
        this.ev3MotorMoveSpeedEditText = ev3MotorMoveSpeedEditText;
        this.legoMotorActionToText = legoMotorActionToText;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickEv3MotorMoveBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickEv3MotorMoveBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_ev3_motor_move, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickEv3MotorMoveBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_ev3_motor_move_label;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_ev3_motor_move_label);
            if (textView != null) {
                i = R.id.brick_ev3_motor_move_layout;
                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_ev3_motor_move_layout);
                if (brickLayout != null) {
                    i = R.id.brick_ev3_motor_move_percent;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_ev3_motor_move_percent);
                    if (textView2 != null) {
                        i = R.id.brick_ev3_motor_move_speed_text;
                        TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_ev3_motor_move_speed_text);
                        if (textView3 != null) {
                            i = R.id.brick_ev3_motor_move_spinner;
                            Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.brick_ev3_motor_move_spinner);
                            if (spinner != null) {
                                i = R.id.ev3_motor_move_speed_edit_text;
                                TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.ev3_motor_move_speed_edit_text);
                                if (textView4 != null) {
                                    i = R.id.lego_motor_action_to_text;
                                    TextView textView5 = (TextView) ViewBindings.findChildViewById(rootView, R.id.lego_motor_action_to_text);
                                    if (textView5 != null) {
                                        return new BrickEv3MotorMoveBinding((LinearLayout) rootView, checkBox, textView, brickLayout, textView2, textView3, spinner, textView4, textView5);
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

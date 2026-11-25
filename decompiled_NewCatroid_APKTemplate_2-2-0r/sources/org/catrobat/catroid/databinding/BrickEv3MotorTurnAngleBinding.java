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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickEv3MotorTurnAngleBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickEv3MotorTurnAngleBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickEv3MotorTurnAngle;
    public final TextView brickEv3MotorTurnDegree;
    public final TextView brickEv3MotorTurnLabel;
    public final BrickLayout brickEv3MotorTurnLayout;
    public final TextView ev3MotorTurnAngleEditText;
    public final Spinner legoEv3MotorTurnAngleSpinner;
    private final LinearLayout rootView;

    private BrickEv3MotorTurnAngleBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickEv3MotorTurnAngle, TextView brickEv3MotorTurnDegree, TextView brickEv3MotorTurnLabel, BrickLayout brickEv3MotorTurnLayout, TextView ev3MotorTurnAngleEditText, Spinner legoEv3MotorTurnAngleSpinner) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickEv3MotorTurnAngle = brickEv3MotorTurnAngle;
        this.brickEv3MotorTurnDegree = brickEv3MotorTurnDegree;
        this.brickEv3MotorTurnLabel = brickEv3MotorTurnLabel;
        this.brickEv3MotorTurnLayout = brickEv3MotorTurnLayout;
        this.ev3MotorTurnAngleEditText = ev3MotorTurnAngleEditText;
        this.legoEv3MotorTurnAngleSpinner = legoEv3MotorTurnAngleSpinner;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickEv3MotorTurnAngleBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickEv3MotorTurnAngleBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_ev3_motor_turn_angle, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickEv3MotorTurnAngleBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_ev3_motor_turn_angle;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_ev3_motor_turn_angle);
            if (textView != null) {
                i = R.id.brick_ev3_motor_turn_degree;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_ev3_motor_turn_degree);
                if (textView2 != null) {
                    i = R.id.brick_ev3_motor_turn_label;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_ev3_motor_turn_label);
                    if (textView3 != null) {
                        i = R.id.brick_ev3_motor_turn_layout;
                        BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_ev3_motor_turn_layout);
                        if (brickLayout != null) {
                            i = R.id.ev3_motor_turn_angle_edit_text;
                            TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.ev3_motor_turn_angle_edit_text);
                            if (textView4 != null) {
                                i = R.id.lego_ev3_motor_turn_angle_spinner;
                                Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.lego_ev3_motor_turn_angle_spinner);
                                if (spinner != null) {
                                    return new BrickEv3MotorTurnAngleBinding((LinearLayout) rootView, checkBox, textView, textView2, textView3, brickLayout, textView4, spinner);
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

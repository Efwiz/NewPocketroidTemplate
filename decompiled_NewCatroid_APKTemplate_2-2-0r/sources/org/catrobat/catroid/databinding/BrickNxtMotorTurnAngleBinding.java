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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickNxtMotorTurnAngleBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickNxtMotorTurnAngleBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final BrickLayout brickNxtMotorTurnLayout;
    public final Spinner legoMotorTurnAngleSpinner;
    public final TextView motorTurnAngleEditText;
    private final LinearLayout rootView;

    private BrickNxtMotorTurnAngleBinding(LinearLayout rootView, CheckBox brickCheckbox, BrickLayout brickNxtMotorTurnLayout, Spinner legoMotorTurnAngleSpinner, TextView motorTurnAngleEditText) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickNxtMotorTurnLayout = brickNxtMotorTurnLayout;
        this.legoMotorTurnAngleSpinner = legoMotorTurnAngleSpinner;
        this.motorTurnAngleEditText = motorTurnAngleEditText;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickNxtMotorTurnAngleBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickNxtMotorTurnAngleBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_nxt_motor_turn_angle, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickNxtMotorTurnAngleBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_nxt_motor_turn_layout;
            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_nxt_motor_turn_layout);
            if (brickLayout != null) {
                i = R.id.lego_motor_turn_angle_spinner;
                Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.lego_motor_turn_angle_spinner);
                if (spinner != null) {
                    i = R.id.motor_turn_angle_edit_text;
                    TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.motor_turn_angle_edit_text);
                    if (textView != null) {
                        return new BrickNxtMotorTurnAngleBinding((LinearLayout) rootView, checkBox, brickLayout, spinner, textView);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

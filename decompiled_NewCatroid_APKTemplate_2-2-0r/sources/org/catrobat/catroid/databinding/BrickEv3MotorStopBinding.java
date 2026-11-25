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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickEv3MotorStopBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickEv3MotorStopBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final BrickLayout brickEv3MotorStopLayout;
    public final TextView ev3MotorStopTextView;
    public final Spinner ev3StopMotorSpinner;
    private final LinearLayout rootView;

    private BrickEv3MotorStopBinding(LinearLayout rootView, CheckBox brickCheckbox, BrickLayout brickEv3MotorStopLayout, TextView ev3MotorStopTextView, Spinner ev3StopMotorSpinner) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickEv3MotorStopLayout = brickEv3MotorStopLayout;
        this.ev3MotorStopTextView = ev3MotorStopTextView;
        this.ev3StopMotorSpinner = ev3StopMotorSpinner;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickEv3MotorStopBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickEv3MotorStopBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_ev3_motor_stop, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickEv3MotorStopBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_ev3_motor_stop_layout;
            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_ev3_motor_stop_layout);
            if (brickLayout != null) {
                i = R.id.ev3_motor_stop_text_view;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.ev3_motor_stop_text_view);
                if (textView != null) {
                    i = R.id.ev3_stop_motor_spinner;
                    Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.ev3_stop_motor_spinner);
                    if (spinner != null) {
                        return new BrickEv3MotorStopBinding((LinearLayout) rootView, checkBox, brickLayout, textView, spinner);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

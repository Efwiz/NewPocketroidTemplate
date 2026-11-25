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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickThreedHingeMotorBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickThreedHingeMotorBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickMotorId;
    public final TextView brickMotorMax;
    public final TextView brickMotorTarget;
    public final BrickLayout brickThreedMotorLayout;
    private final LinearLayout rootView;

    private BrickThreedHingeMotorBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickMotorId, TextView brickMotorMax, TextView brickMotorTarget, BrickLayout brickThreedMotorLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickMotorId = brickMotorId;
        this.brickMotorMax = brickMotorMax;
        this.brickMotorTarget = brickMotorTarget;
        this.brickThreedMotorLayout = brickThreedMotorLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickThreedHingeMotorBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickThreedHingeMotorBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_threed_hinge_motor, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickThreedHingeMotorBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_motor_id;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_motor_id);
            if (textView != null) {
                i = R.id.brick_motor_max;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_motor_max);
                if (textView2 != null) {
                    i = R.id.brick_motor_target;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_motor_target);
                    if (textView3 != null) {
                        i = R.id.brick_threed_motor_layout;
                        BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_threed_motor_layout);
                        if (brickLayout != null) {
                            return new BrickThreedHingeMotorBinding((LinearLayout) rootView, checkBox, textView, textView2, textView3, brickLayout);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

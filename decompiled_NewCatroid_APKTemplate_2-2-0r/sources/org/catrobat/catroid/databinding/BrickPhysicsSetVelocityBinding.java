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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickPhysicsSetVelocityBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickPhysicsSetVelocityBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickSetVelocityEditTextX;
    public final TextView brickSetVelocityEditTextY;
    public final BrickLayout brickSetVelocityLayout;
    public final TextView brickSetVelocityTextview;
    public final TextView brickSetVelocityUnit;
    public final TextView brickSetVelocityXTextview;
    public final TextView brickSetVelocityYTextview;
    private final LinearLayout rootView;

    private BrickPhysicsSetVelocityBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickSetVelocityEditTextX, TextView brickSetVelocityEditTextY, BrickLayout brickSetVelocityLayout, TextView brickSetVelocityTextview, TextView brickSetVelocityUnit, TextView brickSetVelocityXTextview, TextView brickSetVelocityYTextview) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickSetVelocityEditTextX = brickSetVelocityEditTextX;
        this.brickSetVelocityEditTextY = brickSetVelocityEditTextY;
        this.brickSetVelocityLayout = brickSetVelocityLayout;
        this.brickSetVelocityTextview = brickSetVelocityTextview;
        this.brickSetVelocityUnit = brickSetVelocityUnit;
        this.brickSetVelocityXTextview = brickSetVelocityXTextview;
        this.brickSetVelocityYTextview = brickSetVelocityYTextview;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickPhysicsSetVelocityBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickPhysicsSetVelocityBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_physics_set_velocity, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickPhysicsSetVelocityBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_set_velocity_edit_text_x;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_velocity_edit_text_x);
            if (textView != null) {
                i = R.id.brick_set_velocity_edit_text_y;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_velocity_edit_text_y);
                if (textView2 != null) {
                    i = R.id.brick_set_velocity_layout;
                    BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_set_velocity_layout);
                    if (brickLayout != null) {
                        i = R.id.brick_set_velocity_textview;
                        TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_velocity_textview);
                        if (textView3 != null) {
                            i = R.id.brick_set_velocity_unit;
                            TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_velocity_unit);
                            if (textView4 != null) {
                                i = R.id.brick_set_velocity_x_textview;
                                TextView textView5 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_velocity_x_textview);
                                if (textView5 != null) {
                                    i = R.id.brick_set_velocity_y_textview;
                                    TextView textView6 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_velocity_y_textview);
                                    if (textView6 != null) {
                                        return new BrickPhysicsSetVelocityBinding((LinearLayout) rootView, checkBox, textView, textView2, brickLayout, textView3, textView4, textView5, textView6);
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

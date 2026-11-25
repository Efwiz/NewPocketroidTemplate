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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickPhysicsSetGravityBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickPhysicsSetGravityBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickSetGravityEditTextX;
    public final TextView brickSetGravityEditTextY;
    public final TextView brickSetGravityLabel;
    public final BrickLayout brickSetGravityLayout;
    public final TextView brickSetGravityUnit;
    public final TextView brickSetGravityXTextview;
    public final TextView brickSetGravityYTextview;
    private final LinearLayout rootView;

    private BrickPhysicsSetGravityBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickSetGravityEditTextX, TextView brickSetGravityEditTextY, TextView brickSetGravityLabel, BrickLayout brickSetGravityLayout, TextView brickSetGravityUnit, TextView brickSetGravityXTextview, TextView brickSetGravityYTextview) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickSetGravityEditTextX = brickSetGravityEditTextX;
        this.brickSetGravityEditTextY = brickSetGravityEditTextY;
        this.brickSetGravityLabel = brickSetGravityLabel;
        this.brickSetGravityLayout = brickSetGravityLayout;
        this.brickSetGravityUnit = brickSetGravityUnit;
        this.brickSetGravityXTextview = brickSetGravityXTextview;
        this.brickSetGravityYTextview = brickSetGravityYTextview;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickPhysicsSetGravityBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickPhysicsSetGravityBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_physics_set_gravity, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickPhysicsSetGravityBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_set_gravity_edit_text_x;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_gravity_edit_text_x);
            if (textView != null) {
                i = R.id.brick_set_gravity_edit_text_y;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_gravity_edit_text_y);
                if (textView2 != null) {
                    i = R.id.brick_set_gravity_label;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_gravity_label);
                    if (textView3 != null) {
                        i = R.id.brick_set_gravity_layout;
                        BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_set_gravity_layout);
                        if (brickLayout != null) {
                            i = R.id.brick_set_gravity_unit;
                            TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_gravity_unit);
                            if (textView4 != null) {
                                i = R.id.brick_set_gravity_x_textview;
                                TextView textView5 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_gravity_x_textview);
                                if (textView5 != null) {
                                    i = R.id.brick_set_gravity_y_textview;
                                    TextView textView6 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_gravity_y_textview);
                                    if (textView6 != null) {
                                        return new BrickPhysicsSetGravityBinding((LinearLayout) rootView, checkBox, textView, textView2, textView3, brickLayout, textView4, textView5, textView6);
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

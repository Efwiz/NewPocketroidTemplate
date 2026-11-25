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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSetPhysicsStateBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSetPhysicsStateBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final LinearLayout brickSetPhysicsShapeLayout;
    public final Spinner brickSetPhysicsShapeSpinner;
    public final TextView brickSetPhysicsStateId;
    public final BrickLayout brickSetPhysicsStateLayout;
    public final LinearLayout brickSetPhysicsStateMassLayout;
    public final TextView brickSetPhysicsStateMassValue;
    public final Spinner brickSetPhysicsStateSpinner;
    private final LinearLayout rootView;

    private BrickSetPhysicsStateBinding(LinearLayout rootView, CheckBox brickCheckbox, LinearLayout brickSetPhysicsShapeLayout, Spinner brickSetPhysicsShapeSpinner, TextView brickSetPhysicsStateId, BrickLayout brickSetPhysicsStateLayout, LinearLayout brickSetPhysicsStateMassLayout, TextView brickSetPhysicsStateMassValue, Spinner brickSetPhysicsStateSpinner) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickSetPhysicsShapeLayout = brickSetPhysicsShapeLayout;
        this.brickSetPhysicsShapeSpinner = brickSetPhysicsShapeSpinner;
        this.brickSetPhysicsStateId = brickSetPhysicsStateId;
        this.brickSetPhysicsStateLayout = brickSetPhysicsStateLayout;
        this.brickSetPhysicsStateMassLayout = brickSetPhysicsStateMassLayout;
        this.brickSetPhysicsStateMassValue = brickSetPhysicsStateMassValue;
        this.brickSetPhysicsStateSpinner = brickSetPhysicsStateSpinner;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSetPhysicsStateBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSetPhysicsStateBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_set_physics_state, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSetPhysicsStateBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_set_physics_shape_layout;
            LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.brick_set_physics_shape_layout);
            if (linearLayout != null) {
                i = R.id.brick_set_physics_shape_spinner;
                Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.brick_set_physics_shape_spinner);
                if (spinner != null) {
                    i = R.id.brick_set_physics_state_id;
                    TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_physics_state_id);
                    if (textView != null) {
                        i = R.id.brick_set_physics_state_layout;
                        BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_set_physics_state_layout);
                        if (brickLayout != null) {
                            i = R.id.brick_set_physics_state_mass_layout;
                            LinearLayout linearLayout2 = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.brick_set_physics_state_mass_layout);
                            if (linearLayout2 != null) {
                                i = R.id.brick_set_physics_state_mass_value;
                                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_physics_state_mass_value);
                                if (textView2 != null) {
                                    i = R.id.brick_set_physics_state_spinner;
                                    Spinner spinner2 = (Spinner) ViewBindings.findChildViewById(rootView, R.id.brick_set_physics_state_spinner);
                                    if (spinner2 != null) {
                                        return new BrickSetPhysicsStateBinding((LinearLayout) rootView, checkBox, linearLayout, spinner, textView, brickLayout, linearLayout2, textView2, spinner2);
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

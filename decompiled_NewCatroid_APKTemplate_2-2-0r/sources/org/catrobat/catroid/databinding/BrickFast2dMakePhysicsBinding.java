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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickFast2dMakePhysicsBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickFast2dMakePhysicsBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final BrickLayout brickFast2dMakePhysLayout;
    public final TextView brickFast2dPhysBounce;
    public final TextView brickFast2dPhysDen;
    public final TextView brickFast2dPhysDyn;
    public final TextView brickFast2dPhysFric;
    public final TextView brickFast2dPhysId;
    public final TextView brickFast2dPhysShape;
    private final LinearLayout rootView;

    private BrickFast2dMakePhysicsBinding(LinearLayout rootView, CheckBox brickCheckbox, BrickLayout brickFast2dMakePhysLayout, TextView brickFast2dPhysBounce, TextView brickFast2dPhysDen, TextView brickFast2dPhysDyn, TextView brickFast2dPhysFric, TextView brickFast2dPhysId, TextView brickFast2dPhysShape) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickFast2dMakePhysLayout = brickFast2dMakePhysLayout;
        this.brickFast2dPhysBounce = brickFast2dPhysBounce;
        this.brickFast2dPhysDen = brickFast2dPhysDen;
        this.brickFast2dPhysDyn = brickFast2dPhysDyn;
        this.brickFast2dPhysFric = brickFast2dPhysFric;
        this.brickFast2dPhysId = brickFast2dPhysId;
        this.brickFast2dPhysShape = brickFast2dPhysShape;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickFast2dMakePhysicsBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickFast2dMakePhysicsBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_fast2d_make_physics, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickFast2dMakePhysicsBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_fast2d_make_phys_layout;
            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_fast2d_make_phys_layout);
            if (brickLayout != null) {
                i = R.id.brick_fast2d_phys_bounce;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_fast2d_phys_bounce);
                if (textView != null) {
                    i = R.id.brick_fast2d_phys_den;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_fast2d_phys_den);
                    if (textView2 != null) {
                        i = R.id.brick_fast2d_phys_dyn;
                        TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_fast2d_phys_dyn);
                        if (textView3 != null) {
                            i = R.id.brick_fast2d_phys_fric;
                            TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_fast2d_phys_fric);
                            if (textView4 != null) {
                                i = R.id.brick_fast2d_phys_id;
                                TextView textView5 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_fast2d_phys_id);
                                if (textView5 != null) {
                                    i = R.id.brick_fast2d_phys_shape;
                                    TextView textView6 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_fast2d_phys_shape);
                                    if (textView6 != null) {
                                        return new BrickFast2dMakePhysicsBinding((LinearLayout) rootView, checkBox, brickLayout, textView, textView2, textView3, textView4, textView5, textView6);
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

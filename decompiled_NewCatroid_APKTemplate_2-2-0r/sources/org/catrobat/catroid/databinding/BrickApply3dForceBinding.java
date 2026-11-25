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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickApply3dForceBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickApply3dForceBinding implements ViewBinding {
    public final TextView brickApply3dForceObjectId;
    public final TextView brickApply3dForceX;
    public final TextView brickApply3dForceY;
    public final TextView brickApply3dForceZ;
    public final CheckBox brickCheckbox;
    public final BrickLayout brickSet3dPositionLayout;
    private final LinearLayout rootView;

    private BrickApply3dForceBinding(LinearLayout rootView, TextView brickApply3dForceObjectId, TextView brickApply3dForceX, TextView brickApply3dForceY, TextView brickApply3dForceZ, CheckBox brickCheckbox, BrickLayout brickSet3dPositionLayout) {
        this.rootView = rootView;
        this.brickApply3dForceObjectId = brickApply3dForceObjectId;
        this.brickApply3dForceX = brickApply3dForceX;
        this.brickApply3dForceY = brickApply3dForceY;
        this.brickApply3dForceZ = brickApply3dForceZ;
        this.brickCheckbox = brickCheckbox;
        this.brickSet3dPositionLayout = brickSet3dPositionLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickApply3dForceBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickApply3dForceBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_apply_3d_force, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickApply3dForceBinding bind(View rootView) {
        int i = R.id.brick_apply_3d_force_object_id;
        TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_apply_3d_force_object_id);
        if (textView != null) {
            i = R.id.brick_apply_3d_force_x;
            TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_apply_3d_force_x);
            if (textView2 != null) {
                i = R.id.brick_apply_3d_force_y;
                TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_apply_3d_force_y);
                if (textView3 != null) {
                    i = R.id.brick_apply_3d_force_z;
                    TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_apply_3d_force_z);
                    if (textView4 != null) {
                        i = R.id.brick_checkbox;
                        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
                        if (checkBox != null) {
                            i = R.id.brick_set_3d_position_layout;
                            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_set_3d_position_layout);
                            if (brickLayout != null) {
                                return new BrickApply3dForceBinding((LinearLayout) rootView, textView, textView2, textView3, textView4, checkBox, brickLayout);
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

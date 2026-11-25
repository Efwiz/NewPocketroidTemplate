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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSet3dVelocityBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSet3dVelocityBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final BrickLayout brickSet3dPositionLayout;
    public final TextView brickSet3dVelocityObjectId;
    public final TextView brickSet3dVelocityX;
    public final TextView brickSet3dVelocityY;
    public final TextView brickSet3dVelocityZ;
    private final LinearLayout rootView;

    private BrickSet3dVelocityBinding(LinearLayout rootView, CheckBox brickCheckbox, BrickLayout brickSet3dPositionLayout, TextView brickSet3dVelocityObjectId, TextView brickSet3dVelocityX, TextView brickSet3dVelocityY, TextView brickSet3dVelocityZ) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickSet3dPositionLayout = brickSet3dPositionLayout;
        this.brickSet3dVelocityObjectId = brickSet3dVelocityObjectId;
        this.brickSet3dVelocityX = brickSet3dVelocityX;
        this.brickSet3dVelocityY = brickSet3dVelocityY;
        this.brickSet3dVelocityZ = brickSet3dVelocityZ;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSet3dVelocityBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSet3dVelocityBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_set_3d_velocity, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSet3dVelocityBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_set_3d_position_layout;
            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_set_3d_position_layout);
            if (brickLayout != null) {
                i = R.id.brick_set_3d_velocity_object_id;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_3d_velocity_object_id);
                if (textView != null) {
                    i = R.id.brick_set_3d_velocity_x;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_3d_velocity_x);
                    if (textView2 != null) {
                        i = R.id.brick_set_3d_velocity_y;
                        TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_3d_velocity_y);
                        if (textView3 != null) {
                            i = R.id.brick_set_3d_velocity_z;
                            TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_3d_velocity_z);
                            if (textView4 != null) {
                                return new BrickSet3dVelocityBinding((LinearLayout) rootView, checkBox, brickLayout, textView, textView2, textView3, textView4);
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSet3dGravityBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSet3dGravityBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickSet3dGravityX;
    public final TextView brickSet3dGravityY;
    public final TextView brickSet3dGravityZ;
    public final BrickLayout brickSet3dPositionLayout;
    private final LinearLayout rootView;

    private BrickSet3dGravityBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickSet3dGravityX, TextView brickSet3dGravityY, TextView brickSet3dGravityZ, BrickLayout brickSet3dPositionLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickSet3dGravityX = brickSet3dGravityX;
        this.brickSet3dGravityY = brickSet3dGravityY;
        this.brickSet3dGravityZ = brickSet3dGravityZ;
        this.brickSet3dPositionLayout = brickSet3dPositionLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSet3dGravityBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSet3dGravityBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_set_3d_gravity, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSet3dGravityBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_set_3d_gravity_x;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_3d_gravity_x);
            if (textView != null) {
                i = R.id.brick_set_3d_gravity_y;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_3d_gravity_y);
                if (textView2 != null) {
                    i = R.id.brick_set_3d_gravity_z;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_3d_gravity_z);
                    if (textView3 != null) {
                        i = R.id.brick_set_3d_position_layout;
                        BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_set_3d_position_layout);
                        if (brickLayout != null) {
                            return new BrickSet3dGravityBinding((LinearLayout) rootView, checkBox, textView, textView2, textView3, brickLayout);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

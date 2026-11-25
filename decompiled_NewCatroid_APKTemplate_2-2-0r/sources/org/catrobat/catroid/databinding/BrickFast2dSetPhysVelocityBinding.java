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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickFast2dSetPhysVelocityBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickFast2dSetPhysVelocityBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickFast2dPhysVelId;
    public final BrickLayout brickFast2dPhysVelLayout;
    public final TextView brickFast2dPhysVelX;
    public final TextView brickFast2dPhysVelY;
    private final LinearLayout rootView;

    private BrickFast2dSetPhysVelocityBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickFast2dPhysVelId, BrickLayout brickFast2dPhysVelLayout, TextView brickFast2dPhysVelX, TextView brickFast2dPhysVelY) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickFast2dPhysVelId = brickFast2dPhysVelId;
        this.brickFast2dPhysVelLayout = brickFast2dPhysVelLayout;
        this.brickFast2dPhysVelX = brickFast2dPhysVelX;
        this.brickFast2dPhysVelY = brickFast2dPhysVelY;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickFast2dSetPhysVelocityBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickFast2dSetPhysVelocityBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_fast2d_set_phys_velocity, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickFast2dSetPhysVelocityBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_fast2d_phys_vel_id;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_fast2d_phys_vel_id);
            if (textView != null) {
                i = R.id.brick_fast2d_phys_vel_layout;
                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_fast2d_phys_vel_layout);
                if (brickLayout != null) {
                    i = R.id.brick_fast2d_phys_vel_x;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_fast2d_phys_vel_x);
                    if (textView2 != null) {
                        i = R.id.brick_fast2d_phys_vel_y;
                        TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_fast2d_phys_vel_y);
                        if (textView3 != null) {
                            return new BrickFast2dSetPhysVelocityBinding((LinearLayout) rootView, checkBox, textView, brickLayout, textView2, textView3);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

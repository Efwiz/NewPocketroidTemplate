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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickFast2dSetAngularVelocityBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickFast2dSetAngularVelocityBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickFast2dAngVelId;
    public final BrickLayout brickFast2dAngVelLayout;
    public final TextView brickFast2dAngVelV;
    private final LinearLayout rootView;

    private BrickFast2dSetAngularVelocityBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickFast2dAngVelId, BrickLayout brickFast2dAngVelLayout, TextView brickFast2dAngVelV) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickFast2dAngVelId = brickFast2dAngVelId;
        this.brickFast2dAngVelLayout = brickFast2dAngVelLayout;
        this.brickFast2dAngVelV = brickFast2dAngVelV;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickFast2dSetAngularVelocityBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickFast2dSetAngularVelocityBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_fast2d_set_angular_velocity, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickFast2dSetAngularVelocityBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_fast2d_ang_vel_id;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_fast2d_ang_vel_id);
            if (textView != null) {
                i = R.id.brick_fast2d_ang_vel_layout;
                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_fast2d_ang_vel_layout);
                if (brickLayout != null) {
                    i = R.id.brick_fast2d_ang_vel_v;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_fast2d_ang_vel_v);
                    if (textView2 != null) {
                        return new BrickFast2dSetAngularVelocityBinding((LinearLayout) rootView, checkBox, textView, brickLayout, textView2);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

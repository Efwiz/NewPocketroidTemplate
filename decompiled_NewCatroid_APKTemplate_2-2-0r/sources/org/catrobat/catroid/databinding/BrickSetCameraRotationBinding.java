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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSetCameraRotationBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSetCameraRotationBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final BrickLayout brickSetCameraRotationLayout;
    public final TextView brickSetCameraRotationPitch;
    public final TextView brickSetCameraRotationRoll;
    public final TextView brickSetCameraRotationYaw;
    private final LinearLayout rootView;

    private BrickSetCameraRotationBinding(LinearLayout rootView, CheckBox brickCheckbox, BrickLayout brickSetCameraRotationLayout, TextView brickSetCameraRotationPitch, TextView brickSetCameraRotationRoll, TextView brickSetCameraRotationYaw) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickSetCameraRotationLayout = brickSetCameraRotationLayout;
        this.brickSetCameraRotationPitch = brickSetCameraRotationPitch;
        this.brickSetCameraRotationRoll = brickSetCameraRotationRoll;
        this.brickSetCameraRotationYaw = brickSetCameraRotationYaw;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSetCameraRotationBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSetCameraRotationBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_set_camera_rotation, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSetCameraRotationBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_set_camera_rotation_layout;
            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_set_camera_rotation_layout);
            if (brickLayout != null) {
                i = R.id.brick_set_camera_rotation_pitch;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_camera_rotation_pitch);
                if (textView != null) {
                    i = R.id.brick_set_camera_rotation_roll;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_camera_rotation_roll);
                    if (textView2 != null) {
                        i = R.id.brick_set_camera_rotation_yaw;
                        TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_camera_rotation_yaw);
                        if (textView3 != null) {
                            return new BrickSetCameraRotationBinding((LinearLayout) rootView, checkBox, brickLayout, textView, textView2, textView3);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

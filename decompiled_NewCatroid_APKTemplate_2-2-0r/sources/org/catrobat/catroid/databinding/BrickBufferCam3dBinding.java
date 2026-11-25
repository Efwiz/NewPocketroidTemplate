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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickBufferCam3dBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickBufferCam3dBinding implements ViewBinding {
    public final TextView brickBufferCam3dFov;
    public final BrickLayout brickBufferCam3dLayout;
    public final TextView brickBufferCam3dName;
    public final TextView brickBufferCam3dPitch;
    public final TextView brickBufferCam3dRoll;
    public final TextView brickBufferCam3dX;
    public final TextView brickBufferCam3dY;
    public final TextView brickBufferCam3dYaw;
    public final TextView brickBufferCam3dZ;
    public final CheckBox brickCheckbox;
    private final LinearLayout rootView;

    private BrickBufferCam3dBinding(LinearLayout rootView, TextView brickBufferCam3dFov, BrickLayout brickBufferCam3dLayout, TextView brickBufferCam3dName, TextView brickBufferCam3dPitch, TextView brickBufferCam3dRoll, TextView brickBufferCam3dX, TextView brickBufferCam3dY, TextView brickBufferCam3dYaw, TextView brickBufferCam3dZ, CheckBox brickCheckbox) {
        this.rootView = rootView;
        this.brickBufferCam3dFov = brickBufferCam3dFov;
        this.brickBufferCam3dLayout = brickBufferCam3dLayout;
        this.brickBufferCam3dName = brickBufferCam3dName;
        this.brickBufferCam3dPitch = brickBufferCam3dPitch;
        this.brickBufferCam3dRoll = brickBufferCam3dRoll;
        this.brickBufferCam3dX = brickBufferCam3dX;
        this.brickBufferCam3dY = brickBufferCam3dY;
        this.brickBufferCam3dYaw = brickBufferCam3dYaw;
        this.brickBufferCam3dZ = brickBufferCam3dZ;
        this.brickCheckbox = brickCheckbox;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickBufferCam3dBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickBufferCam3dBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_buffer_cam_3d, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickBufferCam3dBinding bind(View rootView) {
        int i = R.id.brick_buffer_cam_3d_fov;
        TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_buffer_cam_3d_fov);
        if (textView != null) {
            i = R.id.brick_buffer_cam_3d_layout;
            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_buffer_cam_3d_layout);
            if (brickLayout != null) {
                i = R.id.brick_buffer_cam_3d_name;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_buffer_cam_3d_name);
                if (textView2 != null) {
                    i = R.id.brick_buffer_cam_3d_pitch;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_buffer_cam_3d_pitch);
                    if (textView3 != null) {
                        i = R.id.brick_buffer_cam_3d_roll;
                        TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_buffer_cam_3d_roll);
                        if (textView4 != null) {
                            i = R.id.brick_buffer_cam_3d_x;
                            TextView textView5 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_buffer_cam_3d_x);
                            if (textView5 != null) {
                                i = R.id.brick_buffer_cam_3d_y;
                                TextView textView6 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_buffer_cam_3d_y);
                                if (textView6 != null) {
                                    i = R.id.brick_buffer_cam_3d_yaw;
                                    TextView textView7 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_buffer_cam_3d_yaw);
                                    if (textView7 != null) {
                                        i = R.id.brick_buffer_cam_3d_z;
                                        TextView textView8 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_buffer_cam_3d_z);
                                        if (textView8 != null) {
                                            i = R.id.brick_checkbox;
                                            CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
                                            if (checkBox != null) {
                                                return new BrickBufferCam3dBinding((LinearLayout) rootView, textView, brickLayout, textView2, textView3, textView4, textView5, textView6, textView7, textView8, checkBox);
                                            }
                                        }
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

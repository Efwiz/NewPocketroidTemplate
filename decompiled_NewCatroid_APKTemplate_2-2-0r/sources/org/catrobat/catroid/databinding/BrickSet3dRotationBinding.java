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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSet3dRotationBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSet3dRotationBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final BrickLayout brickSet3dRotationLayout;
    public final TextView brickSet3dRotationObjectId;
    public final TextView brickSet3dRotationPitch;
    public final TextView brickSet3dRotationRoll;
    public final TextView brickSet3dRotationYaw;
    private final LinearLayout rootView;

    private BrickSet3dRotationBinding(LinearLayout rootView, CheckBox brickCheckbox, BrickLayout brickSet3dRotationLayout, TextView brickSet3dRotationObjectId, TextView brickSet3dRotationPitch, TextView brickSet3dRotationRoll, TextView brickSet3dRotationYaw) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickSet3dRotationLayout = brickSet3dRotationLayout;
        this.brickSet3dRotationObjectId = brickSet3dRotationObjectId;
        this.brickSet3dRotationPitch = brickSet3dRotationPitch;
        this.brickSet3dRotationRoll = brickSet3dRotationRoll;
        this.brickSet3dRotationYaw = brickSet3dRotationYaw;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSet3dRotationBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSet3dRotationBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_set_3d_rotation, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSet3dRotationBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_set_3d_rotation_layout;
            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_set_3d_rotation_layout);
            if (brickLayout != null) {
                i = R.id.brick_set_3d_rotation_object_id;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_3d_rotation_object_id);
                if (textView != null) {
                    i = R.id.brick_set_3d_rotation_pitch;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_3d_rotation_pitch);
                    if (textView2 != null) {
                        i = R.id.brick_set_3d_rotation_roll;
                        TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_3d_rotation_roll);
                        if (textView3 != null) {
                            i = R.id.brick_set_3d_rotation_yaw;
                            TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_3d_rotation_yaw);
                            if (textView4 != null) {
                                return new BrickSet3dRotationBinding((LinearLayout) rootView, checkBox, brickLayout, textView, textView2, textView3, textView4);
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

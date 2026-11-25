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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSetRotationLockBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSetRotationLockBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final BrickLayout brickRotationLockLayout;
    public final TextView brickRotationLockObjectId;
    public final CheckBox brickRotationLockX;
    public final CheckBox brickRotationLockY;
    public final CheckBox brickRotationLockZ;
    private final LinearLayout rootView;

    private BrickSetRotationLockBinding(LinearLayout rootView, CheckBox brickCheckbox, BrickLayout brickRotationLockLayout, TextView brickRotationLockObjectId, CheckBox brickRotationLockX, CheckBox brickRotationLockY, CheckBox brickRotationLockZ) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickRotationLockLayout = brickRotationLockLayout;
        this.brickRotationLockObjectId = brickRotationLockObjectId;
        this.brickRotationLockX = brickRotationLockX;
        this.brickRotationLockY = brickRotationLockY;
        this.brickRotationLockZ = brickRotationLockZ;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSetRotationLockBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSetRotationLockBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_set_rotation_lock, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSetRotationLockBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_rotation_lock_layout;
            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_rotation_lock_layout);
            if (brickLayout != null) {
                i = R.id.brick_rotation_lock_object_id;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_rotation_lock_object_id);
                if (textView != null) {
                    i = R.id.brick_rotation_lock_x;
                    CheckBox checkBox2 = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_rotation_lock_x);
                    if (checkBox2 != null) {
                        i = R.id.brick_rotation_lock_y;
                        CheckBox checkBox3 = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_rotation_lock_y);
                        if (checkBox3 != null) {
                            i = R.id.brick_rotation_lock_z;
                            CheckBox checkBox4 = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_rotation_lock_z);
                            if (checkBox4 != null) {
                                return new BrickSetRotationLockBinding((LinearLayout) rootView, checkBox, brickLayout, textView, checkBox2, checkBox3, checkBox4);
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

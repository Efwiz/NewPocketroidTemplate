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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickRotateCameraByBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickRotateCameraByBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final BrickLayout brickRotateCameraByLayout;
    private final LinearLayout rootView;
    public final TextView rotatePitchEdit;
    public final TextView rotateRollEdit;
    public final TextView rotateYawEdit;

    private BrickRotateCameraByBinding(LinearLayout rootView, CheckBox brickCheckbox, BrickLayout brickRotateCameraByLayout, TextView rotatePitchEdit, TextView rotateRollEdit, TextView rotateYawEdit) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickRotateCameraByLayout = brickRotateCameraByLayout;
        this.rotatePitchEdit = rotatePitchEdit;
        this.rotateRollEdit = rotateRollEdit;
        this.rotateYawEdit = rotateYawEdit;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickRotateCameraByBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickRotateCameraByBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_rotate_camera_by, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickRotateCameraByBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_rotate_camera_by_layout;
            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_rotate_camera_by_layout);
            if (brickLayout != null) {
                i = R.id.rotate_pitch_edit;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.rotate_pitch_edit);
                if (textView != null) {
                    i = R.id.rotate_roll_edit;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.rotate_roll_edit);
                    if (textView2 != null) {
                        i = R.id.rotate_yaw_edit;
                        TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.rotate_yaw_edit);
                        if (textView3 != null) {
                            return new BrickRotateCameraByBinding((LinearLayout) rootView, checkBox, brickLayout, textView, textView2, textView3);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

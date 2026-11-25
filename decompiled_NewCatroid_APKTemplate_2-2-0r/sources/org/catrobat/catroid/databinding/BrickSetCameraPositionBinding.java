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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSetCameraPositionBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSetCameraPositionBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final BrickLayout brickSetCameraPositionLayout;
    public final TextView brickSetCameraPositionX;
    public final TextView brickSetCameraPositionY;
    public final TextView brickSetCameraPositionZ;
    private final LinearLayout rootView;

    private BrickSetCameraPositionBinding(LinearLayout rootView, CheckBox brickCheckbox, BrickLayout brickSetCameraPositionLayout, TextView brickSetCameraPositionX, TextView brickSetCameraPositionY, TextView brickSetCameraPositionZ) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickSetCameraPositionLayout = brickSetCameraPositionLayout;
        this.brickSetCameraPositionX = brickSetCameraPositionX;
        this.brickSetCameraPositionY = brickSetCameraPositionY;
        this.brickSetCameraPositionZ = brickSetCameraPositionZ;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSetCameraPositionBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSetCameraPositionBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_set_camera_position, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSetCameraPositionBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_set_camera_position_layout;
            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_set_camera_position_layout);
            if (brickLayout != null) {
                i = R.id.brick_set_camera_position_x;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_camera_position_x);
                if (textView != null) {
                    i = R.id.brick_set_camera_position_y;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_camera_position_y);
                    if (textView2 != null) {
                        i = R.id.brick_set_camera_position_z;
                        TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_camera_position_z);
                        if (textView3 != null) {
                            return new BrickSetCameraPositionBinding((LinearLayout) rootView, checkBox, brickLayout, textView, textView2, textView3);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

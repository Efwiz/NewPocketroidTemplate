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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickCameraLookAtBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickCameraLookAtBinding implements ViewBinding {
    public final BrickLayout brickCameraLookAtLayout;
    public final TextView brickCameraLookAtX;
    public final TextView brickCameraLookAtY;
    public final TextView brickCameraLookAtZ;
    public final CheckBox brickCheckbox;
    private final LinearLayout rootView;

    private BrickCameraLookAtBinding(LinearLayout rootView, BrickLayout brickCameraLookAtLayout, TextView brickCameraLookAtX, TextView brickCameraLookAtY, TextView brickCameraLookAtZ, CheckBox brickCheckbox) {
        this.rootView = rootView;
        this.brickCameraLookAtLayout = brickCameraLookAtLayout;
        this.brickCameraLookAtX = brickCameraLookAtX;
        this.brickCameraLookAtY = brickCameraLookAtY;
        this.brickCameraLookAtZ = brickCameraLookAtZ;
        this.brickCheckbox = brickCheckbox;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickCameraLookAtBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickCameraLookAtBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_camera_look_at, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickCameraLookAtBinding bind(View rootView) {
        int i = R.id.brick_camera_look_at_layout;
        BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_camera_look_at_layout);
        if (brickLayout != null) {
            i = R.id.brick_camera_look_at_x;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_camera_look_at_x);
            if (textView != null) {
                i = R.id.brick_camera_look_at_y;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_camera_look_at_y);
                if (textView2 != null) {
                    i = R.id.brick_camera_look_at_z;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_camera_look_at_z);
                    if (textView3 != null) {
                        i = R.id.brick_checkbox;
                        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
                        if (checkBox != null) {
                            return new BrickCameraLookAtBinding((LinearLayout) rootView, brickLayout, textView, textView2, textView3, checkBox);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

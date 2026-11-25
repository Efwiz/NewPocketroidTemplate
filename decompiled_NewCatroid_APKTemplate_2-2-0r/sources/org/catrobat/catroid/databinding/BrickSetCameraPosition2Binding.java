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

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSetCameraPosition2Binding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSetCameraPosition2Binding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickSetCameraPosX;
    public final TextView brickSetCameraPosY;
    private final LinearLayout rootView;

    private BrickSetCameraPosition2Binding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickSetCameraPosX, TextView brickSetCameraPosY) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickSetCameraPosX = brickSetCameraPosX;
        this.brickSetCameraPosY = brickSetCameraPosY;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSetCameraPosition2Binding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSetCameraPosition2Binding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_set_camera_position2, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSetCameraPosition2Binding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_set_camera_pos_x;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_camera_pos_x);
            if (textView != null) {
                i = R.id.brick_set_camera_pos_y;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_camera_pos_y);
                if (textView2 != null) {
                    return new BrickSetCameraPosition2Binding((LinearLayout) rootView, checkBox, textView, textView2);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

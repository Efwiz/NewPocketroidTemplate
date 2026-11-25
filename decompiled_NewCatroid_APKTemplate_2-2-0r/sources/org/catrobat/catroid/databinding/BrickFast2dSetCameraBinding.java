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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickFast2dSetCameraBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickFast2dSetCameraBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickFast2dCamEditX;
    public final TextView brickFast2dCamEditY;
    public final TextView brickFast2dCamEditZoom;
    public final BrickLayout brickFast2dCamLayout;
    private final LinearLayout rootView;

    private BrickFast2dSetCameraBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickFast2dCamEditX, TextView brickFast2dCamEditY, TextView brickFast2dCamEditZoom, BrickLayout brickFast2dCamLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickFast2dCamEditX = brickFast2dCamEditX;
        this.brickFast2dCamEditY = brickFast2dCamEditY;
        this.brickFast2dCamEditZoom = brickFast2dCamEditZoom;
        this.brickFast2dCamLayout = brickFast2dCamLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickFast2dSetCameraBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickFast2dSetCameraBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_fast2d_set_camera, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickFast2dSetCameraBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_fast2d_cam_edit_x;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_fast2d_cam_edit_x);
            if (textView != null) {
                i = R.id.brick_fast2d_cam_edit_y;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_fast2d_cam_edit_y);
                if (textView2 != null) {
                    i = R.id.brick_fast2d_cam_edit_zoom;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_fast2d_cam_edit_zoom);
                    if (textView3 != null) {
                        i = R.id.brick_fast2d_cam_layout;
                        BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_fast2d_cam_layout);
                        if (brickLayout != null) {
                            return new BrickFast2dSetCameraBinding((LinearLayout) rootView, checkBox, textView, textView2, textView3, brickLayout);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSetCameraRangeBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSetCameraRangeBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickSetCameraRangeFarValue;
    public final BrickLayout brickSetCameraRangeLayout;
    public final TextView brickSetCameraRangeNearValue;
    private final LinearLayout rootView;

    private BrickSetCameraRangeBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickSetCameraRangeFarValue, BrickLayout brickSetCameraRangeLayout, TextView brickSetCameraRangeNearValue) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickSetCameraRangeFarValue = brickSetCameraRangeFarValue;
        this.brickSetCameraRangeLayout = brickSetCameraRangeLayout;
        this.brickSetCameraRangeNearValue = brickSetCameraRangeNearValue;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSetCameraRangeBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSetCameraRangeBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_set_camera_range, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSetCameraRangeBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_set_camera_range_far_value;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_camera_range_far_value);
            if (textView != null) {
                i = R.id.brick_set_camera_range_layout;
                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_set_camera_range_layout);
                if (brickLayout != null) {
                    i = R.id.brick_set_camera_range_near_value;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_camera_range_near_value);
                    if (textView2 != null) {
                        return new BrickSetCameraRangeBinding((LinearLayout) rootView, checkBox, textView, brickLayout, textView2);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

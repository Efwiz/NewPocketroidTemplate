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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickFast2dSetRotationBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickFast2dSetRotationBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickFast2dSetRotationAngle;
    public final TextView brickFast2dSetRotationId;
    public final BrickLayout brickFast2dSetRotationLayout;
    private final LinearLayout rootView;

    private BrickFast2dSetRotationBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickFast2dSetRotationAngle, TextView brickFast2dSetRotationId, BrickLayout brickFast2dSetRotationLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickFast2dSetRotationAngle = brickFast2dSetRotationAngle;
        this.brickFast2dSetRotationId = brickFast2dSetRotationId;
        this.brickFast2dSetRotationLayout = brickFast2dSetRotationLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickFast2dSetRotationBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickFast2dSetRotationBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_fast2d_set_rotation, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickFast2dSetRotationBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_fast2d_set_rotation_angle;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_fast2d_set_rotation_angle);
            if (textView != null) {
                i = R.id.brick_fast2d_set_rotation_id;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_fast2d_set_rotation_id);
                if (textView2 != null) {
                    i = R.id.brick_fast2d_set_rotation_layout;
                    BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_fast2d_set_rotation_layout);
                    if (brickLayout != null) {
                        return new BrickFast2dSetRotationBinding((LinearLayout) rootView, checkBox, textView, textView2, brickLayout);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

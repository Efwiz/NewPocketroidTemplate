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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSet3dScaleBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSet3dScaleBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final BrickLayout brickSet3dScaleLayout;
    public final TextView brickSet3dScaleObjectId;
    public final TextView brickSet3dScaleX;
    public final TextView brickSet3dScaleY;
    public final TextView brickSet3dScaleZ;
    private final LinearLayout rootView;

    private BrickSet3dScaleBinding(LinearLayout rootView, CheckBox brickCheckbox, BrickLayout brickSet3dScaleLayout, TextView brickSet3dScaleObjectId, TextView brickSet3dScaleX, TextView brickSet3dScaleY, TextView brickSet3dScaleZ) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickSet3dScaleLayout = brickSet3dScaleLayout;
        this.brickSet3dScaleObjectId = brickSet3dScaleObjectId;
        this.brickSet3dScaleX = brickSet3dScaleX;
        this.brickSet3dScaleY = brickSet3dScaleY;
        this.brickSet3dScaleZ = brickSet3dScaleZ;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSet3dScaleBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSet3dScaleBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_set_3d_scale, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSet3dScaleBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_set_3d_scale_layout;
            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_set_3d_scale_layout);
            if (brickLayout != null) {
                i = R.id.brick_set_3d_scale_object_id;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_3d_scale_object_id);
                if (textView != null) {
                    i = R.id.brick_set_3d_scale_x;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_3d_scale_x);
                    if (textView2 != null) {
                        i = R.id.brick_set_3d_scale_y;
                        TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_3d_scale_y);
                        if (textView3 != null) {
                            i = R.id.brick_set_3d_scale_z;
                            TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_3d_scale_z);
                            if (textView4 != null) {
                                return new BrickSet3dScaleBinding((LinearLayout) rootView, checkBox, brickLayout, textView, textView2, textView3, textView4);
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

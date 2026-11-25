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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickVoxelLoadStringBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickVoxelLoadStringBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickVoxelLoadData;
    public final TextView brickVoxelLoadDx;
    public final TextView brickVoxelLoadDy;
    public final TextView brickVoxelLoadDz;
    public final TextView brickVoxelLoadId;
    public final BrickLayout brickVoxelLoadStrLayout;
    private final LinearLayout rootView;

    private BrickVoxelLoadStringBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickVoxelLoadData, TextView brickVoxelLoadDx, TextView brickVoxelLoadDy, TextView brickVoxelLoadDz, TextView brickVoxelLoadId, BrickLayout brickVoxelLoadStrLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickVoxelLoadData = brickVoxelLoadData;
        this.brickVoxelLoadDx = brickVoxelLoadDx;
        this.brickVoxelLoadDy = brickVoxelLoadDy;
        this.brickVoxelLoadDz = brickVoxelLoadDz;
        this.brickVoxelLoadId = brickVoxelLoadId;
        this.brickVoxelLoadStrLayout = brickVoxelLoadStrLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickVoxelLoadStringBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickVoxelLoadStringBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_voxel_load_string, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickVoxelLoadStringBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_voxel_load_data;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_voxel_load_data);
            if (textView != null) {
                i = R.id.brick_voxel_load_dx;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_voxel_load_dx);
                if (textView2 != null) {
                    i = R.id.brick_voxel_load_dy;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_voxel_load_dy);
                    if (textView3 != null) {
                        i = R.id.brick_voxel_load_dz;
                        TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_voxel_load_dz);
                        if (textView4 != null) {
                            i = R.id.brick_voxel_load_id;
                            TextView textView5 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_voxel_load_id);
                            if (textView5 != null) {
                                i = R.id.brick_voxel_load_str_layout;
                                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_voxel_load_str_layout);
                                if (brickLayout != null) {
                                    return new BrickVoxelLoadStringBinding((LinearLayout) rootView, checkBox, textView, textView2, textView3, textView4, textView5, brickLayout);
                                }
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

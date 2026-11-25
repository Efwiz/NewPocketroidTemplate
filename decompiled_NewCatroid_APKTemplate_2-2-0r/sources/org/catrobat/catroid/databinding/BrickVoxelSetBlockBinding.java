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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickVoxelSetBlockBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickVoxelSetBlockBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickVoxelSetEditData;
    public final TextView brickVoxelSetEditType;
    public final TextView brickVoxelSetEditWorld;
    public final TextView brickVoxelSetEditX;
    public final TextView brickVoxelSetEditY;
    public final TextView brickVoxelSetEditZ;
    public final BrickLayout brickVoxelSetLayout;
    private final LinearLayout rootView;

    private BrickVoxelSetBlockBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickVoxelSetEditData, TextView brickVoxelSetEditType, TextView brickVoxelSetEditWorld, TextView brickVoxelSetEditX, TextView brickVoxelSetEditY, TextView brickVoxelSetEditZ, BrickLayout brickVoxelSetLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickVoxelSetEditData = brickVoxelSetEditData;
        this.brickVoxelSetEditType = brickVoxelSetEditType;
        this.brickVoxelSetEditWorld = brickVoxelSetEditWorld;
        this.brickVoxelSetEditX = brickVoxelSetEditX;
        this.brickVoxelSetEditY = brickVoxelSetEditY;
        this.brickVoxelSetEditZ = brickVoxelSetEditZ;
        this.brickVoxelSetLayout = brickVoxelSetLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickVoxelSetBlockBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickVoxelSetBlockBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_voxel_set_block, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickVoxelSetBlockBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_voxel_set_edit_data;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_voxel_set_edit_data);
            if (textView != null) {
                i = R.id.brick_voxel_set_edit_type;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_voxel_set_edit_type);
                if (textView2 != null) {
                    i = R.id.brick_voxel_set_edit_world;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_voxel_set_edit_world);
                    if (textView3 != null) {
                        i = R.id.brick_voxel_set_edit_x;
                        TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_voxel_set_edit_x);
                        if (textView4 != null) {
                            i = R.id.brick_voxel_set_edit_y;
                            TextView textView5 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_voxel_set_edit_y);
                            if (textView5 != null) {
                                i = R.id.brick_voxel_set_edit_z;
                                TextView textView6 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_voxel_set_edit_z);
                                if (textView6 != null) {
                                    i = R.id.brick_voxel_set_layout;
                                    BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_voxel_set_layout);
                                    if (brickLayout != null) {
                                        return new BrickVoxelSetBlockBinding((LinearLayout) rootView, checkBox, textView, textView2, textView3, textView4, textView5, textView6, brickLayout);
                                    }
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

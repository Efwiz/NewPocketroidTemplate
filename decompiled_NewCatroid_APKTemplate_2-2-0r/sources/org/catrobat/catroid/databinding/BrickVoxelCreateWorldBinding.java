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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickVoxelCreateWorldBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickVoxelCreateWorldBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final BrickLayout brickVoxelCreateLayout;
    public final TextView brickVoxelSizeX;
    public final TextView brickVoxelSizeY;
    public final TextView brickVoxelSizeZ;
    public final TextView brickVoxelWorldId;
    public final TextView brickVoxelWorldX;
    public final TextView brickVoxelWorldY;
    public final TextView brickVoxelWorldZ;
    private final LinearLayout rootView;

    private BrickVoxelCreateWorldBinding(LinearLayout rootView, CheckBox brickCheckbox, BrickLayout brickVoxelCreateLayout, TextView brickVoxelSizeX, TextView brickVoxelSizeY, TextView brickVoxelSizeZ, TextView brickVoxelWorldId, TextView brickVoxelWorldX, TextView brickVoxelWorldY, TextView brickVoxelWorldZ) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickVoxelCreateLayout = brickVoxelCreateLayout;
        this.brickVoxelSizeX = brickVoxelSizeX;
        this.brickVoxelSizeY = brickVoxelSizeY;
        this.brickVoxelSizeZ = brickVoxelSizeZ;
        this.brickVoxelWorldId = brickVoxelWorldId;
        this.brickVoxelWorldX = brickVoxelWorldX;
        this.brickVoxelWorldY = brickVoxelWorldY;
        this.brickVoxelWorldZ = brickVoxelWorldZ;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickVoxelCreateWorldBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickVoxelCreateWorldBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_voxel_create_world, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickVoxelCreateWorldBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_voxel_create_layout;
            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_voxel_create_layout);
            if (brickLayout != null) {
                i = R.id.brick_voxel_size_x;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_voxel_size_x);
                if (textView != null) {
                    i = R.id.brick_voxel_size_y;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_voxel_size_y);
                    if (textView2 != null) {
                        i = R.id.brick_voxel_size_z;
                        TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_voxel_size_z);
                        if (textView3 != null) {
                            i = R.id.brick_voxel_world_id;
                            TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_voxel_world_id);
                            if (textView4 != null) {
                                i = R.id.brick_voxel_world_x;
                                TextView textView5 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_voxel_world_x);
                                if (textView5 != null) {
                                    i = R.id.brick_voxel_world_y;
                                    TextView textView6 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_voxel_world_y);
                                    if (textView6 != null) {
                                        i = R.id.brick_voxel_world_z;
                                        TextView textView7 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_voxel_world_z);
                                        if (textView7 != null) {
                                            return new BrickVoxelCreateWorldBinding((LinearLayout) rootView, checkBox, brickLayout, textView, textView2, textView3, textView4, textView5, textView6, textView7);
                                        }
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

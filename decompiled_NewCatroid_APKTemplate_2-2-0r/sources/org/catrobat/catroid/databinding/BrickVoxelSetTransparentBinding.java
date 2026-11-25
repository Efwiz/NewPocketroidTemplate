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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickVoxelSetTransparentBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickVoxelSetTransparentBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final BrickLayout brickVoxelSetTransparentLayout;
    public final TextView brickVoxelTranspEditId;
    public final TextView brickVoxelTranspEditValue;
    private final LinearLayout rootView;

    private BrickVoxelSetTransparentBinding(LinearLayout rootView, CheckBox brickCheckbox, BrickLayout brickVoxelSetTransparentLayout, TextView brickVoxelTranspEditId, TextView brickVoxelTranspEditValue) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickVoxelSetTransparentLayout = brickVoxelSetTransparentLayout;
        this.brickVoxelTranspEditId = brickVoxelTranspEditId;
        this.brickVoxelTranspEditValue = brickVoxelTranspEditValue;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickVoxelSetTransparentBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickVoxelSetTransparentBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_voxel_set_transparent, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickVoxelSetTransparentBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_voxel_set_transparent_layout;
            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_voxel_set_transparent_layout);
            if (brickLayout != null) {
                i = R.id.brick_voxel_transp_edit_id;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_voxel_transp_edit_id);
                if (textView != null) {
                    i = R.id.brick_voxel_transp_edit_value;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_voxel_transp_edit_value);
                    if (textView2 != null) {
                        return new BrickVoxelSetTransparentBinding((LinearLayout) rootView, checkBox, brickLayout, textView, textView2);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

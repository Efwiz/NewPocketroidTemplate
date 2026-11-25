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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickExport3dGlbBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickExport3dGlbBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickExport3dGlbFileField;
    public final TextView brickExport3dGlbIdField;
    public final BrickLayout brickExport3dGlbLayout;
    private final LinearLayout rootView;

    private BrickExport3dGlbBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickExport3dGlbFileField, TextView brickExport3dGlbIdField, BrickLayout brickExport3dGlbLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickExport3dGlbFileField = brickExport3dGlbFileField;
        this.brickExport3dGlbIdField = brickExport3dGlbIdField;
        this.brickExport3dGlbLayout = brickExport3dGlbLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickExport3dGlbBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickExport3dGlbBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_export_3d_glb, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickExport3dGlbBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_export_3d_glb_file_field;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_export_3d_glb_file_field);
            if (textView != null) {
                i = R.id.brick_export_3d_glb_id_field;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_export_3d_glb_id_field);
                if (textView2 != null) {
                    i = R.id.brick_export_3d_glb_layout;
                    BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_export_3d_glb_layout);
                    if (brickLayout != null) {
                        return new BrickExport3dGlbBinding((LinearLayout) rootView, checkBox, textView, textView2, brickLayout);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

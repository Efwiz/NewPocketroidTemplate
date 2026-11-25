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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSetShaderUniformVec3Binding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSetShaderUniformVec3Binding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final BrickLayout brickSetUniformVec3Layout;
    public final TextView brickUniformNameEdit;
    public final TextView brickUniformValueXEdit;
    public final TextView brickUniformValueYEdit;
    public final TextView brickUniformValueZEdit;
    private final LinearLayout rootView;

    private BrickSetShaderUniformVec3Binding(LinearLayout rootView, CheckBox brickCheckbox, BrickLayout brickSetUniformVec3Layout, TextView brickUniformNameEdit, TextView brickUniformValueXEdit, TextView brickUniformValueYEdit, TextView brickUniformValueZEdit) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickSetUniformVec3Layout = brickSetUniformVec3Layout;
        this.brickUniformNameEdit = brickUniformNameEdit;
        this.brickUniformValueXEdit = brickUniformValueXEdit;
        this.brickUniformValueYEdit = brickUniformValueYEdit;
        this.brickUniformValueZEdit = brickUniformValueZEdit;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSetShaderUniformVec3Binding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSetShaderUniformVec3Binding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_set_shader_uniform_vec3, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSetShaderUniformVec3Binding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_set_uniform_vec3_layout;
            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_set_uniform_vec3_layout);
            if (brickLayout != null) {
                i = R.id.brick_uniform_name_edit;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_uniform_name_edit);
                if (textView != null) {
                    i = R.id.brick_uniform_value_x_edit;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_uniform_value_x_edit);
                    if (textView2 != null) {
                        i = R.id.brick_uniform_value_y_edit;
                        TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_uniform_value_y_edit);
                        if (textView3 != null) {
                            i = R.id.brick_uniform_value_z_edit;
                            TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_uniform_value_z_edit);
                            if (textView4 != null) {
                                return new BrickSetShaderUniformVec3Binding((LinearLayout) rootView, checkBox, brickLayout, textView, textView2, textView3, textView4);
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

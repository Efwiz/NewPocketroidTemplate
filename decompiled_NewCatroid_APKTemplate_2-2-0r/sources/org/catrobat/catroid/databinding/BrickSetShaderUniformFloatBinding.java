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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSetShaderUniformFloatBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSetShaderUniformFloatBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final BrickLayout brickSetUniformFloatLayout;
    public final TextView brickUniformNameEdit;
    public final TextView brickUniformValueEdit;
    private final LinearLayout rootView;

    private BrickSetShaderUniformFloatBinding(LinearLayout rootView, CheckBox brickCheckbox, BrickLayout brickSetUniformFloatLayout, TextView brickUniformNameEdit, TextView brickUniformValueEdit) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickSetUniformFloatLayout = brickSetUniformFloatLayout;
        this.brickUniformNameEdit = brickUniformNameEdit;
        this.brickUniformValueEdit = brickUniformValueEdit;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSetShaderUniformFloatBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSetShaderUniformFloatBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_set_shader_uniform_float, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSetShaderUniformFloatBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_set_uniform_float_layout;
            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_set_uniform_float_layout);
            if (brickLayout != null) {
                i = R.id.brick_uniform_name_edit;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_uniform_name_edit);
                if (textView != null) {
                    i = R.id.brick_uniform_value_edit;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_uniform_value_edit);
                    if (textView2 != null) {
                        return new BrickSetShaderUniformFloatBinding((LinearLayout) rootView, checkBox, brickLayout, textView, textView2);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

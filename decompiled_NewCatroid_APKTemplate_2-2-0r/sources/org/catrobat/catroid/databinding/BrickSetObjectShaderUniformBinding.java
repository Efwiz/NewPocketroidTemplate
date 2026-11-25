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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSetObjectShaderUniformBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSetObjectShaderUniformBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickObjectShaderUniformId;
    public final TextView brickObjectShaderUniformName;
    public final TextView brickObjectShaderUniformVal1;
    public final TextView brickObjectShaderUniformVal2;
    public final TextView brickObjectShaderUniformVal3;
    public final BrickLayout brickSetObjectShaderUniformLayout;
    private final LinearLayout rootView;

    private BrickSetObjectShaderUniformBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickObjectShaderUniformId, TextView brickObjectShaderUniformName, TextView brickObjectShaderUniformVal1, TextView brickObjectShaderUniformVal2, TextView brickObjectShaderUniformVal3, BrickLayout brickSetObjectShaderUniformLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickObjectShaderUniformId = brickObjectShaderUniformId;
        this.brickObjectShaderUniformName = brickObjectShaderUniformName;
        this.brickObjectShaderUniformVal1 = brickObjectShaderUniformVal1;
        this.brickObjectShaderUniformVal2 = brickObjectShaderUniformVal2;
        this.brickObjectShaderUniformVal3 = brickObjectShaderUniformVal3;
        this.brickSetObjectShaderUniformLayout = brickSetObjectShaderUniformLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSetObjectShaderUniformBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSetObjectShaderUniformBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_set_object_shader_uniform, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSetObjectShaderUniformBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_object_shader_uniform_id;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_object_shader_uniform_id);
            if (textView != null) {
                i = R.id.brick_object_shader_uniform_name;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_object_shader_uniform_name);
                if (textView2 != null) {
                    i = R.id.brick_object_shader_uniform_val1;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_object_shader_uniform_val1);
                    if (textView3 != null) {
                        i = R.id.brick_object_shader_uniform_val2;
                        TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_object_shader_uniform_val2);
                        if (textView4 != null) {
                            i = R.id.brick_object_shader_uniform_val3;
                            TextView textView5 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_object_shader_uniform_val3);
                            if (textView5 != null) {
                                i = R.id.brick_set_object_shader_uniform_layout;
                                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_set_object_shader_uniform_layout);
                                if (brickLayout != null) {
                                    return new BrickSetObjectShaderUniformBinding((LinearLayout) rootView, checkBox, textView, textView2, textView3, textView4, textView5, brickLayout);
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

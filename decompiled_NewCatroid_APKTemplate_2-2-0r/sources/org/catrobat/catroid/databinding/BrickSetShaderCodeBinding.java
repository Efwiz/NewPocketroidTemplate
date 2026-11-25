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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSetShaderCodeBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSetShaderCodeBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final BrickLayout brickSetShaderCodeLayout;
    public final TextView brickShaderFragmentCode;
    public final TextView brickShaderVertexCode;
    private final LinearLayout rootView;

    private BrickSetShaderCodeBinding(LinearLayout rootView, CheckBox brickCheckbox, BrickLayout brickSetShaderCodeLayout, TextView brickShaderFragmentCode, TextView brickShaderVertexCode) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickSetShaderCodeLayout = brickSetShaderCodeLayout;
        this.brickShaderFragmentCode = brickShaderFragmentCode;
        this.brickShaderVertexCode = brickShaderVertexCode;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSetShaderCodeBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSetShaderCodeBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_set_shader_code, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSetShaderCodeBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_set_shader_code_layout;
            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_set_shader_code_layout);
            if (brickLayout != null) {
                i = R.id.brick_shader_fragment_code;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_shader_fragment_code);
                if (textView != null) {
                    i = R.id.brick_shader_vertex_code;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_shader_vertex_code);
                    if (textView2 != null) {
                        return new BrickSetShaderCodeBinding((LinearLayout) rootView, checkBox, brickLayout, textView, textView2);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

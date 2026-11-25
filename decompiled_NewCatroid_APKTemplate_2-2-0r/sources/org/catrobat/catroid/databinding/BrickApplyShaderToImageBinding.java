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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickApplyShaderToImageBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickApplyShaderToImageBinding implements ViewBinding {
    public final TextView brickApplyShaderFilename;
    public final TextView brickApplyShaderFsh;
    public final BrickLayout brickApplyShaderLayout;
    public final TextView brickApplyShaderVsh;
    public final CheckBox brickCheckbox;
    private final LinearLayout rootView;

    private BrickApplyShaderToImageBinding(LinearLayout rootView, TextView brickApplyShaderFilename, TextView brickApplyShaderFsh, BrickLayout brickApplyShaderLayout, TextView brickApplyShaderVsh, CheckBox brickCheckbox) {
        this.rootView = rootView;
        this.brickApplyShaderFilename = brickApplyShaderFilename;
        this.brickApplyShaderFsh = brickApplyShaderFsh;
        this.brickApplyShaderLayout = brickApplyShaderLayout;
        this.brickApplyShaderVsh = brickApplyShaderVsh;
        this.brickCheckbox = brickCheckbox;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickApplyShaderToImageBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickApplyShaderToImageBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_apply_shader_to_image, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickApplyShaderToImageBinding bind(View rootView) {
        int i = R.id.brick_apply_shader_filename;
        TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_apply_shader_filename);
        if (textView != null) {
            i = R.id.brick_apply_shader_fsh;
            TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_apply_shader_fsh);
            if (textView2 != null) {
                i = R.id.brick_apply_shader_layout;
                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_apply_shader_layout);
                if (brickLayout != null) {
                    i = R.id.brick_apply_shader_vsh;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_apply_shader_vsh);
                    if (textView3 != null) {
                        i = R.id.brick_checkbox;
                        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
                        if (checkBox != null) {
                            return new BrickApplyShaderToImageBinding((LinearLayout) rootView, textView, textView2, brickLayout, textView3, checkBox);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

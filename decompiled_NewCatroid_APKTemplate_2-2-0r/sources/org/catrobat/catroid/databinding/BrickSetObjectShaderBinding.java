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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSetObjectShaderBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSetObjectShaderBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickObjectShaderFragment;
    public final TextView brickObjectShaderId;
    public final TextView brickObjectShaderVertex;
    public final BrickLayout brickSetObjectShaderLayout;
    private final LinearLayout rootView;

    private BrickSetObjectShaderBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickObjectShaderFragment, TextView brickObjectShaderId, TextView brickObjectShaderVertex, BrickLayout brickSetObjectShaderLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickObjectShaderFragment = brickObjectShaderFragment;
        this.brickObjectShaderId = brickObjectShaderId;
        this.brickObjectShaderVertex = brickObjectShaderVertex;
        this.brickSetObjectShaderLayout = brickSetObjectShaderLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSetObjectShaderBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSetObjectShaderBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_set_object_shader, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSetObjectShaderBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_object_shader_fragment;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_object_shader_fragment);
            if (textView != null) {
                i = R.id.brick_object_shader_id;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_object_shader_id);
                if (textView2 != null) {
                    i = R.id.brick_object_shader_vertex;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_object_shader_vertex);
                    if (textView3 != null) {
                        i = R.id.brick_set_object_shader_layout;
                        BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_set_object_shader_layout);
                        if (brickLayout != null) {
                            return new BrickSetObjectShaderBinding((LinearLayout) rootView, checkBox, textView, textView2, textView3, brickLayout);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

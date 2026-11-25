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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSetRenderResolutionBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSetRenderResolutionBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickSetRenderAspectValue;
    public final TextView brickSetRenderResValue;
    public final BrickLayout brickSetRenderResolutionLayout;
    private final LinearLayout rootView;

    private BrickSetRenderResolutionBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickSetRenderAspectValue, TextView brickSetRenderResValue, BrickLayout brickSetRenderResolutionLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickSetRenderAspectValue = brickSetRenderAspectValue;
        this.brickSetRenderResValue = brickSetRenderResValue;
        this.brickSetRenderResolutionLayout = brickSetRenderResolutionLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSetRenderResolutionBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSetRenderResolutionBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_set_render_resolution, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSetRenderResolutionBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_set_render_aspect_value;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_render_aspect_value);
            if (textView != null) {
                i = R.id.brick_set_render_res_value;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_render_res_value);
                if (textView2 != null) {
                    i = R.id.brick_set_render_resolution_layout;
                    BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_set_render_resolution_layout);
                    if (brickLayout != null) {
                        return new BrickSetRenderResolutionBinding((LinearLayout) rootView, checkBox, textView, textView2, brickLayout);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ui.BrickLayout;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickEnablePbrRenderBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickEnablePbrRenderBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final BrickLayout brickEnablePbrRenderLayout;
    public final Spinner brickEnablePbrRenderSpinner;
    private final LinearLayout rootView;

    private BrickEnablePbrRenderBinding(LinearLayout rootView, CheckBox brickCheckbox, BrickLayout brickEnablePbrRenderLayout, Spinner brickEnablePbrRenderSpinner) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickEnablePbrRenderLayout = brickEnablePbrRenderLayout;
        this.brickEnablePbrRenderSpinner = brickEnablePbrRenderSpinner;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickEnablePbrRenderBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickEnablePbrRenderBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_enable_pbr_render, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickEnablePbrRenderBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_enable_pbr_render_layout;
            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_enable_pbr_render_layout);
            if (brickLayout != null) {
                i = R.id.brick_enable_pbr_render_spinner;
                Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.brick_enable_pbr_render_spinner);
                if (spinner != null) {
                    return new BrickEnablePbrRenderBinding((LinearLayout) rootView, checkBox, brickLayout, spinner);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSetShadowQualityBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSetShadowQualityBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final BrickLayout brickSetShadowQualityLayout;
    public final TextView brickShadowResolutionEdit;
    public final TextView brickShadowSizeEdit;
    private final LinearLayout rootView;

    private BrickSetShadowQualityBinding(LinearLayout rootView, CheckBox brickCheckbox, BrickLayout brickSetShadowQualityLayout, TextView brickShadowResolutionEdit, TextView brickShadowSizeEdit) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickSetShadowQualityLayout = brickSetShadowQualityLayout;
        this.brickShadowResolutionEdit = brickShadowResolutionEdit;
        this.brickShadowSizeEdit = brickShadowSizeEdit;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSetShadowQualityBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSetShadowQualityBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_set_shadow_quality, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSetShadowQualityBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_set_shadow_quality_layout;
            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_set_shadow_quality_layout);
            if (brickLayout != null) {
                i = R.id.brick_shadow_resolution_edit;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_shadow_resolution_edit);
                if (textView != null) {
                    i = R.id.brick_shadow_size_edit;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_shadow_size_edit);
                    if (textView2 != null) {
                        return new BrickSetShadowQualityBinding((LinearLayout) rootView, checkBox, brickLayout, textView, textView2);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

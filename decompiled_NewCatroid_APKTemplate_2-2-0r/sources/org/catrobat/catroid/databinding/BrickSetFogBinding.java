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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSetFogBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSetFogBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickSetFogBEdit;
    public final TextView brickSetFogDensityEdit;
    public final TextView brickSetFogGEdit;
    public final BrickLayout brickSetFogLayout;
    public final TextView brickSetFogREdit;
    private final LinearLayout rootView;

    private BrickSetFogBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickSetFogBEdit, TextView brickSetFogDensityEdit, TextView brickSetFogGEdit, BrickLayout brickSetFogLayout, TextView brickSetFogREdit) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickSetFogBEdit = brickSetFogBEdit;
        this.brickSetFogDensityEdit = brickSetFogDensityEdit;
        this.brickSetFogGEdit = brickSetFogGEdit;
        this.brickSetFogLayout = brickSetFogLayout;
        this.brickSetFogREdit = brickSetFogREdit;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSetFogBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSetFogBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_set_fog, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSetFogBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_set_fog_b_edit;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_fog_b_edit);
            if (textView != null) {
                i = R.id.brick_set_fog_density_edit;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_fog_density_edit);
                if (textView2 != null) {
                    i = R.id.brick_set_fog_g_edit;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_fog_g_edit);
                    if (textView3 != null) {
                        i = R.id.brick_set_fog_layout;
                        BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_set_fog_layout);
                        if (brickLayout != null) {
                            i = R.id.brick_set_fog_r_edit;
                            TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_fog_r_edit);
                            if (textView4 != null) {
                                return new BrickSetFogBinding((LinearLayout) rootView, checkBox, textView, textView2, textView3, brickLayout, textView4);
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

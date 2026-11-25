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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSetMaterialBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSetMaterialBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickSetMaterialAEdit;
    public final TextView brickSetMaterialBEdit;
    public final TextView brickSetMaterialGEdit;
    public final BrickLayout brickSetMaterialLayout;
    public final TextView brickSetMaterialMetallicEdit;
    public final TextView brickSetMaterialObjectEdit;
    public final TextView brickSetMaterialREdit;
    public final TextView brickSetMaterialRoughnessEdit;
    public final TextView brickSetMaterialTextureColorEdit;
    public final TextView brickSetMaterialTextureMrEdit;
    public final TextView brickSetMaterialTextureNormalEdit;
    private final LinearLayout rootView;

    private BrickSetMaterialBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickSetMaterialAEdit, TextView brickSetMaterialBEdit, TextView brickSetMaterialGEdit, BrickLayout brickSetMaterialLayout, TextView brickSetMaterialMetallicEdit, TextView brickSetMaterialObjectEdit, TextView brickSetMaterialREdit, TextView brickSetMaterialRoughnessEdit, TextView brickSetMaterialTextureColorEdit, TextView brickSetMaterialTextureMrEdit, TextView brickSetMaterialTextureNormalEdit) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickSetMaterialAEdit = brickSetMaterialAEdit;
        this.brickSetMaterialBEdit = brickSetMaterialBEdit;
        this.brickSetMaterialGEdit = brickSetMaterialGEdit;
        this.brickSetMaterialLayout = brickSetMaterialLayout;
        this.brickSetMaterialMetallicEdit = brickSetMaterialMetallicEdit;
        this.brickSetMaterialObjectEdit = brickSetMaterialObjectEdit;
        this.brickSetMaterialREdit = brickSetMaterialREdit;
        this.brickSetMaterialRoughnessEdit = brickSetMaterialRoughnessEdit;
        this.brickSetMaterialTextureColorEdit = brickSetMaterialTextureColorEdit;
        this.brickSetMaterialTextureMrEdit = brickSetMaterialTextureMrEdit;
        this.brickSetMaterialTextureNormalEdit = brickSetMaterialTextureNormalEdit;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSetMaterialBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSetMaterialBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_set_material, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSetMaterialBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_set_material_a_edit;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_material_a_edit);
            if (textView != null) {
                i = R.id.brick_set_material_b_edit;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_material_b_edit);
                if (textView2 != null) {
                    i = R.id.brick_set_material_g_edit;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_material_g_edit);
                    if (textView3 != null) {
                        i = R.id.brick_set_material_layout;
                        BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_set_material_layout);
                        if (brickLayout != null) {
                            i = R.id.brick_set_material_metallic_edit;
                            TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_material_metallic_edit);
                            if (textView4 != null) {
                                i = R.id.brick_set_material_object_edit;
                                TextView textView5 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_material_object_edit);
                                if (textView5 != null) {
                                    i = R.id.brick_set_material_r_edit;
                                    TextView textView6 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_material_r_edit);
                                    if (textView6 != null) {
                                        i = R.id.brick_set_material_roughness_edit;
                                        TextView textView7 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_material_roughness_edit);
                                        if (textView7 != null) {
                                            i = R.id.brick_set_material_texture_color_edit;
                                            TextView textView8 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_material_texture_color_edit);
                                            if (textView8 != null) {
                                                i = R.id.brick_set_material_texture_mr_edit;
                                                TextView textView9 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_material_texture_mr_edit);
                                                if (textView9 != null) {
                                                    i = R.id.brick_set_material_texture_normal_edit;
                                                    TextView textView10 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_material_texture_normal_edit);
                                                    if (textView10 != null) {
                                                        return new BrickSetMaterialBinding((LinearLayout) rootView, checkBox, textView, textView2, textView3, brickLayout, textView4, textView5, textView6, textView7, textView8, textView9, textView10);
                                                    }
                                                }
                                            }
                                        }
                                    }
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

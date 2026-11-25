package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ui.BrickLayout;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickHttpConfigureMaterialBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickHttpConfigureMaterialBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickHttpConfigureMaterialIdField;
    public final BrickLayout brickHttpConfigureMaterialLayout;
    public final Spinner brickHttpConfigureMaterialSpinner;
    public final TextView brickHttpConfigureMaterialValueField;
    private final LinearLayout rootView;

    private BrickHttpConfigureMaterialBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickHttpConfigureMaterialIdField, BrickLayout brickHttpConfigureMaterialLayout, Spinner brickHttpConfigureMaterialSpinner, TextView brickHttpConfigureMaterialValueField) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickHttpConfigureMaterialIdField = brickHttpConfigureMaterialIdField;
        this.brickHttpConfigureMaterialLayout = brickHttpConfigureMaterialLayout;
        this.brickHttpConfigureMaterialSpinner = brickHttpConfigureMaterialSpinner;
        this.brickHttpConfigureMaterialValueField = brickHttpConfigureMaterialValueField;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickHttpConfigureMaterialBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickHttpConfigureMaterialBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_http_configure_material, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickHttpConfigureMaterialBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_http_configure_material_id_field;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_http_configure_material_id_field);
            if (textView != null) {
                i = R.id.brick_http_configure_material_layout;
                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_http_configure_material_layout);
                if (brickLayout != null) {
                    i = R.id.brick_http_configure_material_spinner;
                    Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.brick_http_configure_material_spinner);
                    if (spinner != null) {
                        i = R.id.brick_http_configure_material_value_field;
                        TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_http_configure_material_value_field);
                        if (textView2 != null) {
                            return new BrickHttpConfigureMaterialBinding((LinearLayout) rootView, checkBox, textView, brickLayout, spinner, textView2);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

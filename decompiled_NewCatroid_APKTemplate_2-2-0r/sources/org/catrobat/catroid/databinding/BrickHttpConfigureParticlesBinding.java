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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickHttpConfigureParticlesBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickHttpConfigureParticlesBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickHttpConfigureParticlesIdField;
    public final BrickLayout brickHttpConfigureParticlesLayout;
    public final Spinner brickHttpConfigureParticlesSpinner;
    public final TextView brickHttpConfigureParticlesValueField;
    private final LinearLayout rootView;

    private BrickHttpConfigureParticlesBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickHttpConfigureParticlesIdField, BrickLayout brickHttpConfigureParticlesLayout, Spinner brickHttpConfigureParticlesSpinner, TextView brickHttpConfigureParticlesValueField) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickHttpConfigureParticlesIdField = brickHttpConfigureParticlesIdField;
        this.brickHttpConfigureParticlesLayout = brickHttpConfigureParticlesLayout;
        this.brickHttpConfigureParticlesSpinner = brickHttpConfigureParticlesSpinner;
        this.brickHttpConfigureParticlesValueField = brickHttpConfigureParticlesValueField;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickHttpConfigureParticlesBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickHttpConfigureParticlesBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_http_configure_particles, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickHttpConfigureParticlesBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_http_configure_particles_id_field;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_http_configure_particles_id_field);
            if (textView != null) {
                i = R.id.brick_http_configure_particles_layout;
                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_http_configure_particles_layout);
                if (brickLayout != null) {
                    i = R.id.brick_http_configure_particles_spinner;
                    Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.brick_http_configure_particles_spinner);
                    if (spinner != null) {
                        i = R.id.brick_http_configure_particles_value_field;
                        TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_http_configure_particles_value_field);
                        if (textView2 != null) {
                            return new BrickHttpConfigureParticlesBinding((LinearLayout) rootView, checkBox, textView, brickLayout, spinner, textView2);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

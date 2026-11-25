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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickDeleteParticlesBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickDeleteParticlesBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final BrickLayout brickDeleteParticlesLayout;
    public final TextView brickParticleIdEdit;
    private final LinearLayout rootView;

    private BrickDeleteParticlesBinding(LinearLayout rootView, CheckBox brickCheckbox, BrickLayout brickDeleteParticlesLayout, TextView brickParticleIdEdit) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickDeleteParticlesLayout = brickDeleteParticlesLayout;
        this.brickParticleIdEdit = brickParticleIdEdit;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickDeleteParticlesBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickDeleteParticlesBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_delete_particles, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickDeleteParticlesBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_delete_particles_layout;
            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_delete_particles_layout);
            if (brickLayout != null) {
                i = R.id.brick_particle_id_edit;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_particle_id_edit);
                if (textView != null) {
                    return new BrickDeleteParticlesBinding((LinearLayout) rootView, checkBox, brickLayout, textView);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

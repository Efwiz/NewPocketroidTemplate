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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickAdditiveParticleEffectBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickAdditiveParticleEffectBinding implements ViewBinding {
    public final Spinner brickAdditiveParticleEffectSpinner;
    public final CheckBox brickCheckbox;
    public final BrickLayout brickParticleEffectAdditivityLayout;
    private final LinearLayout rootView;

    private BrickAdditiveParticleEffectBinding(LinearLayout rootView, Spinner brickAdditiveParticleEffectSpinner, CheckBox brickCheckbox, BrickLayout brickParticleEffectAdditivityLayout) {
        this.rootView = rootView;
        this.brickAdditiveParticleEffectSpinner = brickAdditiveParticleEffectSpinner;
        this.brickCheckbox = brickCheckbox;
        this.brickParticleEffectAdditivityLayout = brickParticleEffectAdditivityLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickAdditiveParticleEffectBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickAdditiveParticleEffectBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_additive_particle_effect, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickAdditiveParticleEffectBinding bind(View rootView) {
        int i = R.id.brick_additive_particle_effect_spinner;
        Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.brick_additive_particle_effect_spinner);
        if (spinner != null) {
            i = R.id.brick_checkbox;
            CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
            if (checkBox != null) {
                i = R.id.brick_particle_effect_additivity_layout;
                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_particle_effect_additivity_layout);
                if (brickLayout != null) {
                    return new BrickAdditiveParticleEffectBinding((LinearLayout) rootView, spinner, checkBox, brickLayout);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

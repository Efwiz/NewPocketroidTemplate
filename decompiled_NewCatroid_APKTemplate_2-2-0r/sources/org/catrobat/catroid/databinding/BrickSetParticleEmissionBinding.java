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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSetParticleEmissionBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSetParticleEmissionBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final BrickLayout brickLayout;
    public final TextView particleObjId;
    public final TextView particleRate2;
    private final LinearLayout rootView;

    private BrickSetParticleEmissionBinding(LinearLayout rootView, CheckBox brickCheckbox, BrickLayout brickLayout, TextView particleObjId, TextView particleRate2) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickLayout = brickLayout;
        this.particleObjId = particleObjId;
        this.particleRate2 = particleRate2;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSetParticleEmissionBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSetParticleEmissionBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_set_particle_emission, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSetParticleEmissionBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_layout;
            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_layout);
            if (brickLayout != null) {
                i = R.id.particle_obj_id;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.particle_obj_id);
                if (textView != null) {
                    i = R.id.particle_rate2;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.particle_rate2);
                    if (textView2 != null) {
                        return new BrickSetParticleEmissionBinding((LinearLayout) rootView, checkBox, brickLayout, textView, textView2);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

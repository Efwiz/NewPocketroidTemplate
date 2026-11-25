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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSetParticleColorBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSetParticleColorBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickSetColorEditText;
    public final BrickLayout brickSetParticleColorLayout;
    public final TextView brickSetParticleColorTextView;
    private final LinearLayout rootView;

    private BrickSetParticleColorBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickSetColorEditText, BrickLayout brickSetParticleColorLayout, TextView brickSetParticleColorTextView) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickSetColorEditText = brickSetColorEditText;
        this.brickSetParticleColorLayout = brickSetParticleColorLayout;
        this.brickSetParticleColorTextView = brickSetParticleColorTextView;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSetParticleColorBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSetParticleColorBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_set_particle_color, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSetParticleColorBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_set_color_edit_text;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_color_edit_text);
            if (textView != null) {
                i = R.id.brick_set_particle_color_layout;
                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_set_particle_color_layout);
                if (brickLayout != null) {
                    i = R.id.brick_set_particle_color_text_view;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_particle_color_text_view);
                    if (textView2 != null) {
                        return new BrickSetParticleColorBinding((LinearLayout) rootView, checkBox, textView, brickLayout, textView2);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

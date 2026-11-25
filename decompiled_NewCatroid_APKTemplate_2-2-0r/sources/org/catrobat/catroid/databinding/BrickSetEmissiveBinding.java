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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSetEmissiveBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSetEmissiveBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickSetEmissiveA;
    public final TextView brickSetEmissiveB;
    public final TextView brickSetEmissiveG;
    public final TextView brickSetEmissiveIntensity;
    public final BrickLayout brickSetEmissiveLayout;
    public final TextView brickSetEmissiveObject;
    public final TextView brickSetEmissiveR;
    public final TextView brickSetEmissiveTexture;
    private final LinearLayout rootView;

    private BrickSetEmissiveBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickSetEmissiveA, TextView brickSetEmissiveB, TextView brickSetEmissiveG, TextView brickSetEmissiveIntensity, BrickLayout brickSetEmissiveLayout, TextView brickSetEmissiveObject, TextView brickSetEmissiveR, TextView brickSetEmissiveTexture) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickSetEmissiveA = brickSetEmissiveA;
        this.brickSetEmissiveB = brickSetEmissiveB;
        this.brickSetEmissiveG = brickSetEmissiveG;
        this.brickSetEmissiveIntensity = brickSetEmissiveIntensity;
        this.brickSetEmissiveLayout = brickSetEmissiveLayout;
        this.brickSetEmissiveObject = brickSetEmissiveObject;
        this.brickSetEmissiveR = brickSetEmissiveR;
        this.brickSetEmissiveTexture = brickSetEmissiveTexture;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSetEmissiveBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSetEmissiveBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_set_emissive, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSetEmissiveBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_set_emissive_a;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_emissive_a);
            if (textView != null) {
                i = R.id.brick_set_emissive_b;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_emissive_b);
                if (textView2 != null) {
                    i = R.id.brick_set_emissive_g;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_emissive_g);
                    if (textView3 != null) {
                        i = R.id.brick_set_emissive_intensity;
                        TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_emissive_intensity);
                        if (textView4 != null) {
                            i = R.id.brick_set_emissive_layout;
                            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_set_emissive_layout);
                            if (brickLayout != null) {
                                i = R.id.brick_set_emissive_object;
                                TextView textView5 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_emissive_object);
                                if (textView5 != null) {
                                    i = R.id.brick_set_emissive_r;
                                    TextView textView6 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_emissive_r);
                                    if (textView6 != null) {
                                        i = R.id.brick_set_emissive_texture;
                                        TextView textView7 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_emissive_texture);
                                        if (textView7 != null) {
                                            return new BrickSetEmissiveBinding((LinearLayout) rootView, checkBox, textView, textView2, textView3, textView4, brickLayout, textView5, textView6, textView7);
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

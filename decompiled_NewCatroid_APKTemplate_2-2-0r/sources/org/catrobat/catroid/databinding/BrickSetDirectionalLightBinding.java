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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSetDirectionalLightBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSetDirectionalLightBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickSetDirectionalLightB;
    public final TextView brickSetDirectionalLightG;
    public final TextView brickSetDirectionalLightId;
    public final BrickLayout brickSetDirectionalLightLayout;
    public final TextView brickSetDirectionalLightR;
    public final TextView brickSetDirectionalLightX;
    public final TextView brickSetDirectionalLightY;
    public final TextView brickSetDirectionalLightZ;
    private final LinearLayout rootView;

    private BrickSetDirectionalLightBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickSetDirectionalLightB, TextView brickSetDirectionalLightG, TextView brickSetDirectionalLightId, BrickLayout brickSetDirectionalLightLayout, TextView brickSetDirectionalLightR, TextView brickSetDirectionalLightX, TextView brickSetDirectionalLightY, TextView brickSetDirectionalLightZ) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickSetDirectionalLightB = brickSetDirectionalLightB;
        this.brickSetDirectionalLightG = brickSetDirectionalLightG;
        this.brickSetDirectionalLightId = brickSetDirectionalLightId;
        this.brickSetDirectionalLightLayout = brickSetDirectionalLightLayout;
        this.brickSetDirectionalLightR = brickSetDirectionalLightR;
        this.brickSetDirectionalLightX = brickSetDirectionalLightX;
        this.brickSetDirectionalLightY = brickSetDirectionalLightY;
        this.brickSetDirectionalLightZ = brickSetDirectionalLightZ;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSetDirectionalLightBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSetDirectionalLightBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_set_directional_light, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSetDirectionalLightBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_set_directional_light_b;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_directional_light_b);
            if (textView != null) {
                i = R.id.brick_set_directional_light_g;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_directional_light_g);
                if (textView2 != null) {
                    i = R.id.brick_set_directional_light_id;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_directional_light_id);
                    if (textView3 != null) {
                        i = R.id.brick_set_directional_light_layout;
                        BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_set_directional_light_layout);
                        if (brickLayout != null) {
                            i = R.id.brick_set_directional_light_r;
                            TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_directional_light_r);
                            if (textView4 != null) {
                                i = R.id.brick_set_directional_light_x;
                                TextView textView5 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_directional_light_x);
                                if (textView5 != null) {
                                    i = R.id.brick_set_directional_light_y;
                                    TextView textView6 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_directional_light_y);
                                    if (textView6 != null) {
                                        i = R.id.brick_set_directional_light_z;
                                        TextView textView7 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_directional_light_z);
                                        if (textView7 != null) {
                                            return new BrickSetDirectionalLightBinding((LinearLayout) rootView, checkBox, textView, textView2, textView3, brickLayout, textView4, textView5, textView6, textView7);
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

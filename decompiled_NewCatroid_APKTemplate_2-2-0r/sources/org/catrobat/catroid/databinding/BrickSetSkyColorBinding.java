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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSetSkyColorBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSetSkyColorBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickSetSkyColorB;
    public final TextView brickSetSkyColorG;
    public final BrickLayout brickSetSkyColorLayout;
    public final TextView brickSetSkyColorR;
    private final LinearLayout rootView;

    private BrickSetSkyColorBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickSetSkyColorB, TextView brickSetSkyColorG, BrickLayout brickSetSkyColorLayout, TextView brickSetSkyColorR) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickSetSkyColorB = brickSetSkyColorB;
        this.brickSetSkyColorG = brickSetSkyColorG;
        this.brickSetSkyColorLayout = brickSetSkyColorLayout;
        this.brickSetSkyColorR = brickSetSkyColorR;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSetSkyColorBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSetSkyColorBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_set_sky_color, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSetSkyColorBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_set_sky_color_b;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_sky_color_b);
            if (textView != null) {
                i = R.id.brick_set_sky_color_g;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_sky_color_g);
                if (textView2 != null) {
                    i = R.id.brick_set_sky_color_layout;
                    BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_set_sky_color_layout);
                    if (brickLayout != null) {
                        i = R.id.brick_set_sky_color_r;
                        TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_sky_color_r);
                        if (textView3 != null) {
                            return new BrickSetSkyColorBinding((LinearLayout) rootView, checkBox, textView, textView2, brickLayout, textView3);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSetMaxPointLightsBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSetMaxPointLightsBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickMaxLightsVal;
    public final BrickLayout brickSetMaxLightsLayout;
    private final LinearLayout rootView;

    private BrickSetMaxPointLightsBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickMaxLightsVal, BrickLayout brickSetMaxLightsLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickMaxLightsVal = brickMaxLightsVal;
        this.brickSetMaxLightsLayout = brickSetMaxLightsLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSetMaxPointLightsBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSetMaxPointLightsBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_set_max_point_lights, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSetMaxPointLightsBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_max_lights_val;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_max_lights_val);
            if (textView != null) {
                i = R.id.brick_set_max_lights_layout;
                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_set_max_lights_layout);
                if (brickLayout != null) {
                    return new BrickSetMaxPointLightsBinding((LinearLayout) rootView, checkBox, textView, brickLayout);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSetAnisotropicFilterBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSetAnisotropicFilterBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final BrickLayout brickSetAnisotropicFilterLayout;
    public final TextView brickSetAnisotropicFilterLevelValue;
    public final TextView brickSetAnisotropicFilterObjectIdValue;
    private final LinearLayout rootView;

    private BrickSetAnisotropicFilterBinding(LinearLayout rootView, CheckBox brickCheckbox, BrickLayout brickSetAnisotropicFilterLayout, TextView brickSetAnisotropicFilterLevelValue, TextView brickSetAnisotropicFilterObjectIdValue) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickSetAnisotropicFilterLayout = brickSetAnisotropicFilterLayout;
        this.brickSetAnisotropicFilterLevelValue = brickSetAnisotropicFilterLevelValue;
        this.brickSetAnisotropicFilterObjectIdValue = brickSetAnisotropicFilterObjectIdValue;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSetAnisotropicFilterBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSetAnisotropicFilterBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_set_anisotropic_filter, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSetAnisotropicFilterBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_set_anisotropic_filter_layout;
            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_set_anisotropic_filter_layout);
            if (brickLayout != null) {
                i = R.id.brick_set_anisotropic_filter_level_value;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_anisotropic_filter_level_value);
                if (textView != null) {
                    i = R.id.brick_set_anisotropic_filter_object_id_value;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_anisotropic_filter_object_id_value);
                    if (textView2 != null) {
                        return new BrickSetAnisotropicFilterBinding((LinearLayout) rootView, checkBox, brickLayout, textView, textView2);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

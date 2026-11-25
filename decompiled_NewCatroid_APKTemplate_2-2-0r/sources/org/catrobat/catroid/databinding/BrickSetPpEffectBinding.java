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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSetPpEffectBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSetPpEffectBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final Spinner brickPpEffectSpinner;
    public final Spinner brickPpParamSpinner;
    public final TextView brickPpValueEdit;
    public final BrickLayout brickSetPpLayout;
    private final LinearLayout rootView;

    private BrickSetPpEffectBinding(LinearLayout rootView, CheckBox brickCheckbox, Spinner brickPpEffectSpinner, Spinner brickPpParamSpinner, TextView brickPpValueEdit, BrickLayout brickSetPpLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickPpEffectSpinner = brickPpEffectSpinner;
        this.brickPpParamSpinner = brickPpParamSpinner;
        this.brickPpValueEdit = brickPpValueEdit;
        this.brickSetPpLayout = brickSetPpLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSetPpEffectBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSetPpEffectBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_set_pp_effect, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSetPpEffectBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_pp_effect_spinner;
            Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.brick_pp_effect_spinner);
            if (spinner != null) {
                i = R.id.brick_pp_param_spinner;
                Spinner spinner2 = (Spinner) ViewBindings.findChildViewById(rootView, R.id.brick_pp_param_spinner);
                if (spinner2 != null) {
                    i = R.id.brick_pp_value_edit;
                    TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_pp_value_edit);
                    if (textView != null) {
                        i = R.id.brick_set_pp_layout;
                        BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_set_pp_layout);
                        if (brickLayout != null) {
                            return new BrickSetPpEffectBinding((LinearLayout) rootView, checkBox, spinner, spinner2, textView, brickLayout);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

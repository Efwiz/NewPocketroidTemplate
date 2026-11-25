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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSetPostProcessingNewBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSetPostProcessingNewBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final Spinner brickPpEffectSpinner;
    public final Spinner brickPpParamSpinner;
    public final TextView brickPpValueEdit;
    public final BrickLayout brickSetPpNewLayout;
    private final LinearLayout rootView;

    private BrickSetPostProcessingNewBinding(LinearLayout rootView, CheckBox brickCheckbox, Spinner brickPpEffectSpinner, Spinner brickPpParamSpinner, TextView brickPpValueEdit, BrickLayout brickSetPpNewLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickPpEffectSpinner = brickPpEffectSpinner;
        this.brickPpParamSpinner = brickPpParamSpinner;
        this.brickPpValueEdit = brickPpValueEdit;
        this.brickSetPpNewLayout = brickSetPpNewLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSetPostProcessingNewBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSetPostProcessingNewBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_set_post_processing_new, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSetPostProcessingNewBinding bind(View rootView) {
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
                        i = R.id.brick_set_pp_new_layout;
                        BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_set_pp_new_layout);
                        if (brickLayout != null) {
                            return new BrickSetPostProcessingNewBinding((LinearLayout) rootView, checkBox, spinner, spinner2, textView, brickLayout);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

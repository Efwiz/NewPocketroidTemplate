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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSetVariableEasingBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSetVariableEasingBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickEasingDurationEdit;
    public final TextView brickEasingEndEdit;
    public final TextView brickEasingStartEdit;
    public final TextView brickEasingTimeEdit;
    public final BrickLayout brickSetVariableEasingLayout;
    private final LinearLayout rootView;
    public final Spinner setVariableEasingTypeSpinner;
    public final Spinner setVariableEasingVariableSpinner;

    private BrickSetVariableEasingBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickEasingDurationEdit, TextView brickEasingEndEdit, TextView brickEasingStartEdit, TextView brickEasingTimeEdit, BrickLayout brickSetVariableEasingLayout, Spinner setVariableEasingTypeSpinner, Spinner setVariableEasingVariableSpinner) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickEasingDurationEdit = brickEasingDurationEdit;
        this.brickEasingEndEdit = brickEasingEndEdit;
        this.brickEasingStartEdit = brickEasingStartEdit;
        this.brickEasingTimeEdit = brickEasingTimeEdit;
        this.brickSetVariableEasingLayout = brickSetVariableEasingLayout;
        this.setVariableEasingTypeSpinner = setVariableEasingTypeSpinner;
        this.setVariableEasingVariableSpinner = setVariableEasingVariableSpinner;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSetVariableEasingBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSetVariableEasingBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_set_variable_easing, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSetVariableEasingBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_easing_duration_edit;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_easing_duration_edit);
            if (textView != null) {
                i = R.id.brick_easing_end_edit;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_easing_end_edit);
                if (textView2 != null) {
                    i = R.id.brick_easing_start_edit;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_easing_start_edit);
                    if (textView3 != null) {
                        i = R.id.brick_easing_time_edit;
                        TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_easing_time_edit);
                        if (textView4 != null) {
                            i = R.id.brick_set_variable_easing_layout;
                            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_set_variable_easing_layout);
                            if (brickLayout != null) {
                                i = R.id.set_variable_easing_type_spinner;
                                Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.set_variable_easing_type_spinner);
                                if (spinner != null) {
                                    i = R.id.set_variable_easing_variable_spinner;
                                    Spinner spinner2 = (Spinner) ViewBindings.findChildViewById(rootView, R.id.set_variable_easing_variable_spinner);
                                    if (spinner2 != null) {
                                        return new BrickSetVariableEasingBinding((LinearLayout) rootView, checkBox, textView, textView2, textView3, textView4, brickLayout, spinner, spinner2);
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

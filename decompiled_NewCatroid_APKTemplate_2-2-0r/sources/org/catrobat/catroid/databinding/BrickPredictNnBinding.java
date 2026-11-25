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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickPredictNnBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickPredictNnBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickPredictNnEdit;
    public final BrickLayout brickShowVariableLayout;
    public final Spinner predictNnSpinner;
    private final LinearLayout rootView;

    private BrickPredictNnBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickPredictNnEdit, BrickLayout brickShowVariableLayout, Spinner predictNnSpinner) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickPredictNnEdit = brickPredictNnEdit;
        this.brickShowVariableLayout = brickShowVariableLayout;
        this.predictNnSpinner = predictNnSpinner;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickPredictNnBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickPredictNnBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_predict_nn, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickPredictNnBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_predict_nn_edit;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_predict_nn_edit);
            if (textView != null) {
                i = R.id.brick_show_variable_layout;
                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_show_variable_layout);
                if (brickLayout != null) {
                    i = R.id.predict_nn_spinner;
                    Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.predict_nn_spinner);
                    if (spinner != null) {
                        return new BrickPredictNnBinding((LinearLayout) rootView, checkBox, textView, brickLayout, spinner);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

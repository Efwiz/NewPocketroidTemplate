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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickForVariableFromToBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickForVariableFromToBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickForValuesFromLabel;
    public final Spinner brickForVariableSpinner;
    public final TextView brickInLabel;
    public final TextView brickLoopFromEdit;
    public final TextView brickLoopToEdit;
    public final BrickLayout brickRepeatLayout;
    public final TextView brickToLabel;
    private final LinearLayout rootView;

    private BrickForVariableFromToBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickForValuesFromLabel, Spinner brickForVariableSpinner, TextView brickInLabel, TextView brickLoopFromEdit, TextView brickLoopToEdit, BrickLayout brickRepeatLayout, TextView brickToLabel) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickForValuesFromLabel = brickForValuesFromLabel;
        this.brickForVariableSpinner = brickForVariableSpinner;
        this.brickInLabel = brickInLabel;
        this.brickLoopFromEdit = brickLoopFromEdit;
        this.brickLoopToEdit = brickLoopToEdit;
        this.brickRepeatLayout = brickRepeatLayout;
        this.brickToLabel = brickToLabel;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickForVariableFromToBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickForVariableFromToBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_for_variable_from_to, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickForVariableFromToBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_for_values_from_label;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_for_values_from_label);
            if (textView != null) {
                i = R.id.brick_for_variable_spinner;
                Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.brick_for_variable_spinner);
                if (spinner != null) {
                    i = R.id.brick_in_label;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_in_label);
                    if (textView2 != null) {
                        i = R.id.brick_loop_from_edit;
                        TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_loop_from_edit);
                        if (textView3 != null) {
                            i = R.id.brick_loop_to_edit;
                            TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_loop_to_edit);
                            if (textView4 != null) {
                                i = R.id.brick_repeat_layout;
                                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_repeat_layout);
                                if (brickLayout != null) {
                                    i = R.id.brick_to_label;
                                    TextView textView5 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_to_label);
                                    if (textView5 != null) {
                                        return new BrickForVariableFromToBinding((LinearLayout) rootView, checkBox, textView, spinner, textView2, textView3, textView4, brickLayout, textView5);
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

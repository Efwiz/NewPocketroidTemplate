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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickShowVariableBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickShowVariableBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickShowVariableEditTextX;
    public final TextView brickShowVariableEditTextY;
    public final TextView brickShowVariableLabel;
    public final TextView brickShowVariableLabelAt;
    public final BrickLayout brickShowVariableLayout;
    public final TextView brickShowVariableXTextview;
    public final TextView brickShowVariableYTextview;
    private final LinearLayout rootView;
    public final Spinner showVariableSpinner;

    private BrickShowVariableBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickShowVariableEditTextX, TextView brickShowVariableEditTextY, TextView brickShowVariableLabel, TextView brickShowVariableLabelAt, BrickLayout brickShowVariableLayout, TextView brickShowVariableXTextview, TextView brickShowVariableYTextview, Spinner showVariableSpinner) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickShowVariableEditTextX = brickShowVariableEditTextX;
        this.brickShowVariableEditTextY = brickShowVariableEditTextY;
        this.brickShowVariableLabel = brickShowVariableLabel;
        this.brickShowVariableLabelAt = brickShowVariableLabelAt;
        this.brickShowVariableLayout = brickShowVariableLayout;
        this.brickShowVariableXTextview = brickShowVariableXTextview;
        this.brickShowVariableYTextview = brickShowVariableYTextview;
        this.showVariableSpinner = showVariableSpinner;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickShowVariableBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickShowVariableBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_show_variable, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickShowVariableBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_show_variable_edit_text_x;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_variable_edit_text_x);
            if (textView != null) {
                i = R.id.brick_show_variable_edit_text_y;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_variable_edit_text_y);
                if (textView2 != null) {
                    i = R.id.brick_show_variable_label;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_variable_label);
                    if (textView3 != null) {
                        i = R.id.brick_show_variable_label_at;
                        TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_variable_label_at);
                        if (textView4 != null) {
                            i = R.id.brick_show_variable_layout;
                            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_show_variable_layout);
                            if (brickLayout != null) {
                                i = R.id.brick_show_variable_x_textview;
                                TextView textView5 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_variable_x_textview);
                                if (textView5 != null) {
                                    i = R.id.brick_show_variable_y_textview;
                                    TextView textView6 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_variable_y_textview);
                                    if (textView6 != null) {
                                        i = R.id.show_variable_spinner;
                                        Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.show_variable_spinner);
                                        if (spinner != null) {
                                            return new BrickShowVariableBinding((LinearLayout) rootView, checkBox, textView, textView2, textView3, textView4, brickLayout, textView5, textView6, spinner);
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

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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickCustom0ParamBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickCustom0ParamBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final BrickLayout brickCustomLayout;
    public final TextView customBrickFormula1;
    public final TextView customBrickHeaderPart1;
    public final Spinner customBrickSpinner1;
    private final LinearLayout rootView;

    private BrickCustom0ParamBinding(LinearLayout rootView, CheckBox brickCheckbox, BrickLayout brickCustomLayout, TextView customBrickFormula1, TextView customBrickHeaderPart1, Spinner customBrickSpinner1) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickCustomLayout = brickCustomLayout;
        this.customBrickFormula1 = customBrickFormula1;
        this.customBrickHeaderPart1 = customBrickHeaderPart1;
        this.customBrickSpinner1 = customBrickSpinner1;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickCustom0ParamBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickCustom0ParamBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_custom_0_param, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickCustom0ParamBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_custom_layout;
            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_custom_layout);
            if (brickLayout != null) {
                i = R.id.custom_brick_formula_1;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.custom_brick_formula_1);
                if (textView != null) {
                    i = R.id.custom_brick_header_part_1;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.custom_brick_header_part_1);
                    if (textView2 != null) {
                        i = R.id.custom_brick_spinner_1;
                        Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.custom_brick_spinner_1);
                        if (spinner != null) {
                            return new BrickCustom0ParamBinding((LinearLayout) rootView, checkBox, brickLayout, textView, textView2, spinner);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

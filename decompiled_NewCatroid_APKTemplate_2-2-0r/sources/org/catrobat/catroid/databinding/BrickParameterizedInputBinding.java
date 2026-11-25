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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickParameterizedInputBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickParameterizedInputBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickParamFirstLabel;
    public final TextView brickParamListOfListText;
    public final TextView brickParamSecondLabel;
    public final BrickLayout brickParameterizedInputLayout;
    private final LinearLayout rootView;

    private BrickParameterizedInputBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickParamFirstLabel, TextView brickParamListOfListText, TextView brickParamSecondLabel, BrickLayout brickParameterizedInputLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickParamFirstLabel = brickParamFirstLabel;
        this.brickParamListOfListText = brickParamListOfListText;
        this.brickParamSecondLabel = brickParamSecondLabel;
        this.brickParameterizedInputLayout = brickParameterizedInputLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickParameterizedInputBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickParameterizedInputBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_parameterized_input, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickParameterizedInputBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_param_first_label;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_param_first_label);
            if (textView != null) {
                i = R.id.brick_param_list_of_list_text;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_param_list_of_list_text);
                if (textView2 != null) {
                    i = R.id.brick_param_second_label;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_param_second_label);
                    if (textView3 != null) {
                        i = R.id.brick_parameterized_input_layout;
                        BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_parameterized_input_layout);
                        if (brickLayout != null) {
                            return new BrickParameterizedInputBinding((LinearLayout) rootView, checkBox, textView, textView2, textView3, brickLayout);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

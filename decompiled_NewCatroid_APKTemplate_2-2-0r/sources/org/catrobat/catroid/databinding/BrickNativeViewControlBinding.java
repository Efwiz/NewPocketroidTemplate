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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickNativeViewControlBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickNativeViewControlBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final Spinner brickCommandSpinner;
    public final TextView brickIdField;
    public final BrickLayout brickLayout;
    public final TextView brickParamField;
    private final LinearLayout rootView;

    private BrickNativeViewControlBinding(LinearLayout rootView, CheckBox brickCheckbox, Spinner brickCommandSpinner, TextView brickIdField, BrickLayout brickLayout, TextView brickParamField) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickCommandSpinner = brickCommandSpinner;
        this.brickIdField = brickIdField;
        this.brickLayout = brickLayout;
        this.brickParamField = brickParamField;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickNativeViewControlBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickNativeViewControlBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_native_view_control, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickNativeViewControlBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_command_spinner;
            Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.brick_command_spinner);
            if (spinner != null) {
                i = R.id.brick_id_field;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_id_field);
                if (textView != null) {
                    i = R.id.brick_layout;
                    BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_layout);
                    if (brickLayout != null) {
                        i = R.id.brick_param_field;
                        TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_param_field);
                        if (textView2 != null) {
                            return new BrickNativeViewControlBinding((LinearLayout) rootView, checkBox, spinner, textView, brickLayout, textView2);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

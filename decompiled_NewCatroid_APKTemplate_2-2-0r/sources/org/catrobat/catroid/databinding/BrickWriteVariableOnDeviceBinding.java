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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickWriteVariableOnDeviceBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickWriteVariableOnDeviceBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final BrickLayout brickWriteVariableLayout;
    private final LinearLayout rootView;
    public final TextView writeVariableOnDeviceLabel;
    public final Spinner writeVariableSpinner;

    private BrickWriteVariableOnDeviceBinding(LinearLayout rootView, CheckBox brickCheckbox, BrickLayout brickWriteVariableLayout, TextView writeVariableOnDeviceLabel, Spinner writeVariableSpinner) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickWriteVariableLayout = brickWriteVariableLayout;
        this.writeVariableOnDeviceLabel = writeVariableOnDeviceLabel;
        this.writeVariableSpinner = writeVariableSpinner;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickWriteVariableOnDeviceBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickWriteVariableOnDeviceBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_write_variable_on_device, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickWriteVariableOnDeviceBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_write_variable_layout;
            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_write_variable_layout);
            if (brickLayout != null) {
                i = R.id.write_variable_on_device_label;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.write_variable_on_device_label);
                if (textView != null) {
                    i = R.id.write_variable_spinner;
                    Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.write_variable_spinner);
                    if (spinner != null) {
                        return new BrickWriteVariableOnDeviceBinding((LinearLayout) rootView, checkBox, brickLayout, textView, spinner);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

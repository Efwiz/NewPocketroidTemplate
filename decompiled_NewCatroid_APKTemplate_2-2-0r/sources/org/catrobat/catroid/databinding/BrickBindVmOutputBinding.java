package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ui.BrickLayout;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickBindVmOutputBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickBindVmOutputBinding implements ViewBinding {
    public final BrickLayout brickBindVmOutputLayout;
    public final Spinner brickBindVmOutputVariableSpinner;
    public final CheckBox brickCheckbox;
    private final LinearLayout rootView;

    private BrickBindVmOutputBinding(LinearLayout rootView, BrickLayout brickBindVmOutputLayout, Spinner brickBindVmOutputVariableSpinner, CheckBox brickCheckbox) {
        this.rootView = rootView;
        this.brickBindVmOutputLayout = brickBindVmOutputLayout;
        this.brickBindVmOutputVariableSpinner = brickBindVmOutputVariableSpinner;
        this.brickCheckbox = brickCheckbox;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickBindVmOutputBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickBindVmOutputBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_bind_vm_output, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickBindVmOutputBinding bind(View rootView) {
        int i = R.id.brick_bind_vm_output_layout;
        BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_bind_vm_output_layout);
        if (brickLayout != null) {
            i = R.id.brick_bind_vm_output_variable_spinner;
            Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.brick_bind_vm_output_variable_spinner);
            if (spinner != null) {
                i = R.id.brick_checkbox;
                CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
                if (checkBox != null) {
                    return new BrickBindVmOutputBinding((LinearLayout) rootView, brickLayout, spinner, checkBox);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

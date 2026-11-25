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

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickRunVm2Binding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickRunVm2Binding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickRunVmArgumentsEditText;
    private final LinearLayout rootView;

    private BrickRunVm2Binding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickRunVmArgumentsEditText) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickRunVmArgumentsEditText = brickRunVmArgumentsEditText;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickRunVm2Binding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickRunVm2Binding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_run_vm2, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickRunVm2Binding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_run_vm_arguments_edit_text;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_run_vm_arguments_edit_text);
            if (textView != null) {
                return new BrickRunVm2Binding((LinearLayout) rootView, checkBox, textView);
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

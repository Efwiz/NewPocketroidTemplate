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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickVmMouseRelativeBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickVmMouseRelativeBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickVmMouseEditDx;
    public final TextView brickVmMouseEditDy;
    public final TextView brickVmMouseMask;
    public final BrickLayout brickVmMouseRelativeLayout;
    private final LinearLayout rootView;

    private BrickVmMouseRelativeBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickVmMouseEditDx, TextView brickVmMouseEditDy, TextView brickVmMouseMask, BrickLayout brickVmMouseRelativeLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickVmMouseEditDx = brickVmMouseEditDx;
        this.brickVmMouseEditDy = brickVmMouseEditDy;
        this.brickVmMouseMask = brickVmMouseMask;
        this.brickVmMouseRelativeLayout = brickVmMouseRelativeLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickVmMouseRelativeBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickVmMouseRelativeBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_vm_mouse_relative, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickVmMouseRelativeBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_vm_mouse_edit_dx;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_vm_mouse_edit_dx);
            if (textView != null) {
                i = R.id.brick_vm_mouse_edit_dy;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_vm_mouse_edit_dy);
                if (textView2 != null) {
                    i = R.id.brick_vm_mouse_mask;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_vm_mouse_mask);
                    if (textView3 != null) {
                        i = R.id.brick_vm_mouse_relative_layout;
                        BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_vm_mouse_relative_layout);
                        if (brickLayout != null) {
                            return new BrickVmMouseRelativeBinding((LinearLayout) rootView, checkBox, textView, textView2, textView3, brickLayout);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

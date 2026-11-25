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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickVmMonitorSizeBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickVmMonitorSizeBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickVmMonitorEditHeight;
    public final TextView brickVmMonitorEditWidth;
    public final BrickLayout brickVmMonitorSizeLayout;
    private final LinearLayout rootView;

    private BrickVmMonitorSizeBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickVmMonitorEditHeight, TextView brickVmMonitorEditWidth, BrickLayout brickVmMonitorSizeLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickVmMonitorEditHeight = brickVmMonitorEditHeight;
        this.brickVmMonitorEditWidth = brickVmMonitorEditWidth;
        this.brickVmMonitorSizeLayout = brickVmMonitorSizeLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickVmMonitorSizeBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickVmMonitorSizeBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_vm_monitor_size, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickVmMonitorSizeBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_vm_monitor_edit_height;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_vm_monitor_edit_height);
            if (textView != null) {
                i = R.id.brick_vm_monitor_edit_width;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_vm_monitor_edit_width);
                if (textView2 != null) {
                    i = R.id.brick_vm_monitor_size_layout;
                    BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_vm_monitor_size_layout);
                    if (brickLayout != null) {
                        return new BrickVmMonitorSizeBinding((LinearLayout) rootView, checkBox, textView, textView2, brickLayout);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

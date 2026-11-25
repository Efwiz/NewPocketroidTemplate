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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickDroneEmergencyBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickDroneEmergencyBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickDroneEmergencyLabel;
    public final BrickLayout brickDroneEmergencyLayout;
    private final LinearLayout rootView;

    private BrickDroneEmergencyBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickDroneEmergencyLabel, BrickLayout brickDroneEmergencyLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickDroneEmergencyLabel = brickDroneEmergencyLabel;
        this.brickDroneEmergencyLayout = brickDroneEmergencyLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickDroneEmergencyBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickDroneEmergencyBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_drone_emergency, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickDroneEmergencyBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_drone_emergency_label;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_drone_emergency_label);
            if (textView != null) {
                i = R.id.brick_drone_emergency_layout;
                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_drone_emergency_layout);
                if (brickLayout != null) {
                    return new BrickDroneEmergencyBinding((LinearLayout) rootView, checkBox, textView, brickLayout);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

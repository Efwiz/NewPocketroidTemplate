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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickApplyTorqueBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickApplyTorqueBinding implements ViewBinding {
    public final BrickLayout brickApplyTorqueLayout;
    public final CheckBox brickCheckbox;
    public final TextView brickTorqueEditText;
    private final LinearLayout rootView;

    private BrickApplyTorqueBinding(LinearLayout rootView, BrickLayout brickApplyTorqueLayout, CheckBox brickCheckbox, TextView brickTorqueEditText) {
        this.rootView = rootView;
        this.brickApplyTorqueLayout = brickApplyTorqueLayout;
        this.brickCheckbox = brickCheckbox;
        this.brickTorqueEditText = brickTorqueEditText;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickApplyTorqueBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickApplyTorqueBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_apply_torque, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickApplyTorqueBinding bind(View rootView) {
        int i = R.id.brick_apply_torque_layout;
        BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_apply_torque_layout);
        if (brickLayout != null) {
            i = R.id.brick_checkbox;
            CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
            if (checkBox != null) {
                i = R.id.brick_torque_edit_text;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_torque_edit_text);
                if (textView != null) {
                    return new BrickApplyTorqueBinding((LinearLayout) rootView, brickLayout, checkBox, textView);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

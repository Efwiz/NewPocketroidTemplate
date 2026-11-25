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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSetDampingBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSetDampingBinding implements ViewBinding {
    public final TextView brickAngularDampingEditText;
    public final CheckBox brickCheckbox;
    public final TextView brickLinearDampingEditText;
    public final BrickLayout brickSetDampingLayout;
    private final LinearLayout rootView;

    private BrickSetDampingBinding(LinearLayout rootView, TextView brickAngularDampingEditText, CheckBox brickCheckbox, TextView brickLinearDampingEditText, BrickLayout brickSetDampingLayout) {
        this.rootView = rootView;
        this.brickAngularDampingEditText = brickAngularDampingEditText;
        this.brickCheckbox = brickCheckbox;
        this.brickLinearDampingEditText = brickLinearDampingEditText;
        this.brickSetDampingLayout = brickSetDampingLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSetDampingBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSetDampingBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_set_damping, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSetDampingBinding bind(View rootView) {
        int i = R.id.brick_angular_damping_edit_text;
        TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_angular_damping_edit_text);
        if (textView != null) {
            i = R.id.brick_checkbox;
            CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
            if (checkBox != null) {
                i = R.id.brick_linear_damping_edit_text;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_linear_damping_edit_text);
                if (textView2 != null) {
                    i = R.id.brick_set_damping_layout;
                    BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_set_damping_layout);
                    if (brickLayout != null) {
                        return new BrickSetDampingBinding((LinearLayout) rootView, textView, checkBox, textView2, brickLayout);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

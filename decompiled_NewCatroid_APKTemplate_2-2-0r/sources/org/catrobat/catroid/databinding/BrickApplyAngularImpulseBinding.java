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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickApplyAngularImpulseBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickApplyAngularImpulseBinding implements ViewBinding {
    public final TextView brickAngularImpulseEditText;
    public final BrickLayout brickApplyAngularImpulseLayout;
    public final CheckBox brickCheckbox;
    private final LinearLayout rootView;

    private BrickApplyAngularImpulseBinding(LinearLayout rootView, TextView brickAngularImpulseEditText, BrickLayout brickApplyAngularImpulseLayout, CheckBox brickCheckbox) {
        this.rootView = rootView;
        this.brickAngularImpulseEditText = brickAngularImpulseEditText;
        this.brickApplyAngularImpulseLayout = brickApplyAngularImpulseLayout;
        this.brickCheckbox = brickCheckbox;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickApplyAngularImpulseBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickApplyAngularImpulseBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_apply_angular_impulse, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickApplyAngularImpulseBinding bind(View rootView) {
        int i = R.id.brick_angular_impulse_edit_text;
        TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_angular_impulse_edit_text);
        if (textView != null) {
            i = R.id.brick_apply_angular_impulse_layout;
            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_apply_angular_impulse_layout);
            if (brickLayout != null) {
                i = R.id.brick_checkbox;
                CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
                if (checkBox != null) {
                    return new BrickApplyAngularImpulseBinding((LinearLayout) rootView, textView, brickLayout, checkBox);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

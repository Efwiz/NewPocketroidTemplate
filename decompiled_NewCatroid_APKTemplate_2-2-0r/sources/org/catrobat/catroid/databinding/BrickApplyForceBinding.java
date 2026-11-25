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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickApplyForceBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickApplyForceBinding implements ViewBinding {
    public final BrickLayout brickApplyForceLayout;
    public final CheckBox brickCheckbox;
    public final TextView brickForceXEditText;
    public final TextView brickForceYEditText;
    private final LinearLayout rootView;

    private BrickApplyForceBinding(LinearLayout rootView, BrickLayout brickApplyForceLayout, CheckBox brickCheckbox, TextView brickForceXEditText, TextView brickForceYEditText) {
        this.rootView = rootView;
        this.brickApplyForceLayout = brickApplyForceLayout;
        this.brickCheckbox = brickCheckbox;
        this.brickForceXEditText = brickForceXEditText;
        this.brickForceYEditText = brickForceYEditText;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickApplyForceBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickApplyForceBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_apply_force, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickApplyForceBinding bind(View rootView) {
        int i = R.id.brick_apply_force_layout;
        BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_apply_force_layout);
        if (brickLayout != null) {
            i = R.id.brick_checkbox;
            CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
            if (checkBox != null) {
                i = R.id.brick_force_x_edit_text;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_force_x_edit_text);
                if (textView != null) {
                    i = R.id.brick_force_y_edit_text;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_force_y_edit_text);
                    if (textView2 != null) {
                        return new BrickApplyForceBinding((LinearLayout) rootView, brickLayout, checkBox, textView, textView2);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

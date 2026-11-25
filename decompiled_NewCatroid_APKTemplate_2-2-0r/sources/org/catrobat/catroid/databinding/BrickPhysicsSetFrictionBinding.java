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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickPhysicsSetFrictionBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickPhysicsSetFrictionBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickSetFrictionEditText;
    public final TextView brickSetFrictionLabel;
    public final BrickLayout brickSetFrictionLayout;
    public final TextView brickSetFrictionTextView;
    private final LinearLayout rootView;

    private BrickPhysicsSetFrictionBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickSetFrictionEditText, TextView brickSetFrictionLabel, BrickLayout brickSetFrictionLayout, TextView brickSetFrictionTextView) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickSetFrictionEditText = brickSetFrictionEditText;
        this.brickSetFrictionLabel = brickSetFrictionLabel;
        this.brickSetFrictionLayout = brickSetFrictionLayout;
        this.brickSetFrictionTextView = brickSetFrictionTextView;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickPhysicsSetFrictionBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickPhysicsSetFrictionBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_physics_set_friction, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickPhysicsSetFrictionBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_set_friction_edit_text;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_friction_edit_text);
            if (textView != null) {
                i = R.id.brick_set_friction_label;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_friction_label);
                if (textView2 != null) {
                    i = R.id.brick_set_friction_layout;
                    BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_set_friction_layout);
                    if (brickLayout != null) {
                        i = R.id.brick_set_friction_text_view;
                        TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_friction_text_view);
                        if (textView3 != null) {
                            return new BrickPhysicsSetFrictionBinding((LinearLayout) rootView, checkBox, textView, textView2, brickLayout, textView3);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

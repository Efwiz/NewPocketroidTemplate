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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickPhysicsSetMassBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickPhysicsSetMassBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickSetMassEditText;
    public final BrickLayout brickSetMassLayout;
    public final TextView brickSetMassTextLabel;
    public final TextView brickSetMassTextView;
    private final LinearLayout rootView;

    private BrickPhysicsSetMassBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickSetMassEditText, BrickLayout brickSetMassLayout, TextView brickSetMassTextLabel, TextView brickSetMassTextView) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickSetMassEditText = brickSetMassEditText;
        this.brickSetMassLayout = brickSetMassLayout;
        this.brickSetMassTextLabel = brickSetMassTextLabel;
        this.brickSetMassTextView = brickSetMassTextView;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickPhysicsSetMassBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickPhysicsSetMassBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_physics_set_mass, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickPhysicsSetMassBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_set_mass_edit_text;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_mass_edit_text);
            if (textView != null) {
                i = R.id.brick_set_mass_layout;
                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_set_mass_layout);
                if (brickLayout != null) {
                    i = R.id.brick_set_mass_text_label;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_mass_text_label);
                    if (textView2 != null) {
                        i = R.id.brick_set_mass_text_view;
                        TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_mass_text_view);
                        if (textView3 != null) {
                            return new BrickPhysicsSetMassBinding((LinearLayout) rootView, checkBox, textView, brickLayout, textView2, textView3);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ui.BrickLayout;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickPhysicsSetPhysicsObjectTypeBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickPhysicsSetPhysicsObjectTypeBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final BrickLayout brickSetPhysicsObjectLayout;
    public final TextView brickSetPhysicsObjectTextView;
    public final Spinner brickSetPhysicsObjectTypeSpinner;
    private final LinearLayout rootView;

    private BrickPhysicsSetPhysicsObjectTypeBinding(LinearLayout rootView, CheckBox brickCheckbox, BrickLayout brickSetPhysicsObjectLayout, TextView brickSetPhysicsObjectTextView, Spinner brickSetPhysicsObjectTypeSpinner) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickSetPhysicsObjectLayout = brickSetPhysicsObjectLayout;
        this.brickSetPhysicsObjectTextView = brickSetPhysicsObjectTextView;
        this.brickSetPhysicsObjectTypeSpinner = brickSetPhysicsObjectTypeSpinner;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickPhysicsSetPhysicsObjectTypeBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickPhysicsSetPhysicsObjectTypeBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_physics_set_physics_object_type, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickPhysicsSetPhysicsObjectTypeBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_set_physics_object_layout;
            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_set_physics_object_layout);
            if (brickLayout != null) {
                i = R.id.brick_set_physics_object_text_view;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_physics_object_text_view);
                if (textView != null) {
                    i = R.id.brick_set_physics_object_type_spinner;
                    Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.brick_set_physics_object_type_spinner);
                    if (spinner != null) {
                        return new BrickPhysicsSetPhysicsObjectTypeBinding((LinearLayout) rootView, checkBox, brickLayout, textView, spinner);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

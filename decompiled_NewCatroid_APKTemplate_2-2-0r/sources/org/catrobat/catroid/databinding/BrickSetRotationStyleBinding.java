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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSetRotationStyleBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSetRotationStyleBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final BrickLayout brickSetRotationStyleNormalLayout;
    public final TextView brickSetRotationStyleNormalTextView;
    public final Spinner brickSetRotationStyleSpinner;
    private final LinearLayout rootView;

    private BrickSetRotationStyleBinding(LinearLayout rootView, CheckBox brickCheckbox, BrickLayout brickSetRotationStyleNormalLayout, TextView brickSetRotationStyleNormalTextView, Spinner brickSetRotationStyleSpinner) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickSetRotationStyleNormalLayout = brickSetRotationStyleNormalLayout;
        this.brickSetRotationStyleNormalTextView = brickSetRotationStyleNormalTextView;
        this.brickSetRotationStyleSpinner = brickSetRotationStyleSpinner;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSetRotationStyleBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSetRotationStyleBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_set_rotation_style, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSetRotationStyleBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_set_rotation_style_normal_layout;
            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_set_rotation_style_normal_layout);
            if (brickLayout != null) {
                i = R.id.brick_set_rotation_style_normal_text_view;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_rotation_style_normal_text_view);
                if (textView != null) {
                    i = R.id.brick_set_rotation_style_spinner;
                    Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.brick_set_rotation_style_spinner);
                    if (spinner != null) {
                        return new BrickSetRotationStyleBinding((LinearLayout) rootView, checkBox, brickLayout, textView, spinner);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

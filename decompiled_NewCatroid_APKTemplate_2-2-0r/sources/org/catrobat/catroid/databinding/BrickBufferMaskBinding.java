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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickBufferMaskBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickBufferMaskBinding implements ViewBinding {
    public final BrickLayout brickBufferMaskLayout;
    public final TextView brickBufferMaskNameField;
    public final Spinner brickBufferMaskSpinner;
    public final CheckBox brickCheckbox;
    private final LinearLayout rootView;

    private BrickBufferMaskBinding(LinearLayout rootView, BrickLayout brickBufferMaskLayout, TextView brickBufferMaskNameField, Spinner brickBufferMaskSpinner, CheckBox brickCheckbox) {
        this.rootView = rootView;
        this.brickBufferMaskLayout = brickBufferMaskLayout;
        this.brickBufferMaskNameField = brickBufferMaskNameField;
        this.brickBufferMaskSpinner = brickBufferMaskSpinner;
        this.brickCheckbox = brickCheckbox;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickBufferMaskBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickBufferMaskBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_buffer_mask, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickBufferMaskBinding bind(View rootView) {
        int i = R.id.brick_buffer_mask_layout;
        BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_buffer_mask_layout);
        if (brickLayout != null) {
            i = R.id.brick_buffer_mask_name_field;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_buffer_mask_name_field);
            if (textView != null) {
                i = R.id.brick_buffer_mask_spinner;
                Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.brick_buffer_mask_spinner);
                if (spinner != null) {
                    i = R.id.brick_checkbox;
                    CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
                    if (checkBox != null) {
                        return new BrickBufferMaskBinding((LinearLayout) rootView, brickLayout, textView, spinner, checkBox);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

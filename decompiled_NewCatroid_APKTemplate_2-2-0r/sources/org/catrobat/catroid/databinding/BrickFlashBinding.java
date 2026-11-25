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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickFlashBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickFlashBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final BrickLayout brickFlashLayout;
    public final TextView brickFlashPrototypeTextView;
    public final Spinner brickFlashSpinner;
    private final LinearLayout rootView;

    private BrickFlashBinding(LinearLayout rootView, CheckBox brickCheckbox, BrickLayout brickFlashLayout, TextView brickFlashPrototypeTextView, Spinner brickFlashSpinner) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickFlashLayout = brickFlashLayout;
        this.brickFlashPrototypeTextView = brickFlashPrototypeTextView;
        this.brickFlashSpinner = brickFlashSpinner;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickFlashBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickFlashBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_flash, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickFlashBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_flash_layout;
            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_flash_layout);
            if (brickLayout != null) {
                i = R.id.brick_flash_prototype_text_view;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_flash_prototype_text_view);
                if (textView != null) {
                    i = R.id.brick_flash_spinner;
                    Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.brick_flash_spinner);
                    if (spinner != null) {
                        return new BrickFlashBinding((LinearLayout) rootView, checkBox, brickLayout, textView, spinner);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

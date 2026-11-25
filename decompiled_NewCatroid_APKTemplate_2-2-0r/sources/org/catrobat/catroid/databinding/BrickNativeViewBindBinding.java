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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickNativeViewBindBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickNativeViewBindBinding implements ViewBinding {
    public final Spinner brickAlignSpinner;
    public final CheckBox brickCheckbox;
    public final TextView brickIdField;
    public final BrickLayout brickLayout;
    public final TextView brickSpriteField;
    public final TextView brickXField;
    public final TextView brickYField;
    private final LinearLayout rootView;

    private BrickNativeViewBindBinding(LinearLayout rootView, Spinner brickAlignSpinner, CheckBox brickCheckbox, TextView brickIdField, BrickLayout brickLayout, TextView brickSpriteField, TextView brickXField, TextView brickYField) {
        this.rootView = rootView;
        this.brickAlignSpinner = brickAlignSpinner;
        this.brickCheckbox = brickCheckbox;
        this.brickIdField = brickIdField;
        this.brickLayout = brickLayout;
        this.brickSpriteField = brickSpriteField;
        this.brickXField = brickXField;
        this.brickYField = brickYField;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickNativeViewBindBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickNativeViewBindBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_native_view_bind, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickNativeViewBindBinding bind(View rootView) {
        int i = R.id.brick_align_spinner;
        Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.brick_align_spinner);
        if (spinner != null) {
            i = R.id.brick_checkbox;
            CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
            if (checkBox != null) {
                i = R.id.brick_id_field;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_id_field);
                if (textView != null) {
                    i = R.id.brick_layout;
                    BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_layout);
                    if (brickLayout != null) {
                        i = R.id.brick_sprite_field;
                        TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_sprite_field);
                        if (textView2 != null) {
                            i = R.id.brick_x_field;
                            TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_x_field);
                            if (textView3 != null) {
                                i = R.id.brick_y_field;
                                TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_y_field);
                                if (textView4 != null) {
                                    return new BrickNativeViewBindBinding((LinearLayout) rootView, spinner, checkBox, textView, brickLayout, textView2, textView3, textView4);
                                }
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

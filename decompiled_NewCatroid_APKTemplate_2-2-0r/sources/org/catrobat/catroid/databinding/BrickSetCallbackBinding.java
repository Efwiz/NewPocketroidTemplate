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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSetCallbackBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSetCallbackBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickSetCallbackEditName;
    public final BrickLayout brickSetCallbackLayout;
    private final LinearLayout rootView;
    public final Spinner setCallbackSpinner;

    private BrickSetCallbackBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickSetCallbackEditName, BrickLayout brickSetCallbackLayout, Spinner setCallbackSpinner) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickSetCallbackEditName = brickSetCallbackEditName;
        this.brickSetCallbackLayout = brickSetCallbackLayout;
        this.setCallbackSpinner = setCallbackSpinner;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSetCallbackBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSetCallbackBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_set_callback, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSetCallbackBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_set_callback_edit_name;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_callback_edit_name);
            if (textView != null) {
                i = R.id.brick_set_callback_layout;
                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_set_callback_layout);
                if (brickLayout != null) {
                    i = R.id.set_callback_spinner;
                    Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.set_callback_spinner);
                    if (spinner != null) {
                        return new BrickSetCallbackBinding((LinearLayout) rootView, checkBox, textView, brickLayout, spinner);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

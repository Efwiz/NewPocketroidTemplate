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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSetActiveBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSetActiveBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final BrickLayout brickSetActiveLayout;
    public final TextView brickSetActiveObjectName;
    public final Spinner brickSetActiveStateSpinner;
    private final LinearLayout rootView;

    private BrickSetActiveBinding(LinearLayout rootView, CheckBox brickCheckbox, BrickLayout brickSetActiveLayout, TextView brickSetActiveObjectName, Spinner brickSetActiveStateSpinner) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickSetActiveLayout = brickSetActiveLayout;
        this.brickSetActiveObjectName = brickSetActiveObjectName;
        this.brickSetActiveStateSpinner = brickSetActiveStateSpinner;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSetActiveBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSetActiveBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_set_active, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSetActiveBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_set_active_layout;
            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_set_active_layout);
            if (brickLayout != null) {
                i = R.id.brick_set_active_object_name;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_active_object_name);
                if (textView != null) {
                    i = R.id.brick_set_active_state_spinner;
                    Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.brick_set_active_state_spinner);
                    if (spinner != null) {
                        return new BrickSetActiveBinding((LinearLayout) rootView, checkBox, brickLayout, textView, spinner);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ui.BrickLayout;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickUnloadNnBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickUnloadNnBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final BrickLayout brickShowVariableLayout;
    private final LinearLayout rootView;

    private BrickUnloadNnBinding(LinearLayout rootView, CheckBox brickCheckbox, BrickLayout brickShowVariableLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickShowVariableLayout = brickShowVariableLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickUnloadNnBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickUnloadNnBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_unload_nn, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickUnloadNnBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_show_variable_layout;
            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_show_variable_layout);
            if (brickLayout != null) {
                return new BrickUnloadNnBinding((LinearLayout) rootView, checkBox, brickLayout);
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

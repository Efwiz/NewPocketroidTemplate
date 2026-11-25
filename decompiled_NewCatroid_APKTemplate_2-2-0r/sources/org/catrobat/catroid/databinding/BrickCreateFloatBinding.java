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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickCreateFloatBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickCreateFloatBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickCreateFloatEdit;
    public final BrickLayout brickCreateTableLayout;
    private final LinearLayout rootView;

    private BrickCreateFloatBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickCreateFloatEdit, BrickLayout brickCreateTableLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickCreateFloatEdit = brickCreateFloatEdit;
        this.brickCreateTableLayout = brickCreateTableLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickCreateFloatBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickCreateFloatBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_create_float, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickCreateFloatBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_create_float_edit;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_create_float_edit);
            if (textView != null) {
                i = R.id.brick_create_table_layout;
                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_create_table_layout);
                if (brickLayout != null) {
                    return new BrickCreateFloatBinding((LinearLayout) rootView, checkBox, textView, brickLayout);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSetNativeParentBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSetNativeParentBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final BrickLayout brickLayout;
    public final TextView brickSetNativeParentIdField;
    public final TextView brickSetNativeParentTargetField;
    private final LinearLayout rootView;

    private BrickSetNativeParentBinding(LinearLayout rootView, CheckBox brickCheckbox, BrickLayout brickLayout, TextView brickSetNativeParentIdField, TextView brickSetNativeParentTargetField) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickLayout = brickLayout;
        this.brickSetNativeParentIdField = brickSetNativeParentIdField;
        this.brickSetNativeParentTargetField = brickSetNativeParentTargetField;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSetNativeParentBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSetNativeParentBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_set_native_parent, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSetNativeParentBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_layout;
            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_layout);
            if (brickLayout != null) {
                i = R.id.brick_set_native_parent_id_field;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_native_parent_id_field);
                if (textView != null) {
                    i = R.id.brick_set_native_parent_target_field;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_native_parent_target_field);
                    if (textView2 != null) {
                        return new BrickSetNativeParentBinding((LinearLayout) rootView, checkBox, brickLayout, textView, textView2);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

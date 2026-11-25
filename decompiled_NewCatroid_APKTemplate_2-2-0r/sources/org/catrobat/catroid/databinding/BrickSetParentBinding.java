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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSetParentBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSetParentBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickSetParentChildEdit;
    public final BrickLayout brickSetParentLayout;
    public final TextView brickSetParentParentEdit;
    private final LinearLayout rootView;

    private BrickSetParentBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickSetParentChildEdit, BrickLayout brickSetParentLayout, TextView brickSetParentParentEdit) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickSetParentChildEdit = brickSetParentChildEdit;
        this.brickSetParentLayout = brickSetParentLayout;
        this.brickSetParentParentEdit = brickSetParentParentEdit;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSetParentBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSetParentBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_set_parent, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSetParentBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_set_parent_child_edit;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_parent_child_edit);
            if (textView != null) {
                i = R.id.brick_set_parent_layout;
                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_set_parent_layout);
                if (brickLayout != null) {
                    i = R.id.brick_set_parent_parent_edit;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_parent_parent_edit);
                    if (textView2 != null) {
                        return new BrickSetParentBinding((LinearLayout) rootView, checkBox, textView, brickLayout, textView2);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

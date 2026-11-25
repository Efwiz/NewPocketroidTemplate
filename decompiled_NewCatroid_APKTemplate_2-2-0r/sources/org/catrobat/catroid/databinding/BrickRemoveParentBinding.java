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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickRemoveParentBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickRemoveParentBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final BrickLayout brickRemoveParentLayout;
    public final TextView brickRemoveParentObjectEdit;
    private final LinearLayout rootView;

    private BrickRemoveParentBinding(LinearLayout rootView, CheckBox brickCheckbox, BrickLayout brickRemoveParentLayout, TextView brickRemoveParentObjectEdit) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickRemoveParentLayout = brickRemoveParentLayout;
        this.brickRemoveParentObjectEdit = brickRemoveParentObjectEdit;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickRemoveParentBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickRemoveParentBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_remove_parent, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickRemoveParentBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_remove_parent_layout;
            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_remove_parent_layout);
            if (brickLayout != null) {
                i = R.id.brick_remove_parent_object_edit;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_remove_parent_object_edit);
                if (textView != null) {
                    return new BrickRemoveParentBinding((LinearLayout) rootView, checkBox, brickLayout, textView);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

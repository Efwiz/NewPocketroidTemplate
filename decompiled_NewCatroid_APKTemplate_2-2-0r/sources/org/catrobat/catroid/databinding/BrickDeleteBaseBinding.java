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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickDeleteBaseBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickDeleteBaseBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickDeleteBaseEditBase;
    public final TextView brickDeleteBaseEditKey;
    public final BrickLayout brickDeleteBaseLayout;
    private final LinearLayout rootView;

    private BrickDeleteBaseBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickDeleteBaseEditBase, TextView brickDeleteBaseEditKey, BrickLayout brickDeleteBaseLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickDeleteBaseEditBase = brickDeleteBaseEditBase;
        this.brickDeleteBaseEditKey = brickDeleteBaseEditKey;
        this.brickDeleteBaseLayout = brickDeleteBaseLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickDeleteBaseBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickDeleteBaseBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_delete_base, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickDeleteBaseBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_delete_base_edit_base;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_delete_base_edit_base);
            if (textView != null) {
                i = R.id.brick_delete_base_edit_key;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_delete_base_edit_key);
                if (textView2 != null) {
                    i = R.id.brick_delete_base_layout;
                    BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_delete_base_layout);
                    if (brickLayout != null) {
                        return new BrickDeleteBaseBinding((LinearLayout) rootView, checkBox, textView, textView2, brickLayout);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

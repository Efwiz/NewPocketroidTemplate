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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickAddEditBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickAddEditBinding implements ViewBinding {
    public final TextView brickAddEditEditName;
    public final TextView brickAddEditEditText;
    public final BrickLayout brickAddEditLayout;
    public final CheckBox brickCheckbox;
    private final LinearLayout rootView;

    private BrickAddEditBinding(LinearLayout rootView, TextView brickAddEditEditName, TextView brickAddEditEditText, BrickLayout brickAddEditLayout, CheckBox brickCheckbox) {
        this.rootView = rootView;
        this.brickAddEditEditName = brickAddEditEditName;
        this.brickAddEditEditText = brickAddEditEditText;
        this.brickAddEditLayout = brickAddEditLayout;
        this.brickCheckbox = brickCheckbox;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickAddEditBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickAddEditBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_add_edit, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickAddEditBinding bind(View rootView) {
        int i = R.id.brick_add_edit_edit_name;
        TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_add_edit_edit_name);
        if (textView != null) {
            i = R.id.brick_add_edit_edit_text;
            TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_add_edit_edit_text);
            if (textView2 != null) {
                i = R.id.brick_add_edit_layout;
                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_add_edit_layout);
                if (brickLayout != null) {
                    i = R.id.brick_checkbox;
                    CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
                    if (checkBox != null) {
                        return new BrickAddEditBinding((LinearLayout) rootView, textView, textView2, brickLayout, checkBox);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

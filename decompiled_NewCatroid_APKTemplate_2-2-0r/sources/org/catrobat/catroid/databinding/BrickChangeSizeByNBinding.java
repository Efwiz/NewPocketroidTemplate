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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickChangeSizeByNBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickChangeSizeByNBinding implements ViewBinding {
    public final TextView brickChangeSizeByEditText;
    public final TextView brickChangeSizeByLabel;
    public final BrickLayout brickChangeSizeByLayout;
    public final CheckBox brickCheckbox;
    private final LinearLayout rootView;

    private BrickChangeSizeByNBinding(LinearLayout rootView, TextView brickChangeSizeByEditText, TextView brickChangeSizeByLabel, BrickLayout brickChangeSizeByLayout, CheckBox brickCheckbox) {
        this.rootView = rootView;
        this.brickChangeSizeByEditText = brickChangeSizeByEditText;
        this.brickChangeSizeByLabel = brickChangeSizeByLabel;
        this.brickChangeSizeByLayout = brickChangeSizeByLayout;
        this.brickCheckbox = brickCheckbox;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickChangeSizeByNBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickChangeSizeByNBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_change_size_by_n, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickChangeSizeByNBinding bind(View rootView) {
        int i = R.id.brick_change_size_by_edit_text;
        TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_change_size_by_edit_text);
        if (textView != null) {
            i = R.id.brick_change_size_by_label;
            TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_change_size_by_label);
            if (textView2 != null) {
                i = R.id.brick_change_size_by_layout;
                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_change_size_by_layout);
                if (brickLayout != null) {
                    i = R.id.brick_checkbox;
                    CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
                    if (checkBox != null) {
                        return new BrickChangeSizeByNBinding((LinearLayout) rootView, textView, textView2, brickLayout, checkBox);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

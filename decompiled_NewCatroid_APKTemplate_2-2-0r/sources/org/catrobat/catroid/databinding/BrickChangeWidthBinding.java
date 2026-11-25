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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickChangeWidthBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickChangeWidthBinding implements ViewBinding {
    public final TextView brickChangeWidthEditText;
    public final TextView brickChangeWidthLabel;
    public final BrickLayout brickChangeWidthLayout;
    public final TextView brickChangeWidthPercent;
    public final CheckBox brickCheckbox;
    private final LinearLayout rootView;

    private BrickChangeWidthBinding(LinearLayout rootView, TextView brickChangeWidthEditText, TextView brickChangeWidthLabel, BrickLayout brickChangeWidthLayout, TextView brickChangeWidthPercent, CheckBox brickCheckbox) {
        this.rootView = rootView;
        this.brickChangeWidthEditText = brickChangeWidthEditText;
        this.brickChangeWidthLabel = brickChangeWidthLabel;
        this.brickChangeWidthLayout = brickChangeWidthLayout;
        this.brickChangeWidthPercent = brickChangeWidthPercent;
        this.brickCheckbox = brickCheckbox;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickChangeWidthBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickChangeWidthBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_change_width, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickChangeWidthBinding bind(View rootView) {
        int i = R.id.brick_change_width_edit_text;
        TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_change_width_edit_text);
        if (textView != null) {
            i = R.id.brick_change_width_label;
            TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_change_width_label);
            if (textView2 != null) {
                i = R.id.brick_change_width_layout;
                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_change_width_layout);
                if (brickLayout != null) {
                    i = R.id.brick_change_width_percent;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_change_width_percent);
                    if (textView3 != null) {
                        i = R.id.brick_checkbox;
                        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
                        if (checkBox != null) {
                            return new BrickChangeWidthBinding((LinearLayout) rootView, textView, textView2, brickLayout, textView3, checkBox);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

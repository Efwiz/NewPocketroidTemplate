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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSetSizeToBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSetSizeToBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickSetSizeToEditText;
    public final TextView brickSetSizeToLabel;
    public final BrickLayout brickSetSizeToLayout;
    public final TextView brickSetSizeToPercent;
    private final LinearLayout rootView;

    private BrickSetSizeToBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickSetSizeToEditText, TextView brickSetSizeToLabel, BrickLayout brickSetSizeToLayout, TextView brickSetSizeToPercent) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickSetSizeToEditText = brickSetSizeToEditText;
        this.brickSetSizeToLabel = brickSetSizeToLabel;
        this.brickSetSizeToLayout = brickSetSizeToLayout;
        this.brickSetSizeToPercent = brickSetSizeToPercent;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSetSizeToBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSetSizeToBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_set_size_to, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSetSizeToBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_set_size_to_edit_text;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_size_to_edit_text);
            if (textView != null) {
                i = R.id.brick_set_size_to_label;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_size_to_label);
                if (textView2 != null) {
                    i = R.id.brick_set_size_to_layout;
                    BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_set_size_to_layout);
                    if (brickLayout != null) {
                        i = R.id.brick_set_size_to_percent;
                        TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_size_to_percent);
                        if (textView3 != null) {
                            return new BrickSetSizeToBinding((LinearLayout) rootView, checkBox, textView, textView2, brickLayout, textView3);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

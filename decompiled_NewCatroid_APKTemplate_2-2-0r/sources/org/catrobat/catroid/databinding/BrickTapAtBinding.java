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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickTapAtBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickTapAtBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickTapAtEditTextX;
    public final TextView brickTapAtEditTextY;
    public final BrickLayout brickTapAtLayout;
    private final LinearLayout rootView;

    private BrickTapAtBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickTapAtEditTextX, TextView brickTapAtEditTextY, BrickLayout brickTapAtLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickTapAtEditTextX = brickTapAtEditTextX;
        this.brickTapAtEditTextY = brickTapAtEditTextY;
        this.brickTapAtLayout = brickTapAtLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickTapAtBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickTapAtBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_tap_at, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickTapAtBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_tap_at_edit_text_x;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_tap_at_edit_text_x);
            if (textView != null) {
                i = R.id.brick_tap_at_edit_text_y;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_tap_at_edit_text_y);
                if (textView2 != null) {
                    i = R.id.brick_tap_at_layout;
                    BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_tap_at_layout);
                    if (brickLayout != null) {
                        return new BrickTapAtBinding((LinearLayout) rootView, checkBox, textView, textView2, brickLayout);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSetPenSizeBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSetPenSizeBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickSetPenSizeEditText;
    public final TextView brickSetPenSizeLabel;
    public final BrickLayout brickSetPenSizeLayout;
    private final LinearLayout rootView;

    private BrickSetPenSizeBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickSetPenSizeEditText, TextView brickSetPenSizeLabel, BrickLayout brickSetPenSizeLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickSetPenSizeEditText = brickSetPenSizeEditText;
        this.brickSetPenSizeLabel = brickSetPenSizeLabel;
        this.brickSetPenSizeLayout = brickSetPenSizeLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSetPenSizeBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSetPenSizeBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_set_pen_size, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSetPenSizeBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_set_pen_size_edit_text;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_pen_size_edit_text);
            if (textView != null) {
                i = R.id.brick_set_pen_size_label;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_pen_size_label);
                if (textView2 != null) {
                    i = R.id.brick_set_pen_size_layout;
                    BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_set_pen_size_layout);
                    if (brickLayout != null) {
                        return new BrickSetPenSizeBinding((LinearLayout) rootView, checkBox, textView, textView2, brickLayout);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

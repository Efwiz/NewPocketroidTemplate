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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSetTextBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSetTextBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickSecondsLabel;
    public final TextView brickSetTextEditText;
    public final TextView brickSetTextEditTextX;
    public final TextView brickSetTextEditTextY;
    public final BrickLayout brickSetTextLayout;
    private final LinearLayout rootView;

    private BrickSetTextBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickSecondsLabel, TextView brickSetTextEditText, TextView brickSetTextEditTextX, TextView brickSetTextEditTextY, BrickLayout brickSetTextLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickSecondsLabel = brickSecondsLabel;
        this.brickSetTextEditText = brickSetTextEditText;
        this.brickSetTextEditTextX = brickSetTextEditTextX;
        this.brickSetTextEditTextY = brickSetTextEditTextY;
        this.brickSetTextLayout = brickSetTextLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSetTextBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSetTextBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_set_text, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSetTextBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_seconds_label;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_seconds_label);
            if (textView != null) {
                i = R.id.brick_set_text_edit_text;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_text_edit_text);
                if (textView2 != null) {
                    i = R.id.brick_set_text_edit_text_x;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_text_edit_text_x);
                    if (textView3 != null) {
                        i = R.id.brick_set_text_edit_text_y;
                        TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_text_edit_text_y);
                        if (textView4 != null) {
                            i = R.id.brick_set_text_layout;
                            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_set_text_layout);
                            if (brickLayout != null) {
                                return new BrickSetTextBinding((LinearLayout) rootView, checkBox, textView, textView2, textView3, textView4, brickLayout);
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

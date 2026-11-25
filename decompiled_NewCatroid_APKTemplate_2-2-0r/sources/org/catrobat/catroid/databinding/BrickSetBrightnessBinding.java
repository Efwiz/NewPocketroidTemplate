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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSetBrightnessBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSetBrightnessBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickSetBrightnessEditText;
    public final TextView brickSetBrightnessLabel;
    public final BrickLayout brickSetBrightnessLayout;
    public final TextView brickSetBrightnessToPercent;
    public final TextView brickSetBrightnessToTextview;
    private final LinearLayout rootView;

    private BrickSetBrightnessBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickSetBrightnessEditText, TextView brickSetBrightnessLabel, BrickLayout brickSetBrightnessLayout, TextView brickSetBrightnessToPercent, TextView brickSetBrightnessToTextview) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickSetBrightnessEditText = brickSetBrightnessEditText;
        this.brickSetBrightnessLabel = brickSetBrightnessLabel;
        this.brickSetBrightnessLayout = brickSetBrightnessLayout;
        this.brickSetBrightnessToPercent = brickSetBrightnessToPercent;
        this.brickSetBrightnessToTextview = brickSetBrightnessToTextview;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSetBrightnessBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSetBrightnessBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_set_brightness, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSetBrightnessBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_set_brightness_edit_text;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_brightness_edit_text);
            if (textView != null) {
                i = R.id.brick_set_brightness_label;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_brightness_label);
                if (textView2 != null) {
                    i = R.id.brick_set_brightness_layout;
                    BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_set_brightness_layout);
                    if (brickLayout != null) {
                        i = R.id.brick_set_brightness_to_percent;
                        TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_brightness_to_percent);
                        if (textView3 != null) {
                            i = R.id.brick_set_brightness_to_textview;
                            TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_brightness_to_textview);
                            if (textView4 != null) {
                                return new BrickSetBrightnessBinding((LinearLayout) rootView, checkBox, textView, textView2, brickLayout, textView3, textView4);
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

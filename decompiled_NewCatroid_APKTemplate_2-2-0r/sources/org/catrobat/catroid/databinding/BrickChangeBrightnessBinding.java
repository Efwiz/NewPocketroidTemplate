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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickChangeBrightnessBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickChangeBrightnessBinding implements ViewBinding {
    public final TextView brickChangeBrightnessByTextview;
    public final TextView brickChangeBrightnessEditText;
    public final TextView brickChangeBrightnessLabel;
    public final BrickLayout brickChangeBrightnessLayout;
    public final CheckBox brickCheckbox;
    private final LinearLayout rootView;

    private BrickChangeBrightnessBinding(LinearLayout rootView, TextView brickChangeBrightnessByTextview, TextView brickChangeBrightnessEditText, TextView brickChangeBrightnessLabel, BrickLayout brickChangeBrightnessLayout, CheckBox brickCheckbox) {
        this.rootView = rootView;
        this.brickChangeBrightnessByTextview = brickChangeBrightnessByTextview;
        this.brickChangeBrightnessEditText = brickChangeBrightnessEditText;
        this.brickChangeBrightnessLabel = brickChangeBrightnessLabel;
        this.brickChangeBrightnessLayout = brickChangeBrightnessLayout;
        this.brickCheckbox = brickCheckbox;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickChangeBrightnessBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickChangeBrightnessBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_change_brightness, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickChangeBrightnessBinding bind(View rootView) {
        int i = R.id.brick_change_brightness_by_textview;
        TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_change_brightness_by_textview);
        if (textView != null) {
            i = R.id.brick_change_brightness_edit_text;
            TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_change_brightness_edit_text);
            if (textView2 != null) {
                i = R.id.brick_change_brightness_label;
                TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_change_brightness_label);
                if (textView3 != null) {
                    i = R.id.brick_change_brightness_layout;
                    BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_change_brightness_layout);
                    if (brickLayout != null) {
                        i = R.id.brick_checkbox;
                        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
                        if (checkBox != null) {
                            return new BrickChangeBrightnessBinding((LinearLayout) rootView, textView, textView2, textView3, brickLayout, checkBox);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

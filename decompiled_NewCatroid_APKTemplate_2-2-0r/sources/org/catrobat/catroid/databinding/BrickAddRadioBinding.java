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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickAddRadioBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickAddRadioBinding implements ViewBinding {
    public final TextView brickAddRadioEditName;
    public final TextView brickAddRadioEditText;
    public final BrickLayout brickAddRadioLayout;
    public final CheckBox brickCheckbox;
    private final LinearLayout rootView;

    private BrickAddRadioBinding(LinearLayout rootView, TextView brickAddRadioEditName, TextView brickAddRadioEditText, BrickLayout brickAddRadioLayout, CheckBox brickCheckbox) {
        this.rootView = rootView;
        this.brickAddRadioEditName = brickAddRadioEditName;
        this.brickAddRadioEditText = brickAddRadioEditText;
        this.brickAddRadioLayout = brickAddRadioLayout;
        this.brickCheckbox = brickCheckbox;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickAddRadioBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickAddRadioBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_add_radio, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickAddRadioBinding bind(View rootView) {
        int i = R.id.brick_add_radio_edit_name;
        TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_add_radio_edit_name);
        if (textView != null) {
            i = R.id.brick_add_radio_edit_text;
            TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_add_radio_edit_text);
            if (textView2 != null) {
                i = R.id.brick_add_radio_layout;
                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_add_radio_layout);
                if (brickLayout != null) {
                    i = R.id.brick_checkbox;
                    CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
                    if (checkBox != null) {
                        return new BrickAddRadioBinding((LinearLayout) rootView, textView, textView2, brickLayout, checkBox);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

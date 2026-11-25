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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSaveLookBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSaveLookBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickSaveLookEditText;
    public final BrickLayout brickSaveLookLayout;
    public final TextView brickShowToastLabel;
    private final LinearLayout rootView;

    private BrickSaveLookBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickSaveLookEditText, BrickLayout brickSaveLookLayout, TextView brickShowToastLabel) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickSaveLookEditText = brickSaveLookEditText;
        this.brickSaveLookLayout = brickSaveLookLayout;
        this.brickShowToastLabel = brickShowToastLabel;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSaveLookBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSaveLookBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_save_look, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSaveLookBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_save_look_edit_text;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_save_look_edit_text);
            if (textView != null) {
                i = R.id.brick_save_look_layout;
                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_save_look_layout);
                if (brickLayout != null) {
                    i = R.id.brick_show_toast_label;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_toast_label);
                    if (textView2 != null) {
                        return new BrickSaveLookBinding((LinearLayout) rootView, checkBox, textView, brickLayout, textView2);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

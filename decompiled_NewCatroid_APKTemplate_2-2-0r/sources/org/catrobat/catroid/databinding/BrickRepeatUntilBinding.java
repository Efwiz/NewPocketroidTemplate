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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickRepeatUntilBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickRepeatUntilBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickRepeatUntilEditText;
    public final TextView brickRepeatUntilLabel;
    public final BrickLayout brickRepeatUntilLayout;
    private final LinearLayout rootView;
    public final TextView waitUntilLabelSecondPart;

    private BrickRepeatUntilBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickRepeatUntilEditText, TextView brickRepeatUntilLabel, BrickLayout brickRepeatUntilLayout, TextView waitUntilLabelSecondPart) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickRepeatUntilEditText = brickRepeatUntilEditText;
        this.brickRepeatUntilLabel = brickRepeatUntilLabel;
        this.brickRepeatUntilLayout = brickRepeatUntilLayout;
        this.waitUntilLabelSecondPart = waitUntilLabelSecondPart;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickRepeatUntilBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickRepeatUntilBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_repeat_until, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickRepeatUntilBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_repeat_until_edit_text;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_repeat_until_edit_text);
            if (textView != null) {
                i = R.id.brick_repeat_until_label;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_repeat_until_label);
                if (textView2 != null) {
                    i = R.id.brick_repeat_until_layout;
                    BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_repeat_until_layout);
                    if (brickLayout != null) {
                        i = R.id.wait_until_label_second_part;
                        TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.wait_until_label_second_part);
                        if (textView3 != null) {
                            return new BrickRepeatUntilBinding((LinearLayout) rootView, checkBox, textView, textView2, brickLayout, textView3);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

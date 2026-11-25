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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickRepeatBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickRepeatBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickRepeatEditText;
    public final TextView brickRepeatLabel;
    public final BrickLayout brickRepeatLayout;
    public final TextView brickRepeatTimeTextView;
    private final LinearLayout rootView;

    private BrickRepeatBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickRepeatEditText, TextView brickRepeatLabel, BrickLayout brickRepeatLayout, TextView brickRepeatTimeTextView) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickRepeatEditText = brickRepeatEditText;
        this.brickRepeatLabel = brickRepeatLabel;
        this.brickRepeatLayout = brickRepeatLayout;
        this.brickRepeatTimeTextView = brickRepeatTimeTextView;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickRepeatBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickRepeatBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_repeat, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickRepeatBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_repeat_edit_text;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_repeat_edit_text);
            if (textView != null) {
                i = R.id.brick_repeat_label;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_repeat_label);
                if (textView2 != null) {
                    i = R.id.brick_repeat_layout;
                    BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_repeat_layout);
                    if (brickLayout != null) {
                        i = R.id.brick_repeat_time_text_view;
                        TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_repeat_time_text_view);
                        if (textView3 != null) {
                            return new BrickRepeatBinding((LinearLayout) rootView, checkBox, textView, textView2, brickLayout, textView3);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

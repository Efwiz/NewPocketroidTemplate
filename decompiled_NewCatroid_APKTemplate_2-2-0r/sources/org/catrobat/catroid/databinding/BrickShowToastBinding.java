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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickShowToastBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickShowToastBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickShowToastEditText;
    public final TextView brickShowToastLabel;
    public final BrickLayout brickShowToastLayout;
    public final TextView brickShowToastTextview;
    private final LinearLayout rootView;

    private BrickShowToastBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickShowToastEditText, TextView brickShowToastLabel, BrickLayout brickShowToastLayout, TextView brickShowToastTextview) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickShowToastEditText = brickShowToastEditText;
        this.brickShowToastLabel = brickShowToastLabel;
        this.brickShowToastLayout = brickShowToastLayout;
        this.brickShowToastTextview = brickShowToastTextview;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickShowToastBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickShowToastBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_show_toast, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickShowToastBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_show_toast_edit_text;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_toast_edit_text);
            if (textView != null) {
                i = R.id.brick_show_toast_label;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_toast_label);
                if (textView2 != null) {
                    i = R.id.brick_show_toast_layout;
                    BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_show_toast_layout);
                    if (brickLayout != null) {
                        i = R.id.brick_show_toast_textview;
                        TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_toast_textview);
                        if (textView3 != null) {
                            return new BrickShowToastBinding((LinearLayout) rootView, checkBox, textView, textView2, brickLayout, textView3);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

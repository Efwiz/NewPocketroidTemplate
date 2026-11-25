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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickOpenFileBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickOpenFileBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickOpenFileEditText;
    public final TextView brickOpenFileLabel;
    public final BrickLayout brickOpenFileLayout;
    private final LinearLayout rootView;

    private BrickOpenFileBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickOpenFileEditText, TextView brickOpenFileLabel, BrickLayout brickOpenFileLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickOpenFileEditText = brickOpenFileEditText;
        this.brickOpenFileLabel = brickOpenFileLabel;
        this.brickOpenFileLayout = brickOpenFileLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickOpenFileBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickOpenFileBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_open_file, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickOpenFileBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_open_file_edit_text;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_open_file_edit_text);
            if (textView != null) {
                i = R.id.brick_open_file_label;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_open_file_label);
                if (textView2 != null) {
                    i = R.id.brick_open_file_layout;
                    BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_open_file_layout);
                    if (brickLayout != null) {
                        return new BrickOpenFileBinding((LinearLayout) rootView, checkBox, textView, textView2, brickLayout);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

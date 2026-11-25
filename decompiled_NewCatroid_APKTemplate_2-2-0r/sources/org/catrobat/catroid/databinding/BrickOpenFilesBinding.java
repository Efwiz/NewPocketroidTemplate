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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickOpenFilesBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickOpenFilesBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickOpenFilesEditText;
    public final TextView brickOpenFilesLabel;
    public final BrickLayout brickOpenFilesLayout;
    private final LinearLayout rootView;

    private BrickOpenFilesBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickOpenFilesEditText, TextView brickOpenFilesLabel, BrickLayout brickOpenFilesLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickOpenFilesEditText = brickOpenFilesEditText;
        this.brickOpenFilesLabel = brickOpenFilesLabel;
        this.brickOpenFilesLayout = brickOpenFilesLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickOpenFilesBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickOpenFilesBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_open_files, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickOpenFilesBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_open_files_edit_text;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_open_files_edit_text);
            if (textView != null) {
                i = R.id.brick_open_files_label;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_open_files_label);
                if (textView2 != null) {
                    i = R.id.brick_open_files_layout;
                    BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_open_files_layout);
                    if (brickLayout != null) {
                        return new BrickOpenFilesBinding((LinearLayout) rootView, checkBox, textView, textView2, brickLayout);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

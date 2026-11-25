package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ui.BrickLayout;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickWriteToFilesBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickWriteToFilesBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickWriteFilesEditText;
    public final BrickLayout brickWriteToFileLayout;
    private final LinearLayout rootView;
    public final Spinner writeFilesSpinner;

    private BrickWriteToFilesBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickWriteFilesEditText, BrickLayout brickWriteToFileLayout, Spinner writeFilesSpinner) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickWriteFilesEditText = brickWriteFilesEditText;
        this.brickWriteToFileLayout = brickWriteToFileLayout;
        this.writeFilesSpinner = writeFilesSpinner;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickWriteToFilesBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickWriteToFilesBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_write_to_files, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickWriteToFilesBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_write_files_edit_text;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_write_files_edit_text);
            if (textView != null) {
                i = R.id.brick_write_to_file_layout;
                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_write_to_file_layout);
                if (brickLayout != null) {
                    i = R.id.write_files_spinner;
                    Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.write_files_spinner);
                    if (spinner != null) {
                        return new BrickWriteToFilesBinding((LinearLayout) rootView, checkBox, textView, brickLayout, spinner);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickReadFromFilesBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickReadFromFilesBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickReadFilesEditText;
    public final BrickLayout brickReadFromFileLayout;
    public final Spinner readFilesSpinner;
    private final LinearLayout rootView;

    private BrickReadFromFilesBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickReadFilesEditText, BrickLayout brickReadFromFileLayout, Spinner readFilesSpinner) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickReadFilesEditText = brickReadFilesEditText;
        this.brickReadFromFileLayout = brickReadFromFileLayout;
        this.readFilesSpinner = readFilesSpinner;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickReadFromFilesBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickReadFromFilesBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_read_from_files, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickReadFromFilesBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_read_files_edit_text;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_read_files_edit_text);
            if (textView != null) {
                i = R.id.brick_read_from_file_layout;
                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_read_from_file_layout);
                if (brickLayout != null) {
                    i = R.id.read_files_spinner;
                    Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.read_files_spinner);
                    if (spinner != null) {
                        return new BrickReadFromFilesBinding((LinearLayout) rootView, checkBox, textView, brickLayout, spinner);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

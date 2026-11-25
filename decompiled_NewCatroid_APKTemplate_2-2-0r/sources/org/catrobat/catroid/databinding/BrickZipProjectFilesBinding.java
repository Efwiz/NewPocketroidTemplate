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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickZipProjectFilesBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickZipProjectFilesBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickZipProjectFilesEditArchiveName;
    public final TextView brickZipProjectFilesEditFiles;
    public final BrickLayout brickZipProjectFilesLayout;
    private final LinearLayout rootView;

    private BrickZipProjectFilesBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickZipProjectFilesEditArchiveName, TextView brickZipProjectFilesEditFiles, BrickLayout brickZipProjectFilesLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickZipProjectFilesEditArchiveName = brickZipProjectFilesEditArchiveName;
        this.brickZipProjectFilesEditFiles = brickZipProjectFilesEditFiles;
        this.brickZipProjectFilesLayout = brickZipProjectFilesLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickZipProjectFilesBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickZipProjectFilesBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_zip_project_files, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickZipProjectFilesBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_zip_project_files_edit_archive_name;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_zip_project_files_edit_archive_name);
            if (textView != null) {
                i = R.id.brick_zip_project_files_edit_files;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_zip_project_files_edit_files);
                if (textView2 != null) {
                    i = R.id.brick_zip_project_files_layout;
                    BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_zip_project_files_layout);
                    if (brickLayout != null) {
                        return new BrickZipProjectFilesBinding((LinearLayout) rootView, checkBox, textView, textView2, brickLayout);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

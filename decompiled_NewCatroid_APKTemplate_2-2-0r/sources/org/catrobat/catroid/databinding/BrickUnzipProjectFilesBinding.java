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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickUnzipProjectFilesBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickUnzipProjectFilesBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickUnzipProjectFilesEditArchiveName;
    public final BrickLayout brickUnzipProjectFilesLayout;
    private final LinearLayout rootView;

    private BrickUnzipProjectFilesBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickUnzipProjectFilesEditArchiveName, BrickLayout brickUnzipProjectFilesLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickUnzipProjectFilesEditArchiveName = brickUnzipProjectFilesEditArchiveName;
        this.brickUnzipProjectFilesLayout = brickUnzipProjectFilesLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickUnzipProjectFilesBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickUnzipProjectFilesBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_unzip_project_files, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickUnzipProjectFilesBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_unzip_project_files_edit_archive_name;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_unzip_project_files_edit_archive_name);
            if (textView != null) {
                i = R.id.brick_unzip_project_files_layout;
                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_unzip_project_files_layout);
                if (brickLayout != null) {
                    return new BrickUnzipProjectFilesBinding((LinearLayout) rootView, checkBox, textView, brickLayout);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

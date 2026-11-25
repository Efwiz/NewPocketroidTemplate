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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickUploadFileBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickUploadFileBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final BrickLayout brickUploadFileLayout;
    public final TextView brickUploadFilePathEdit;
    public final Spinner brickUploadFileTypeSpinner;
    public final TextView brickUploadMimeTypeEdit;
    public final Spinner brickUploadStorageTypeSpinner;
    public final TextView brickUploadUrlEdit;
    private final LinearLayout rootView;

    private BrickUploadFileBinding(LinearLayout rootView, CheckBox brickCheckbox, BrickLayout brickUploadFileLayout, TextView brickUploadFilePathEdit, Spinner brickUploadFileTypeSpinner, TextView brickUploadMimeTypeEdit, Spinner brickUploadStorageTypeSpinner, TextView brickUploadUrlEdit) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickUploadFileLayout = brickUploadFileLayout;
        this.brickUploadFilePathEdit = brickUploadFilePathEdit;
        this.brickUploadFileTypeSpinner = brickUploadFileTypeSpinner;
        this.brickUploadMimeTypeEdit = brickUploadMimeTypeEdit;
        this.brickUploadStorageTypeSpinner = brickUploadStorageTypeSpinner;
        this.brickUploadUrlEdit = brickUploadUrlEdit;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickUploadFileBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickUploadFileBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_upload_file, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickUploadFileBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_upload_file_layout;
            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_upload_file_layout);
            if (brickLayout != null) {
                i = R.id.brick_upload_file_path_edit;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_upload_file_path_edit);
                if (textView != null) {
                    i = R.id.brick_upload_file_type_spinner;
                    Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.brick_upload_file_type_spinner);
                    if (spinner != null) {
                        i = R.id.brick_upload_mime_type_edit;
                        TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_upload_mime_type_edit);
                        if (textView2 != null) {
                            i = R.id.brick_upload_storage_type_spinner;
                            Spinner spinner2 = (Spinner) ViewBindings.findChildViewById(rootView, R.id.brick_upload_storage_type_spinner);
                            if (spinner2 != null) {
                                i = R.id.brick_upload_url_edit;
                                TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_upload_url_edit);
                                if (textView3 != null) {
                                    return new BrickUploadFileBinding((LinearLayout) rootView, checkBox, brickLayout, textView, spinner, textView2, spinner2, textView3);
                                }
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickZipBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickZipBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickShowToastLabel;
    public final TextView brickShowToastLabel2;
    public final TextView brickZipEditFiles;
    public final TextView brickZipEditName;
    public final BrickLayout brickZipLayout;
    private final LinearLayout rootView;

    private BrickZipBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickShowToastLabel, TextView brickShowToastLabel2, TextView brickZipEditFiles, TextView brickZipEditName, BrickLayout brickZipLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickShowToastLabel = brickShowToastLabel;
        this.brickShowToastLabel2 = brickShowToastLabel2;
        this.brickZipEditFiles = brickZipEditFiles;
        this.brickZipEditName = brickZipEditName;
        this.brickZipLayout = brickZipLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickZipBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickZipBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_zip, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickZipBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_show_toast_label;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_toast_label);
            if (textView != null) {
                i = R.id.brick_show_toast_label2;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_show_toast_label2);
                if (textView2 != null) {
                    i = R.id.brick_zip_edit_files;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_zip_edit_files);
                    if (textView3 != null) {
                        i = R.id.brick_zip_edit_name;
                        TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_zip_edit_name);
                        if (textView4 != null) {
                            i = R.id.brick_zip_layout;
                            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_zip_layout);
                            if (brickLayout != null) {
                                return new BrickZipBinding((LinearLayout) rootView, checkBox, textView, textView2, textView3, textView4, brickLayout);
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

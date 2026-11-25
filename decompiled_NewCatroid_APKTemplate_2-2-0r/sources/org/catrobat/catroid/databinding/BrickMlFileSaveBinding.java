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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickMlFileSaveBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickMlFileSaveBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickMlEditFilename;
    public final BrickLayout brickMlFileLayout;
    private final LinearLayout rootView;

    private BrickMlFileSaveBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickMlEditFilename, BrickLayout brickMlFileLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickMlEditFilename = brickMlEditFilename;
        this.brickMlFileLayout = brickMlFileLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickMlFileSaveBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickMlFileSaveBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_ml_file_save, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickMlFileSaveBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_ml_edit_filename;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_ml_edit_filename);
            if (textView != null) {
                i = R.id.brick_ml_file_layout;
                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_ml_file_layout);
                if (brickLayout != null) {
                    return new BrickMlFileSaveBinding((LinearLayout) rootView, checkBox, textView, brickLayout);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

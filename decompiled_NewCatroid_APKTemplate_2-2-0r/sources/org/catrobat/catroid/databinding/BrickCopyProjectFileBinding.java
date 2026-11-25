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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickCopyProjectFileBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickCopyProjectFileBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickCopyFileDestinationEditText;
    public final BrickLayout brickCopyFileLayout;
    public final TextView brickCopyFileSourceEditText;
    private final LinearLayout rootView;

    private BrickCopyProjectFileBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickCopyFileDestinationEditText, BrickLayout brickCopyFileLayout, TextView brickCopyFileSourceEditText) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickCopyFileDestinationEditText = brickCopyFileDestinationEditText;
        this.brickCopyFileLayout = brickCopyFileLayout;
        this.brickCopyFileSourceEditText = brickCopyFileSourceEditText;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickCopyProjectFileBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickCopyProjectFileBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_copy_project_file, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickCopyProjectFileBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_copy_file_destination_edit_text;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_copy_file_destination_edit_text);
            if (textView != null) {
                i = R.id.brick_copy_file_layout;
                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_copy_file_layout);
                if (brickLayout != null) {
                    i = R.id.brick_copy_file_source_edit_text;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_copy_file_source_edit_text);
                    if (textView2 != null) {
                        return new BrickCopyProjectFileBinding((LinearLayout) rootView, checkBox, textView, brickLayout, textView2);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

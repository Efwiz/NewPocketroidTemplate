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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSaveBufferBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSaveBufferBinding implements ViewBinding {
    public final TextView brickBufferFile;
    public final TextView brickBufferName;
    public final CheckBox brickCheckbox;
    public final BrickLayout brickSaveBufferLayout;
    private final LinearLayout rootView;

    private BrickSaveBufferBinding(LinearLayout rootView, TextView brickBufferFile, TextView brickBufferName, CheckBox brickCheckbox, BrickLayout brickSaveBufferLayout) {
        this.rootView = rootView;
        this.brickBufferFile = brickBufferFile;
        this.brickBufferName = brickBufferName;
        this.brickCheckbox = brickCheckbox;
        this.brickSaveBufferLayout = brickSaveBufferLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSaveBufferBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSaveBufferBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_save_buffer, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSaveBufferBinding bind(View rootView) {
        int i = R.id.brick_buffer_file;
        TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_buffer_file);
        if (textView != null) {
            i = R.id.brick_buffer_name;
            TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_buffer_name);
            if (textView2 != null) {
                i = R.id.brick_checkbox;
                CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
                if (checkBox != null) {
                    i = R.id.brick_save_buffer_layout;
                    BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_save_buffer_layout);
                    if (brickLayout != null) {
                        return new BrickSaveBufferBinding((LinearLayout) rootView, textView, textView2, checkBox, brickLayout);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

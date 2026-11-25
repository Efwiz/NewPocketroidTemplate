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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickRunChip8Binding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickRunChip8Binding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickChip8EditFile;
    public final BrickLayout brickRunChip8Layout;
    private final LinearLayout rootView;

    private BrickRunChip8Binding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickChip8EditFile, BrickLayout brickRunChip8Layout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickChip8EditFile = brickChip8EditFile;
        this.brickRunChip8Layout = brickRunChip8Layout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickRunChip8Binding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickRunChip8Binding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_run_chip8, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickRunChip8Binding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_chip8_edit_file;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_chip8_edit_file);
            if (textView != null) {
                i = R.id.brick_run_chip8_layout;
                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_run_chip8_layout);
                if (brickLayout != null) {
                    return new BrickRunChip8Binding((LinearLayout) rootView, checkBox, textView, brickLayout);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

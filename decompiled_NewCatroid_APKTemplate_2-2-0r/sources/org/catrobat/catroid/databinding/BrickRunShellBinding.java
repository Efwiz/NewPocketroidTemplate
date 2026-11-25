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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickRunShellBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickRunShellBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickRunShellEditText;
    public final BrickLayout brickRunShellLayout;
    private final LinearLayout rootView;
    public final Spinner runShellSpinner;

    private BrickRunShellBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickRunShellEditText, BrickLayout brickRunShellLayout, Spinner runShellSpinner) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickRunShellEditText = brickRunShellEditText;
        this.brickRunShellLayout = brickRunShellLayout;
        this.runShellSpinner = runShellSpinner;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickRunShellBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickRunShellBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_run_shell, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickRunShellBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_run_shell_edit_text;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_run_shell_edit_text);
            if (textView != null) {
                i = R.id.brick_run_shell_layout;
                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_run_shell_layout);
                if (brickLayout != null) {
                    i = R.id.run_shell_spinner;
                    Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.run_shell_spinner);
                    if (spinner != null) {
                        return new BrickRunShellBinding((LinearLayout) rootView, checkBox, textView, brickLayout, spinner);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

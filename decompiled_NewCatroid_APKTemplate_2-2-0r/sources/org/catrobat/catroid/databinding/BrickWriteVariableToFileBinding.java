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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickWriteVariableToFileBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickWriteVariableToFileBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickWriteVariableToFileBottom;
    public final TextView brickWriteVariableToFileEditText;
    public final BrickLayout brickWriteVariableToFileLayout;
    public final Spinner brickWriteVariableToFileSpinner;
    public final TextView brickWriteVariableToFileTop;
    private final LinearLayout rootView;

    private BrickWriteVariableToFileBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickWriteVariableToFileBottom, TextView brickWriteVariableToFileEditText, BrickLayout brickWriteVariableToFileLayout, Spinner brickWriteVariableToFileSpinner, TextView brickWriteVariableToFileTop) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickWriteVariableToFileBottom = brickWriteVariableToFileBottom;
        this.brickWriteVariableToFileEditText = brickWriteVariableToFileEditText;
        this.brickWriteVariableToFileLayout = brickWriteVariableToFileLayout;
        this.brickWriteVariableToFileSpinner = brickWriteVariableToFileSpinner;
        this.brickWriteVariableToFileTop = brickWriteVariableToFileTop;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickWriteVariableToFileBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickWriteVariableToFileBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_write_variable_to_file, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickWriteVariableToFileBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_write_variable_to_file_bottom;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_write_variable_to_file_bottom);
            if (textView != null) {
                i = R.id.brick_write_variable_to_file_edit_text;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_write_variable_to_file_edit_text);
                if (textView2 != null) {
                    i = R.id.brick_write_variable_to_file_layout;
                    BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_write_variable_to_file_layout);
                    if (brickLayout != null) {
                        i = R.id.brick_write_variable_to_file_spinner;
                        Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.brick_write_variable_to_file_spinner);
                        if (spinner != null) {
                            i = R.id.brick_write_variable_to_file_top;
                            TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_write_variable_to_file_top);
                            if (textView3 != null) {
                                return new BrickWriteVariableToFileBinding((LinearLayout) rootView, checkBox, textView, textView2, brickLayout, spinner, textView3);
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

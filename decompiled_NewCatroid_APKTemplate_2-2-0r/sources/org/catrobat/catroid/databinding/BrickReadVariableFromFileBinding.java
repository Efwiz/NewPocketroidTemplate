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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickReadVariableFromFileBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickReadVariableFromFileBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickReadVariableFromFileBottom;
    public final TextView brickReadVariableFromFileCenter;
    public final TextView brickReadVariableFromFileEditText;
    public final Spinner brickReadVariableFromFileSpinnerMode;
    public final Spinner brickReadVariableFromFileSpinnerVariable;
    public final TextView brickReadVariableFromFileTop;
    public final BrickLayout brickWriteVariableToFileLayout;
    private final LinearLayout rootView;

    private BrickReadVariableFromFileBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickReadVariableFromFileBottom, TextView brickReadVariableFromFileCenter, TextView brickReadVariableFromFileEditText, Spinner brickReadVariableFromFileSpinnerMode, Spinner brickReadVariableFromFileSpinnerVariable, TextView brickReadVariableFromFileTop, BrickLayout brickWriteVariableToFileLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickReadVariableFromFileBottom = brickReadVariableFromFileBottom;
        this.brickReadVariableFromFileCenter = brickReadVariableFromFileCenter;
        this.brickReadVariableFromFileEditText = brickReadVariableFromFileEditText;
        this.brickReadVariableFromFileSpinnerMode = brickReadVariableFromFileSpinnerMode;
        this.brickReadVariableFromFileSpinnerVariable = brickReadVariableFromFileSpinnerVariable;
        this.brickReadVariableFromFileTop = brickReadVariableFromFileTop;
        this.brickWriteVariableToFileLayout = brickWriteVariableToFileLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickReadVariableFromFileBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickReadVariableFromFileBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_read_variable_from_file, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickReadVariableFromFileBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_read_variable_from_file_bottom;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_read_variable_from_file_bottom);
            if (textView != null) {
                i = R.id.brick_read_variable_from_file_center;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_read_variable_from_file_center);
                if (textView2 != null) {
                    i = R.id.brick_read_variable_from_file_edit_text;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_read_variable_from_file_edit_text);
                    if (textView3 != null) {
                        i = R.id.brick_read_variable_from_file_spinner_mode;
                        Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.brick_read_variable_from_file_spinner_mode);
                        if (spinner != null) {
                            i = R.id.brick_read_variable_from_file_spinner_variable;
                            Spinner spinner2 = (Spinner) ViewBindings.findChildViewById(rootView, R.id.brick_read_variable_from_file_spinner_variable);
                            if (spinner2 != null) {
                                i = R.id.brick_read_variable_from_file_top;
                                TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_read_variable_from_file_top);
                                if (textView4 != null) {
                                    i = R.id.brick_write_variable_to_file_layout;
                                    BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_write_variable_to_file_layout);
                                    if (brickLayout != null) {
                                        return new BrickReadVariableFromFileBinding((LinearLayout) rootView, checkBox, textView, textView2, textView3, spinner, spinner2, textView4, brickLayout);
                                    }
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

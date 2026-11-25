package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickCatchBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickCatchBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final Spinner catchErrorVariableSpinner;
    private final LinearLayout rootView;

    private BrickCatchBinding(LinearLayout rootView, CheckBox brickCheckbox, Spinner catchErrorVariableSpinner) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.catchErrorVariableSpinner = catchErrorVariableSpinner;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickCatchBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickCatchBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_catch, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickCatchBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.catch_error_variable_spinner;
            Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.catch_error_variable_spinner);
            if (spinner != null) {
                return new BrickCatchBinding((LinearLayout) rootView, checkBox, spinner);
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

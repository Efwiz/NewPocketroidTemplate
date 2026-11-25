package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ui.BrickLayout;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickPythonClearEnvironmentBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickPythonClearEnvironmentBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final BrickLayout brickPythonClearEnvironmentLayout;
    private final LinearLayout rootView;

    private BrickPythonClearEnvironmentBinding(LinearLayout rootView, CheckBox brickCheckbox, BrickLayout brickPythonClearEnvironmentLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickPythonClearEnvironmentLayout = brickPythonClearEnvironmentLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickPythonClearEnvironmentBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickPythonClearEnvironmentBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_python_clear_environment, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickPythonClearEnvironmentBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_python_clear_environment_layout;
            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_python_clear_environment_layout);
            if (brickLayout != null) {
                return new BrickPythonClearEnvironmentBinding((LinearLayout) rootView, checkBox, brickLayout);
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

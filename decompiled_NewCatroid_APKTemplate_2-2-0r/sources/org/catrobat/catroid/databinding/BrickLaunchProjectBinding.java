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

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickLaunchProjectBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickLaunchProjectBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickLaunchProjectName;
    private final LinearLayout rootView;

    private BrickLaunchProjectBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickLaunchProjectName) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickLaunchProjectName = brickLaunchProjectName;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickLaunchProjectBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickLaunchProjectBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_launch_project, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickLaunchProjectBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_launch_project_name;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_launch_project_name);
            if (textView != null) {
                return new BrickLaunchProjectBinding((LinearLayout) rootView, checkBox, textView);
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

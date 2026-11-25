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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSaveToInternalBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSaveToInternalBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickSaveToInternalPath;
    public final TextView brickSaveToInternalProjectFile;
    private final LinearLayout rootView;

    private BrickSaveToInternalBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickSaveToInternalPath, TextView brickSaveToInternalProjectFile) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickSaveToInternalPath = brickSaveToInternalPath;
        this.brickSaveToInternalProjectFile = brickSaveToInternalProjectFile;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSaveToInternalBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSaveToInternalBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_save_to_internal, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSaveToInternalBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_save_to_internal_path;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_save_to_internal_path);
            if (textView != null) {
                i = R.id.brick_save_to_internal_project_file;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_save_to_internal_project_file);
                if (textView2 != null) {
                    return new BrickSaveToInternalBinding((LinearLayout) rootView, checkBox, textView, textView2);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

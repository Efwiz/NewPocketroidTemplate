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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickReplace3dModelBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickReplace3dModelBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickReplace3dModelEditModelPath;
    public final TextView brickReplace3dModelEditObjectId;
    public final BrickLayout brickReplace3dModelLayout;
    private final LinearLayout rootView;

    private BrickReplace3dModelBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickReplace3dModelEditModelPath, TextView brickReplace3dModelEditObjectId, BrickLayout brickReplace3dModelLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickReplace3dModelEditModelPath = brickReplace3dModelEditModelPath;
        this.brickReplace3dModelEditObjectId = brickReplace3dModelEditObjectId;
        this.brickReplace3dModelLayout = brickReplace3dModelLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickReplace3dModelBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickReplace3dModelBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_replace_3d_model, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickReplace3dModelBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_replace_3d_model_edit_model_path;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_replace_3d_model_edit_model_path);
            if (textView != null) {
                i = R.id.brick_replace_3d_model_edit_object_id;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_replace_3d_model_edit_object_id);
                if (textView2 != null) {
                    i = R.id.brick_replace_3d_model_layout;
                    BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_replace_3d_model_layout);
                    if (brickLayout != null) {
                        return new BrickReplace3dModelBinding((LinearLayout) rootView, checkBox, textView, textView2, brickLayout);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

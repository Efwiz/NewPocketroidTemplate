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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickRemove3dObjectBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickRemove3dObjectBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickRemove3dObjectId;
    public final BrickLayout brickRemove3dObjectLayout;
    private final LinearLayout rootView;

    private BrickRemove3dObjectBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickRemove3dObjectId, BrickLayout brickRemove3dObjectLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickRemove3dObjectId = brickRemove3dObjectId;
        this.brickRemove3dObjectLayout = brickRemove3dObjectLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickRemove3dObjectBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickRemove3dObjectBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_remove_3d_object, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickRemove3dObjectBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_remove_3d_object_id;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_remove_3d_object_id);
            if (textView != null) {
                i = R.id.brick_remove_3d_object_layout;
                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_remove_3d_object_layout);
                if (brickLayout != null) {
                    return new BrickRemove3dObjectBinding((LinearLayout) rootView, checkBox, textView, brickLayout);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

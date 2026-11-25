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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickCreateSphereBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickCreateSphereBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickCreateSphereId;
    public final BrickLayout brickCreateSphereLayout;
    private final LinearLayout rootView;

    private BrickCreateSphereBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickCreateSphereId, BrickLayout brickCreateSphereLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickCreateSphereId = brickCreateSphereId;
        this.brickCreateSphereLayout = brickCreateSphereLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickCreateSphereBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickCreateSphereBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_create_sphere, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickCreateSphereBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_create_sphere_id;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_create_sphere_id);
            if (textView != null) {
                i = R.id.brick_create_sphere_layout;
                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_create_sphere_layout);
                if (brickLayout != null) {
                    return new BrickCreateSphereBinding((LinearLayout) rootView, checkBox, textView, brickLayout);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

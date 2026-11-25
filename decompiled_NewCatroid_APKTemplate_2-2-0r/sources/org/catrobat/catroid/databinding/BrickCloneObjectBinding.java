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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickCloneObjectBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickCloneObjectBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickCloneNewNameEdit;
    public final BrickLayout brickCloneObjectLayout;
    public final TextView brickCloneSourceEdit;
    private final LinearLayout rootView;

    private BrickCloneObjectBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickCloneNewNameEdit, BrickLayout brickCloneObjectLayout, TextView brickCloneSourceEdit) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickCloneNewNameEdit = brickCloneNewNameEdit;
        this.brickCloneObjectLayout = brickCloneObjectLayout;
        this.brickCloneSourceEdit = brickCloneSourceEdit;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickCloneObjectBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickCloneObjectBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_clone_object, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickCloneObjectBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_clone_new_name_edit;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_clone_new_name_edit);
            if (textView != null) {
                i = R.id.brick_clone_object_layout;
                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_clone_object_layout);
                if (brickLayout != null) {
                    i = R.id.brick_clone_source_edit;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_clone_source_edit);
                    if (textView2 != null) {
                        return new BrickCloneObjectBinding((LinearLayout) rootView, checkBox, textView, brickLayout, textView2);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

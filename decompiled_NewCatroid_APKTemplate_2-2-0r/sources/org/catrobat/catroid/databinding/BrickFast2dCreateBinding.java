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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickFast2dCreateBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickFast2dCreateBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickFast2dCreateEditId;
    public final BrickLayout brickFast2dCreateLayout;
    private final LinearLayout rootView;

    private BrickFast2dCreateBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickFast2dCreateEditId, BrickLayout brickFast2dCreateLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickFast2dCreateEditId = brickFast2dCreateEditId;
        this.brickFast2dCreateLayout = brickFast2dCreateLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickFast2dCreateBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickFast2dCreateBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_fast2d_create, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickFast2dCreateBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_fast2d_create_edit_id;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_fast2d_create_edit_id);
            if (textView != null) {
                i = R.id.brick_fast2d_create_layout;
                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_fast2d_create_layout);
                if (brickLayout != null) {
                    return new BrickFast2dCreateBinding((LinearLayout) rootView, checkBox, textView, brickLayout);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

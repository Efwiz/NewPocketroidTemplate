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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickPtReshapeBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickPtReshapeBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final BrickLayout brickPtReshapeLayout;
    public final TextView brickPtReshapeName;
    public final TextView brickPtReshapeShape;
    private final LinearLayout rootView;

    private BrickPtReshapeBinding(LinearLayout rootView, CheckBox brickCheckbox, BrickLayout brickPtReshapeLayout, TextView brickPtReshapeName, TextView brickPtReshapeShape) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickPtReshapeLayout = brickPtReshapeLayout;
        this.brickPtReshapeName = brickPtReshapeName;
        this.brickPtReshapeShape = brickPtReshapeShape;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickPtReshapeBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickPtReshapeBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_pt_reshape, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickPtReshapeBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_pt_reshape_layout;
            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_pt_reshape_layout);
            if (brickLayout != null) {
                i = R.id.brick_pt_reshape_name;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_pt_reshape_name);
                if (textView != null) {
                    i = R.id.brick_pt_reshape_shape;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_pt_reshape_shape);
                    if (textView2 != null) {
                        return new BrickPtReshapeBinding((LinearLayout) rootView, checkBox, brickLayout, textView, textView2);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

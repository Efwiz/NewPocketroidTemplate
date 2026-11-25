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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickPtCreateTensorBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickPtCreateTensorBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickPtCreateName;
    public final TextView brickPtCreateShape;
    public final BrickLayout brickPtCreateTensorLayout;
    public final TextView brickPtCreateTrainable;
    public final TextView brickPtCreateValue;
    private final LinearLayout rootView;

    private BrickPtCreateTensorBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickPtCreateName, TextView brickPtCreateShape, BrickLayout brickPtCreateTensorLayout, TextView brickPtCreateTrainable, TextView brickPtCreateValue) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickPtCreateName = brickPtCreateName;
        this.brickPtCreateShape = brickPtCreateShape;
        this.brickPtCreateTensorLayout = brickPtCreateTensorLayout;
        this.brickPtCreateTrainable = brickPtCreateTrainable;
        this.brickPtCreateValue = brickPtCreateValue;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickPtCreateTensorBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickPtCreateTensorBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_pt_create_tensor, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickPtCreateTensorBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_pt_create_name;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_pt_create_name);
            if (textView != null) {
                i = R.id.brick_pt_create_shape;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_pt_create_shape);
                if (textView2 != null) {
                    i = R.id.brick_pt_create_tensor_layout;
                    BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_pt_create_tensor_layout);
                    if (brickLayout != null) {
                        i = R.id.brick_pt_create_trainable;
                        TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_pt_create_trainable);
                        if (textView3 != null) {
                            i = R.id.brick_pt_create_value;
                            TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_pt_create_value);
                            if (textView4 != null) {
                                return new BrickPtCreateTensorBinding((LinearLayout) rootView, checkBox, textView, textView2, brickLayout, textView3, textView4);
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

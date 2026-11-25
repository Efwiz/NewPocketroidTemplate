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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickPtSetTensorBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickPtSetTensorBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickPtSetTensorData;
    public final BrickLayout brickPtSetTensorLayout;
    public final TextView brickPtSetTensorName;
    private final LinearLayout rootView;

    private BrickPtSetTensorBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickPtSetTensorData, BrickLayout brickPtSetTensorLayout, TextView brickPtSetTensorName) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickPtSetTensorData = brickPtSetTensorData;
        this.brickPtSetTensorLayout = brickPtSetTensorLayout;
        this.brickPtSetTensorName = brickPtSetTensorName;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickPtSetTensorBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickPtSetTensorBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_pt_set_tensor, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickPtSetTensorBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_pt_set_tensor_data;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_pt_set_tensor_data);
            if (textView != null) {
                i = R.id.brick_pt_set_tensor_layout;
                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_pt_set_tensor_layout);
                if (brickLayout != null) {
                    i = R.id.brick_pt_set_tensor_name;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_pt_set_tensor_name);
                    if (textView2 != null) {
                        return new BrickPtSetTensorBinding((LinearLayout) rootView, checkBox, textView, brickLayout, textView2);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

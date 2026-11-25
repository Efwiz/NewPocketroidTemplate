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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickFast2dSetScaleBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickFast2dSetScaleBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickFast2dSetScaleId;
    public final BrickLayout brickFast2dSetScaleLayout;
    public final TextView brickFast2dSetScaleX;
    public final TextView brickFast2dSetScaleY;
    private final LinearLayout rootView;

    private BrickFast2dSetScaleBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickFast2dSetScaleId, BrickLayout brickFast2dSetScaleLayout, TextView brickFast2dSetScaleX, TextView brickFast2dSetScaleY) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickFast2dSetScaleId = brickFast2dSetScaleId;
        this.brickFast2dSetScaleLayout = brickFast2dSetScaleLayout;
        this.brickFast2dSetScaleX = brickFast2dSetScaleX;
        this.brickFast2dSetScaleY = brickFast2dSetScaleY;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickFast2dSetScaleBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickFast2dSetScaleBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_fast2d_set_scale, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickFast2dSetScaleBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_fast2d_set_scale_id;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_fast2d_set_scale_id);
            if (textView != null) {
                i = R.id.brick_fast2d_set_scale_layout;
                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_fast2d_set_scale_layout);
                if (brickLayout != null) {
                    i = R.id.brick_fast2d_set_scale_x;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_fast2d_set_scale_x);
                    if (textView2 != null) {
                        i = R.id.brick_fast2d_set_scale_y;
                        TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_fast2d_set_scale_y);
                        if (textView3 != null) {
                            return new BrickFast2dSetScaleBinding((LinearLayout) rootView, checkBox, textView, brickLayout, textView2, textView3);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

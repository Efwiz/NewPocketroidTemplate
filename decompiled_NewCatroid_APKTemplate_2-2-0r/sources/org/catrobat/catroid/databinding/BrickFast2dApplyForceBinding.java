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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickFast2dApplyForceBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickFast2dApplyForceBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickFast2dForceId;
    public final BrickLayout brickFast2dForceLayout;
    public final TextView brickFast2dForceX;
    public final TextView brickFast2dForceY;
    private final LinearLayout rootView;

    private BrickFast2dApplyForceBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickFast2dForceId, BrickLayout brickFast2dForceLayout, TextView brickFast2dForceX, TextView brickFast2dForceY) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickFast2dForceId = brickFast2dForceId;
        this.brickFast2dForceLayout = brickFast2dForceLayout;
        this.brickFast2dForceX = brickFast2dForceX;
        this.brickFast2dForceY = brickFast2dForceY;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickFast2dApplyForceBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickFast2dApplyForceBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_fast2d_apply_force, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickFast2dApplyForceBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_fast2d_force_id;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_fast2d_force_id);
            if (textView != null) {
                i = R.id.brick_fast2d_force_layout;
                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_fast2d_force_layout);
                if (brickLayout != null) {
                    i = R.id.brick_fast2d_force_x;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_fast2d_force_x);
                    if (textView2 != null) {
                        i = R.id.brick_fast2d_force_y;
                        TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_fast2d_force_y);
                        if (textView3 != null) {
                            return new BrickFast2dApplyForceBinding((LinearLayout) rootView, checkBox, textView, brickLayout, textView2, textView3);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

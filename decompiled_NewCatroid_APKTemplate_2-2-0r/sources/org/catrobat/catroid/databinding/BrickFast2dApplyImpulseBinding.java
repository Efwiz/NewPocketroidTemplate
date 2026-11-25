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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickFast2dApplyImpulseBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickFast2dApplyImpulseBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickFast2dImpulseId;
    public final BrickLayout brickFast2dImpulseLayout;
    public final TextView brickFast2dImpulseX;
    public final TextView brickFast2dImpulseY;
    private final LinearLayout rootView;

    private BrickFast2dApplyImpulseBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickFast2dImpulseId, BrickLayout brickFast2dImpulseLayout, TextView brickFast2dImpulseX, TextView brickFast2dImpulseY) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickFast2dImpulseId = brickFast2dImpulseId;
        this.brickFast2dImpulseLayout = brickFast2dImpulseLayout;
        this.brickFast2dImpulseX = brickFast2dImpulseX;
        this.brickFast2dImpulseY = brickFast2dImpulseY;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickFast2dApplyImpulseBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickFast2dApplyImpulseBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_fast2d_apply_impulse, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickFast2dApplyImpulseBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_fast2d_impulse_id;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_fast2d_impulse_id);
            if (textView != null) {
                i = R.id.brick_fast2d_impulse_layout;
                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_fast2d_impulse_layout);
                if (brickLayout != null) {
                    i = R.id.brick_fast2d_impulse_x;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_fast2d_impulse_x);
                    if (textView2 != null) {
                        i = R.id.brick_fast2d_impulse_y;
                        TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_fast2d_impulse_y);
                        if (textView3 != null) {
                            return new BrickFast2dApplyImpulseBinding((LinearLayout) rootView, checkBox, textView, brickLayout, textView2, textView3);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickFast2dSetGravityBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickFast2dSetGravityBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final BrickLayout brickFast2dSetGravityLayout;
    public final TextView brickFast2dSetGravityX;
    public final TextView brickFast2dSetGravityY;
    private final LinearLayout rootView;

    private BrickFast2dSetGravityBinding(LinearLayout rootView, CheckBox brickCheckbox, BrickLayout brickFast2dSetGravityLayout, TextView brickFast2dSetGravityX, TextView brickFast2dSetGravityY) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickFast2dSetGravityLayout = brickFast2dSetGravityLayout;
        this.brickFast2dSetGravityX = brickFast2dSetGravityX;
        this.brickFast2dSetGravityY = brickFast2dSetGravityY;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickFast2dSetGravityBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickFast2dSetGravityBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_fast2d_set_gravity, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickFast2dSetGravityBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_fast2d_set_gravity_layout;
            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_fast2d_set_gravity_layout);
            if (brickLayout != null) {
                i = R.id.brick_fast2d_set_gravity_x;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_fast2d_set_gravity_x);
                if (textView != null) {
                    i = R.id.brick_fast2d_set_gravity_y;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_fast2d_set_gravity_y);
                    if (textView2 != null) {
                        return new BrickFast2dSetGravityBinding((LinearLayout) rootView, checkBox, brickLayout, textView, textView2);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

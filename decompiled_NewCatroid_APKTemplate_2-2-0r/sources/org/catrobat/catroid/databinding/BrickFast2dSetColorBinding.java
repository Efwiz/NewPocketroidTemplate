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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickFast2dSetColorBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickFast2dSetColorBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickFast2dColorA;
    public final TextView brickFast2dColorB;
    public final TextView brickFast2dColorG;
    public final TextView brickFast2dColorId;
    public final BrickLayout brickFast2dColorLayout;
    public final TextView brickFast2dColorR;
    private final LinearLayout rootView;

    private BrickFast2dSetColorBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickFast2dColorA, TextView brickFast2dColorB, TextView brickFast2dColorG, TextView brickFast2dColorId, BrickLayout brickFast2dColorLayout, TextView brickFast2dColorR) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickFast2dColorA = brickFast2dColorA;
        this.brickFast2dColorB = brickFast2dColorB;
        this.brickFast2dColorG = brickFast2dColorG;
        this.brickFast2dColorId = brickFast2dColorId;
        this.brickFast2dColorLayout = brickFast2dColorLayout;
        this.brickFast2dColorR = brickFast2dColorR;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickFast2dSetColorBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickFast2dSetColorBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_fast2d_set_color, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickFast2dSetColorBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_fast2d_color_a;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_fast2d_color_a);
            if (textView != null) {
                i = R.id.brick_fast2d_color_b;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_fast2d_color_b);
                if (textView2 != null) {
                    i = R.id.brick_fast2d_color_g;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_fast2d_color_g);
                    if (textView3 != null) {
                        i = R.id.brick_fast2d_color_id;
                        TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_fast2d_color_id);
                        if (textView4 != null) {
                            i = R.id.brick_fast2d_color_layout;
                            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_fast2d_color_layout);
                            if (brickLayout != null) {
                                i = R.id.brick_fast2d_color_r;
                                TextView textView5 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_fast2d_color_r);
                                if (textView5 != null) {
                                    return new BrickFast2dSetColorBinding((LinearLayout) rootView, checkBox, textView, textView2, textView3, textView4, brickLayout, textView5);
                                }
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickFast2dSetCollisionFilterBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickFast2dSetCollisionFilterBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickFast2dColFilterGroup;
    public final TextView brickFast2dColFilterId;
    public final BrickLayout brickFast2dColFilterLayout;
    public final TextView brickFast2dColFilterSensor;
    private final LinearLayout rootView;

    private BrickFast2dSetCollisionFilterBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickFast2dColFilterGroup, TextView brickFast2dColFilterId, BrickLayout brickFast2dColFilterLayout, TextView brickFast2dColFilterSensor) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickFast2dColFilterGroup = brickFast2dColFilterGroup;
        this.brickFast2dColFilterId = brickFast2dColFilterId;
        this.brickFast2dColFilterLayout = brickFast2dColFilterLayout;
        this.brickFast2dColFilterSensor = brickFast2dColFilterSensor;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickFast2dSetCollisionFilterBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickFast2dSetCollisionFilterBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_fast2d_set_collision_filter, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickFast2dSetCollisionFilterBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_fast2d_col_filter_group;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_fast2d_col_filter_group);
            if (textView != null) {
                i = R.id.brick_fast2d_col_filter_id;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_fast2d_col_filter_id);
                if (textView2 != null) {
                    i = R.id.brick_fast2d_col_filter_layout;
                    BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_fast2d_col_filter_layout);
                    if (brickLayout != null) {
                        i = R.id.brick_fast2d_col_filter_sensor;
                        TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_fast2d_col_filter_sensor);
                        if (textView3 != null) {
                            return new BrickFast2dSetCollisionFilterBinding((LinearLayout) rootView, checkBox, textView, textView2, brickLayout, textView3);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

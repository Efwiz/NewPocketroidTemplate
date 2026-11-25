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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSetFrictionBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSetFrictionBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickSetFrictionId;
    public final BrickLayout brickSetFrictionLayout;
    public final TextView brickSetFrictionValue;
    private final LinearLayout rootView;

    private BrickSetFrictionBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickSetFrictionId, BrickLayout brickSetFrictionLayout, TextView brickSetFrictionValue) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickSetFrictionId = brickSetFrictionId;
        this.brickSetFrictionLayout = brickSetFrictionLayout;
        this.brickSetFrictionValue = brickSetFrictionValue;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSetFrictionBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSetFrictionBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_set_friction, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSetFrictionBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_set_friction_id;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_friction_id);
            if (textView != null) {
                i = R.id.brick_set_friction_layout;
                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_set_friction_layout);
                if (brickLayout != null) {
                    i = R.id.brick_set_friction_value;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_friction_value);
                    if (textView2 != null) {
                        return new BrickSetFrictionBinding((LinearLayout) rootView, checkBox, textView, brickLayout, textView2);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

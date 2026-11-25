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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSetRestitutionBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSetRestitutionBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickSetRestitutionId;
    public final BrickLayout brickSetRestitutionLayout;
    public final TextView brickSetRestitutionValue;
    private final LinearLayout rootView;

    private BrickSetRestitutionBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickSetRestitutionId, BrickLayout brickSetRestitutionLayout, TextView brickSetRestitutionValue) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickSetRestitutionId = brickSetRestitutionId;
        this.brickSetRestitutionLayout = brickSetRestitutionLayout;
        this.brickSetRestitutionValue = brickSetRestitutionValue;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSetRestitutionBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSetRestitutionBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_set_restitution, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSetRestitutionBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_set_restitution_id;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_restitution_id);
            if (textView != null) {
                i = R.id.brick_set_restitution_layout;
                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_set_restitution_layout);
                if (brickLayout != null) {
                    i = R.id.brick_set_restitution_value;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_restitution_value);
                    if (textView2 != null) {
                        return new BrickSetRestitutionBinding((LinearLayout) rootView, checkBox, textView, brickLayout, textView2);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

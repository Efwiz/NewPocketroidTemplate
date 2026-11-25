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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickGoToFrontBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickGoToFrontBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickGoToFrontLabel;
    public final BrickLayout brickGoToFrontLayout;
    private final LinearLayout rootView;

    private BrickGoToFrontBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickGoToFrontLabel, BrickLayout brickGoToFrontLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickGoToFrontLabel = brickGoToFrontLabel;
        this.brickGoToFrontLayout = brickGoToFrontLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickGoToFrontBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickGoToFrontBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_go_to_front, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickGoToFrontBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_go_to_front_label;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_go_to_front_label);
            if (textView != null) {
                i = R.id.brick_go_to_front_layout;
                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_go_to_front_layout);
                if (brickLayout != null) {
                    return new BrickGoToFrontBinding((LinearLayout) rootView, checkBox, textView, brickLayout);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

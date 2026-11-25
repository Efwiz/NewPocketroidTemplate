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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickIfEndIfBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickIfEndIfBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickIfEndIfLabel;
    public final BrickLayout brickIfEndIfLayout;
    private final LinearLayout rootView;

    private BrickIfEndIfBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickIfEndIfLabel, BrickLayout brickIfEndIfLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickIfEndIfLabel = brickIfEndIfLabel;
        this.brickIfEndIfLayout = brickIfEndIfLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickIfEndIfBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickIfEndIfBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_if_end_if, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickIfEndIfBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_if_end_if_label;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_if_end_if_label);
            if (textView != null) {
                i = R.id.brick_if_end_if_layout;
                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_if_end_if_layout);
                if (brickLayout != null) {
                    return new BrickIfEndIfBinding((LinearLayout) rootView, checkBox, textView, brickLayout);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

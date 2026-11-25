package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ui.BrickLayout;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSetWebBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSetWebBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickSetWebName;
    public final TextView brickSetWidthLabel;
    public final BrickLayout brickSetWidthLayout;
    public final TextView brickSetWidthPercent;
    private final LinearLayout rootView;
    public final Spinner setWebSpinner;

    private BrickSetWebBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickSetWebName, TextView brickSetWidthLabel, BrickLayout brickSetWidthLayout, TextView brickSetWidthPercent, Spinner setWebSpinner) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickSetWebName = brickSetWebName;
        this.brickSetWidthLabel = brickSetWidthLabel;
        this.brickSetWidthLayout = brickSetWidthLayout;
        this.brickSetWidthPercent = brickSetWidthPercent;
        this.setWebSpinner = setWebSpinner;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSetWebBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSetWebBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_set_web, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSetWebBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_set_web_name;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_web_name);
            if (textView != null) {
                i = R.id.brick_set_width_label;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_width_label);
                if (textView2 != null) {
                    i = R.id.brick_set_width_layout;
                    BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_set_width_layout);
                    if (brickLayout != null) {
                        i = R.id.brick_set_width_percent;
                        TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_width_percent);
                        if (textView3 != null) {
                            i = R.id.set_web_spinner;
                            Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.set_web_spinner);
                            if (spinner != null) {
                                return new BrickSetWebBinding((LinearLayout) rootView, checkBox, textView, textView2, brickLayout, textView3, spinner);
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

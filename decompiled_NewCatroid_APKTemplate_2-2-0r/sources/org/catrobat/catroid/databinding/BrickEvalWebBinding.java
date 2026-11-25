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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickEvalWebBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickEvalWebBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickEvalWebJs;
    public final TextView brickEvalWebName;
    public final TextView brickSetWidthLabel;
    public final BrickLayout brickSetWidthLayout;
    public final TextView brickSetWidthPercent;
    private final LinearLayout rootView;

    private BrickEvalWebBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickEvalWebJs, TextView brickEvalWebName, TextView brickSetWidthLabel, BrickLayout brickSetWidthLayout, TextView brickSetWidthPercent) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickEvalWebJs = brickEvalWebJs;
        this.brickEvalWebName = brickEvalWebName;
        this.brickSetWidthLabel = brickSetWidthLabel;
        this.brickSetWidthLayout = brickSetWidthLayout;
        this.brickSetWidthPercent = brickSetWidthPercent;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickEvalWebBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickEvalWebBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_eval_web, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickEvalWebBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_eval_web_js;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_eval_web_js);
            if (textView != null) {
                i = R.id.brick_eval_web_name;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_eval_web_name);
                if (textView2 != null) {
                    i = R.id.brick_set_width_label;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_width_label);
                    if (textView3 != null) {
                        i = R.id.brick_set_width_layout;
                        BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_set_width_layout);
                        if (brickLayout != null) {
                            i = R.id.brick_set_width_percent;
                            TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_width_percent);
                            if (textView4 != null) {
                                return new BrickEvalWebBinding((LinearLayout) rootView, checkBox, textView, textView2, textView3, brickLayout, textView4);
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

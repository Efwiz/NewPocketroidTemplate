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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSavePlotBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSavePlotBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickSavePlotEditText;
    public final BrickLayout brickSavePlotLayout;
    public final TextView brickSavePlotTop;
    private final LinearLayout rootView;

    private BrickSavePlotBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickSavePlotEditText, BrickLayout brickSavePlotLayout, TextView brickSavePlotTop) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickSavePlotEditText = brickSavePlotEditText;
        this.brickSavePlotLayout = brickSavePlotLayout;
        this.brickSavePlotTop = brickSavePlotTop;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSavePlotBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSavePlotBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_save_plot, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSavePlotBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_save_plot_edit_text;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_save_plot_edit_text);
            if (textView != null) {
                i = R.id.brick_save_plot_layout;
                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_save_plot_layout);
                if (brickLayout != null) {
                    i = R.id.brick_save_plot_top;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_save_plot_top);
                    if (textView2 != null) {
                        return new BrickSavePlotBinding((LinearLayout) rootView, checkBox, textView, brickLayout, textView2);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSetHeightBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSetHeightBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickSetHeightEditText;
    public final TextView brickSetHeightLabel;
    public final BrickLayout brickSetHeightLayout;
    public final TextView brickSetHeightPercent;
    private final LinearLayout rootView;

    private BrickSetHeightBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickSetHeightEditText, TextView brickSetHeightLabel, BrickLayout brickSetHeightLayout, TextView brickSetHeightPercent) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickSetHeightEditText = brickSetHeightEditText;
        this.brickSetHeightLabel = brickSetHeightLabel;
        this.brickSetHeightLayout = brickSetHeightLayout;
        this.brickSetHeightPercent = brickSetHeightPercent;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSetHeightBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSetHeightBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_set_height, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSetHeightBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_set_height_edit_text;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_height_edit_text);
            if (textView != null) {
                i = R.id.brick_set_height_label;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_height_label);
                if (textView2 != null) {
                    i = R.id.brick_set_height_layout;
                    BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_set_height_layout);
                    if (brickLayout != null) {
                        i = R.id.brick_set_height_percent;
                        TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_height_percent);
                        if (textView3 != null) {
                            return new BrickSetHeightBinding((LinearLayout) rootView, checkBox, textView, textView2, brickLayout, textView3);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

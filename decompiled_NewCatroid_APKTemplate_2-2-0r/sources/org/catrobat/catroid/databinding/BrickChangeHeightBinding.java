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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickChangeHeightBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickChangeHeightBinding implements ViewBinding {
    public final TextView brickChangeHeightEditText;
    public final TextView brickChangeHeightLabel;
    public final BrickLayout brickChangeHeightLayout;
    public final TextView brickChangeHeightPercent;
    public final CheckBox brickCheckbox;
    private final LinearLayout rootView;

    private BrickChangeHeightBinding(LinearLayout rootView, TextView brickChangeHeightEditText, TextView brickChangeHeightLabel, BrickLayout brickChangeHeightLayout, TextView brickChangeHeightPercent, CheckBox brickCheckbox) {
        this.rootView = rootView;
        this.brickChangeHeightEditText = brickChangeHeightEditText;
        this.brickChangeHeightLabel = brickChangeHeightLabel;
        this.brickChangeHeightLayout = brickChangeHeightLayout;
        this.brickChangeHeightPercent = brickChangeHeightPercent;
        this.brickCheckbox = brickCheckbox;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickChangeHeightBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickChangeHeightBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_change_height, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickChangeHeightBinding bind(View rootView) {
        int i = R.id.brick_change_height_edit_text;
        TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_change_height_edit_text);
        if (textView != null) {
            i = R.id.brick_change_height_label;
            TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_change_height_label);
            if (textView2 != null) {
                i = R.id.brick_change_height_layout;
                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_change_height_layout);
                if (brickLayout != null) {
                    i = R.id.brick_change_height_percent;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_change_height_percent);
                    if (textView3 != null) {
                        i = R.id.brick_checkbox;
                        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
                        if (checkBox != null) {
                            return new BrickChangeHeightBinding((LinearLayout) rootView, textView, textView2, brickLayout, textView3, checkBox);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

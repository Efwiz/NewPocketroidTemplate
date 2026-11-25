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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickPointInDirectionBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickPointInDirectionBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickPointInDirectionDegree;
    public final TextView brickPointInDirectionEditText;
    public final TextView brickPointInDirectionLabel;
    public final BrickLayout brickPointInDirectionLayout;
    private final LinearLayout rootView;

    private BrickPointInDirectionBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickPointInDirectionDegree, TextView brickPointInDirectionEditText, TextView brickPointInDirectionLabel, BrickLayout brickPointInDirectionLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickPointInDirectionDegree = brickPointInDirectionDegree;
        this.brickPointInDirectionEditText = brickPointInDirectionEditText;
        this.brickPointInDirectionLabel = brickPointInDirectionLabel;
        this.brickPointInDirectionLayout = brickPointInDirectionLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickPointInDirectionBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickPointInDirectionBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_point_in_direction, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickPointInDirectionBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_point_in_direction_degree;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_point_in_direction_degree);
            if (textView != null) {
                i = R.id.brick_point_in_direction_edit_text;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_point_in_direction_edit_text);
                if (textView2 != null) {
                    i = R.id.brick_point_in_direction_label;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_point_in_direction_label);
                    if (textView3 != null) {
                        i = R.id.brick_point_in_direction_layout;
                        BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_point_in_direction_layout);
                        if (brickLayout != null) {
                            return new BrickPointInDirectionBinding((LinearLayout) rootView, checkBox, textView, textView2, textView3, brickLayout);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

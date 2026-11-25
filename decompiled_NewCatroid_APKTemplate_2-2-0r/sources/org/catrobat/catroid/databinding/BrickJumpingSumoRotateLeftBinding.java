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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickJumpingSumoRotateLeftBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickJumpingSumoRotateLeftBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickJumpingSumoChangeLeftVariableEditText;
    public final TextView brickJumpingSumoRotateLeftLabel;
    public final BrickLayout brickJumpingSumoRotateLeftLayout;
    public final TextView brickJumpingSumoRotateLeftText;
    private final LinearLayout rootView;

    private BrickJumpingSumoRotateLeftBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickJumpingSumoChangeLeftVariableEditText, TextView brickJumpingSumoRotateLeftLabel, BrickLayout brickJumpingSumoRotateLeftLayout, TextView brickJumpingSumoRotateLeftText) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickJumpingSumoChangeLeftVariableEditText = brickJumpingSumoChangeLeftVariableEditText;
        this.brickJumpingSumoRotateLeftLabel = brickJumpingSumoRotateLeftLabel;
        this.brickJumpingSumoRotateLeftLayout = brickJumpingSumoRotateLeftLayout;
        this.brickJumpingSumoRotateLeftText = brickJumpingSumoRotateLeftText;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickJumpingSumoRotateLeftBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickJumpingSumoRotateLeftBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_jumping_sumo_rotate_left, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickJumpingSumoRotateLeftBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_jumping_sumo_change_left_variable_edit_text;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_jumping_sumo_change_left_variable_edit_text);
            if (textView != null) {
                i = R.id.brick_jumping_sumo_rotate_left_label;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_jumping_sumo_rotate_left_label);
                if (textView2 != null) {
                    i = R.id.brick_jumping_sumo_rotate_left_layout;
                    BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_jumping_sumo_rotate_left_layout);
                    if (brickLayout != null) {
                        i = R.id.brick_jumping_sumo_rotate_left_text;
                        TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_jumping_sumo_rotate_left_text);
                        if (textView3 != null) {
                            return new BrickJumpingSumoRotateLeftBinding((LinearLayout) rootView, checkBox, textView, textView2, brickLayout, textView3);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

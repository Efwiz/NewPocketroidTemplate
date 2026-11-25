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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickMoveNStepsBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickMoveNStepsBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickMoveNStepsEditText;
    public final TextView brickMoveNStepsLabel;
    public final BrickLayout brickMoveNStepsLayout;
    public final TextView brickMoveNStepsStepTextView;
    private final LinearLayout rootView;

    private BrickMoveNStepsBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickMoveNStepsEditText, TextView brickMoveNStepsLabel, BrickLayout brickMoveNStepsLayout, TextView brickMoveNStepsStepTextView) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickMoveNStepsEditText = brickMoveNStepsEditText;
        this.brickMoveNStepsLabel = brickMoveNStepsLabel;
        this.brickMoveNStepsLayout = brickMoveNStepsLayout;
        this.brickMoveNStepsStepTextView = brickMoveNStepsStepTextView;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickMoveNStepsBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickMoveNStepsBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_move_n_steps, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickMoveNStepsBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_move_n_steps_edit_text;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_move_n_steps_edit_text);
            if (textView != null) {
                i = R.id.brick_move_n_steps_label;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_move_n_steps_label);
                if (textView2 != null) {
                    i = R.id.brick_move_n_steps_layout;
                    BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_move_n_steps_layout);
                    if (brickLayout != null) {
                        i = R.id.brick_move_n_steps_step_text_view;
                        TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_move_n_steps_step_text_view);
                        if (textView3 != null) {
                            return new BrickMoveNStepsBinding((LinearLayout) rootView, checkBox, textView, textView2, brickLayout, textView3);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

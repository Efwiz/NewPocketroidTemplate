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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickTurnLeftBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickTurnLeftBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickTurnLeftDegreeTextView;
    public final TextView brickTurnLeftEditText;
    public final TextView brickTurnLeftLabel;
    public final BrickLayout brickTurnLeftLayout;
    private final LinearLayout rootView;

    private BrickTurnLeftBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickTurnLeftDegreeTextView, TextView brickTurnLeftEditText, TextView brickTurnLeftLabel, BrickLayout brickTurnLeftLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickTurnLeftDegreeTextView = brickTurnLeftDegreeTextView;
        this.brickTurnLeftEditText = brickTurnLeftEditText;
        this.brickTurnLeftLabel = brickTurnLeftLabel;
        this.brickTurnLeftLayout = brickTurnLeftLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickTurnLeftBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickTurnLeftBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_turn_left, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickTurnLeftBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_turn_left_degree_text_view;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_turn_left_degree_text_view);
            if (textView != null) {
                i = R.id.brick_turn_left_edit_text;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_turn_left_edit_text);
                if (textView2 != null) {
                    i = R.id.brick_turn_left_label;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_turn_left_label);
                    if (textView3 != null) {
                        i = R.id.brick_turn_left_layout;
                        BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_turn_left_layout);
                        if (brickLayout != null) {
                            return new BrickTurnLeftBinding((LinearLayout) rootView, checkBox, textView, textView2, textView3, brickLayout);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickTurnRightBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickTurnRightBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickTurnRightDegreeTextView;
    public final TextView brickTurnRightEditText;
    public final TextView brickTurnRightLabel;
    public final BrickLayout brickTurnRightLayout;
    private final LinearLayout rootView;

    private BrickTurnRightBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickTurnRightDegreeTextView, TextView brickTurnRightEditText, TextView brickTurnRightLabel, BrickLayout brickTurnRightLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickTurnRightDegreeTextView = brickTurnRightDegreeTextView;
        this.brickTurnRightEditText = brickTurnRightEditText;
        this.brickTurnRightLabel = brickTurnRightLabel;
        this.brickTurnRightLayout = brickTurnRightLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickTurnRightBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickTurnRightBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_turn_right, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickTurnRightBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_turn_right_degree_text_view;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_turn_right_degree_text_view);
            if (textView != null) {
                i = R.id.brick_turn_right_edit_text;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_turn_right_edit_text);
                if (textView2 != null) {
                    i = R.id.brick_turn_right_label;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_turn_right_label);
                    if (textView3 != null) {
                        i = R.id.brick_turn_right_layout;
                        BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_turn_right_layout);
                        if (brickLayout != null) {
                            return new BrickTurnRightBinding((LinearLayout) rootView, checkBox, textView, textView2, textView3, brickLayout);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

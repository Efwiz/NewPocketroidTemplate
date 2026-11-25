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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickThinkForBubbleBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickThinkForBubbleBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickForBubbleEditTextDuration;
    public final TextView brickForBubbleEditTextText;
    public final TextView brickSecondsLabel;
    public final BrickLayout brickThinkForBubbleLayout;
    private final LinearLayout rootView;

    private BrickThinkForBubbleBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickForBubbleEditTextDuration, TextView brickForBubbleEditTextText, TextView brickSecondsLabel, BrickLayout brickThinkForBubbleLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickForBubbleEditTextDuration = brickForBubbleEditTextDuration;
        this.brickForBubbleEditTextText = brickForBubbleEditTextText;
        this.brickSecondsLabel = brickSecondsLabel;
        this.brickThinkForBubbleLayout = brickThinkForBubbleLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickThinkForBubbleBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickThinkForBubbleBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_think_for_bubble, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickThinkForBubbleBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_for_bubble_edit_text_duration;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_for_bubble_edit_text_duration);
            if (textView != null) {
                i = R.id.brick_for_bubble_edit_text_text;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_for_bubble_edit_text_text);
                if (textView2 != null) {
                    i = R.id.brick_seconds_label;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_seconds_label);
                    if (textView3 != null) {
                        i = R.id.brick_think_for_bubble_layout;
                        BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_think_for_bubble_layout);
                        if (brickLayout != null) {
                            return new BrickThinkForBubbleBinding((LinearLayout) rootView, checkBox, textView, textView2, textView3, brickLayout);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

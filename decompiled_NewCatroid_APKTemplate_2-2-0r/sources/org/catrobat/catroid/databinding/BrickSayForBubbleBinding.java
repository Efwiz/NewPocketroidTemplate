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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSayForBubbleBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSayForBubbleBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickForBubbleEditTextDuration;
    public final TextView brickForBubbleEditTextText;
    public final BrickLayout brickSayForBubbleLayout;
    public final TextView brickSecondsLabel;
    private final LinearLayout rootView;

    private BrickSayForBubbleBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickForBubbleEditTextDuration, TextView brickForBubbleEditTextText, BrickLayout brickSayForBubbleLayout, TextView brickSecondsLabel) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickForBubbleEditTextDuration = brickForBubbleEditTextDuration;
        this.brickForBubbleEditTextText = brickForBubbleEditTextText;
        this.brickSayForBubbleLayout = brickSayForBubbleLayout;
        this.brickSecondsLabel = brickSecondsLabel;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSayForBubbleBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSayForBubbleBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_say_for_bubble, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSayForBubbleBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_for_bubble_edit_text_duration;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_for_bubble_edit_text_duration);
            if (textView != null) {
                i = R.id.brick_for_bubble_edit_text_text;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_for_bubble_edit_text_text);
                if (textView2 != null) {
                    i = R.id.brick_say_for_bubble_layout;
                    BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_say_for_bubble_layout);
                    if (brickLayout != null) {
                        i = R.id.brick_seconds_label;
                        TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_seconds_label);
                        if (textView3 != null) {
                            return new BrickSayForBubbleBinding((LinearLayout) rootView, checkBox, textView, textView2, brickLayout, textView3);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

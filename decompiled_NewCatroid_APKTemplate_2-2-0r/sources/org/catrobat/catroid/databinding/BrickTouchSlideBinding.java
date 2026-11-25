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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickTouchSlideBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickTouchSlideBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickSecondsLabel;
    public final TextView brickTapForEditDuration;
    public final BrickLayout brickTouchAndSlideLayout;
    public final TextView brickTouchSlideEditFromX;
    public final TextView brickTouchSlideEditFromY;
    public final TextView brickTouchSlideEditToX;
    public final TextView brickTouchSlideEditToY;
    private final LinearLayout rootView;

    private BrickTouchSlideBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickSecondsLabel, TextView brickTapForEditDuration, BrickLayout brickTouchAndSlideLayout, TextView brickTouchSlideEditFromX, TextView brickTouchSlideEditFromY, TextView brickTouchSlideEditToX, TextView brickTouchSlideEditToY) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickSecondsLabel = brickSecondsLabel;
        this.brickTapForEditDuration = brickTapForEditDuration;
        this.brickTouchAndSlideLayout = brickTouchAndSlideLayout;
        this.brickTouchSlideEditFromX = brickTouchSlideEditFromX;
        this.brickTouchSlideEditFromY = brickTouchSlideEditFromY;
        this.brickTouchSlideEditToX = brickTouchSlideEditToX;
        this.brickTouchSlideEditToY = brickTouchSlideEditToY;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickTouchSlideBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickTouchSlideBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_touch_slide, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickTouchSlideBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_seconds_label;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_seconds_label);
            if (textView != null) {
                i = R.id.brick_tap_for_edit_duration;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_tap_for_edit_duration);
                if (textView2 != null) {
                    i = R.id.brick_touch_and_slide_layout;
                    BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_touch_and_slide_layout);
                    if (brickLayout != null) {
                        i = R.id.brick_touch_slide_edit_from_x;
                        TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_touch_slide_edit_from_x);
                        if (textView3 != null) {
                            i = R.id.brick_touch_slide_edit_from_y;
                            TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_touch_slide_edit_from_y);
                            if (textView4 != null) {
                                i = R.id.brick_touch_slide_edit_to_x;
                                TextView textView5 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_touch_slide_edit_to_x);
                                if (textView5 != null) {
                                    i = R.id.brick_touch_slide_edit_to_y;
                                    TextView textView6 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_touch_slide_edit_to_y);
                                    if (textView6 != null) {
                                        return new BrickTouchSlideBinding((LinearLayout) rootView, checkBox, textView, textView2, brickLayout, textView3, textView4, textView5, textView6);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

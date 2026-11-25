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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickTapForBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickTapForBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickSecondsLabel;
    public final TextView brickTapForEditDuration;
    public final TextView brickTapForEditX;
    public final TextView brickTapForEditY;
    public final BrickLayout brickTapForLayout;
    private final LinearLayout rootView;

    private BrickTapForBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickSecondsLabel, TextView brickTapForEditDuration, TextView brickTapForEditX, TextView brickTapForEditY, BrickLayout brickTapForLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickSecondsLabel = brickSecondsLabel;
        this.brickTapForEditDuration = brickTapForEditDuration;
        this.brickTapForEditX = brickTapForEditX;
        this.brickTapForEditY = brickTapForEditY;
        this.brickTapForLayout = brickTapForLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickTapForBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickTapForBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_tap_for, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickTapForBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_seconds_label;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_seconds_label);
            if (textView != null) {
                i = R.id.brick_tap_for_edit_duration;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_tap_for_edit_duration);
                if (textView2 != null) {
                    i = R.id.brick_tap_for_edit_x;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_tap_for_edit_x);
                    if (textView3 != null) {
                        i = R.id.brick_tap_for_edit_y;
                        TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_tap_for_edit_y);
                        if (textView4 != null) {
                            i = R.id.brick_tap_for_layout;
                            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_tap_for_layout);
                            if (brickLayout != null) {
                                return new BrickTapForBinding((LinearLayout) rootView, checkBox, textView, textView2, textView3, textView4, brickLayout);
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

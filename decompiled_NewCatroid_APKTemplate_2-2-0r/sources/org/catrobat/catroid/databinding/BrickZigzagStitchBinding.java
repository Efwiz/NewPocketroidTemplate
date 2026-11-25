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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickZigzagStitchBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickZigzagStitchBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final BrickLayout brickStitchLayout;
    public final TextView brickZigzagStitchEditTextLength;
    public final TextView brickZigzagStitchEditTextWidth;
    private final LinearLayout rootView;

    private BrickZigzagStitchBinding(LinearLayout rootView, CheckBox brickCheckbox, BrickLayout brickStitchLayout, TextView brickZigzagStitchEditTextLength, TextView brickZigzagStitchEditTextWidth) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickStitchLayout = brickStitchLayout;
        this.brickZigzagStitchEditTextLength = brickZigzagStitchEditTextLength;
        this.brickZigzagStitchEditTextWidth = brickZigzagStitchEditTextWidth;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickZigzagStitchBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickZigzagStitchBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_zigzag_stitch, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickZigzagStitchBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_stitch_layout;
            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_stitch_layout);
            if (brickLayout != null) {
                i = R.id.brick_zigzag_stitch_edit_text_length;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_zigzag_stitch_edit_text_length);
                if (textView != null) {
                    i = R.id.brick_zigzag_stitch_edit_text_width;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_zigzag_stitch_edit_text_width);
                    if (textView2 != null) {
                        return new BrickZigzagStitchBinding((LinearLayout) rootView, checkBox, brickLayout, textView, textView2);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

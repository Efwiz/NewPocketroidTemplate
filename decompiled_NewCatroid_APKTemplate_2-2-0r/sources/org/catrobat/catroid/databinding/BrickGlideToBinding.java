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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickGlideToBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickGlideToBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickGlideToEditTextDuration;
    public final TextView brickGlideToEditTextX;
    public final TextView brickGlideToEditTextY;
    public final BrickLayout brickGlideToLayout;
    public final TextView brickGlideToX;
    public final TextView brickGlideToY;
    public final TextView brickSecondsLabel;
    private final LinearLayout rootView;

    private BrickGlideToBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickGlideToEditTextDuration, TextView brickGlideToEditTextX, TextView brickGlideToEditTextY, BrickLayout brickGlideToLayout, TextView brickGlideToX, TextView brickGlideToY, TextView brickSecondsLabel) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickGlideToEditTextDuration = brickGlideToEditTextDuration;
        this.brickGlideToEditTextX = brickGlideToEditTextX;
        this.brickGlideToEditTextY = brickGlideToEditTextY;
        this.brickGlideToLayout = brickGlideToLayout;
        this.brickGlideToX = brickGlideToX;
        this.brickGlideToY = brickGlideToY;
        this.brickSecondsLabel = brickSecondsLabel;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickGlideToBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickGlideToBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_glide_to, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickGlideToBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_glide_to_edit_text_duration;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_glide_to_edit_text_duration);
            if (textView != null) {
                i = R.id.brick_glide_to_edit_text_x;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_glide_to_edit_text_x);
                if (textView2 != null) {
                    i = R.id.brick_glide_to_edit_text_y;
                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_glide_to_edit_text_y);
                    if (textView3 != null) {
                        i = R.id.brick_glide_to_layout;
                        BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_glide_to_layout);
                        if (brickLayout != null) {
                            i = R.id.brick_glide_to_x;
                            TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_glide_to_x);
                            if (textView4 != null) {
                                i = R.id.brick_glide_to_y;
                                TextView textView5 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_glide_to_y);
                                if (textView5 != null) {
                                    i = R.id.brick_seconds_label;
                                    TextView textView6 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_seconds_label);
                                    if (textView6 != null) {
                                        return new BrickGlideToBinding((LinearLayout) rootView, checkBox, textView, textView2, textView3, brickLayout, textView4, textView5, textView6);
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

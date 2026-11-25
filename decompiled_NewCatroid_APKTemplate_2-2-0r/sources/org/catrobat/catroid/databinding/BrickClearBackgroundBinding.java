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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickClearBackgroundBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickClearBackgroundBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final BrickLayout brickClearBackgroundLayout;
    public final TextView brickClearBackgroundTextView;
    private final LinearLayout rootView;

    private BrickClearBackgroundBinding(LinearLayout rootView, CheckBox brickCheckbox, BrickLayout brickClearBackgroundLayout, TextView brickClearBackgroundTextView) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickClearBackgroundLayout = brickClearBackgroundLayout;
        this.brickClearBackgroundTextView = brickClearBackgroundTextView;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickClearBackgroundBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickClearBackgroundBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_clear_background, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickClearBackgroundBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_clear_background_layout;
            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_clear_background_layout);
            if (brickLayout != null) {
                i = R.id.brick_clear_background_text_view;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_clear_background_text_view);
                if (textView != null) {
                    return new BrickClearBackgroundBinding((LinearLayout) rootView, checkBox, brickLayout, textView);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickStampBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickStampBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final BrickLayout brickStampLayout;
    public final TextView brickStampTextView;
    private final LinearLayout rootView;

    private BrickStampBinding(LinearLayout rootView, CheckBox brickCheckbox, BrickLayout brickStampLayout, TextView brickStampTextView) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickStampLayout = brickStampLayout;
        this.brickStampTextView = brickStampTextView;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickStampBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickStampBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_stamp, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickStampBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_stamp_layout;
            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_stamp_layout);
            if (brickLayout != null) {
                i = R.id.brick_stamp_text_view;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_stamp_text_view);
                if (textView != null) {
                    return new BrickStampBinding((LinearLayout) rootView, checkBox, brickLayout, textView);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

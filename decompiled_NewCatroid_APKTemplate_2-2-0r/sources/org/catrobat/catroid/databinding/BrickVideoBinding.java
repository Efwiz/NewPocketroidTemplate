package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ui.BrickLayout;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickVideoBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickVideoBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final BrickLayout brickVideoLayout;
    public final TextView brickVideoPrototypeTextView;
    public final Spinner brickVideoSpinner;
    private final LinearLayout rootView;

    private BrickVideoBinding(LinearLayout rootView, CheckBox brickCheckbox, BrickLayout brickVideoLayout, TextView brickVideoPrototypeTextView, Spinner brickVideoSpinner) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickVideoLayout = brickVideoLayout;
        this.brickVideoPrototypeTextView = brickVideoPrototypeTextView;
        this.brickVideoSpinner = brickVideoSpinner;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickVideoBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickVideoBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_video, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickVideoBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_video_layout;
            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_video_layout);
            if (brickLayout != null) {
                i = R.id.brick_video_prototype_text_view;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_video_prototype_text_view);
                if (textView != null) {
                    i = R.id.brick_video_spinner;
                    Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.brick_video_spinner);
                    if (spinner != null) {
                        return new BrickVideoBinding((LinearLayout) rootView, checkBox, brickLayout, textView, spinner);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSpeakBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSpeakBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickSpeakEditText;
    public final TextView brickSpeakLabel;
    public final BrickLayout brickSpeakLayout;
    private final LinearLayout rootView;

    private BrickSpeakBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickSpeakEditText, TextView brickSpeakLabel, BrickLayout brickSpeakLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickSpeakEditText = brickSpeakEditText;
        this.brickSpeakLabel = brickSpeakLabel;
        this.brickSpeakLayout = brickSpeakLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSpeakBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSpeakBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_speak, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSpeakBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_speak_edit_text;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_speak_edit_text);
            if (textView != null) {
                i = R.id.brick_speak_label;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_speak_label);
                if (textView2 != null) {
                    i = R.id.brick_speak_layout;
                    BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_speak_layout);
                    if (brickLayout != null) {
                        return new BrickSpeakBinding((LinearLayout) rootView, checkBox, textView, textView2, brickLayout);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

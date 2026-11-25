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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickPhiroPlayToneBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickPhiroPlayToneBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickPhiroPlayToneDurationEditText;
    public final BrickLayout brickPhiroPlayToneLayout;
    public final Spinner brickPhiroSelectToneSpinner;
    public final TextView brickSecondsLabel;
    private final LinearLayout rootView;

    private BrickPhiroPlayToneBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickPhiroPlayToneDurationEditText, BrickLayout brickPhiroPlayToneLayout, Spinner brickPhiroSelectToneSpinner, TextView brickSecondsLabel) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickPhiroPlayToneDurationEditText = brickPhiroPlayToneDurationEditText;
        this.brickPhiroPlayToneLayout = brickPhiroPlayToneLayout;
        this.brickPhiroSelectToneSpinner = brickPhiroSelectToneSpinner;
        this.brickSecondsLabel = brickSecondsLabel;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickPhiroPlayToneBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickPhiroPlayToneBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_phiro_play_tone, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickPhiroPlayToneBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_phiro_play_tone_duration_edit_text;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_phiro_play_tone_duration_edit_text);
            if (textView != null) {
                i = R.id.brick_phiro_play_tone_layout;
                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_phiro_play_tone_layout);
                if (brickLayout != null) {
                    i = R.id.brick_phiro_select_tone_spinner;
                    Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.brick_phiro_select_tone_spinner);
                    if (spinner != null) {
                        i = R.id.brick_seconds_label;
                        TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_seconds_label);
                        if (textView2 != null) {
                            return new BrickPhiroPlayToneBinding((LinearLayout) rootView, checkBox, textView, brickLayout, spinner, textView2);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

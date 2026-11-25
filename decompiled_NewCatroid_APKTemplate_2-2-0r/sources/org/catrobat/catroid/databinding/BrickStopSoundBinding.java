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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickStopSoundBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickStopSoundBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final BrickLayout brickStopSoundLayout;
    public final Spinner brickStopSoundSpinner;
    public final TextView brickStopSoundTextView;
    private final LinearLayout rootView;

    private BrickStopSoundBinding(LinearLayout rootView, CheckBox brickCheckbox, BrickLayout brickStopSoundLayout, Spinner brickStopSoundSpinner, TextView brickStopSoundTextView) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickStopSoundLayout = brickStopSoundLayout;
        this.brickStopSoundSpinner = brickStopSoundSpinner;
        this.brickStopSoundTextView = brickStopSoundTextView;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickStopSoundBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickStopSoundBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_stop_sound, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickStopSoundBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_stop_sound_layout;
            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_stop_sound_layout);
            if (brickLayout != null) {
                i = R.id.brick_stop_sound_spinner;
                Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.brick_stop_sound_spinner);
                if (spinner != null) {
                    i = R.id.brick_stop_sound_text_view;
                    TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_stop_sound_text_view);
                    if (textView != null) {
                        return new BrickStopSoundBinding((LinearLayout) rootView, checkBox, brickLayout, spinner, textView);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

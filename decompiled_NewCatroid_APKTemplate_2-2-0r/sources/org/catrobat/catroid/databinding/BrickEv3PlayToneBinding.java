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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickEv3PlayToneBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickEv3PlayToneBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final BrickLayout brickEv3PlayToneLayout;
    public final TextView brickEv3ToneDurationEditText;
    public final TextView brickEv3ToneFreqEditText;
    public final TextView brickEv3ToneVolumeEditText;
    public final TextView brickSecondsLabel;
    private final LinearLayout rootView;

    private BrickEv3PlayToneBinding(LinearLayout rootView, CheckBox brickCheckbox, BrickLayout brickEv3PlayToneLayout, TextView brickEv3ToneDurationEditText, TextView brickEv3ToneFreqEditText, TextView brickEv3ToneVolumeEditText, TextView brickSecondsLabel) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickEv3PlayToneLayout = brickEv3PlayToneLayout;
        this.brickEv3ToneDurationEditText = brickEv3ToneDurationEditText;
        this.brickEv3ToneFreqEditText = brickEv3ToneFreqEditText;
        this.brickEv3ToneVolumeEditText = brickEv3ToneVolumeEditText;
        this.brickSecondsLabel = brickSecondsLabel;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickEv3PlayToneBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickEv3PlayToneBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_ev3_play_tone, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickEv3PlayToneBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_ev3_play_tone_layout;
            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_ev3_play_tone_layout);
            if (brickLayout != null) {
                i = R.id.brick_ev3_tone_duration_edit_text;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_ev3_tone_duration_edit_text);
                if (textView != null) {
                    i = R.id.brick_ev3_tone_freq_edit_text;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_ev3_tone_freq_edit_text);
                    if (textView2 != null) {
                        i = R.id.brick_ev3_tone_volume_edit_text;
                        TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_ev3_tone_volume_edit_text);
                        if (textView3 != null) {
                            i = R.id.brick_seconds_label;
                            TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_seconds_label);
                            if (textView4 != null) {
                                return new BrickEv3PlayToneBinding((LinearLayout) rootView, checkBox, brickLayout, textView, textView2, textView3, textView4);
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

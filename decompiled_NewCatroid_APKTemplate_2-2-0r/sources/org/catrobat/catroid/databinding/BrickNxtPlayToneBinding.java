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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickNxtPlayToneBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickNxtPlayToneBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final BrickLayout brickNxtPlayToneLayout;
    public final TextView brickSecondsLabel;
    public final TextView nxtToneDurationEditText;
    public final TextView nxtToneFreqEditText;
    private final LinearLayout rootView;

    private BrickNxtPlayToneBinding(LinearLayout rootView, CheckBox brickCheckbox, BrickLayout brickNxtPlayToneLayout, TextView brickSecondsLabel, TextView nxtToneDurationEditText, TextView nxtToneFreqEditText) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickNxtPlayToneLayout = brickNxtPlayToneLayout;
        this.brickSecondsLabel = brickSecondsLabel;
        this.nxtToneDurationEditText = nxtToneDurationEditText;
        this.nxtToneFreqEditText = nxtToneFreqEditText;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickNxtPlayToneBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickNxtPlayToneBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_nxt_play_tone, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickNxtPlayToneBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_nxt_play_tone_layout;
            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_nxt_play_tone_layout);
            if (brickLayout != null) {
                i = R.id.brick_seconds_label;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_seconds_label);
                if (textView != null) {
                    i = R.id.nxt_tone_duration_edit_text;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.nxt_tone_duration_edit_text);
                    if (textView2 != null) {
                        i = R.id.nxt_tone_freq_edit_text;
                        TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.nxt_tone_freq_edit_text);
                        if (textView3 != null) {
                            return new BrickNxtPlayToneBinding((LinearLayout) rootView, checkBox, brickLayout, textView, textView2, textView3);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

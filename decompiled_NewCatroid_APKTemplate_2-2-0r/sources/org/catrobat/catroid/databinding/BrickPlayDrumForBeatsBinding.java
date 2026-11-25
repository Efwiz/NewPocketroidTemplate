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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickPlayDrumForBeatsBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickPlayDrumForBeatsBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickPlayDrumForBeatsEditText;
    public final BrickLayout brickPlayDrumForBeatsLayout;
    public final Spinner playDrumForBeatsSpinner;
    private final LinearLayout rootView;

    private BrickPlayDrumForBeatsBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickPlayDrumForBeatsEditText, BrickLayout brickPlayDrumForBeatsLayout, Spinner playDrumForBeatsSpinner) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickPlayDrumForBeatsEditText = brickPlayDrumForBeatsEditText;
        this.brickPlayDrumForBeatsLayout = brickPlayDrumForBeatsLayout;
        this.playDrumForBeatsSpinner = playDrumForBeatsSpinner;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickPlayDrumForBeatsBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickPlayDrumForBeatsBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_play_drum_for_beats, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickPlayDrumForBeatsBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_play_drum_for_beats_edit_text;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_play_drum_for_beats_edit_text);
            if (textView != null) {
                i = R.id.brick_play_drum_for_beats_layout;
                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_play_drum_for_beats_layout);
                if (brickLayout != null) {
                    i = R.id.play_drum_for_beats_spinner;
                    Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.play_drum_for_beats_spinner);
                    if (spinner != null) {
                        return new BrickPlayDrumForBeatsBinding((LinearLayout) rootView, checkBox, textView, brickLayout, spinner);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

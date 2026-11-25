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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSetSoundVolumeBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSetSoundVolumeBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickSetSoundVolumeEditText;
    public final BrickLayout brickSetSoundVolumeLayout;
    public final Spinner brickSetSoundVolumeSpinner;
    private final LinearLayout rootView;

    private BrickSetSoundVolumeBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickSetSoundVolumeEditText, BrickLayout brickSetSoundVolumeLayout, Spinner brickSetSoundVolumeSpinner) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickSetSoundVolumeEditText = brickSetSoundVolumeEditText;
        this.brickSetSoundVolumeLayout = brickSetSoundVolumeLayout;
        this.brickSetSoundVolumeSpinner = brickSetSoundVolumeSpinner;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSetSoundVolumeBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSetSoundVolumeBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_set_sound_volume, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSetSoundVolumeBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_set_sound_volume_edit_text;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_set_sound_volume_edit_text);
            if (textView != null) {
                i = R.id.brick_set_sound_volume_layout;
                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_set_sound_volume_layout);
                if (brickLayout != null) {
                    i = R.id.brick_set_sound_volume_spinner;
                    Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.brick_set_sound_volume_spinner);
                    if (spinner != null) {
                        return new BrickSetSoundVolumeBinding((LinearLayout) rootView, checkBox, textView, brickLayout, spinner);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

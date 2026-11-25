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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickJumpingSumoSoundBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickJumpingSumoSoundBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickJumpingSumoSoundEditText;
    public final BrickLayout brickJumpingSumoSoundLayout;
    public final Spinner brickJumpingSumoSoundSpinner;
    private final LinearLayout rootView;

    private BrickJumpingSumoSoundBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickJumpingSumoSoundEditText, BrickLayout brickJumpingSumoSoundLayout, Spinner brickJumpingSumoSoundSpinner) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickJumpingSumoSoundEditText = brickJumpingSumoSoundEditText;
        this.brickJumpingSumoSoundLayout = brickJumpingSumoSoundLayout;
        this.brickJumpingSumoSoundSpinner = brickJumpingSumoSoundSpinner;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickJumpingSumoSoundBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickJumpingSumoSoundBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_jumping_sumo_sound, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickJumpingSumoSoundBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_jumping_sumo_sound_edit_text;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_jumping_sumo_sound_edit_text);
            if (textView != null) {
                i = R.id.brick_jumping_sumo_sound_layout;
                BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_jumping_sumo_sound_layout);
                if (brickLayout != null) {
                    i = R.id.brick_jumping_sumo_sound_spinner;
                    Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.brick_jumping_sumo_sound_spinner);
                    if (spinner != null) {
                        return new BrickJumpingSumoSoundBinding((LinearLayout) rootView, checkBox, textView, brickLayout, spinner);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

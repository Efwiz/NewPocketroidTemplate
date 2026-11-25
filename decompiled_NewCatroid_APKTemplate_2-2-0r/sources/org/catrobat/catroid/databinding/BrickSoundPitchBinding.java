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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSoundPitchBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSoundPitchBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final BrickLayout brickSoundPitchLayout;
    private final LinearLayout rootView;
    public final TextView soundInstNameP;
    public final TextView soundInstPitch;

    private BrickSoundPitchBinding(LinearLayout rootView, CheckBox brickCheckbox, BrickLayout brickSoundPitchLayout, TextView soundInstNameP, TextView soundInstPitch) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickSoundPitchLayout = brickSoundPitchLayout;
        this.soundInstNameP = soundInstNameP;
        this.soundInstPitch = soundInstPitch;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSoundPitchBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSoundPitchBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_sound_pitch, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSoundPitchBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_sound_pitch_layout;
            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_sound_pitch_layout);
            if (brickLayout != null) {
                i = R.id.sound_inst_name_p;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.sound_inst_name_p);
                if (textView != null) {
                    i = R.id.sound_inst_pitch;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.sound_inst_pitch);
                    if (textView2 != null) {
                        return new BrickSoundPitchBinding((LinearLayout) rootView, checkBox, brickLayout, textView, textView2);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

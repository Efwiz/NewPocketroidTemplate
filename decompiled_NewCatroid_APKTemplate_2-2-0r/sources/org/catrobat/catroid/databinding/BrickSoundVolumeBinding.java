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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickSoundVolumeBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickSoundVolumeBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final BrickLayout brickSoundVolumeLayout;
    private final LinearLayout rootView;
    public final TextView soundInstName;
    public final TextView soundInstVol;

    private BrickSoundVolumeBinding(LinearLayout rootView, CheckBox brickCheckbox, BrickLayout brickSoundVolumeLayout, TextView soundInstName, TextView soundInstVol) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickSoundVolumeLayout = brickSoundVolumeLayout;
        this.soundInstName = soundInstName;
        this.soundInstVol = soundInstVol;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickSoundVolumeBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickSoundVolumeBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_sound_volume, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickSoundVolumeBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_sound_volume_layout;
            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_sound_volume_layout);
            if (brickLayout != null) {
                i = R.id.sound_inst_name;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.sound_inst_name);
                if (textView != null) {
                    i = R.id.sound_inst_vol;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.sound_inst_vol);
                    if (textView2 != null) {
                        return new BrickSoundVolumeBinding((LinearLayout) rootView, checkBox, brickLayout, textView, textView2);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

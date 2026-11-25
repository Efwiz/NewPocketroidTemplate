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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickPlaySoundAtPosBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickPlaySoundAtPosBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final TextView brickPlaySound3dInstanceEdit;
    public final Spinner brickPlaySound3dLoopSpinner;
    public final TextView brickPlaySound3dNameEdit;
    public final TextView brickPlaySound3dPitchEdit;
    public final TextView brickPlaySound3dVolumeEdit;
    public final TextView brickPlaySound3dXEdit;
    public final TextView brickPlaySound3dYEdit;
    public final TextView brickPlaySound3dZEdit;
    public final BrickLayout brickPlaySoundAtPosLayout;
    private final LinearLayout rootView;

    private BrickPlaySoundAtPosBinding(LinearLayout rootView, CheckBox brickCheckbox, TextView brickPlaySound3dInstanceEdit, Spinner brickPlaySound3dLoopSpinner, TextView brickPlaySound3dNameEdit, TextView brickPlaySound3dPitchEdit, TextView brickPlaySound3dVolumeEdit, TextView brickPlaySound3dXEdit, TextView brickPlaySound3dYEdit, TextView brickPlaySound3dZEdit, BrickLayout brickPlaySoundAtPosLayout) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickPlaySound3dInstanceEdit = brickPlaySound3dInstanceEdit;
        this.brickPlaySound3dLoopSpinner = brickPlaySound3dLoopSpinner;
        this.brickPlaySound3dNameEdit = brickPlaySound3dNameEdit;
        this.brickPlaySound3dPitchEdit = brickPlaySound3dPitchEdit;
        this.brickPlaySound3dVolumeEdit = brickPlaySound3dVolumeEdit;
        this.brickPlaySound3dXEdit = brickPlaySound3dXEdit;
        this.brickPlaySound3dYEdit = brickPlaySound3dYEdit;
        this.brickPlaySound3dZEdit = brickPlaySound3dZEdit;
        this.brickPlaySoundAtPosLayout = brickPlaySoundAtPosLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickPlaySoundAtPosBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickPlaySoundAtPosBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_play_sound_at_pos, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickPlaySoundAtPosBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_play_sound_3d_instance_edit;
            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_play_sound_3d_instance_edit);
            if (textView != null) {
                i = R.id.brick_play_sound_3d_loop_spinner;
                Spinner spinner = (Spinner) ViewBindings.findChildViewById(rootView, R.id.brick_play_sound_3d_loop_spinner);
                if (spinner != null) {
                    i = R.id.brick_play_sound_3d_name_edit;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_play_sound_3d_name_edit);
                    if (textView2 != null) {
                        i = R.id.brick_play_sound_3d_pitch_edit;
                        TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_play_sound_3d_pitch_edit);
                        if (textView3 != null) {
                            i = R.id.brick_play_sound_3d_volume_edit;
                            TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_play_sound_3d_volume_edit);
                            if (textView4 != null) {
                                i = R.id.brick_play_sound_3d_x_edit;
                                TextView textView5 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_play_sound_3d_x_edit);
                                if (textView5 != null) {
                                    i = R.id.brick_play_sound_3d_y_edit;
                                    TextView textView6 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_play_sound_3d_y_edit);
                                    if (textView6 != null) {
                                        i = R.id.brick_play_sound_3d_z_edit;
                                        TextView textView7 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_play_sound_3d_z_edit);
                                        if (textView7 != null) {
                                            i = R.id.brick_play_sound_at_pos_layout;
                                            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_play_sound_at_pos_layout);
                                            if (brickLayout != null) {
                                                return new BrickPlaySoundAtPosBinding((LinearLayout) rootView, checkBox, textView, spinner, textView2, textView3, textView4, textView5, textView6, textView7, brickLayout);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

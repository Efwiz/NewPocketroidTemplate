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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/BrickCreateParticlesBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickCreateParticlesBinding implements ViewBinding {
    public final CheckBox brickCheckbox;
    public final BrickLayout brickCreateParticlesLayout;
    public final TextView brickParticleAdditiveEdit;
    public final TextView brickParticleAngleEdit;
    public final TextView brickParticleDurationEdit;
    public final TextView brickParticleEndColorEdit;
    public final TextView brickParticleEndSizeEdit;
    public final TextView brickParticleGravityEdit;
    public final TextView brickParticleIdEdit;
    public final TextView brickParticleLifetimeEdit;
    public final TextView brickParticleLoopingEdit;
    public final TextView brickParticleMaxCountEdit;
    public final TextView brickParticlePosXEdit;
    public final TextView brickParticlePosYEdit;
    public final TextView brickParticlePosZEdit;
    public final TextView brickParticleRadiusEdit;
    public final TextView brickParticleRateEdit;
    public final TextView brickParticleRotPitchEdit;
    public final TextView brickParticleRotRollEdit;
    public final TextView brickParticleRotYawEdit;
    public final TextView brickParticleRotationEdit;
    public final TextView brickParticleRotationSpeedEdit;
    public final TextView brickParticleSizeEdit;
    public final TextView brickParticleSpeedEdit;
    public final TextView brickParticleStartColorEdit;
    public final TextView brickParticleTextureEdit;
    private final LinearLayout rootView;

    private BrickCreateParticlesBinding(LinearLayout rootView, CheckBox brickCheckbox, BrickLayout brickCreateParticlesLayout, TextView brickParticleAdditiveEdit, TextView brickParticleAngleEdit, TextView brickParticleDurationEdit, TextView brickParticleEndColorEdit, TextView brickParticleEndSizeEdit, TextView brickParticleGravityEdit, TextView brickParticleIdEdit, TextView brickParticleLifetimeEdit, TextView brickParticleLoopingEdit, TextView brickParticleMaxCountEdit, TextView brickParticlePosXEdit, TextView brickParticlePosYEdit, TextView brickParticlePosZEdit, TextView brickParticleRadiusEdit, TextView brickParticleRateEdit, TextView brickParticleRotPitchEdit, TextView brickParticleRotRollEdit, TextView brickParticleRotYawEdit, TextView brickParticleRotationEdit, TextView brickParticleRotationSpeedEdit, TextView brickParticleSizeEdit, TextView brickParticleSpeedEdit, TextView brickParticleStartColorEdit, TextView brickParticleTextureEdit) {
        this.rootView = rootView;
        this.brickCheckbox = brickCheckbox;
        this.brickCreateParticlesLayout = brickCreateParticlesLayout;
        this.brickParticleAdditiveEdit = brickParticleAdditiveEdit;
        this.brickParticleAngleEdit = brickParticleAngleEdit;
        this.brickParticleDurationEdit = brickParticleDurationEdit;
        this.brickParticleEndColorEdit = brickParticleEndColorEdit;
        this.brickParticleEndSizeEdit = brickParticleEndSizeEdit;
        this.brickParticleGravityEdit = brickParticleGravityEdit;
        this.brickParticleIdEdit = brickParticleIdEdit;
        this.brickParticleLifetimeEdit = brickParticleLifetimeEdit;
        this.brickParticleLoopingEdit = brickParticleLoopingEdit;
        this.brickParticleMaxCountEdit = brickParticleMaxCountEdit;
        this.brickParticlePosXEdit = brickParticlePosXEdit;
        this.brickParticlePosYEdit = brickParticlePosYEdit;
        this.brickParticlePosZEdit = brickParticlePosZEdit;
        this.brickParticleRadiusEdit = brickParticleRadiusEdit;
        this.brickParticleRateEdit = brickParticleRateEdit;
        this.brickParticleRotPitchEdit = brickParticleRotPitchEdit;
        this.brickParticleRotRollEdit = brickParticleRotRollEdit;
        this.brickParticleRotYawEdit = brickParticleRotYawEdit;
        this.brickParticleRotationEdit = brickParticleRotationEdit;
        this.brickParticleRotationSpeedEdit = brickParticleRotationSpeedEdit;
        this.brickParticleSizeEdit = brickParticleSizeEdit;
        this.brickParticleSpeedEdit = brickParticleSpeedEdit;
        this.brickParticleStartColorEdit = brickParticleStartColorEdit;
        this.brickParticleTextureEdit = brickParticleTextureEdit;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static BrickCreateParticlesBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BrickCreateParticlesBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.brick_create_particles, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static BrickCreateParticlesBinding bind(View rootView) {
        int i = R.id.brick_checkbox;
        CheckBox checkBox = (CheckBox) ViewBindings.findChildViewById(rootView, R.id.brick_checkbox);
        if (checkBox != null) {
            i = R.id.brick_create_particles_layout;
            BrickLayout brickLayout = (BrickLayout) ViewBindings.findChildViewById(rootView, R.id.brick_create_particles_layout);
            if (brickLayout != null) {
                i = R.id.brick_particle_additive_edit;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_particle_additive_edit);
                if (textView != null) {
                    i = R.id.brick_particle_angle_edit;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_particle_angle_edit);
                    if (textView2 != null) {
                        i = R.id.brick_particle_duration_edit;
                        TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_particle_duration_edit);
                        if (textView3 != null) {
                            i = R.id.brick_particle_end_color_edit;
                            TextView textView4 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_particle_end_color_edit);
                            if (textView4 != null) {
                                i = R.id.brick_particle_end_size_edit;
                                TextView textView5 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_particle_end_size_edit);
                                if (textView5 != null) {
                                    i = R.id.brick_particle_gravity_edit;
                                    TextView textView6 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_particle_gravity_edit);
                                    if (textView6 != null) {
                                        i = R.id.brick_particle_id_edit;
                                        TextView textView7 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_particle_id_edit);
                                        if (textView7 != null) {
                                            i = R.id.brick_particle_lifetime_edit;
                                            TextView textView8 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_particle_lifetime_edit);
                                            if (textView8 != null) {
                                                i = R.id.brick_particle_looping_edit;
                                                TextView textView9 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_particle_looping_edit);
                                                if (textView9 != null) {
                                                    i = R.id.brick_particle_max_count_edit;
                                                    TextView textView10 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_particle_max_count_edit);
                                                    if (textView10 != null) {
                                                        i = R.id.brick_particle_pos_x_edit;
                                                        TextView textView11 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_particle_pos_x_edit);
                                                        if (textView11 != null) {
                                                            i = R.id.brick_particle_pos_y_edit;
                                                            TextView textView12 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_particle_pos_y_edit);
                                                            if (textView12 != null) {
                                                                i = R.id.brick_particle_pos_z_edit;
                                                                TextView textView13 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_particle_pos_z_edit);
                                                                if (textView13 != null) {
                                                                    i = R.id.brick_particle_radius_edit;
                                                                    TextView textView14 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_particle_radius_edit);
                                                                    if (textView14 != null) {
                                                                        i = R.id.brick_particle_rate_edit;
                                                                        TextView textView15 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_particle_rate_edit);
                                                                        if (textView15 != null) {
                                                                            i = R.id.brick_particle_rot_pitch_edit;
                                                                            TextView textView16 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_particle_rot_pitch_edit);
                                                                            if (textView16 != null) {
                                                                                i = R.id.brick_particle_rot_roll_edit;
                                                                                TextView textView17 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_particle_rot_roll_edit);
                                                                                if (textView17 != null) {
                                                                                    i = R.id.brick_particle_rot_yaw_edit;
                                                                                    TextView textView18 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_particle_rot_yaw_edit);
                                                                                    if (textView18 != null) {
                                                                                        i = R.id.brick_particle_rotation_edit;
                                                                                        TextView textView19 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_particle_rotation_edit);
                                                                                        if (textView19 != null) {
                                                                                            i = R.id.brick_particle_rotation_speed_edit;
                                                                                            TextView textView20 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_particle_rotation_speed_edit);
                                                                                            if (textView20 != null) {
                                                                                                i = R.id.brick_particle_size_edit;
                                                                                                TextView textView21 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_particle_size_edit);
                                                                                                if (textView21 != null) {
                                                                                                    i = R.id.brick_particle_speed_edit;
                                                                                                    TextView textView22 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_particle_speed_edit);
                                                                                                    if (textView22 != null) {
                                                                                                        i = R.id.brick_particle_start_color_edit;
                                                                                                        TextView textView23 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_particle_start_color_edit);
                                                                                                        if (textView23 != null) {
                                                                                                            i = R.id.brick_particle_texture_edit;
                                                                                                            TextView textView24 = (TextView) ViewBindings.findChildViewById(rootView, R.id.brick_particle_texture_edit);
                                                                                                            if (textView24 != null) {
                                                                                                                return new BrickCreateParticlesBinding((LinearLayout) rootView, checkBox, brickLayout, textView, textView2, textView3, textView4, textView5, textView6, textView7, textView8, textView9, textView10, textView11, textView12, textView13, textView14, textView15, textView16, textView17, textView18, textView19, textView20, textView21, textView22, textView23, textView24);
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
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/CreateParticlesBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class CreateParticlesBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_create_particles;
    }

    public CreateParticlesBrick() {
        addAllowedBrickField(Brick.BrickField.PARTICLE_ID, R.id.brick_particle_id_edit);
        addAllowedBrickField(Brick.BrickField.PARTICLE_LOOPING, R.id.brick_particle_looping_edit);
        addAllowedBrickField(Brick.BrickField.PARTICLE_DURATION, R.id.brick_particle_duration_edit);
        addAllowedBrickField(Brick.BrickField.PARTICLE_LIFETIME, R.id.brick_particle_lifetime_edit);
        addAllowedBrickField(Brick.BrickField.PARTICLE_SPEED, R.id.brick_particle_speed_edit);
        addAllowedBrickField(Brick.BrickField.PARTICLE_SIZE, R.id.brick_particle_size_edit);
        addAllowedBrickField(Brick.BrickField.PARTICLE_END_SIZE, R.id.brick_particle_end_size_edit);
        addAllowedBrickField(Brick.BrickField.PARTICLE_GRAVITY, R.id.brick_particle_gravity_edit);
        addAllowedBrickField(Brick.BrickField.PARTICLE_MAX_COUNT, R.id.brick_particle_max_count_edit);
        addAllowedBrickField(Brick.BrickField.PARTICLE_RATE, R.id.brick_particle_rate_edit);
        addAllowedBrickField(Brick.BrickField.PARTICLE_ANGLE, R.id.brick_particle_angle_edit);
        addAllowedBrickField(Brick.BrickField.PARTICLE_RADIUS, R.id.brick_particle_radius_edit);
        addAllowedBrickField(Brick.BrickField.PARTICLE_START_COLOR, R.id.brick_particle_start_color_edit);
        addAllowedBrickField(Brick.BrickField.PARTICLE_END_COLOR, R.id.brick_particle_end_color_edit);
        addAllowedBrickField(Brick.BrickField.PARTICLE_TEXTURE, R.id.brick_particle_texture_edit);
        addAllowedBrickField(Brick.BrickField.PARTICLE_ADDITIVE, R.id.brick_particle_additive_edit);
        addAllowedBrickField(Brick.BrickField.PARTICLE_ROTATION, R.id.brick_particle_rotation_edit);
        addAllowedBrickField(Brick.BrickField.PARTICLE_ROTATION_SPEED, R.id.brick_particle_rotation_speed_edit);
        addAllowedBrickField(Brick.BrickField.PARTICLE_POS_X, R.id.brick_particle_pos_x_edit);
        addAllowedBrickField(Brick.BrickField.PARTICLE_POS_Y, R.id.brick_particle_pos_y_edit);
        addAllowedBrickField(Brick.BrickField.PARTICLE_POS_Z, R.id.brick_particle_pos_z_edit);
        addAllowedBrickField(Brick.BrickField.PARTICLE_ROT_PITCH, R.id.brick_particle_rot_pitch_edit);
        addAllowedBrickField(Brick.BrickField.PARTICLE_ROT_YAW, R.id.brick_particle_rot_yaw_edit);
        addAllowedBrickField(Brick.BrickField.PARTICLE_ROT_ROLL, R.id.brick_particle_rot_roll_edit);
    }

    public CreateParticlesBrick(String particleId) {
        this();
        setFormulaWithBrickField(Brick.BrickField.PARTICLE_ID, new Formula(particleId));
        setFormulaWithBrickField(Brick.BrickField.PARTICLE_POS_X, new Formula((Integer) 0));
        setFormulaWithBrickField(Brick.BrickField.PARTICLE_POS_Y, new Formula((Integer) 0));
        setFormulaWithBrickField(Brick.BrickField.PARTICLE_POS_Z, new Formula((Integer) 0));
        setFormulaWithBrickField(Brick.BrickField.PARTICLE_ROT_PITCH, new Formula((Integer) 0));
        setFormulaWithBrickField(Brick.BrickField.PARTICLE_ROT_YAW, new Formula((Integer) 0));
        setFormulaWithBrickField(Brick.BrickField.PARTICLE_ROT_ROLL, new Formula((Integer) 0));
        setFormulaWithBrickField(Brick.BrickField.PARTICLE_LOOPING, new Formula((Integer) 1));
        setFormulaWithBrickField(Brick.BrickField.PARTICLE_DURATION, new Formula(Double.valueOf(5.0d)));
        setFormulaWithBrickField(Brick.BrickField.PARTICLE_LIFETIME, new Formula(Double.valueOf(1.5d)));
        Brick.BrickField brickField = Brick.BrickField.PARTICLE_SPEED;
        Double dValueOf = Double.valueOf(2.0d);
        setFormulaWithBrickField(brickField, new Formula(dValueOf));
        setFormulaWithBrickField(Brick.BrickField.PARTICLE_SIZE, new Formula(Double.valueOf(0.5d)));
        setFormulaWithBrickField(Brick.BrickField.PARTICLE_END_SIZE, new Formula(dValueOf));
        setFormulaWithBrickField(Brick.BrickField.PARTICLE_GRAVITY, new Formula(Double.valueOf(-0.2d)));
        setFormulaWithBrickField(Brick.BrickField.PARTICLE_MAX_COUNT, new Formula((Integer) 500));
        setFormulaWithBrickField(Brick.BrickField.PARTICLE_RATE, new Formula((Integer) 40));
        setFormulaWithBrickField(Brick.BrickField.PARTICLE_ANGLE, new Formula((Integer) 15));
        setFormulaWithBrickField(Brick.BrickField.PARTICLE_RADIUS, new Formula(Double.valueOf(0.2d)));
        setFormulaWithBrickField(Brick.BrickField.PARTICLE_START_COLOR, new Formula("#FFFF8800"));
        setFormulaWithBrickField(Brick.BrickField.PARTICLE_END_COLOR, new Formula("#66FF0000"));
        setFormulaWithBrickField(Brick.BrickField.PARTICLE_TEXTURE, new Formula(""));
        setFormulaWithBrickField(Brick.BrickField.PARTICLE_ADDITIVE, new Formula((Integer) 1));
        setFormulaWithBrickField(Brick.BrickField.PARTICLE_ROTATION, new Formula((Integer) 0));
        setFormulaWithBrickField(Brick.BrickField.PARTICLE_ROTATION_SPEED, new Formula((Integer) 180));
    }

    public CreateParticlesBrick(Formula particleId, Formula positionX, Formula positionY, Formula positionZ, Formula rotationPitch, Formula rotationYaw, Formula rotationRoll, Formula looping, Formula duration, Formula startLifetime, Formula startSpeed, Formula startSize, Formula endSize, Formula gravityModifier, Formula maxParticles, Formula emissionRate, Formula coneAngle, Formula coneRadius, Formula startColor, Formula endColor, Formula texturePath, Formula isAdditive, Formula startRotation, Formula rotationOverLifetime) {
        this();
        setFormulaWithBrickField(Brick.BrickField.PARTICLE_ID, particleId);
        setFormulaWithBrickField(Brick.BrickField.PARTICLE_POS_X, positionX);
        setFormulaWithBrickField(Brick.BrickField.PARTICLE_POS_Y, positionY);
        setFormulaWithBrickField(Brick.BrickField.PARTICLE_POS_Z, positionZ);
        setFormulaWithBrickField(Brick.BrickField.PARTICLE_ROT_PITCH, rotationPitch);
        setFormulaWithBrickField(Brick.BrickField.PARTICLE_ROT_YAW, rotationYaw);
        setFormulaWithBrickField(Brick.BrickField.PARTICLE_ROT_ROLL, rotationRoll);
        setFormulaWithBrickField(Brick.BrickField.PARTICLE_LOOPING, looping);
        setFormulaWithBrickField(Brick.BrickField.PARTICLE_DURATION, duration);
        setFormulaWithBrickField(Brick.BrickField.PARTICLE_LIFETIME, startLifetime);
        setFormulaWithBrickField(Brick.BrickField.PARTICLE_SPEED, startSpeed);
        setFormulaWithBrickField(Brick.BrickField.PARTICLE_SIZE, startSize);
        setFormulaWithBrickField(Brick.BrickField.PARTICLE_END_SIZE, endSize);
        setFormulaWithBrickField(Brick.BrickField.PARTICLE_GRAVITY, gravityModifier);
        setFormulaWithBrickField(Brick.BrickField.PARTICLE_MAX_COUNT, maxParticles);
        setFormulaWithBrickField(Brick.BrickField.PARTICLE_RATE, emissionRate);
        setFormulaWithBrickField(Brick.BrickField.PARTICLE_ANGLE, coneAngle);
        setFormulaWithBrickField(Brick.BrickField.PARTICLE_RADIUS, coneRadius);
        setFormulaWithBrickField(Brick.BrickField.PARTICLE_START_COLOR, startColor);
        setFormulaWithBrickField(Brick.BrickField.PARTICLE_END_COLOR, endColor);
        setFormulaWithBrickField(Brick.BrickField.PARTICLE_TEXTURE, texturePath);
        setFormulaWithBrickField(Brick.BrickField.PARTICLE_ADDITIVE, isAdditive);
        setFormulaWithBrickField(Brick.BrickField.PARTICLE_ROTATION, startRotation);
        setFormulaWithBrickField(Brick.BrickField.PARTICLE_ROTATION_SPEED, rotationOverLifetime);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createParticlesAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.PARTICLE_ID), getFormulaWithBrickField(Brick.BrickField.PARTICLE_LOOPING), getFormulaWithBrickField(Brick.BrickField.PARTICLE_DURATION), getFormulaWithBrickField(Brick.BrickField.PARTICLE_LIFETIME), getFormulaWithBrickField(Brick.BrickField.PARTICLE_SPEED), getFormulaWithBrickField(Brick.BrickField.PARTICLE_SIZE), getFormulaWithBrickField(Brick.BrickField.PARTICLE_GRAVITY), getFormulaWithBrickField(Brick.BrickField.PARTICLE_MAX_COUNT), getFormulaWithBrickField(Brick.BrickField.PARTICLE_RATE), getFormulaWithBrickField(Brick.BrickField.PARTICLE_ANGLE), getFormulaWithBrickField(Brick.BrickField.PARTICLE_RADIUS), getFormulaWithBrickField(Brick.BrickField.PARTICLE_START_COLOR), getFormulaWithBrickField(Brick.BrickField.PARTICLE_END_COLOR), getFormulaWithBrickField(Brick.BrickField.PARTICLE_END_SIZE), getFormulaWithBrickField(Brick.BrickField.PARTICLE_TEXTURE), getFormulaWithBrickField(Brick.BrickField.PARTICLE_ADDITIVE), getFormulaWithBrickField(Brick.BrickField.PARTICLE_ROTATION), getFormulaWithBrickField(Brick.BrickField.PARTICLE_ROTATION_SPEED), getFormulaWithBrickField(Brick.BrickField.PARTICLE_POS_X), getFormulaWithBrickField(Brick.BrickField.PARTICLE_POS_Y), getFormulaWithBrickField(Brick.BrickField.PARTICLE_POS_Z), getFormulaWithBrickField(Brick.BrickField.PARTICLE_ROT_PITCH), getFormulaWithBrickField(Brick.BrickField.PARTICLE_ROT_YAW), getFormulaWithBrickField(Brick.BrickField.PARTICLE_ROT_ROLL)));
    }
}

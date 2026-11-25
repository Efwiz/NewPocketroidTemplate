package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/CreateDistanceJointBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class CreateDistanceJointBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_create_distance_joint;
    }

    public CreateDistanceJointBrick() {
        addAllowedBrickField(Brick.BrickField.JOINT_ID, R.id.brick_joint_id_edit_text);
        addAllowedBrickField(Brick.BrickField.SPRITE, R.id.brick_sprite_b_edit_text);
        addAllowedBrickField(Brick.BrickField.JOINT_LENGTH, R.id.brick_length_edit_text);
        addAllowedBrickField(Brick.BrickField.JOINT_FREQUENCY, R.id.brick_frequency_edit_text);
        addAllowedBrickField(Brick.BrickField.JOINT_DAMPING, R.id.brick_damping_edit_text);
    }

    public CreateDistanceJointBrick(String jointId, String spriteB, String length, String frequency, String damping) {
        this(new Formula(jointId), new Formula(spriteB), new Formula(length), new Formula(frequency), new Formula(damping));
    }

    public CreateDistanceJointBrick(Formula jointId, Formula spriteB, Formula length, Formula frequency, Formula damping) {
        this();
        setFormulaWithBrickField(Brick.BrickField.JOINT_ID, jointId);
        setFormulaWithBrickField(Brick.BrickField.SPRITE, spriteB);
        setFormulaWithBrickField(Brick.BrickField.JOINT_LENGTH, length);
        setFormulaWithBrickField(Brick.BrickField.JOINT_FREQUENCY, frequency);
        setFormulaWithBrickField(Brick.BrickField.JOINT_DAMPING, damping);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createDistanceJointAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.JOINT_ID), getFormulaWithBrickField(Brick.BrickField.SPRITE), getFormulaWithBrickField(Brick.BrickField.JOINT_LENGTH), getFormulaWithBrickField(Brick.BrickField.JOINT_FREQUENCY), getFormulaWithBrickField(Brick.BrickField.JOINT_DAMPING)));
    }
}

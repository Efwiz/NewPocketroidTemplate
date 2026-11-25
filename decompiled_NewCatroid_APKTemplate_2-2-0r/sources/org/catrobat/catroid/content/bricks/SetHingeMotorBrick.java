package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetHingeMotorBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetHingeMotorBrick extends FormulaBrick {
    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_threed_hinge_motor;
    }

    public SetHingeMotorBrick() {
        addAllowedBrickField(Brick.BrickField.CONSTRAINT_ID, R.id.brick_motor_id);
        addAllowedBrickField(Brick.BrickField.MOTOR_TARGET, R.id.brick_motor_target);
        addAllowedBrickField(Brick.BrickField.MOTOR_MAX_FORCE, R.id.brick_motor_max);
    }

    public SetHingeMotorBrick(String id2, double angle, double force) {
        this();
        setFormulaWithBrickField(Brick.BrickField.CONSTRAINT_ID, new Formula(id2));
        setFormulaWithBrickField(Brick.BrickField.MOTOR_TARGET, new Formula(Double.valueOf(angle)));
        setFormulaWithBrickField(Brick.BrickField.MOTOR_MAX_FORCE, new Formula(Double.valueOf(force)));
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetHingeMotorAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.CONSTRAINT_ID), getFormulaWithBrickField(Brick.BrickField.MOTOR_TARGET), getFormulaWithBrickField(Brick.BrickField.MOTOR_MAX_FORCE)));
    }
}

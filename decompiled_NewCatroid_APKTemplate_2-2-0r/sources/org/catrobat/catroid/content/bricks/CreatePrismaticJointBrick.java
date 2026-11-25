package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/CreatePrismaticJointBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class CreatePrismaticJointBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_create_prismatic_joint;
    }

    public CreatePrismaticJointBrick() {
        addAllowedBrickField(Brick.BrickField.JOINT_ID, R.id.brick_joint_id_edit_text);
        addAllowedBrickField(Brick.BrickField.SPRITE, R.id.brick_sprite_b_edit_text);
        addAllowedBrickField(Brick.BrickField.X_POSITION, R.id.brick_anchor_x_edit_text);
        addAllowedBrickField(Brick.BrickField.Y_POSITION, R.id.brick_anchor_y_edit_text);
        addAllowedBrickField(Brick.BrickField.AXIS_X, R.id.brick_axis_x_edit_text);
        addAllowedBrickField(Brick.BrickField.AXIS_Y, R.id.brick_axis_y_edit_text);
    }

    public CreatePrismaticJointBrick(String jointId, String spriteB, int aX, int aY, int axisX, int axisY) {
        this(new Formula(jointId), new Formula(spriteB), new Formula(Integer.valueOf(aX)), new Formula(Integer.valueOf(aY)), new Formula(Integer.valueOf(axisX)), new Formula(Integer.valueOf(axisY)));
    }

    public CreatePrismaticJointBrick(Formula jId, Formula sB, Formula aX, Formula aY, Formula axisX, Formula axisY) {
        this();
        setFormulaWithBrickField(Brick.BrickField.JOINT_ID, jId);
        setFormulaWithBrickField(Brick.BrickField.SPRITE, sB);
        setFormulaWithBrickField(Brick.BrickField.X_POSITION, aX);
        setFormulaWithBrickField(Brick.BrickField.Y_POSITION, aY);
        setFormulaWithBrickField(Brick.BrickField.AXIS_X, axisX);
        setFormulaWithBrickField(Brick.BrickField.AXIS_Y, axisY);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createPrismaticJointAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.JOINT_ID), getFormulaWithBrickField(Brick.BrickField.SPRITE), getFormulaWithBrickField(Brick.BrickField.X_POSITION), getFormulaWithBrickField(Brick.BrickField.Y_POSITION), getFormulaWithBrickField(Brick.BrickField.AXIS_X), getFormulaWithBrickField(Brick.BrickField.AXIS_Y)));
    }
}

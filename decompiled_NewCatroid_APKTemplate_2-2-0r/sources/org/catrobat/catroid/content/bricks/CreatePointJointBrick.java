package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/CreatePointJointBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class CreatePointJointBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_create_point_joint;
    }

    public CreatePointJointBrick() {
        addAllowedBrickField(Brick.BrickField.JOINT_NAME, R.id.brick_joint_name_edit);
        addAllowedBrickField(Brick.BrickField.OBJECT_A, R.id.brick_joint_object_a_edit);
        addAllowedBrickField(Brick.BrickField.OBJECT_B, R.id.brick_joint_object_b_edit);
        addAllowedBrickField(Brick.BrickField.PIVOT_A_X, R.id.brick_joint_pivot_a_x);
        addAllowedBrickField(Brick.BrickField.PIVOT_A_Y, R.id.brick_joint_pivot_a_y);
        addAllowedBrickField(Brick.BrickField.PIVOT_A_Z, R.id.brick_joint_pivot_a_z);
        addAllowedBrickField(Brick.BrickField.PIVOT_B_X, R.id.brick_joint_pivot_b_x);
        addAllowedBrickField(Brick.BrickField.PIVOT_B_Y, R.id.brick_joint_pivot_b_y);
        addAllowedBrickField(Brick.BrickField.PIVOT_B_Z, R.id.brick_joint_pivot_b_z);
    }

    public CreatePointJointBrick(String jointName, String objectA, String objectB) {
        this();
        setFormulaWithBrickField(Brick.BrickField.JOINT_NAME, new Formula(jointName));
        setFormulaWithBrickField(Brick.BrickField.OBJECT_A, new Formula(objectA));
        setFormulaWithBrickField(Brick.BrickField.OBJECT_B, new Formula(objectB));
        setFormulaWithBrickField(Brick.BrickField.PIVOT_A_X, new Formula((Integer) 0));
        setFormulaWithBrickField(Brick.BrickField.PIVOT_A_Y, new Formula((Integer) 0));
        setFormulaWithBrickField(Brick.BrickField.PIVOT_A_Z, new Formula((Integer) 0));
        setFormulaWithBrickField(Brick.BrickField.PIVOT_B_X, new Formula((Integer) 0));
        setFormulaWithBrickField(Brick.BrickField.PIVOT_B_Y, new Formula((Integer) 0));
        setFormulaWithBrickField(Brick.BrickField.PIVOT_B_Z, new Formula((Integer) 0));
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createCreatePointJointAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.JOINT_NAME), getFormulaWithBrickField(Brick.BrickField.OBJECT_A), getFormulaWithBrickField(Brick.BrickField.OBJECT_B), getFormulaWithBrickField(Brick.BrickField.PIVOT_A_X), getFormulaWithBrickField(Brick.BrickField.PIVOT_A_Y), getFormulaWithBrickField(Brick.BrickField.PIVOT_A_Z), getFormulaWithBrickField(Brick.BrickField.PIVOT_B_X), getFormulaWithBrickField(Brick.BrickField.PIVOT_B_Y), getFormulaWithBrickField(Brick.BrickField.PIVOT_B_Z)));
    }
}

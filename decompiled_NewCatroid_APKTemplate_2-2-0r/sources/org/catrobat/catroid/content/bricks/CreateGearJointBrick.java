package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/CreateGearJointBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class CreateGearJointBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_create_gear_joint;
    }

    public CreateGearJointBrick() {
        addAllowedBrickField(Brick.BrickField.JOINT_ID, R.id.brick_joint_id_edit_text);
        addAllowedBrickField(Brick.BrickField.JOINT_A_ID, R.id.brick_joint_a_id_edit_text);
        addAllowedBrickField(Brick.BrickField.JOINT_B_ID, R.id.brick_joint_b_id_edit_text);
        addAllowedBrickField(Brick.BrickField.RATIO, R.id.brick_ratio_edit_text);
    }

    public CreateGearJointBrick(String jointId, String jointA, String jointB, float ratio) {
        this(new Formula(jointId), new Formula(jointA), new Formula(jointB), new Formula(Float.valueOf(ratio)));
    }

    public CreateGearJointBrick(Formula jId, Formula jAId, Formula jBId, Formula ratio) {
        this();
        setFormulaWithBrickField(Brick.BrickField.JOINT_ID, jId);
        setFormulaWithBrickField(Brick.BrickField.JOINT_A_ID, jAId);
        setFormulaWithBrickField(Brick.BrickField.JOINT_B_ID, jBId);
        setFormulaWithBrickField(Brick.BrickField.RATIO, ratio);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createGearJointAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.JOINT_ID), getFormulaWithBrickField(Brick.BrickField.JOINT_A_ID), getFormulaWithBrickField(Brick.BrickField.JOINT_B_ID), getFormulaWithBrickField(Brick.BrickField.RATIO)));
    }
}

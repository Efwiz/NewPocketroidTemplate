package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/AddHingeBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class AddHingeBrick extends FormulaBrick {
    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_threed_hinge;
    }

    public AddHingeBrick() {
        addAllowedBrickField(Brick.BrickField.CONSTRAINT_ID, R.id.brick_hinge_id);
        addAllowedBrickField(Brick.BrickField.OBJECT_A, R.id.brick_hinge_obja);
        addAllowedBrickField(Brick.BrickField.OBJECT_B, R.id.brick_hinge_objb);
        addAllowedBrickField(Brick.BrickField.PIVOT_A_X, R.id.brick_hinge_pax);
        addAllowedBrickField(Brick.BrickField.PIVOT_A_Y, R.id.brick_hinge_pay);
        addAllowedBrickField(Brick.BrickField.PIVOT_A_Z, R.id.brick_hinge_paz);
        addAllowedBrickField(Brick.BrickField.AXIS_A_X, R.id.brick_hinge_aax);
        addAllowedBrickField(Brick.BrickField.AXIS_A_Y, R.id.brick_hinge_aay);
        addAllowedBrickField(Brick.BrickField.AXIS_A_Z, R.id.brick_hinge_aaz);
        addAllowedBrickField(Brick.BrickField.PIVOT_B_X, R.id.brick_hinge_pbx);
        addAllowedBrickField(Brick.BrickField.PIVOT_B_Y, R.id.brick_hinge_pby);
        addAllowedBrickField(Brick.BrickField.PIVOT_B_Z, R.id.brick_hinge_pbz);
        addAllowedBrickField(Brick.BrickField.AXIS_B_X, R.id.brick_hinge_abx);
        addAllowedBrickField(Brick.BrickField.AXIS_B_Y, R.id.brick_hinge_aby);
        addAllowedBrickField(Brick.BrickField.AXIS_B_Z, R.id.brick_hinge_abz);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createAddHingeAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.CONSTRAINT_ID), getFormulaWithBrickField(Brick.BrickField.OBJECT_A), getFormulaWithBrickField(Brick.BrickField.OBJECT_B), getFormulaWithBrickField(Brick.BrickField.PIVOT_A_X), getFormulaWithBrickField(Brick.BrickField.PIVOT_A_Y), getFormulaWithBrickField(Brick.BrickField.PIVOT_A_Z), getFormulaWithBrickField(Brick.BrickField.AXIS_A_X), getFormulaWithBrickField(Brick.BrickField.AXIS_A_Y), getFormulaWithBrickField(Brick.BrickField.AXIS_A_Z), getFormulaWithBrickField(Brick.BrickField.PIVOT_B_X), getFormulaWithBrickField(Brick.BrickField.PIVOT_B_Y), getFormulaWithBrickField(Brick.BrickField.PIVOT_B_Z), getFormulaWithBrickField(Brick.BrickField.AXIS_B_X), getFormulaWithBrickField(Brick.BrickField.AXIS_B_Y), getFormulaWithBrickField(Brick.BrickField.AXIS_B_Z)));
    }
}

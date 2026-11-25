package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/CreatePulleyJointBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class CreatePulleyJointBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_create_pulley_joint;
    }

    public CreatePulleyJointBrick() {
        addAllowedBrickField(Brick.BrickField.JOINT_ID, R.id.brick_joint_id_edit_text);
        addAllowedBrickField(Brick.BrickField.SPRITE_A, R.id.brick_sprite_a_edit_text);
        addAllowedBrickField(Brick.BrickField.SPRITE_B, R.id.brick_sprite_b_edit_text);
        addAllowedBrickField(Brick.BrickField.GROUND_ANCHOR_A_X, R.id.brick_ground_anchor_a_x_edit_text);
        addAllowedBrickField(Brick.BrickField.GROUND_ANCHOR_A_Y, R.id.brick_ground_anchor_a_y_edit_text);
        addAllowedBrickField(Brick.BrickField.GROUND_ANCHOR_B_X, R.id.brick_ground_anchor_b_x_edit_text);
        addAllowedBrickField(Brick.BrickField.GROUND_ANCHOR_B_Y, R.id.brick_ground_anchor_b_y_edit_text);
        addAllowedBrickField(Brick.BrickField.RATIO, R.id.brick_ratio_edit_text);
    }

    public CreatePulleyJointBrick(String jId, String sA, String sB, int gAX, int gAY, int gBX, int gBY, float ratio) {
        this(new Formula(jId), new Formula(sA), new Formula(sB), new Formula(Integer.valueOf(gAX)), new Formula(Integer.valueOf(gAY)), new Formula(Integer.valueOf(gBX)), new Formula(Integer.valueOf(gBY)), new Formula(Float.valueOf(ratio)));
    }

    public CreatePulleyJointBrick(Formula jId, Formula sA, Formula sB, Formula gAX, Formula gAY, Formula gBX, Formula gBY, Formula ratio) {
        this();
        setFormulaWithBrickField(Brick.BrickField.JOINT_ID, jId);
        setFormulaWithBrickField(Brick.BrickField.SPRITE_A, sA);
        setFormulaWithBrickField(Brick.BrickField.SPRITE_B, sB);
        setFormulaWithBrickField(Brick.BrickField.GROUND_ANCHOR_A_X, gAX);
        setFormulaWithBrickField(Brick.BrickField.GROUND_ANCHOR_A_Y, gAY);
        setFormulaWithBrickField(Brick.BrickField.GROUND_ANCHOR_B_X, gBX);
        setFormulaWithBrickField(Brick.BrickField.GROUND_ANCHOR_B_Y, gBY);
        setFormulaWithBrickField(Brick.BrickField.RATIO, ratio);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createPulleyJointAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.JOINT_ID), getFormulaWithBrickField(Brick.BrickField.SPRITE_A), getFormulaWithBrickField(Brick.BrickField.SPRITE_B), getFormulaWithBrickField(Brick.BrickField.GROUND_ANCHOR_A_X), getFormulaWithBrickField(Brick.BrickField.GROUND_ANCHOR_A_Y), getFormulaWithBrickField(Brick.BrickField.GROUND_ANCHOR_B_X), getFormulaWithBrickField(Brick.BrickField.GROUND_ANCHOR_B_Y), getFormulaWithBrickField(Brick.BrickField.RATIO)));
    }
}

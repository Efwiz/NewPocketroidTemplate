package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/CreateWeldJointBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class CreateWeldJointBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_create_weld_joint;
    }

    public CreateWeldJointBrick() {
        addAllowedBrickField(Brick.BrickField.JOINT_ID, R.id.brick_joint_id_edit_text);
        addAllowedBrickField(Brick.BrickField.SPRITE, R.id.brick_sprite_b_edit_text);
        addAllowedBrickField(Brick.BrickField.X_POSITION, R.id.brick_anchor_x_edit_text);
        addAllowedBrickField(Brick.BrickField.Y_POSITION, R.id.brick_anchor_y_edit_text);
    }

    public CreateWeldJointBrick(String jointId, String spriteBName, int anchorX, int anchorY) {
        this(new Formula(jointId), new Formula(spriteBName), new Formula(Integer.valueOf(anchorX)), new Formula(Integer.valueOf(anchorY)));
    }

    public CreateWeldJointBrick(Formula jointId, Formula spriteB, Formula anchorX, Formula anchorY) {
        this();
        setFormulaWithBrickField(Brick.BrickField.JOINT_ID, jointId);
        setFormulaWithBrickField(Brick.BrickField.SPRITE, spriteB);
        setFormulaWithBrickField(Brick.BrickField.X_POSITION, anchorX);
        setFormulaWithBrickField(Brick.BrickField.Y_POSITION, anchorY);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createWeldJointAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.JOINT_ID), getFormulaWithBrickField(Brick.BrickField.SPRITE), getFormulaWithBrickField(Brick.BrickField.X_POSITION), getFormulaWithBrickField(Brick.BrickField.Y_POSITION)));
    }
}

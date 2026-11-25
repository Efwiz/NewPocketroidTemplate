package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/Fast2DSetRotationBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class Fast2DSetRotationBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_fast2d_set_rotation;
    }

    public Fast2DSetRotationBrick() {
        addAllowedBrickField(Brick.BrickField.NAME, R.id.brick_fast2d_set_rotation_id);
        addAllowedBrickField(Brick.BrickField.ROTATION, R.id.brick_fast2d_set_rotation_angle);
    }

    public Fast2DSetRotationBrick(String id2, Double angle) {
        this(new Formula(id2), new Formula(angle));
    }

    public Fast2DSetRotationBrick(Formula id2, Formula angle) {
        this();
        setFormulaWithBrickField(Brick.BrickField.NAME, id2);
        setFormulaWithBrickField(Brick.BrickField.ROTATION, angle);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createFast2DSetRotationAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.NAME), getFormulaWithBrickField(Brick.BrickField.ROTATION)));
    }
}

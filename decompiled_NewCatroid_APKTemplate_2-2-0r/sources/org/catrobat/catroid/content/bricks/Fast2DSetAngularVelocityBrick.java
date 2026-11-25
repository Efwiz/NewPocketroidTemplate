package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/Fast2DSetAngularVelocityBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class Fast2DSetAngularVelocityBrick extends FormulaBrick {
    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_fast2d_set_angular_velocity;
    }

    public Fast2DSetAngularVelocityBrick() {
        addAllowedBrickField(Brick.BrickField.NAME, R.id.brick_fast2d_ang_vel_id);
        addAllowedBrickField(Brick.BrickField.ROTATION, R.id.brick_fast2d_ang_vel_v);
    }

    public Fast2DSetAngularVelocityBrick(String id2, Double v) {
        this(new Formula(id2), new Formula(v));
    }

    public Fast2DSetAngularVelocityBrick(Formula id2, Formula v) {
        this();
        setFormulaWithBrickField(Brick.BrickField.NAME, id2);
        setFormulaWithBrickField(Brick.BrickField.ROTATION, v);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createFast2DSetAngularVelocityAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.NAME), getFormulaWithBrickField(Brick.BrickField.ROTATION)));
    }
}

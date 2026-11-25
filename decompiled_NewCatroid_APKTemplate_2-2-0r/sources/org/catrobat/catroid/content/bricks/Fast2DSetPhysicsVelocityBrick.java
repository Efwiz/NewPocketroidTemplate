package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/Fast2DSetPhysicsVelocityBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class Fast2DSetPhysicsVelocityBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_fast2d_set_phys_velocity;
    }

    public Fast2DSetPhysicsVelocityBrick() {
        addAllowedBrickField(Brick.BrickField.NAME, R.id.brick_fast2d_phys_vel_id);
        addAllowedBrickField(Brick.BrickField.X_POSITION, R.id.brick_fast2d_phys_vel_x);
        addAllowedBrickField(Brick.BrickField.Y_POSITION, R.id.brick_fast2d_phys_vel_y);
    }

    public Fast2DSetPhysicsVelocityBrick(String id2, Double vx, Double vy) {
        this(new Formula(id2), new Formula(vx), new Formula(vy));
    }

    public Fast2DSetPhysicsVelocityBrick(Formula id2, Formula vx, Formula vy) {
        this();
        setFormulaWithBrickField(Brick.BrickField.NAME, id2);
        setFormulaWithBrickField(Brick.BrickField.X_POSITION, vx);
        setFormulaWithBrickField(Brick.BrickField.Y_POSITION, vy);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createFast2DSetPhysicsVelocityAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.NAME), getFormulaWithBrickField(Brick.BrickField.X_POSITION), getFormulaWithBrickField(Brick.BrickField.Y_POSITION)));
    }
}

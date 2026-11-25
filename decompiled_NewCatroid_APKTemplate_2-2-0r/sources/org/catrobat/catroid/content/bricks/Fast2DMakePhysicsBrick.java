package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/Fast2DMakePhysicsBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class Fast2DMakePhysicsBrick extends FormulaBrick {
    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_fast2d_make_physics;
    }

    public Fast2DMakePhysicsBrick() {
        addAllowedBrickField(Brick.BrickField.NAME, R.id.brick_fast2d_phys_id);
        addAllowedBrickField(Brick.BrickField.X_POSITION, R.id.brick_fast2d_phys_dyn);
        addAllowedBrickField(Brick.BrickField.STRING, R.id.brick_fast2d_phys_shape);
        addAllowedBrickField(Brick.BrickField.Y_POSITION, R.id.brick_fast2d_phys_den);
        addAllowedBrickField(Brick.BrickField.ROTATION, R.id.brick_fast2d_phys_fric);
        addAllowedBrickField(Brick.BrickField.SIZE, R.id.brick_fast2d_phys_bounce);
    }

    public Fast2DMakePhysicsBrick(String id2, Double dyn, String shape, Double den, Double fric, Double bnc) {
        this(new Formula(id2), new Formula(dyn), new Formula(shape), new Formula(den), new Formula(fric), new Formula(bnc));
    }

    public Fast2DMakePhysicsBrick(Formula id2, Formula dyn, Formula shape, Formula den, Formula fric, Formula bnc) {
        this();
        setFormulaWithBrickField(Brick.BrickField.NAME, id2);
        setFormulaWithBrickField(Brick.BrickField.X_POSITION, dyn);
        setFormulaWithBrickField(Brick.BrickField.STRING, shape);
        setFormulaWithBrickField(Brick.BrickField.Y_POSITION, den);
        setFormulaWithBrickField(Brick.BrickField.ROTATION, fric);
        setFormulaWithBrickField(Brick.BrickField.SIZE, bnc);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createFast2DMakePhysicsAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.NAME), getFormulaWithBrickField(Brick.BrickField.X_POSITION), getFormulaWithBrickField(Brick.BrickField.STRING), getFormulaWithBrickField(Brick.BrickField.Y_POSITION), getFormulaWithBrickField(Brick.BrickField.ROTATION), getFormulaWithBrickField(Brick.BrickField.SIZE)));
    }
}

package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/Fast2DApplyImpulseBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class Fast2DApplyImpulseBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_fast2d_apply_impulse;
    }

    public Fast2DApplyImpulseBrick() {
        addAllowedBrickField(Brick.BrickField.NAME, R.id.brick_fast2d_impulse_id);
        addAllowedBrickField(Brick.BrickField.X_POSITION, R.id.brick_fast2d_impulse_x);
        addAllowedBrickField(Brick.BrickField.Y_POSITION, R.id.brick_fast2d_impulse_y);
    }

    public Fast2DApplyImpulseBrick(String id2, Double x, Double y) {
        this(new Formula(id2), new Formula(x), new Formula(y));
    }

    public Fast2DApplyImpulseBrick(Formula id2, Formula x, Formula y) {
        this();
        setFormulaWithBrickField(Brick.BrickField.NAME, id2);
        setFormulaWithBrickField(Brick.BrickField.X_POSITION, x);
        setFormulaWithBrickField(Brick.BrickField.Y_POSITION, y);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createFast2DApplyImpulseAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.NAME), getFormulaWithBrickField(Brick.BrickField.X_POSITION), getFormulaWithBrickField(Brick.BrickField.Y_POSITION)));
    }
}

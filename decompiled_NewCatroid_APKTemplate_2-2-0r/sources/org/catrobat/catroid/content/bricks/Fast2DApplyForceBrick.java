package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/Fast2DApplyForceBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class Fast2DApplyForceBrick extends FormulaBrick {
    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_fast2d_apply_force;
    }

    public Fast2DApplyForceBrick() {
        addAllowedBrickField(Brick.BrickField.NAME, R.id.brick_fast2d_force_id);
        addAllowedBrickField(Brick.BrickField.X_POSITION, R.id.brick_fast2d_force_x);
        addAllowedBrickField(Brick.BrickField.Y_POSITION, R.id.brick_fast2d_force_y);
    }

    public Fast2DApplyForceBrick(String id2, Double x, Double y) {
        this(new Formula(id2), new Formula(x), new Formula(y));
    }

    public Fast2DApplyForceBrick(Formula id2, Formula x, Formula y) {
        this();
        setFormulaWithBrickField(Brick.BrickField.NAME, id2);
        setFormulaWithBrickField(Brick.BrickField.X_POSITION, x);
        setFormulaWithBrickField(Brick.BrickField.Y_POSITION, y);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createFast2DApplyForceAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.NAME), getFormulaWithBrickField(Brick.BrickField.X_POSITION), getFormulaWithBrickField(Brick.BrickField.Y_POSITION)));
    }
}

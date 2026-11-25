package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/ApplyImpulseBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ApplyImpulseBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_apply_impulse;
    }

    public ApplyImpulseBrick() {
        addAllowedBrickField(Brick.BrickField.IMPULSE_X, R.id.brick_impulse_x_edit_text);
        addAllowedBrickField(Brick.BrickField.IMPULSE_Y, R.id.brick_impulse_y_edit_text);
    }

    public ApplyImpulseBrick(int impulseX, int impulseY) {
        this(new Formula(Integer.valueOf(impulseX)), new Formula(Integer.valueOf(impulseY)));
    }

    public ApplyImpulseBrick(Formula impulseX, Formula impulseY) {
        this();
        setFormulaWithBrickField(Brick.BrickField.IMPULSE_X, impulseX);
        setFormulaWithBrickField(Brick.BrickField.IMPULSE_Y, impulseY);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createApplyImpulseAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.IMPULSE_X), getFormulaWithBrickField(Brick.BrickField.IMPULSE_Y)));
    }
}

package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/ApplyForceBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ApplyForceBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_apply_force;
    }

    public ApplyForceBrick() {
        addAllowedBrickField(Brick.BrickField.FORCE_X, R.id.brick_force_x_edit_text);
        addAllowedBrickField(Brick.BrickField.FORCE_Y, R.id.brick_force_y_edit_text);
    }

    public ApplyForceBrick(int forceX, int forceY) {
        this(new Formula(Integer.valueOf(forceX)), new Formula(Integer.valueOf(forceY)));
    }

    public ApplyForceBrick(Formula forceX, Formula forceY) {
        this();
        setFormulaWithBrickField(Brick.BrickField.FORCE_X, forceX);
        setFormulaWithBrickField(Brick.BrickField.FORCE_Y, forceY);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createApplyForceAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.FORCE_X), getFormulaWithBrickField(Brick.BrickField.FORCE_Y)));
    }
}

package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/PtStepBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class PtStepBrick extends FormulaBrick {
    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_pt_step;
    }

    public PtStepBrick() {
        addAllowedBrickField(Brick.BrickField.VALUE_1, R.id.brick_pt_step_lr);
    }

    public PtStepBrick(float lr) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VALUE_1, new Formula(Float.valueOf(lr)));
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createPtStepAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VALUE_1)));
    }
}

package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/MLStepAdamBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class MLStepAdamBrick extends FormulaBrick {
    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_ml_step_adam;
    }

    public MLStepAdamBrick() {
        addAllowedBrickField(Brick.BrickField.VALUE, R.id.brick_ml_edit_lr);
    }

    public MLStepAdamBrick(Formula lr) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VALUE, lr);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createMLStepAdamAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VALUE)));
    }
}

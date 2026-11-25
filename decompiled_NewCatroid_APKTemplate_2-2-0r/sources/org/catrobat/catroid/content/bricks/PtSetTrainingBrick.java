package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/PtSetTrainingBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class PtSetTrainingBrick extends FormulaBrick {
    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_pt_set_training;
    }

    public PtSetTrainingBrick() {
        addAllowedBrickField(Brick.BrickField.VALUE_1, R.id.brick_pt_set_training_text);
    }

    public PtSetTrainingBrick(int on) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VALUE_1, new Formula(Integer.valueOf(on)));
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createPtSetTrainingAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VALUE_1)));
    }
}

package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.ActionFactory;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/RaspiIfLogicBeginBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class RaspiIfLogicBeginBrick extends IfLogicBeginBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.IfLogicBeginBrick, org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_raspi_if_begin_if;
    }

    public RaspiIfLogicBeginBrick() {
    }

    public RaspiIfLogicBeginBrick(Formula formula) {
        super(formula);
    }

    @Override // org.catrobat.catroid.content.bricks.IfLogicBeginBrick, org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public void addRequiredResources(final Brick.ResourcesSet requiredResourcesSet) {
        requiredResourcesSet.add((Integer) 7);
        super.addRequiredResources(requiredResourcesSet);
    }

    @Override // org.catrobat.catroid.content.bricks.IfLogicBeginBrick, org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        ScriptSequenceAction scriptSequenceAction = (ScriptSequenceAction) ActionFactory.createScriptSequenceAction(sequence.getScript());
        ScriptSequenceAction scriptSequenceAction2 = (ScriptSequenceAction) ActionFactory.createScriptSequenceAction(sequence.getScript());
        for (Brick brick : this.ifBranchBricks) {
            if (!brick.isCommentedOut()) {
                brick.addActionToSequence(sprite, scriptSequenceAction);
            }
        }
        for (Brick brick2 : this.elseBranchBricks) {
            if (!brick2.isCommentedOut()) {
                brick2.addActionToSequence(sprite, scriptSequenceAction2);
            }
        }
        sequence.addAction(sprite.getActionFactory().createRaspiIfLogicActionAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.IF_CONDITION), scriptSequenceAction, scriptSequenceAction2));
    }
}

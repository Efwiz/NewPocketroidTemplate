package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/WaitUntilBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class WaitUntilBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_wait_until;
    }

    public WaitUntilBrick() {
        addAllowedBrickField(Brick.BrickField.IF_CONDITION, R.id.brick_wait_until_edit_text);
    }

    public WaitUntilBrick(Formula formula) {
        this();
        setFormulaWithBrickField(Brick.BrickField.IF_CONDITION, formula);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createWaitUntilAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.IF_CONDITION)));
    }
}

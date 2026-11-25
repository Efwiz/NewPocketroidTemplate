package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/WaitThreadBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class WaitThreadBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_wait_thread;
    }

    public WaitThreadBrick() {
        addAllowedBrickField(Brick.BrickField.IF_CONDITION, R.id.brick_wait_thread_id_edit);
    }

    public WaitThreadBrick(Formula threadId) {
        this();
        setFormulaWithBrickField(Brick.BrickField.IF_CONDITION, threadId);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createWaitThreadAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.IF_CONDITION)));
    }
}

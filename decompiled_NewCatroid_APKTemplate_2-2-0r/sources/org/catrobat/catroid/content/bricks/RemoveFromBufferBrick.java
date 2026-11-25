package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/RemoveFromBufferBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class RemoveFromBufferBrick extends FormulaBrick {
    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_remove_from_buffer;
    }

    public RemoveFromBufferBrick() {
        addAllowedBrickField(Brick.BrickField.NAME, R.id.brick_buffer_name);
    }

    public RemoveFromBufferBrick(Formula n) {
        this();
        setFormulaWithBrickField(Brick.BrickField.NAME, n);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().removeBufferAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.NAME)));
    }
}

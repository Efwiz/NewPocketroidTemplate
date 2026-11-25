package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetBufferOnlyBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetBufferOnlyBrick extends FormulaBrick {
    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_buffer_only;
    }

    public SetBufferOnlyBrick() {
        addAllowedBrickField(Brick.BrickField.TIME, R.id.brick_buffer_state);
    }

    public SetBufferOnlyBrick(Formula s) {
        this();
        setFormulaWithBrickField(Brick.BrickField.TIME, s);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetBufferOnlyAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.TIME)));
    }
}

package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SaveBufferBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SaveBufferBrick extends FormulaBrick {
    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_save_buffer;
    }

    public SaveBufferBrick() {
        addAllowedBrickField(Brick.BrickField.NAME, R.id.brick_buffer_name);
        addAllowedBrickField(Brick.BrickField.STRING_VALUE, R.id.brick_buffer_file);
    }

    public SaveBufferBrick(Formula n, Formula f) {
        this();
        setFormulaWithBrickField(Brick.BrickField.NAME, n);
        setFormulaWithBrickField(Brick.BrickField.STRING_VALUE, f);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSaveBufferAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.NAME), getFormulaWithBrickField(Brick.BrickField.STRING_VALUE)));
    }
}

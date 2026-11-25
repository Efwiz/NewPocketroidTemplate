package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/RunningStitchBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class RunningStitchBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_running_stitch_with_length;
    }

    public RunningStitchBrick() {
        addAllowedBrickField(Brick.BrickField.EMBROIDERY_LENGTH, R.id.brick_running_stitch_edit_text_length);
    }

    public RunningStitchBrick(Formula length) {
        this();
        setFormulaWithBrickField(Brick.BrickField.EMBROIDERY_LENGTH, length);
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick
    public Brick.BrickField getDefaultBrickField() {
        return Brick.BrickField.EMBROIDERY_LENGTH;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createRunningStitchAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.EMBROIDERY_LENGTH)));
    }
}

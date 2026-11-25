package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/StopRecordingBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class StopRecordingBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_stop_recording_and_save;
    }

    public StopRecordingBrick() {
        addAllowedBrickField(Brick.BrickField.FILE_NAME, R.id.brick_recording_filename);
    }

    public StopRecordingBrick(String fileName) {
        this(new Formula(fileName));
    }

    public StopRecordingBrick(Formula fileNameFormula) {
        this();
        setFormulaWithBrickField(Brick.BrickField.FILE_NAME, fileNameFormula);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createStopRecordingAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.FILE_NAME)));
    }
}

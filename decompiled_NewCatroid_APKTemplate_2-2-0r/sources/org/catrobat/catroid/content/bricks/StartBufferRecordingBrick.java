package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/StartBufferRecordingBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class StartBufferRecordingBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_start_buffer_recording;
    }

    public StartBufferRecordingBrick() {
        addAllowedBrickField(Brick.BrickField.VALUE_1, R.id.brick_start_buffer_recording_edit_buf);
        addAllowedBrickField(Brick.BrickField.VALUE_2, R.id.brick_start_buffer_recording_edit_file);
        addAllowedBrickField(Brick.BrickField.VALUE_3, R.id.brick_start_buffer_recording_edit_fps);
        addAllowedBrickField(Brick.BrickField.VALUE_4, R.id.brick_start_buffer_recording_edit_bitrate);
    }

    public StartBufferRecordingBrick(String bufferName, String fileName, int fps, int bitrate) {
        this(new Formula(bufferName), new Formula(fileName), new Formula(Integer.valueOf(fps)), new Formula(Integer.valueOf(bitrate)));
    }

    public StartBufferRecordingBrick(Formula bufferName, Formula fileName, Formula fps, Formula bitrate) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VALUE_1, bufferName);
        setFormulaWithBrickField(Brick.BrickField.VALUE_2, fileName);
        setFormulaWithBrickField(Brick.BrickField.VALUE_3, fps);
        setFormulaWithBrickField(Brick.BrickField.VALUE_4, bitrate);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createStartBufferRecordingAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VALUE_1), getFormulaWithBrickField(Brick.BrickField.VALUE_2), getFormulaWithBrickField(Brick.BrickField.VALUE_3), getFormulaWithBrickField(Brick.BrickField.VALUE_4)));
    }
}

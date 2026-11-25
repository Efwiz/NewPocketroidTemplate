package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/PrepareSoundBrick2.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class PrepareSoundBrick2 extends FormulaBrick {
    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_prepare_sound2;
    }

    public PrepareSoundBrick2() {
        addAllowedBrickField(Brick.BrickField.FILE_NAME, R.id.brick_prepare_sound_file_edit);
        addAllowedBrickField(Brick.BrickField.SOUND_NAME, R.id.brick_prepare_sound_name_edit);
    }

    public PrepareSoundBrick2(String fileName, String soundName) {
        this(new Formula(fileName), new Formula(soundName));
    }

    public PrepareSoundBrick2(Formula fileName, Formula soundName) {
        this();
        setFormulaWithBrickField(Brick.BrickField.FILE_NAME, fileName);
        setFormulaWithBrickField(Brick.BrickField.SOUND_NAME, soundName);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createPrepareSound2Action(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.FILE_NAME), getFormulaWithBrickField(Brick.BrickField.SOUND_NAME)));
    }
}

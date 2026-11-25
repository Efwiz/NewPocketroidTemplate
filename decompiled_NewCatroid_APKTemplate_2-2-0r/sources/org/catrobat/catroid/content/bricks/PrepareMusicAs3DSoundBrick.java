package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/PrepareMusicAs3DSoundBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class PrepareMusicAs3DSoundBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_prepare_music;
    }

    public PrepareMusicAs3DSoundBrick() {
        addAllowedBrickField(Brick.BrickField.FILE_NAME, R.id.brick_prepare_music_file_edit);
        addAllowedBrickField(Brick.BrickField.SOUND_NAME, R.id.brick_prepare_music_name_edit);
    }

    public PrepareMusicAs3DSoundBrick(String fileName, String soundName) {
        this(new Formula(fileName), new Formula(soundName));
    }

    public PrepareMusicAs3DSoundBrick(Formula fileName, Formula soundName) {
        this();
        setFormulaWithBrickField(Brick.BrickField.FILE_NAME, fileName);
        setFormulaWithBrickField(Brick.BrickField.SOUND_NAME, soundName);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createPrepareMusicAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.FILE_NAME), getFormulaWithBrickField(Brick.BrickField.SOUND_NAME)));
    }
}

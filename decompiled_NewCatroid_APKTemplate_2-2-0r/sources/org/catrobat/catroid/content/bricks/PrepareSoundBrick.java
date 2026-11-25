package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/PrepareSoundBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class PrepareSoundBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_prepare_sound;
    }

    public PrepareSoundBrick() {
        addAllowedBrickField(Brick.BrickField.FILE_NAME, R.id.brick_prepare_sound_file_name);
        addAllowedBrickField(Brick.BrickField.CACHE_NAME, R.id.brick_prepare_sound_cache_name);
    }

    public PrepareSoundBrick(String fileName, String cacheName) {
        this(new Formula(fileName), new Formula(cacheName));
    }

    public PrepareSoundBrick(Formula fileName, Formula cacheName) {
        this();
        setFormulaWithBrickField(Brick.BrickField.FILE_NAME, fileName);
        setFormulaWithBrickField(Brick.BrickField.CACHE_NAME, cacheName);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createPrepareSoundAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.FILE_NAME), getFormulaWithBrickField(Brick.BrickField.CACHE_NAME)));
    }
}

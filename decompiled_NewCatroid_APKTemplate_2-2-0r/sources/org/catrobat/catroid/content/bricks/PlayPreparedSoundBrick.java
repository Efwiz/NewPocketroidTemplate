package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/PlayPreparedSoundBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class PlayPreparedSoundBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_play_prepared_sound;
    }

    public PlayPreparedSoundBrick() {
        addAllowedBrickField(Brick.BrickField.CACHE_NAME, R.id.brick_play_prepared_sound_cache_name);
    }

    public PlayPreparedSoundBrick(String cacheName) {
        this(new Formula(cacheName));
    }

    public PlayPreparedSoundBrick(Formula cacheName) {
        this();
        setFormulaWithBrickField(Brick.BrickField.CACHE_NAME, cacheName);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createPlayPreparedSoundAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.CACHE_NAME)));
    }
}

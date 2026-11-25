package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/PauseForBeatsBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class PauseForBeatsBrick extends FormulaBrick {
    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_pause_for_beats;
    }

    public PauseForBeatsBrick() {
        addAllowedBrickField(Brick.BrickField.BEATS_TO_PAUSE, R.id.brick_pause_for_beats_edit_text);
    }

    public PauseForBeatsBrick(float pausedBeats) {
        this(new Formula(Float.valueOf(pausedBeats)));
    }

    public PauseForBeatsBrick(Formula formula) {
        this();
        setFormulaWithBrickField(Brick.BrickField.BEATS_TO_PAUSE, formula);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createPauseForBeatsAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.BEATS_TO_PAUSE)));
    }
}

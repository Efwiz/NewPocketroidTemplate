package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetSoundInstancePitchBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetSoundInstancePitchBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_sound_pitch;
    }

    public SetSoundInstancePitchBrick() {
        addAllowedBrickField(Brick.BrickField.NAME, R.id.sound_inst_name_p);
        addAllowedBrickField(Brick.BrickField.PITCH, R.id.sound_inst_pitch);
    }

    public SetSoundInstancePitchBrick(String name2, float pitch) {
        this(new Formula(name2), new Formula(Float.valueOf(pitch)));
    }

    public SetSoundInstancePitchBrick(Formula name2, Formula pitch) {
        this();
        setFormulaWithBrickField(Brick.BrickField.NAME, name2);
        setFormulaWithBrickField(Brick.BrickField.PITCH, pitch);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetSoundInstancePitchAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.NAME), getFormulaWithBrickField(Brick.BrickField.PITCH)));
    }
}

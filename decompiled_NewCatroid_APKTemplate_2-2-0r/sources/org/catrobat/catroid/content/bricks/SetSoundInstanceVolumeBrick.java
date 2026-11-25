package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetSoundInstanceVolumeBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetSoundInstanceVolumeBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_sound_volume;
    }

    public SetSoundInstanceVolumeBrick() {
        addAllowedBrickField(Brick.BrickField.NAME, R.id.sound_inst_name);
        addAllowedBrickField(Brick.BrickField.VOLUME, R.id.sound_inst_vol);
    }

    public SetSoundInstanceVolumeBrick(String name2, float vol) {
        this(new Formula(name2), new Formula(Float.valueOf(vol)));
    }

    public SetSoundInstanceVolumeBrick(Formula name2, Formula vol) {
        this();
        setFormulaWithBrickField(Brick.BrickField.NAME, name2);
        setFormulaWithBrickField(Brick.BrickField.VOLUME, vol);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetSoundInstanceVolumeAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.NAME), getFormulaWithBrickField(Brick.BrickField.VOLUME)));
    }
}

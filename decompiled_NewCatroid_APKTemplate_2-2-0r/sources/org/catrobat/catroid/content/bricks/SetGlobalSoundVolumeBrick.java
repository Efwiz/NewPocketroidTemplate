package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetGlobalSoundVolumeBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetGlobalSoundVolumeBrick extends FormulaBrick {
    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_set_global_sound_volume;
    }

    public SetGlobalSoundVolumeBrick() {
        addAllowedBrickField(Brick.BrickField.VOLUME, R.id.brick_set_global_sound_volume_edit);
    }

    public SetGlobalSoundVolumeBrick(double volume) {
        this(new Formula(Double.valueOf(volume)));
    }

    public SetGlobalSoundVolumeBrick(Formula volume) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VOLUME, volume);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetGlobalSoundVolumeAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VOLUME)));
    }
}

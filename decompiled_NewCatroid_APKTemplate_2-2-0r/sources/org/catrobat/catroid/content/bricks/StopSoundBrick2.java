package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/StopSoundBrick2.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class StopSoundBrick2 extends FormulaBrick {
    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_stop_sound_3d;
    }

    public StopSoundBrick2() {
        addAllowedBrickField(Brick.BrickField.INSTANCE_NAME, R.id.brick_stop_sound_3d_instance_edit);
    }

    public StopSoundBrick2(String instanceName) {
        this(new Formula(instanceName));
    }

    public StopSoundBrick2(Formula instanceName) {
        this();
        setFormulaWithBrickField(Brick.BrickField.INSTANCE_NAME, instanceName);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createStopSoundAction2(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.INSTANCE_NAME)));
    }
}

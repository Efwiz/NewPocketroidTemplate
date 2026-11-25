package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetVolumeToBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetVolumeToBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_set_volume_to;
    }

    public SetVolumeToBrick() {
        addAllowedBrickField(Brick.BrickField.VOLUME, R.id.brick_set_volume_to_edit_text);
    }

    public SetVolumeToBrick(double volume) {
        this(new Formula(Double.valueOf(volume)));
    }

    public SetVolumeToBrick(Formula formula) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VOLUME, formula);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetVolumeToAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VOLUME)));
    }
}

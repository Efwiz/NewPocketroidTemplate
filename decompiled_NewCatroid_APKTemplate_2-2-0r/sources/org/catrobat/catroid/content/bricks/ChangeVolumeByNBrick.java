package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/ChangeVolumeByNBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ChangeVolumeByNBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_change_volume_by;
    }

    public ChangeVolumeByNBrick() {
        addAllowedBrickField(Brick.BrickField.VOLUME_CHANGE, R.id.brick_change_volume_by_edit_text);
    }

    public ChangeVolumeByNBrick(double changeVolumeValue) {
        this(new Formula(Double.valueOf(changeVolumeValue)));
    }

    public ChangeVolumeByNBrick(Formula formula) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VOLUME_CHANGE, formula);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createChangeVolumeByNAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VOLUME_CHANGE)));
    }
}

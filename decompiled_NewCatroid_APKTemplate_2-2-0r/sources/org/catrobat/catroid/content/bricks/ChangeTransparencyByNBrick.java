package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/ChangeTransparencyByNBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ChangeTransparencyByNBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_change_transparency;
    }

    public ChangeTransparencyByNBrick() {
        addAllowedBrickField(Brick.BrickField.TRANSPARENCY_CHANGE, R.id.brick_change_transparency_edit_text);
    }

    public ChangeTransparencyByNBrick(double changeTransparencyValue) {
        this(new Formula(Double.valueOf(changeTransparencyValue)));
    }

    public ChangeTransparencyByNBrick(Formula formula) {
        this();
        setFormulaWithBrickField(Brick.BrickField.TRANSPARENCY_CHANGE, formula);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createChangeTransparencyByNAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.TRANSPARENCY_CHANGE)));
    }
}

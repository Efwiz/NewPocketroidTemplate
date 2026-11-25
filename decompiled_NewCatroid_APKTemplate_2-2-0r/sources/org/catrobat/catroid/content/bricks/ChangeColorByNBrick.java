package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/ChangeColorByNBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ChangeColorByNBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_change_color_by;
    }

    public ChangeColorByNBrick() {
        addAllowedBrickField(Brick.BrickField.COLOR_CHANGE, R.id.brick_change_color_by_edit_text);
    }

    public ChangeColorByNBrick(double changeColorValue) {
        this(new Formula(Double.valueOf(changeColorValue)));
    }

    public ChangeColorByNBrick(Formula formula) {
        this();
        setFormulaWithBrickField(Brick.BrickField.COLOR_CHANGE, formula);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createChangeColorByNAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.COLOR_CHANGE)));
    }
}

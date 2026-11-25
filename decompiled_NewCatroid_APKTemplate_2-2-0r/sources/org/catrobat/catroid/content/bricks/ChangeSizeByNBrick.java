package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/ChangeSizeByNBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ChangeSizeByNBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_change_size_by_n;
    }

    public ChangeSizeByNBrick() {
        addAllowedBrickField(Brick.BrickField.SIZE_CHANGE, R.id.brick_change_size_by_edit_text);
    }

    public ChangeSizeByNBrick(double sizeValue) {
        this(new Formula(Double.valueOf(sizeValue)));
    }

    public ChangeSizeByNBrick(Formula formula) {
        this();
        setFormulaWithBrickField(Brick.BrickField.SIZE_CHANGE, formula);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createChangeSizeByNAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.SIZE_CHANGE)));
    }
}

package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetPenSizeBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetPenSizeBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_set_pen_size;
    }

    public SetPenSizeBrick() {
        addAllowedBrickField(Brick.BrickField.PEN_SIZE, R.id.brick_set_pen_size_edit_text);
    }

    public SetPenSizeBrick(double penSize) {
        this(new Formula(Double.valueOf(penSize)));
    }

    public SetPenSizeBrick(Formula formula) {
        this();
        setFormulaWithBrickField(Brick.BrickField.PEN_SIZE, formula);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetPenSizeAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.PEN_SIZE)));
    }
}

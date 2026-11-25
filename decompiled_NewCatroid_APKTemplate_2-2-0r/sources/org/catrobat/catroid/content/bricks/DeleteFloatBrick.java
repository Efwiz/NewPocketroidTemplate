package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/DeleteFloatBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class DeleteFloatBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_delete_float;
    }

    public DeleteFloatBrick() {
        addAllowedBrickField(Brick.BrickField.LOOK_INDEX, R.id.brick_delete_float_index);
        addAllowedBrickField(Brick.BrickField.FLOAT_ARRAY, R.id.brick_delete_float_float);
    }

    public DeleteFloatBrick(String value, Integer val2) {
        this(new Formula(value), new Formula(val2));
    }

    public DeleteFloatBrick(Formula formula, Formula form2) {
        this();
        setFormulaWithBrickField(Brick.BrickField.LOOK_INDEX, form2);
        setFormulaWithBrickField(Brick.BrickField.FLOAT_ARRAY, formula);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createDeleteFloat(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.FLOAT_ARRAY), getFormulaWithBrickField(Brick.BrickField.LOOK_INDEX)));
    }
}

package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/PutFloatBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class PutFloatBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_put_float;
    }

    public PutFloatBrick() {
        addAllowedBrickField(Brick.BrickField.FLOAT_ARRAY, R.id.brick_put_float_float);
        addAllowedBrickField(Brick.BrickField.VALUE, R.id.brick_put_float_value);
        addAllowedBrickField(Brick.BrickField.LOOK_INDEX, R.id.brick_put_float_index);
    }

    public PutFloatBrick(String value, Integer val2, Integer val3) {
        this(new Formula(value), new Formula(val2), new Formula(val3));
    }

    public PutFloatBrick(Formula formula, Formula form2, Formula form3) {
        this();
        setFormulaWithBrickField(Brick.BrickField.FLOAT_ARRAY, formula);
        setFormulaWithBrickField(Brick.BrickField.VALUE, form2);
        setFormulaWithBrickField(Brick.BrickField.LOOK_INDEX, form3);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createPutFloat(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.FLOAT_ARRAY), getFormulaWithBrickField(Brick.BrickField.VALUE), getFormulaWithBrickField(Brick.BrickField.LOOK_INDEX)));
    }
}

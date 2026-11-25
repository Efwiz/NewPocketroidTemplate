package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/PtCreateTensorBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class PtCreateTensorBrick extends FormulaBrick {
    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_pt_create_tensor;
    }

    public PtCreateTensorBrick() {
        addAllowedBrickField(Brick.BrickField.NAME, R.id.brick_pt_create_name);
        addAllowedBrickField(Brick.BrickField.VALUE_1, R.id.brick_pt_create_shape);
        addAllowedBrickField(Brick.BrickField.VALUE_2, R.id.brick_pt_create_value);
        addAllowedBrickField(Brick.BrickField.VALUE_3, R.id.brick_pt_create_trainable);
    }

    public PtCreateTensorBrick(String str, String str2, float f, boolean z) {
        this();
        setFormulaWithBrickField(Brick.BrickField.NAME, new Formula(str));
        setFormulaWithBrickField(Brick.BrickField.VALUE_1, new Formula(str2));
        setFormulaWithBrickField(Brick.BrickField.VALUE_2, new Formula(Float.valueOf(f)));
        setFormulaWithBrickField(Brick.BrickField.VALUE_3, new Formula(Integer.valueOf(z ? 1 : 0)));
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createPtCreateTensorAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.NAME), getFormulaWithBrickField(Brick.BrickField.VALUE_1), getFormulaWithBrickField(Brick.BrickField.VALUE_2), getFormulaWithBrickField(Brick.BrickField.VALUE_3)));
    }
}

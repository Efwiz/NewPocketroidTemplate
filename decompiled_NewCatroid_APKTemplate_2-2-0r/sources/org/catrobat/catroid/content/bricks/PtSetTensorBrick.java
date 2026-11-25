package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/PtSetTensorBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class PtSetTensorBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_pt_set_tensor;
    }

    public PtSetTensorBrick() {
        addAllowedBrickField(Brick.BrickField.NAME, R.id.brick_pt_set_tensor_name);
        addAllowedBrickField(Brick.BrickField.VALUE_1, R.id.brick_pt_set_tensor_data);
    }

    public PtSetTensorBrick(String name2, String data) {
        this();
        setFormulaWithBrickField(Brick.BrickField.NAME, new Formula(name2));
        setFormulaWithBrickField(Brick.BrickField.VALUE_1, new Formula(data));
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createPtSetTensorAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.NAME), getFormulaWithBrickField(Brick.BrickField.VALUE_1)));
    }
}

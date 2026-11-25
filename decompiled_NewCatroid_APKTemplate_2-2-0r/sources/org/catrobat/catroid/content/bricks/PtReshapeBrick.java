package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/PtReshapeBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class PtReshapeBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_pt_reshape;
    }

    public PtReshapeBrick() {
        addAllowedBrickField(Brick.BrickField.NAME, R.id.brick_pt_reshape_name);
        addAllowedBrickField(Brick.BrickField.VALUE_1, R.id.brick_pt_reshape_shape);
    }

    public PtReshapeBrick(String tensorName, String newShape) {
        this();
        setFormulaWithBrickField(Brick.BrickField.NAME, new Formula(tensorName));
        setFormulaWithBrickField(Brick.BrickField.VALUE_1, new Formula(newShape));
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createPtReshapeAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.NAME), getFormulaWithBrickField(Brick.BrickField.VALUE_1)));
    }
}

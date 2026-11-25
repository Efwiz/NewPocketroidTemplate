package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/PtSetByIndexBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class PtSetByIndexBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_pt_set_by_index;
    }

    public PtSetByIndexBrick() {
        addAllowedBrickField(Brick.BrickField.NAME, R.id.brick_pt_set_index_name);
        addAllowedBrickField(Brick.BrickField.VALUE_1, R.id.brick_pt_set_index_idx);
        addAllowedBrickField(Brick.BrickField.VALUE_2, R.id.brick_pt_set_index_val);
    }

    public PtSetByIndexBrick(String name2, int index, float value) {
        this();
        setFormulaWithBrickField(Brick.BrickField.NAME, new Formula(name2));
        setFormulaWithBrickField(Brick.BrickField.VALUE_1, new Formula(Integer.valueOf(index)));
        setFormulaWithBrickField(Brick.BrickField.VALUE_2, new Formula(Float.valueOf(value)));
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createPtSetByIndexAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.NAME), getFormulaWithBrickField(Brick.BrickField.VALUE_1), getFormulaWithBrickField(Brick.BrickField.VALUE_2)));
    }
}

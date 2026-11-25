package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetCornerOffsetsBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetCornerOffsetsBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_set_corner_offsets;
    }

    public SetCornerOffsetsBrick() {
        addAllowedBrickField(Brick.BrickField.VALUE_1, R.id.brick_set_corner_offsets_tlx);
        addAllowedBrickField(Brick.BrickField.VALUE_2, R.id.brick_set_corner_offsets_tly);
        addAllowedBrickField(Brick.BrickField.VALUE_3, R.id.brick_set_corner_offsets_trx);
        addAllowedBrickField(Brick.BrickField.VALUE_4, R.id.brick_set_corner_offsets_try);
        addAllowedBrickField(Brick.BrickField.VALUE_5, R.id.brick_set_corner_offsets_brx);
        addAllowedBrickField(Brick.BrickField.VALUE_6, R.id.brick_set_corner_offsets_bry);
        addAllowedBrickField(Brick.BrickField.VALUE_7, R.id.brick_set_corner_offsets_blx);
        addAllowedBrickField(Brick.BrickField.VALUE_8, R.id.brick_set_corner_offsets_bly);
    }

    public SetCornerOffsetsBrick(Formula tlx, Formula tly, Formula trx, Formula tryF, Formula brx, Formula bry, Formula blx, Formula bly) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VALUE_1, tlx);
        setFormulaWithBrickField(Brick.BrickField.VALUE_2, tly);
        setFormulaWithBrickField(Brick.BrickField.VALUE_3, trx);
        setFormulaWithBrickField(Brick.BrickField.VALUE_4, tryF);
        setFormulaWithBrickField(Brick.BrickField.VALUE_5, brx);
        setFormulaWithBrickField(Brick.BrickField.VALUE_6, bry);
        setFormulaWithBrickField(Brick.BrickField.VALUE_7, blx);
        setFormulaWithBrickField(Brick.BrickField.VALUE_8, bly);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetCornerOffsetsAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VALUE_1), getFormulaWithBrickField(Brick.BrickField.VALUE_2), getFormulaWithBrickField(Brick.BrickField.VALUE_3), getFormulaWithBrickField(Brick.BrickField.VALUE_4), getFormulaWithBrickField(Brick.BrickField.VALUE_5), getFormulaWithBrickField(Brick.BrickField.VALUE_6), getFormulaWithBrickField(Brick.BrickField.VALUE_7), getFormulaWithBrickField(Brick.BrickField.VALUE_8)));
    }
}

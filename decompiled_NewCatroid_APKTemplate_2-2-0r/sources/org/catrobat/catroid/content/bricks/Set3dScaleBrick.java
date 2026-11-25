package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/Set3dScaleBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class Set3dScaleBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_set_3d_scale;
    }

    public Set3dScaleBrick() {
        addAllowedBrickField(Brick.BrickField.VALUE_1, R.id.brick_set_3d_scale_object_id);
        addAllowedBrickField(Brick.BrickField.VALUE_2, R.id.brick_set_3d_scale_x);
        addAllowedBrickField(Brick.BrickField.VALUE_3, R.id.brick_set_3d_scale_y);
        addAllowedBrickField(Brick.BrickField.VALUE_4, R.id.brick_set_3d_scale_z);
    }

    public Set3dScaleBrick(String id2, double x, double y, double z) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VALUE_1, new Formula(id2));
        setFormulaWithBrickField(Brick.BrickField.VALUE_2, new Formula(Double.valueOf(x)));
        setFormulaWithBrickField(Brick.BrickField.VALUE_3, new Formula(Double.valueOf(y)));
        setFormulaWithBrickField(Brick.BrickField.VALUE_4, new Formula(Double.valueOf(z)));
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSet3dScaleAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VALUE_1), getFormulaWithBrickField(Brick.BrickField.VALUE_2), getFormulaWithBrickField(Brick.BrickField.VALUE_3), getFormulaWithBrickField(Brick.BrickField.VALUE_4)));
    }
}

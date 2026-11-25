package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/Set3dFrictionBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class Set3dFrictionBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_set_friction;
    }

    public Set3dFrictionBrick() {
        addAllowedBrickField(Brick.BrickField.VALUE_1, R.id.brick_set_friction_id);
        addAllowedBrickField(Brick.BrickField.VALUE_2, R.id.brick_set_friction_value);
    }

    public Set3dFrictionBrick(String objectId, double friction) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VALUE_1, new Formula(objectId));
        setFormulaWithBrickField(Brick.BrickField.VALUE_2, new Formula(Double.valueOf(friction)));
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSet3dFrictionAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VALUE_1), getFormulaWithBrickField(Brick.BrickField.VALUE_2)));
    }
}

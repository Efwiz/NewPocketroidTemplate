package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetRestitutionBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetRestitutionBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_set_restitution;
    }

    public SetRestitutionBrick() {
        addAllowedBrickField(Brick.BrickField.VALUE_1, R.id.brick_set_restitution_id);
        addAllowedBrickField(Brick.BrickField.VALUE_2, R.id.brick_set_restitution_value);
    }

    public SetRestitutionBrick(String objectId, double restitution) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VALUE_1, new Formula(objectId));
        setFormulaWithBrickField(Brick.BrickField.VALUE_2, new Formula(Double.valueOf(restitution)));
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetRestitutionAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VALUE_1), getFormulaWithBrickField(Brick.BrickField.VALUE_2)));
    }
}

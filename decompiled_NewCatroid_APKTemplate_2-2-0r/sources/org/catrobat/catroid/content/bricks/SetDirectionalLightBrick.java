package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetDirectionalLightBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetDirectionalLightBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_set_directional_light;
    }

    public SetDirectionalLightBrick() {
        addAllowedBrickField(Brick.BrickField.VALUE_1, R.id.brick_set_directional_light_id);
        addAllowedBrickField(Brick.BrickField.VALUE_2, R.id.brick_set_directional_light_r);
        addAllowedBrickField(Brick.BrickField.VALUE_3, R.id.brick_set_directional_light_g);
        addAllowedBrickField(Brick.BrickField.VALUE_4, R.id.brick_set_directional_light_b);
        addAllowedBrickField(Brick.BrickField.VALUE_5, R.id.brick_set_directional_light_x);
        addAllowedBrickField(Brick.BrickField.VALUE_6, R.id.brick_set_directional_light_y);
        addAllowedBrickField(Brick.BrickField.VALUE_7, R.id.brick_set_directional_light_z);
    }

    public SetDirectionalLightBrick(String id2, double r, double g, double b, double dx, double dy, double dz) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VALUE_1, new Formula(id2));
        setFormulaWithBrickField(Brick.BrickField.VALUE_2, new Formula(Double.valueOf(r)));
        setFormulaWithBrickField(Brick.BrickField.VALUE_3, new Formula(Double.valueOf(g)));
        setFormulaWithBrickField(Brick.BrickField.VALUE_4, new Formula(Double.valueOf(b)));
        setFormulaWithBrickField(Brick.BrickField.VALUE_5, new Formula(Double.valueOf(dx)));
        setFormulaWithBrickField(Brick.BrickField.VALUE_6, new Formula(Double.valueOf(dy)));
        setFormulaWithBrickField(Brick.BrickField.VALUE_7, new Formula(Double.valueOf(dz)));
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetDirectionalLightAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VALUE_1), getFormulaWithBrickField(Brick.BrickField.VALUE_2), getFormulaWithBrickField(Brick.BrickField.VALUE_3), getFormulaWithBrickField(Brick.BrickField.VALUE_4), getFormulaWithBrickField(Brick.BrickField.VALUE_5), getFormulaWithBrickField(Brick.BrickField.VALUE_6), getFormulaWithBrickField(Brick.BrickField.VALUE_7)));
    }
}

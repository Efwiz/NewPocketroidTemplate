package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/Fast2DSetGravityBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class Fast2DSetGravityBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_fast2d_set_gravity;
    }

    public Fast2DSetGravityBrick() {
        addAllowedBrickField(Brick.BrickField.VALUE_1, R.id.brick_fast2d_set_gravity_x);
        addAllowedBrickField(Brick.BrickField.VALUE_2, R.id.brick_fast2d_set_gravity_y);
    }

    public Fast2DSetGravityBrick(float gx, float gy) {
        this(new Formula(Float.valueOf(gx)), new Formula(Float.valueOf(gy)));
    }

    public Fast2DSetGravityBrick(Formula gxFormula, Formula gyFormula) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VALUE_1, gxFormula);
        setFormulaWithBrickField(Brick.BrickField.VALUE_2, gyFormula);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createFast2DSetGravityAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VALUE_1), getFormulaWithBrickField(Brick.BrickField.VALUE_2)));
    }
}

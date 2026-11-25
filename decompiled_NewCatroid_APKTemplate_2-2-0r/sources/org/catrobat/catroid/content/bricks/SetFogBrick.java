package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetFogBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetFogBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_set_fog;
    }

    public SetFogBrick() {
        addAllowedBrickField(Brick.BrickField.FORMULA_1, R.id.brick_set_fog_r_edit);
        addAllowedBrickField(Brick.BrickField.FORMULA_2, R.id.brick_set_fog_g_edit);
        addAllowedBrickField(Brick.BrickField.FORMULA_3, R.id.brick_set_fog_b_edit);
        addAllowedBrickField(Brick.BrickField.FORMULA_4, R.id.brick_set_fog_density_edit);
    }

    public SetFogBrick(float r, float g, float b, float density) {
        this(new Formula(Float.valueOf(r)), new Formula(Float.valueOf(g)), new Formula(Float.valueOf(b)), new Formula(Float.valueOf(density)));
    }

    public SetFogBrick(Formula r, Formula g, Formula b, Formula density) {
        this();
        setFormulaWithBrickField(Brick.BrickField.FORMULA_1, r);
        setFormulaWithBrickField(Brick.BrickField.FORMULA_2, g);
        setFormulaWithBrickField(Brick.BrickField.FORMULA_3, b);
        setFormulaWithBrickField(Brick.BrickField.FORMULA_4, density);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetFogAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.FORMULA_1), getFormulaWithBrickField(Brick.BrickField.FORMULA_2), getFormulaWithBrickField(Brick.BrickField.FORMULA_3), getFormulaWithBrickField(Brick.BrickField.FORMULA_4)));
    }
}

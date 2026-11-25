package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/Fast2DSetColorBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class Fast2DSetColorBrick extends FormulaBrick {
    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_fast2d_set_color;
    }

    public Fast2DSetColorBrick() {
        addAllowedBrickField(Brick.BrickField.NAME, R.id.brick_fast2d_color_id);
        addAllowedBrickField(Brick.BrickField.X_POSITION, R.id.brick_fast2d_color_r);
        addAllowedBrickField(Brick.BrickField.Y_POSITION, R.id.brick_fast2d_color_g);
        addAllowedBrickField(Brick.BrickField.ROTATION, R.id.brick_fast2d_color_b);
        addAllowedBrickField(Brick.BrickField.SIZE, R.id.brick_fast2d_color_a);
    }

    public Fast2DSetColorBrick(String id2, Double r, Double g, Double b, Double a) {
        this(new Formula(id2), new Formula(r), new Formula(g), new Formula(b), new Formula(a));
    }

    public Fast2DSetColorBrick(Formula id2, Formula r, Formula g, Formula b, Formula a) {
        this();
        setFormulaWithBrickField(Brick.BrickField.NAME, id2);
        setFormulaWithBrickField(Brick.BrickField.X_POSITION, r);
        setFormulaWithBrickField(Brick.BrickField.Y_POSITION, g);
        setFormulaWithBrickField(Brick.BrickField.ROTATION, b);
        setFormulaWithBrickField(Brick.BrickField.SIZE, a);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createFast2DSetColorAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.NAME), getFormulaWithBrickField(Brick.BrickField.X_POSITION), getFormulaWithBrickField(Brick.BrickField.Y_POSITION), getFormulaWithBrickField(Brick.BrickField.ROTATION), getFormulaWithBrickField(Brick.BrickField.SIZE)));
    }
}

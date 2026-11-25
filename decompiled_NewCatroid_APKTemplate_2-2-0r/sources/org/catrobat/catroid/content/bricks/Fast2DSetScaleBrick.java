package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/Fast2DSetScaleBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class Fast2DSetScaleBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_fast2d_set_scale;
    }

    public Fast2DSetScaleBrick() {
        addAllowedBrickField(Brick.BrickField.NAME, R.id.brick_fast2d_set_scale_id);
        addAllowedBrickField(Brick.BrickField.X_SCALE, R.id.brick_fast2d_set_scale_x);
        addAllowedBrickField(Brick.BrickField.Y_SCALE, R.id.brick_fast2d_set_scale_y);
    }

    public Fast2DSetScaleBrick(String id2, Double sx, Double sy) {
        this(new Formula(id2), new Formula(sx), new Formula(sy));
    }

    public Fast2DSetScaleBrick(Formula id2, Formula sx, Formula sy) {
        this();
        setFormulaWithBrickField(Brick.BrickField.NAME, id2);
        setFormulaWithBrickField(Brick.BrickField.X_SCALE, sx);
        setFormulaWithBrickField(Brick.BrickField.Y_SCALE, sy);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createFast2DSetScaleAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.NAME), getFormulaWithBrickField(Brick.BrickField.X_SCALE), getFormulaWithBrickField(Brick.BrickField.Y_SCALE)));
    }
}

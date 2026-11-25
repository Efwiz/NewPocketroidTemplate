package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/CastRayBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class CastRayBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_cast_ray;
    }

    public CastRayBrick() {
        addAllowedBrickField(Brick.BrickField.VALUE_1, R.id.brick_cast_ray_name);
        addAllowedBrickField(Brick.BrickField.VALUE_2, R.id.brick_cast_ray_from_x);
        addAllowedBrickField(Brick.BrickField.VALUE_3, R.id.brick_cast_ray_from_y);
        addAllowedBrickField(Brick.BrickField.VALUE_4, R.id.brick_cast_ray_from_z);
        addAllowedBrickField(Brick.BrickField.VALUE_5, R.id.brick_cast_ray_dir_x);
        addAllowedBrickField(Brick.BrickField.VALUE_6, R.id.brick_cast_ray_dir_y);
        addAllowedBrickField(Brick.BrickField.VALUE_7, R.id.brick_cast_ray_dir_z);
    }

    public CastRayBrick(Formula rayName, Formula fromX, Formula fromY, Formula fromZ, Formula dirX, Formula dirY, Formula dirZ) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VALUE_1, rayName);
        setFormulaWithBrickField(Brick.BrickField.VALUE_2, fromX);
        setFormulaWithBrickField(Brick.BrickField.VALUE_3, fromY);
        setFormulaWithBrickField(Brick.BrickField.VALUE_4, fromZ);
        setFormulaWithBrickField(Brick.BrickField.VALUE_5, dirX);
        setFormulaWithBrickField(Brick.BrickField.VALUE_6, dirY);
        setFormulaWithBrickField(Brick.BrickField.VALUE_7, dirZ);
    }

    public CastRayBrick(String rayName, double fromX, double fromY, double fromZ, double dirX, double dirY, double dirZ) {
        this(new Formula(rayName), new Formula(Double.valueOf(fromX)), new Formula(Double.valueOf(fromY)), new Formula(Double.valueOf(fromZ)), new Formula(Double.valueOf(dirX)), new Formula(Double.valueOf(dirY)), new Formula(Double.valueOf(dirZ)));
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createCastRayAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VALUE_1), getFormulaWithBrickField(Brick.BrickField.VALUE_2), getFormulaWithBrickField(Brick.BrickField.VALUE_3), getFormulaWithBrickField(Brick.BrickField.VALUE_4), getFormulaWithBrickField(Brick.BrickField.VALUE_5), getFormulaWithBrickField(Brick.BrickField.VALUE_6), getFormulaWithBrickField(Brick.BrickField.VALUE_7)));
    }
}

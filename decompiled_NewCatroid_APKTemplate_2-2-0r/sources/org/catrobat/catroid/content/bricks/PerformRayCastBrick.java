package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/PerformRayCastBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class PerformRayCastBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_perform_raycast;
    }

    public PerformRayCastBrick() {
        addAllowedBrickField(Brick.BrickField.RAY_ID, R.id.brick_ray_id_edit_text);
        addAllowedBrickField(Brick.BrickField.X_START, R.id.brick_start_x_edit_text);
        addAllowedBrickField(Brick.BrickField.Y_START, R.id.brick_start_y_edit_text);
        addAllowedBrickField(Brick.BrickField.X_END, R.id.brick_end_x_edit_text);
        addAllowedBrickField(Brick.BrickField.Y_END, R.id.brick_end_y_edit_text);
    }

    public PerformRayCastBrick(String rayId, int sX, int sY, int eX, int eY) {
        this(new Formula(rayId), new Formula(Integer.valueOf(sX)), new Formula(Integer.valueOf(sY)), new Formula(Integer.valueOf(eX)), new Formula(Integer.valueOf(eY)));
    }

    public PerformRayCastBrick(Formula rId, Formula sX, Formula sY, Formula eX, Formula eY) {
        this();
        setFormulaWithBrickField(Brick.BrickField.RAY_ID, rId);
        setFormulaWithBrickField(Brick.BrickField.X_START, sX);
        setFormulaWithBrickField(Brick.BrickField.Y_START, sY);
        setFormulaWithBrickField(Brick.BrickField.X_END, eX);
        setFormulaWithBrickField(Brick.BrickField.Y_END, eY);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createPerformRayCastaction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.RAY_ID), getFormulaWithBrickField(Brick.BrickField.X_START), getFormulaWithBrickField(Brick.BrickField.Y_START), getFormulaWithBrickField(Brick.BrickField.X_END), getFormulaWithBrickField(Brick.BrickField.Y_END)));
    }
}

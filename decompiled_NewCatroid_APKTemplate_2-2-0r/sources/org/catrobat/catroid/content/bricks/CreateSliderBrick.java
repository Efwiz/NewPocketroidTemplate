package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/CreateSliderBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class CreateSliderBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_create_slider;
    }

    public CreateSliderBrick() {
        addAllowedBrickField(Brick.BrickField.VALUE_1, R.id.brick_id_field);
        addAllowedBrickField(Brick.BrickField.VALUE_2, R.id.brick_min_field);
        addAllowedBrickField(Brick.BrickField.VALUE_3, R.id.brick_max_field);
        addAllowedBrickField(Brick.BrickField.VALUE_4, R.id.brick_val_field);
        addAllowedBrickField(Brick.BrickField.X_POSITION, R.id.brick_x_field);
        addAllowedBrickField(Brick.BrickField.Y_POSITION, R.id.brick_y_field);
        addAllowedBrickField(Brick.BrickField.WIDTH, R.id.brick_w_field);
        addAllowedBrickField(Brick.BrickField.HEIGHT, R.id.brick_h_field);
    }

    public CreateSliderBrick(String viewId, double min, double max, double current, int x, int y, int w, int h) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VALUE_1, new Formula(viewId));
        setFormulaWithBrickField(Brick.BrickField.VALUE_2, new Formula(Double.valueOf(min)));
        setFormulaWithBrickField(Brick.BrickField.VALUE_3, new Formula(Double.valueOf(max)));
        setFormulaWithBrickField(Brick.BrickField.VALUE_4, new Formula(Double.valueOf(current)));
        setFormulaWithBrickField(Brick.BrickField.X_POSITION, new Formula(Integer.valueOf(x)));
        setFormulaWithBrickField(Brick.BrickField.Y_POSITION, new Formula(Integer.valueOf(y)));
        setFormulaWithBrickField(Brick.BrickField.WIDTH, new Formula(Integer.valueOf(w)));
        setFormulaWithBrickField(Brick.BrickField.HEIGHT, new Formula(Integer.valueOf(h)));
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createCreateSliderAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VALUE_1), getFormulaWithBrickField(Brick.BrickField.VALUE_2), getFormulaWithBrickField(Brick.BrickField.VALUE_3), getFormulaWithBrickField(Brick.BrickField.VALUE_4), getFormulaWithBrickField(Brick.BrickField.X_POSITION), getFormulaWithBrickField(Brick.BrickField.Y_POSITION), getFormulaWithBrickField(Brick.BrickField.WIDTH), getFormulaWithBrickField(Brick.BrickField.HEIGHT)));
    }
}

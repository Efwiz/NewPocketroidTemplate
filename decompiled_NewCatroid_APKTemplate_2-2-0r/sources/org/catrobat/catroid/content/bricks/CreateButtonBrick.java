package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/CreateButtonBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class CreateButtonBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_create_button;
    }

    public CreateButtonBrick() {
        addAllowedBrickField(Brick.BrickField.VALUE_1, R.id.brick_id_field);
        addAllowedBrickField(Brick.BrickField.VALUE_2, R.id.brick_text_field);
        addAllowedBrickField(Brick.BrickField.VALUE_3, R.id.brick_color_field);
        addAllowedBrickField(Brick.BrickField.VALUE_4, R.id.brick_size_field);
        addAllowedBrickField(Brick.BrickField.VALUE_5, R.id.brick_bg_field);
        addAllowedBrickField(Brick.BrickField.VALUE_6, R.id.brick_radius_field);
        addAllowedBrickField(Brick.BrickField.X_POSITION, R.id.brick_x_field);
        addAllowedBrickField(Brick.BrickField.Y_POSITION, R.id.brick_y_field);
        addAllowedBrickField(Brick.BrickField.WIDTH, R.id.brick_w_field);
        addAllowedBrickField(Brick.BrickField.HEIGHT, R.id.brick_h_field);
    }

    public CreateButtonBrick(String viewId, String text, String color, double size, String bg, int radius, int x, int y, int w, int h) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VALUE_1, new Formula(viewId));
        setFormulaWithBrickField(Brick.BrickField.VALUE_2, new Formula(text));
        setFormulaWithBrickField(Brick.BrickField.VALUE_3, new Formula(color));
        setFormulaWithBrickField(Brick.BrickField.VALUE_4, new Formula(Double.valueOf(size)));
        setFormulaWithBrickField(Brick.BrickField.VALUE_5, new Formula(bg));
        setFormulaWithBrickField(Brick.BrickField.VALUE_6, new Formula(Integer.valueOf(radius)));
        setFormulaWithBrickField(Brick.BrickField.X_POSITION, new Formula(Integer.valueOf(x)));
        setFormulaWithBrickField(Brick.BrickField.Y_POSITION, new Formula(Integer.valueOf(y)));
        setFormulaWithBrickField(Brick.BrickField.WIDTH, new Formula(Integer.valueOf(w)));
        setFormulaWithBrickField(Brick.BrickField.HEIGHT, new Formula(Integer.valueOf(h)));
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createCreateButtonAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VALUE_1), getFormulaWithBrickField(Brick.BrickField.VALUE_2), getFormulaWithBrickField(Brick.BrickField.VALUE_3), getFormulaWithBrickField(Brick.BrickField.VALUE_4), getFormulaWithBrickField(Brick.BrickField.VALUE_5), getFormulaWithBrickField(Brick.BrickField.VALUE_6), getFormulaWithBrickField(Brick.BrickField.X_POSITION), getFormulaWithBrickField(Brick.BrickField.Y_POSITION), getFormulaWithBrickField(Brick.BrickField.WIDTH), getFormulaWithBrickField(Brick.BrickField.HEIGHT)));
    }
}

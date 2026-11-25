package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetPointLightBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetPointLightBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_set_point_light;
    }

    public SetPointLightBrick() {
        addAllowedBrickField(Brick.BrickField.VALUE_1, R.id.brick_set_point_light_id_value);
        addAllowedBrickField(Brick.BrickField.VALUE_2, R.id.brick_set_point_light_pos_x_value);
        addAllowedBrickField(Brick.BrickField.VALUE_3, R.id.brick_set_point_light_pos_y_value);
        addAllowedBrickField(Brick.BrickField.VALUE_4, R.id.brick_set_point_light_pos_z_value);
        addAllowedBrickField(Brick.BrickField.VALUE_5, R.id.brick_set_point_light_color_r_value);
        addAllowedBrickField(Brick.BrickField.VALUE_6, R.id.brick_set_point_light_color_g_value);
        addAllowedBrickField(Brick.BrickField.VALUE_7, R.id.brick_set_point_light_color_b_value);
        addAllowedBrickField(Brick.BrickField.VALUE_8, R.id.brick_set_point_light_intensity_value);
        addAllowedBrickField(Brick.BrickField.VALUE_9, R.id.brick_set_point_light_range_value);
    }

    public SetPointLightBrick(String lightId, double x, double y, double z, int r, int g, int b, double intensity, double range) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VALUE_1, new Formula(lightId));
        setFormulaWithBrickField(Brick.BrickField.VALUE_2, new Formula(Double.valueOf(x)));
        setFormulaWithBrickField(Brick.BrickField.VALUE_3, new Formula(Double.valueOf(y)));
        setFormulaWithBrickField(Brick.BrickField.VALUE_4, new Formula(Double.valueOf(z)));
        setFormulaWithBrickField(Brick.BrickField.VALUE_5, new Formula(Integer.valueOf(r)));
        setFormulaWithBrickField(Brick.BrickField.VALUE_6, new Formula(Integer.valueOf(g)));
        setFormulaWithBrickField(Brick.BrickField.VALUE_7, new Formula(Integer.valueOf(b)));
        setFormulaWithBrickField(Brick.BrickField.VALUE_8, new Formula(Double.valueOf(intensity)));
        setFormulaWithBrickField(Brick.BrickField.VALUE_9, new Formula(Double.valueOf(range)));
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetPointLightAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VALUE_1), getFormulaWithBrickField(Brick.BrickField.VALUE_2), getFormulaWithBrickField(Brick.BrickField.VALUE_3), getFormulaWithBrickField(Brick.BrickField.VALUE_4), getFormulaWithBrickField(Brick.BrickField.VALUE_5), getFormulaWithBrickField(Brick.BrickField.VALUE_6), getFormulaWithBrickField(Brick.BrickField.VALUE_7), getFormulaWithBrickField(Brick.BrickField.VALUE_8), getFormulaWithBrickField(Brick.BrickField.VALUE_9)));
    }
}

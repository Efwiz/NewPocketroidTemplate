package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetSpotLightBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetSpotLightBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_set_spot_light;
    }

    public SetSpotLightBrick() {
        addAllowedBrickField(Brick.BrickField.VALUE_1, R.id.brick_set_spot_light_id_value);
        addAllowedBrickField(Brick.BrickField.VALUE_2, R.id.brick_set_spot_light_pos_x_value);
        addAllowedBrickField(Brick.BrickField.VALUE_3, R.id.brick_set_spot_light_pos_y_value);
        addAllowedBrickField(Brick.BrickField.VALUE_4, R.id.brick_set_spot_light_pos_z_value);
        addAllowedBrickField(Brick.BrickField.VALUE_5, R.id.brick_set_spot_light_dir_x_value);
        addAllowedBrickField(Brick.BrickField.VALUE_6, R.id.brick_set_spot_light_dir_y_value);
        addAllowedBrickField(Brick.BrickField.VALUE_7, R.id.brick_set_spot_light_dir_z_value);
        addAllowedBrickField(Brick.BrickField.VALUE_8, R.id.brick_set_spot_light_color_r_value);
        addAllowedBrickField(Brick.BrickField.VALUE_9, R.id.brick_set_spot_light_color_g_value);
        addAllowedBrickField(Brick.BrickField.VALUE_10, R.id.brick_set_spot_light_color_b_value);
        addAllowedBrickField(Brick.BrickField.VALUE_11, R.id.brick_set_spot_light_intensity_value);
        addAllowedBrickField(Brick.BrickField.VALUE_12, R.id.brick_set_spot_light_angle_value);
        addAllowedBrickField(Brick.BrickField.VALUE_13, R.id.brick_set_spot_light_exponent_value);
        addAllowedBrickField(Brick.BrickField.VALUE_14, R.id.brick_set_spot_light_range_value);
    }

    public SetSpotLightBrick(String lightId, double x, double y, double z, double dx, double dy, double dz, int r, int g, int b, double intensity, double angle, double exponent, double range) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VALUE_1, new Formula(lightId));
        setFormulaWithBrickField(Brick.BrickField.VALUE_2, new Formula(Double.valueOf(x)));
        setFormulaWithBrickField(Brick.BrickField.VALUE_3, new Formula(Double.valueOf(y)));
        setFormulaWithBrickField(Brick.BrickField.VALUE_4, new Formula(Double.valueOf(z)));
        setFormulaWithBrickField(Brick.BrickField.VALUE_5, new Formula(Double.valueOf(dx)));
        setFormulaWithBrickField(Brick.BrickField.VALUE_6, new Formula(Double.valueOf(dy)));
        setFormulaWithBrickField(Brick.BrickField.VALUE_7, new Formula(Double.valueOf(dz)));
        setFormulaWithBrickField(Brick.BrickField.VALUE_8, new Formula(Integer.valueOf(r)));
        setFormulaWithBrickField(Brick.BrickField.VALUE_9, new Formula(Integer.valueOf(g)));
        setFormulaWithBrickField(Brick.BrickField.VALUE_10, new Formula(Integer.valueOf(b)));
        setFormulaWithBrickField(Brick.BrickField.VALUE_11, new Formula(Double.valueOf(intensity)));
        setFormulaWithBrickField(Brick.BrickField.VALUE_12, new Formula(Double.valueOf(angle)));
        setFormulaWithBrickField(Brick.BrickField.VALUE_13, new Formula(Double.valueOf(exponent)));
        setFormulaWithBrickField(Brick.BrickField.VALUE_14, new Formula(Double.valueOf(range)));
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetSpotLightAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VALUE_1), getFormulaWithBrickField(Brick.BrickField.VALUE_2), getFormulaWithBrickField(Brick.BrickField.VALUE_3), getFormulaWithBrickField(Brick.BrickField.VALUE_4), getFormulaWithBrickField(Brick.BrickField.VALUE_5), getFormulaWithBrickField(Brick.BrickField.VALUE_6), getFormulaWithBrickField(Brick.BrickField.VALUE_7), getFormulaWithBrickField(Brick.BrickField.VALUE_8), getFormulaWithBrickField(Brick.BrickField.VALUE_9), getFormulaWithBrickField(Brick.BrickField.VALUE_10), getFormulaWithBrickField(Brick.BrickField.VALUE_11), getFormulaWithBrickField(Brick.BrickField.VALUE_12), getFormulaWithBrickField(Brick.BrickField.VALUE_13), getFormulaWithBrickField(Brick.BrickField.VALUE_14)));
    }
}

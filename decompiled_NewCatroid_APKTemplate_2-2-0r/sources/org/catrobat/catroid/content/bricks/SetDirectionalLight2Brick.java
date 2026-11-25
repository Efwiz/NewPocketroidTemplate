package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetDirectionalLight2Brick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetDirectionalLight2Brick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_set_directional_light2;
    }

    public SetDirectionalLight2Brick() {
        addAllowedBrickField(Brick.BrickField.VALUE_1, R.id.brick_set_directional_light_dir_x_value);
        addAllowedBrickField(Brick.BrickField.VALUE_2, R.id.brick_set_directional_light_dir_y_value);
        addAllowedBrickField(Brick.BrickField.VALUE_3, R.id.brick_set_directional_light_dir_z_value);
        addAllowedBrickField(Brick.BrickField.VALUE_4, R.id.brick_set_directional_light_intensity_value);
    }

    public SetDirectionalLight2Brick(double dirX, double dirY, double dirZ, double intensity) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VALUE_1, new Formula(Double.valueOf(dirX)));
        setFormulaWithBrickField(Brick.BrickField.VALUE_2, new Formula(Double.valueOf(dirY)));
        setFormulaWithBrickField(Brick.BrickField.VALUE_3, new Formula(Double.valueOf(dirZ)));
        setFormulaWithBrickField(Brick.BrickField.VALUE_4, new Formula(Double.valueOf(intensity)));
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetDirectionalLight2Action(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VALUE_1), getFormulaWithBrickField(Brick.BrickField.VALUE_2), getFormulaWithBrickField(Brick.BrickField.VALUE_3), getFormulaWithBrickField(Brick.BrickField.VALUE_4)));
    }
}

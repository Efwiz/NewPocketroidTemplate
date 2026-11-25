package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetShaderUniformVec3Brick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetShaderUniformVec3Brick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_set_shader_uniform_vec3;
    }

    public SetShaderUniformVec3Brick() {
        addAllowedBrickField(Brick.BrickField.THREED_UNIFORM_NAME, R.id.brick_uniform_name_edit);
        addAllowedBrickField(Brick.BrickField.VALUE_X, R.id.brick_uniform_value_x_edit);
        addAllowedBrickField(Brick.BrickField.VALUE_Y, R.id.brick_uniform_value_y_edit);
        addAllowedBrickField(Brick.BrickField.VALUE_Z, R.id.brick_uniform_value_z_edit);
    }

    public SetShaderUniformVec3Brick(String name2, double x, double y, double z) {
        this(new Formula(name2), new Formula(Double.valueOf(x)), new Formula(Double.valueOf(y)), new Formula(Double.valueOf(z)));
    }

    public SetShaderUniformVec3Brick(Formula name2, Formula x, Formula y, Formula z) {
        this();
        setFormulaWithBrickField(Brick.BrickField.THREED_UNIFORM_NAME, name2);
        setFormulaWithBrickField(Brick.BrickField.VALUE_X, x);
        setFormulaWithBrickField(Brick.BrickField.VALUE_Y, y);
        setFormulaWithBrickField(Brick.BrickField.VALUE_Z, z);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetShaderUniformAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.THREED_UNIFORM_NAME), getFormulaWithBrickField(Brick.BrickField.VALUE_X), getFormulaWithBrickField(Brick.BrickField.VALUE_Y), getFormulaWithBrickField(Brick.BrickField.VALUE_Z)));
    }
}

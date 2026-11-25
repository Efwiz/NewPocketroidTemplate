package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetShaderUniformFloatBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetShaderUniformFloatBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_set_shader_uniform_float;
    }

    public SetShaderUniformFloatBrick() {
        addAllowedBrickField(Brick.BrickField.THREED_UNIFORM_NAME, R.id.brick_uniform_name_edit);
        addAllowedBrickField(Brick.BrickField.VALUE_X, R.id.brick_uniform_value_edit);
    }

    public SetShaderUniformFloatBrick(String name2, double value) {
        this(new Formula(name2), new Formula(Double.valueOf(value)));
    }

    public SetShaderUniformFloatBrick(Formula name2, Formula value) {
        this();
        setFormulaWithBrickField(Brick.BrickField.THREED_UNIFORM_NAME, name2);
        setFormulaWithBrickField(Brick.BrickField.VALUE_X, value);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetShaderUniformAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.THREED_UNIFORM_NAME), getFormulaWithBrickField(Brick.BrickField.VALUE_X), null, null));
    }
}

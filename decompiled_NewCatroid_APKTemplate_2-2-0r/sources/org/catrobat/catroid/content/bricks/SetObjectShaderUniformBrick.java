package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetObjectShaderUniformBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetObjectShaderUniformBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_set_object_shader_uniform;
    }

    public SetObjectShaderUniformBrick() {
        addAllowedBrickField(Brick.BrickField.NAME, R.id.brick_object_shader_uniform_id);
        addAllowedBrickField(Brick.BrickField.TEXT, R.id.brick_object_shader_uniform_name);
        addAllowedBrickField(Brick.BrickField.X, R.id.brick_object_shader_uniform_val1);
        addAllowedBrickField(Brick.BrickField.Y, R.id.brick_object_shader_uniform_val2);
        addAllowedBrickField(Brick.BrickField.Z, R.id.brick_object_shader_uniform_val3);
    }

    public SetObjectShaderUniformBrick(String objectId, String name2, float v1, float v2, float v3) {
        this(new Formula(objectId), new Formula(name2), new Formula(Float.valueOf(v1)), new Formula(Float.valueOf(v2)), new Formula(Float.valueOf(v3)));
    }

    public SetObjectShaderUniformBrick(Formula objectId, Formula name2, Formula v1, Formula v2, Formula v3) {
        this();
        setFormulaWithBrickField(Brick.BrickField.NAME, objectId);
        setFormulaWithBrickField(Brick.BrickField.TEXT, name2);
        setFormulaWithBrickField(Brick.BrickField.X, v1);
        setFormulaWithBrickField(Brick.BrickField.Y, v2);
        setFormulaWithBrickField(Brick.BrickField.Z, v3);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetObjectShaderUniformAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.NAME), getFormulaWithBrickField(Brick.BrickField.TEXT), getFormulaWithBrickField(Brick.BrickField.X), getFormulaWithBrickField(Brick.BrickField.Y), getFormulaWithBrickField(Brick.BrickField.Z)));
    }
}

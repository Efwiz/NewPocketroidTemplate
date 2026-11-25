package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetShaderCodeBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetShaderCodeBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_set_shader_code;
    }

    public SetShaderCodeBrick() {
        addAllowedBrickField(Brick.BrickField.THREED_VERTEX_SHADER_CODE, R.id.brick_shader_vertex_code);
        addAllowedBrickField(Brick.BrickField.THREED_FRAGMENT_SHADER_CODE, R.id.brick_shader_fragment_code);
    }

    public SetShaderCodeBrick(String vertexCode, String fragmentCode) {
        this(new Formula(vertexCode), new Formula(fragmentCode));
    }

    public SetShaderCodeBrick(Formula vertexFormula, Formula fragmentFormula) {
        this();
        setFormulaWithBrickField(Brick.BrickField.THREED_VERTEX_SHADER_CODE, vertexFormula);
        setFormulaWithBrickField(Brick.BrickField.THREED_FRAGMENT_SHADER_CODE, fragmentFormula);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetShaderCodeAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.THREED_VERTEX_SHADER_CODE), getFormulaWithBrickField(Brick.BrickField.THREED_FRAGMENT_SHADER_CODE)));
    }
}

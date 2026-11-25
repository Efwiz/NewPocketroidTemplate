package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/ShaderBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ShaderBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_shader;
    }

    public ShaderBrick() {
        addAllowedBrickField(Brick.BrickField.VERTEX, R.id.brick_shader_edit_vert);
        addAllowedBrickField(Brick.BrickField.FRAGMENT, R.id.brick_shader_edit_frag);
    }

    public ShaderBrick(String value, String value2) {
        this(new Formula(value), new Formula(value2));
    }

    public ShaderBrick(Formula formula, Formula f2) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VERTEX, formula);
        setFormulaWithBrickField(Brick.BrickField.FRAGMENT, f2);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createShader(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.FRAGMENT), getFormulaWithBrickField(Brick.BrickField.VERTEX)));
    }
}

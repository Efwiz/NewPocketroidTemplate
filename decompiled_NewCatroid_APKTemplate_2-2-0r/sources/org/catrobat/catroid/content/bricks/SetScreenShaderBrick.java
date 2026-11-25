package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetScreenShaderBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetScreenShaderBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_set_screen_shader;
    }

    public SetScreenShaderBrick() {
        addAllowedBrickField(Brick.BrickField.VERTEX, R.id.brick_screen_shader_vertex);
        addAllowedBrickField(Brick.BrickField.FRAGMENT, R.id.brick_screen_shader_fragment);
    }

    public SetScreenShaderBrick(String vertex, String fragment) {
        this(new Formula(vertex), new Formula(fragment));
    }

    public SetScreenShaderBrick(Formula vertex, Formula fragment) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VERTEX, vertex);
        setFormulaWithBrickField(Brick.BrickField.FRAGMENT, fragment);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetScreenShaderAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VERTEX), getFormulaWithBrickField(Brick.BrickField.FRAGMENT)));
    }
}

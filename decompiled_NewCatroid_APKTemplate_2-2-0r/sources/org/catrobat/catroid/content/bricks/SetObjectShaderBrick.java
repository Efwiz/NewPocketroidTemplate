package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetObjectShaderBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetObjectShaderBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_set_object_shader;
    }

    public SetObjectShaderBrick() {
        addAllowedBrickField(Brick.BrickField.NAME, R.id.brick_object_shader_id);
        addAllowedBrickField(Brick.BrickField.TEXT, R.id.brick_object_shader_vertex);
        addAllowedBrickField(Brick.BrickField.VALUE, R.id.brick_object_shader_fragment);
    }

    public SetObjectShaderBrick(String objectId, String vertex, String fragment) {
        this(new Formula(objectId), new Formula(vertex), new Formula(fragment));
    }

    public SetObjectShaderBrick(Formula objectId, Formula vertex, Formula fragment) {
        this();
        setFormulaWithBrickField(Brick.BrickField.NAME, objectId);
        setFormulaWithBrickField(Brick.BrickField.TEXT, vertex);
        setFormulaWithBrickField(Brick.BrickField.VALUE, fragment);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetObjectShaderAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.NAME), getFormulaWithBrickField(Brick.BrickField.TEXT), getFormulaWithBrickField(Brick.BrickField.VALUE)));
    }
}

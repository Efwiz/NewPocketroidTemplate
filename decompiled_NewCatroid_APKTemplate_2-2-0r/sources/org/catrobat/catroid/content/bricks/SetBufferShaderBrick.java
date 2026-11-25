package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetBufferShaderBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetBufferShaderBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_set_buffer_shader;
    }

    public SetBufferShaderBrick() {
        addAllowedBrickField(Brick.BrickField.IF_CONDITION, R.id.brick_buffer_shader_name);
        addAllowedBrickField(Brick.BrickField.INSERT_ITEM_INTO_USERLIST_VALUE, R.id.brick_buffer_shader_vsh);
        addAllowedBrickField(Brick.BrickField.REPLACE_ITEM_IN_USERLIST_VALUE, R.id.brick_buffer_shader_fsh);
    }

    public SetBufferShaderBrick(String bufferName, String vsh, String fsh) {
        this();
        setFormulaWithBrickField(Brick.BrickField.IF_CONDITION, new Formula(bufferName));
        setFormulaWithBrickField(Brick.BrickField.INSERT_ITEM_INTO_USERLIST_VALUE, new Formula(vsh));
        setFormulaWithBrickField(Brick.BrickField.REPLACE_ITEM_IN_USERLIST_VALUE, new Formula(fsh));
    }

    public SetBufferShaderBrick(Formula bufferName, Formula vsh, Formula fsh) {
        this();
        setFormulaWithBrickField(Brick.BrickField.IF_CONDITION, bufferName);
        setFormulaWithBrickField(Brick.BrickField.INSERT_ITEM_INTO_USERLIST_VALUE, vsh);
        setFormulaWithBrickField(Brick.BrickField.REPLACE_ITEM_IN_USERLIST_VALUE, fsh);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetBufferShaderAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.IF_CONDITION), getFormulaWithBrickField(Brick.BrickField.INSERT_ITEM_INTO_USERLIST_VALUE), getFormulaWithBrickField(Brick.BrickField.REPLACE_ITEM_IN_USERLIST_VALUE)));
    }
}

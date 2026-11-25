package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetBufferShaderUniformBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetBufferShaderUniformBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;
    private int paramCount;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_set_buffer_uniform;
    }

    public SetBufferShaderUniformBrick() {
        this.paramCount = 1;
        addAllowedBrickField(Brick.BrickField.IF_CONDITION, R.id.brick_buf_uniform_buf_name);
        addAllowedBrickField(Brick.BrickField.INSERT_ITEM_INTO_USERLIST_VALUE, R.id.brick_buf_uniform_name);
        addAllowedBrickField(Brick.BrickField.REPLACE_ITEM_IN_USERLIST_VALUE, R.id.brick_buf_uniform_v1);
        addAllowedBrickField(Brick.BrickField.INSERT_ITEM_INTO_USERLIST_INDEX, R.id.brick_buf_uniform_v2);
        addAllowedBrickField(Brick.BrickField.TIMES_TO_REPEAT, R.id.brick_buf_uniform_v3);
    }

    public SetBufferShaderUniformBrick(String bufferName, String uniformName, float v1, float v2, float v3, int paramCount) {
        this();
        this.paramCount = paramCount;
        setFormulaWithBrickField(Brick.BrickField.IF_CONDITION, new Formula(bufferName));
        setFormulaWithBrickField(Brick.BrickField.INSERT_ITEM_INTO_USERLIST_VALUE, new Formula(uniformName));
        setFormulaWithBrickField(Brick.BrickField.REPLACE_ITEM_IN_USERLIST_VALUE, new Formula(Float.valueOf(v1)));
        setFormulaWithBrickField(Brick.BrickField.INSERT_ITEM_INTO_USERLIST_INDEX, new Formula(Float.valueOf(v2)));
        setFormulaWithBrickField(Brick.BrickField.TIMES_TO_REPEAT, new Formula(Float.valueOf(v3)));
    }

    public SetBufferShaderUniformBrick(Formula bufferName, Formula uniformName, Formula v1, Formula v2, Formula v3, int paramCount) {
        this();
        this.paramCount = paramCount;
        setFormulaWithBrickField(Brick.BrickField.IF_CONDITION, bufferName);
        setFormulaWithBrickField(Brick.BrickField.INSERT_ITEM_INTO_USERLIST_VALUE, uniformName);
        setFormulaWithBrickField(Brick.BrickField.REPLACE_ITEM_IN_USERLIST_VALUE, v1);
        setFormulaWithBrickField(Brick.BrickField.INSERT_ITEM_INTO_USERLIST_INDEX, v2);
        setFormulaWithBrickField(Brick.BrickField.TIMES_TO_REPEAT, v3);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetBufferShaderUniformAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.IF_CONDITION), getFormulaWithBrickField(Brick.BrickField.INSERT_ITEM_INTO_USERLIST_VALUE), getFormulaWithBrickField(Brick.BrickField.REPLACE_ITEM_IN_USERLIST_VALUE), getFormulaWithBrickField(Brick.BrickField.INSERT_ITEM_INTO_USERLIST_INDEX), getFormulaWithBrickField(Brick.BrickField.TIMES_TO_REPEAT), this.paramCount));
    }
}

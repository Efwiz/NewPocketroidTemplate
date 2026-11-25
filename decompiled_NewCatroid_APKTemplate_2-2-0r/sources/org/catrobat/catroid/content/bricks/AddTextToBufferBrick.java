package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/AddTextToBufferBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class AddTextToBufferBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_add_text_to_buffer;
    }

    public AddTextToBufferBrick() {
        addAllowedBrickField(Brick.BrickField.IF_CONDITION, R.id.brick_add_text_buffer_name);
        addAllowedBrickField(Brick.BrickField.INSERT_ITEM_INTO_USERLIST_VALUE, R.id.brick_add_text_var_name);
    }

    public AddTextToBufferBrick(String varName, String bufferName) {
        this();
        setFormulaWithBrickField(Brick.BrickField.IF_CONDITION, new Formula(bufferName));
        setFormulaWithBrickField(Brick.BrickField.INSERT_ITEM_INTO_USERLIST_VALUE, new Formula(varName));
    }

    public AddTextToBufferBrick(Formula varName, Formula bufferName) {
        this();
        setFormulaWithBrickField(Brick.BrickField.IF_CONDITION, bufferName);
        setFormulaWithBrickField(Brick.BrickField.INSERT_ITEM_INTO_USERLIST_VALUE, varName);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createAddTextToBufferAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.IF_CONDITION), getFormulaWithBrickField(Brick.BrickField.INSERT_ITEM_INTO_USERLIST_VALUE)));
    }
}

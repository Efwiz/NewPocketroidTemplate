package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetTextBufferOnlyBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetTextBufferOnlyBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_set_text_buffer_only;
    }

    public SetTextBufferOnlyBrick() {
        addAllowedBrickField(Brick.BrickField.VALUE_1, R.id.brick_set_text_buffer_only_name);
        addAllowedBrickField(Brick.BrickField.VALUE_2, R.id.brick_set_text_buffer_only_state);
    }

    public SetTextBufferOnlyBrick(String textName, int state) {
        this(new Formula(textName), new Formula(Integer.valueOf(state)));
    }

    public SetTextBufferOnlyBrick(Formula textName, Formula state) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VALUE_1, textName);
        setFormulaWithBrickField(Brick.BrickField.VALUE_2, state);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetTextBufferOnlyAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VALUE_1), getFormulaWithBrickField(Brick.BrickField.VALUE_2)));
    }
}

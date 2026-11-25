package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/KeyEventBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class KeyEventBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_key_event;
    }

    public KeyEventBrick() {
        addAllowedBrickField(Brick.BrickField.VALUE, R.id.brick_key_char_edit);
        addAllowedBrickField(Brick.BrickField.VM_KEY_DOWN, R.id.brick_key_down_edit);
    }

    public KeyEventBrick(String character, int isDown) {
        this(new Formula(character), new Formula(Integer.valueOf(isDown)));
    }

    public KeyEventBrick(Formula character, Formula isDown) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VALUE, character);
        setFormulaWithBrickField(Brick.BrickField.VM_KEY_DOWN, isDown);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createKeyEventAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VALUE), getFormulaWithBrickField(Brick.BrickField.VM_KEY_DOWN)));
    }
}

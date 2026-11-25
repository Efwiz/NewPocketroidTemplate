package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SendVmInputBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SendVmInputBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_send_vm_input;
    }

    public SendVmInputBrick() {
        addAllowedBrickField(Brick.BrickField.INPUT_TEXT, R.id.brick_send_vm_input_text);
    }

    public SendVmInputBrick(String inputText) {
        this(new Formula(inputText));
    }

    public SendVmInputBrick(Formula inputText) {
        this();
        setFormulaWithBrickField(Brick.BrickField.INPUT_TEXT, inputText);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSendVmInputAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.INPUT_TEXT)));
    }
}

package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/CreateDialogBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class CreateDialogBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_create_dialog;
    }

    public CreateDialogBrick() {
        addAllowedBrickField(Brick.BrickField.NAME, R.id.brick_create_dialog_edit_name);
        addAllowedBrickField(Brick.BrickField.TITLE, R.id.brick_create_dialog_edit_title);
        addAllowedBrickField(Brick.BrickField.MESSAGE, R.id.brick_create_dialog_edit_message);
    }

    public CreateDialogBrick(String value, String value2, String value3) {
        this(new Formula(value), new Formula(value2), new Formula(value3));
    }

    public CreateDialogBrick(Formula formula, Formula formula2, Formula formula3) {
        this();
        setFormulaWithBrickField(Brick.BrickField.NAME, formula);
        setFormulaWithBrickField(Brick.BrickField.TITLE, formula2);
        setFormulaWithBrickField(Brick.BrickField.MESSAGE, formula3);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createDialogAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.NAME), getFormulaWithBrickField(Brick.BrickField.TITLE), getFormulaWithBrickField(Brick.BrickField.MESSAGE)));
    }
}

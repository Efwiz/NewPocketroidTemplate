package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/CreateVarBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class CreateVarBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_create_var;
    }

    public CreateVarBrick() {
        addAllowedBrickField(Brick.BrickField.VARNAME, R.id.brick_create_var_edit_text);
        addAllowedBrickField(Brick.BrickField.VALUE, R.id.brick_create_var_edit_text2);
    }

    public CreateVarBrick(String value, String value2) {
        this(new Formula(value), new Formula(value2));
    }

    public CreateVarBrick(Formula formula, Formula formula2) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VARNAME, formula);
        setFormulaWithBrickField(Brick.BrickField.VALUE, formula2);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createVarAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VARNAME), getFormulaWithBrickField(Brick.BrickField.VALUE)));
    }
}

package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.UserVariable;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/ChangeVariableBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ChangeVariableBrick extends UserVariableBrickWithFormula {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.UserVariableBrickWithFormula
    protected int getSpinnerId() {
        return R.id.change_variable_spinner;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_change_variable_by;
    }

    public ChangeVariableBrick() {
        addAllowedBrickField(Brick.BrickField.VARIABLE_CHANGE, R.id.brick_change_variable_edit_text);
    }

    public ChangeVariableBrick(double value) {
        this(new Formula(Double.valueOf(value)));
    }

    public ChangeVariableBrick(Formula formula, UserVariable userVariable) {
        this(formula);
        this.userVariable = userVariable;
    }

    public ChangeVariableBrick(Formula formula) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VARIABLE_CHANGE, formula);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createChangeVariableAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VARIABLE_CHANGE), this.userVariable));
    }
}

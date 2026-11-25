package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.FormulaElement;
import org.catrobat.catroid.formulaeditor.Sensors;
import org.catrobat.catroid.formulaeditor.UserVariable;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/RunShellBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class RunShellBrick extends UserVariableBrickWithFormula {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.UserVariableBrickWithFormula
    protected int getSpinnerId() {
        return R.id.run_shell_spinner;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_run_shell;
    }

    public RunShellBrick() {
        addAllowedBrickField(Brick.BrickField.CODE, R.id.brick_run_shell_edit_text);
    }

    public RunShellBrick(String value) {
        this(new Formula(value));
    }

    public RunShellBrick(Formula variableFormula, UserVariable userVariable) {
        this(variableFormula);
        this.userVariable = userVariable;
    }

    public RunShellBrick(Sensors defaultValue) {
        this(new Formula(new FormulaElement(FormulaElement.ElementType.SENSOR, defaultValue.name(), null)));
    }

    private RunShellBrick(Formula formula) {
        this();
        setFormulaWithBrickField(Brick.BrickField.CODE, formula);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().RunShellAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.CODE), this.userVariable));
    }
}

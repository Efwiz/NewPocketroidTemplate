package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.FormulaElement;
import org.catrobat.catroid.formulaeditor.Sensors;
import org.catrobat.catroid.formulaeditor.UserVariable;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetWebBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetWebBrick extends UserVariableBrickWithFormula {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.UserVariableBrickWithFormula
    protected int getSpinnerId() {
        return R.id.set_web_spinner;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_set_web;
    }

    public SetWebBrick() {
        addAllowedBrickField(Brick.BrickField.NAME, R.id.brick_set_web_name);
    }

    public SetWebBrick(String value) {
        this(new Formula(value));
    }

    public SetWebBrick(Formula variableFormula, UserVariable userVariable) {
        this(variableFormula);
        this.userVariable = userVariable;
    }

    public SetWebBrick(Sensors defaultValue) {
        this(new Formula(new FormulaElement(FormulaElement.ElementType.SENSOR, defaultValue.name(), null)));
    }

    private SetWebBrick(Formula formula) {
        this();
        setFormulaWithBrickField(Brick.BrickField.NAME, formula);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().setWebAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.NAME), this.userVariable));
    }
}

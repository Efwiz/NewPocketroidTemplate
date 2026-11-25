package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.FormulaElement;
import org.catrobat.catroid.formulaeditor.Sensors;
import org.catrobat.catroid.formulaeditor.UserVariable;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/WriteToFilesBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class WriteToFilesBrick extends UserVariableBrickWithFormula {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.UserVariableBrickWithFormula
    protected int getSpinnerId() {
        return R.id.write_files_spinner;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_write_to_files;
    }

    public WriteToFilesBrick() {
        addAllowedBrickField(Brick.BrickField.VALUE, R.id.brick_write_files_edit_text);
    }

    public WriteToFilesBrick(String value) {
        this(new Formula(value));
    }

    public WriteToFilesBrick(Formula variableFormula, UserVariable userVariable) {
        this(variableFormula);
        this.userVariable = userVariable;
    }

    public WriteToFilesBrick(Sensors defaultValue) {
        this(new Formula(new FormulaElement(FormulaElement.ElementType.SENSOR, defaultValue.name(), null)));
    }

    private WriteToFilesBrick(Formula formula) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VALUE, formula);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createWriteToFilesAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VALUE), this.userVariable));
    }
}

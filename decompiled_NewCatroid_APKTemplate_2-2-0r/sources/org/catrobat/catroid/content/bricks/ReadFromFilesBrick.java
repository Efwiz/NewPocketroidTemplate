package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.FormulaElement;
import org.catrobat.catroid.formulaeditor.Sensors;
import org.catrobat.catroid.formulaeditor.UserVariable;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/ReadFromFilesBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ReadFromFilesBrick extends UserVariableBrickWithFormula {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.UserVariableBrickWithFormula
    protected int getSpinnerId() {
        return R.id.read_files_spinner;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_read_from_files;
    }

    public ReadFromFilesBrick() {
        addAllowedBrickField(Brick.BrickField.VALUE, R.id.brick_read_files_edit_text);
    }

    public ReadFromFilesBrick(String value) {
        this(new Formula(value));
    }

    public ReadFromFilesBrick(Formula variableFormula, UserVariable userVariable) {
        this(variableFormula);
        this.userVariable = userVariable;
    }

    public ReadFromFilesBrick(Sensors defaultValue) {
        this(new Formula(new FormulaElement(FormulaElement.ElementType.SENSOR, defaultValue.name(), null)));
    }

    private ReadFromFilesBrick(Formula formula) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VALUE, formula);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createReadFromFilesAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VALUE), this.userVariable));
    }
}

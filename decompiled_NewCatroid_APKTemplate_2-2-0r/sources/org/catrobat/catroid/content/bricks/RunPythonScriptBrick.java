package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/RunPythonScriptBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class RunPythonScriptBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_python_run_script;
    }

    public RunPythonScriptBrick() {
        addAllowedBrickField(Brick.BrickField.SCRIPT, R.id.brick_python_script_text);
        addAllowedBrickField(Brick.BrickField.VARIABLE_NAME, R.id.brick_python_script_variable_name);
    }

    public RunPythonScriptBrick(String script, String variableName) {
        this(new Formula(script), new Formula(variableName));
    }

    public RunPythonScriptBrick(Formula script, Formula variableName) {
        this();
        setFormulaWithBrickField(Brick.BrickField.SCRIPT, script);
        setFormulaWithBrickField(Brick.BrickField.VARIABLE_NAME, variableName);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createRunPythonScriptAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.SCRIPT), getFormulaWithBrickField(Brick.BrickField.VARIABLE_NAME)));
    }
}

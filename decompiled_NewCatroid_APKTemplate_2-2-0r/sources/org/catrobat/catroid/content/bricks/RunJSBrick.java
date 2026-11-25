package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.UserVariable;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/RunJSBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class RunJSBrick extends UserVariableBrickWithFormula {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.UserVariableBrickWithFormula
    protected int getSpinnerId() {
        return R.id.brick_run_js_spinner;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_run_js;
    }

    public RunJSBrick() {
        addAllowedBrickField(Brick.BrickField.CODE, R.id.brick_run_js_edit_text);
    }

    public RunJSBrick(String val1) {
        this(new Formula(val1), null);
    }

    public RunJSBrick(Formula code, UserVariable userVariable) {
        this();
        setFormulaWithBrickField(Brick.BrickField.CODE, code);
        this.userVariable = userVariable;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createRunJS(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.CODE), this.userVariable));
    }
}

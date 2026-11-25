package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.UserVariable;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/AskGemini2Brick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class AskGemini2Brick extends UserVariableBrickWithFormula {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.UserVariableBrickWithFormula
    protected int getSpinnerId() {
        return R.id.brick_ask_gemini2_spinner;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_ask_gemini2;
    }

    public AskGemini2Brick() {
        addAllowedBrickField(Brick.BrickField.QUESTION, R.id.brick_ask_gemini2_edit_url);
        addAllowedBrickField(Brick.BrickField.MODEL, R.id.brick_ask_gemini2_edit_model);
    }

    public AskGemini2Brick(String val1, String val2) {
        this(new Formula(val1), new Formula(val2), null);
    }

    public AskGemini2Brick(Formula question, Formula model, UserVariable userVariable) {
        this();
        setFormulaWithBrickField(Brick.BrickField.QUESTION, question);
        setFormulaWithBrickField(Brick.BrickField.MODEL, model);
        this.userVariable = userVariable;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createAskGemini2Action(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.QUESTION), getFormulaWithBrickField(Brick.BrickField.MODEL), this.userVariable));
    }
}

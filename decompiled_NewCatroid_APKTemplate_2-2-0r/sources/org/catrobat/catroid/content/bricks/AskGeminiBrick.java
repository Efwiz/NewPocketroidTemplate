package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.UserVariable;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/AskGeminiBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class AskGeminiBrick extends UserVariableBrickWithFormula {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.UserVariableBrickWithFormula
    protected int getSpinnerId() {
        return R.id.brick_ask_gemini_spinner;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_ask_gemini;
    }

    public AskGeminiBrick() {
        addAllowedBrickField(Brick.BrickField.QUESTION, R.id.brick_ask_gemini_edit_url);
    }

    public AskGeminiBrick(String val1) {
        this(new Formula(val1), null);
    }

    public AskGeminiBrick(Formula question, UserVariable userVariable) {
        this();
        setFormulaWithBrickField(Brick.BrickField.QUESTION, question);
        this.userVariable = userVariable;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createAskGeminiAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.QUESTION), this.userVariable));
    }
}

package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.UserVariable;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/AskBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class AskBrick extends UserVariableBrickWithFormula {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.UserVariableBrickWithFormula
    protected int getSpinnerId() {
        return R.id.brick_ask_spinner;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_ask;
    }

    public AskBrick() {
        addAllowedBrickField(Brick.BrickField.ASK_QUESTION, R.id.brick_ask_question_edit_text);
    }

    public AskBrick(String questionText) {
        this(new Formula(questionText));
    }

    public AskBrick(Formula questionFormula, UserVariable answerVariable) {
        this(questionFormula);
        this.userVariable = answerVariable;
    }

    public AskBrick(Formula questionFormula) {
        this();
        setFormulaWithBrickField(Brick.BrickField.ASK_QUESTION, questionFormula);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createAskAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.ASK_QUESTION), this.userVariable));
    }
}

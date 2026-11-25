package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.UserVariable;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/BigAskBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class BigAskBrick extends UserVariableBrickWithFormula {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.UserVariableBrickWithFormula
    protected int getSpinnerId() {
        return R.id.brick_big_ask_spinner;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_big_ask;
    }

    public BigAskBrick() {
        addAllowedBrickField(Brick.BrickField.ASK_QUESTION, R.id.brick_big_ask_question);
        addAllowedBrickField(Brick.BrickField.TEXT, R.id.brick_big_ask_clue);
        addAllowedBrickField(Brick.BrickField.OK, R.id.brick_big_ask_ok);
        addAllowedBrickField(Brick.BrickField.CANEL, R.id.brick_big_ask_canel);
        addAllowedBrickField(Brick.BrickField.DEFAULT, R.id.brick_big_ask_default);
    }

    public BigAskBrick(String questionText, String value2, String value3, String value4, String value5) {
        this(new Formula(questionText), new Formula(value2), new Formula(value3), new Formula(value4), new Formula(value5));
    }

    public BigAskBrick(Formula questionFormula, Formula value2, Formula value3, Formula value4, Formula value5, UserVariable answerVariable) {
        this(questionFormula, value2, value3, value4, value5);
        this.userVariable = answerVariable;
    }

    public BigAskBrick(Formula questionFormula, Formula value2, Formula value3, Formula value4, Formula value5) {
        this();
        setFormulaWithBrickField(Brick.BrickField.ASK_QUESTION, questionFormula);
        setFormulaWithBrickField(Brick.BrickField.TEXT, value2);
        setFormulaWithBrickField(Brick.BrickField.OK, value3);
        setFormulaWithBrickField(Brick.BrickField.CANEL, value4);
        setFormulaWithBrickField(Brick.BrickField.DEFAULT, value5);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createBigAskAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.ASK_QUESTION), getFormulaWithBrickField(Brick.BrickField.TEXT), getFormulaWithBrickField(Brick.BrickField.OK), getFormulaWithBrickField(Brick.BrickField.CANEL), getFormulaWithBrickField(Brick.BrickField.DEFAULT), this.userVariable));
    }
}

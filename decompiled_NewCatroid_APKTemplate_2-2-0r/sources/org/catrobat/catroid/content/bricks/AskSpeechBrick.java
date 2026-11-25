package org.catrobat.catroid.content.bricks;

import java.util.Arrays;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/AskSpeechBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class AskSpeechBrick extends UserVariableBrickWithFormula {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.UserVariableBrickWithFormula
    protected int getSpinnerId() {
        return R.id.brick_ask_speech_spinner;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_ask_speech;
    }

    public AskSpeechBrick() {
        addAllowedBrickField(Brick.BrickField.ASK_SPEECH_QUESTION, R.id.brick_ask_speech_question_edit_text);
    }

    public AskSpeechBrick(String questionText) {
        this(new Formula(questionText));
    }

    public AskSpeechBrick(Formula questionFormula) {
        this();
        setFormulaWithBrickField(Brick.BrickField.ASK_SPEECH_QUESTION, questionFormula);
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public void addRequiredResources(final Brick.ResourcesSet requiredResourcesSet) {
        requiredResourcesSet.addAll(Arrays.asList(24, 23));
        super.addRequiredResources(requiredResourcesSet);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createAskSpeechAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.ASK_SPEECH_QUESTION), this.userVariable));
    }
}

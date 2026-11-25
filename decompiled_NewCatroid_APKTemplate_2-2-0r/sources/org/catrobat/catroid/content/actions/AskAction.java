package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.Action;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.stage.BrickDialogManager;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.web.ServerAuthenticationConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/AskAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: AskAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u001c\u001a\u00020\u00042\u0006\u0010\u001d\u001a\u00020\u001eH\u0016J\b\u0010\u001f\u001a\u00020 H\u0002J\b\u0010!\u001a\u00020 H\u0016J\u000e\u0010\"\u001a\u00020 2\u0006\u0010#\u001a\u00020$R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001a\u0010\u000b\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001c\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u001c\u0010\u0016\u001a\u0004\u0018\u00010\u0017X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001b¨\u0006%"}, d2 = {"Lorg/catrobat/catroid/content/actions/AskAction;", "Lcom/badlogic/gdx/scenes/scene2d/Action;", "()V", "answerReceived", "", "answerVariable", "Lorg/catrobat/catroid/formulaeditor/UserVariable;", "getAnswerVariable", "()Lorg/catrobat/catroid/formulaeditor/UserVariable;", "setAnswerVariable", "(Lorg/catrobat/catroid/formulaeditor/UserVariable;)V", "questionAsked", "getQuestionAsked", "()Z", "setQuestionAsked", "(Z)V", "questionFormula", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getQuestionFormula", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setQuestionFormula", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "act", "delta", "", "askQuestion", "", "restart", "setAnswerText", ServerAuthenticationConstants.JSON_ANSWER, "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AskAction extends Action {
    private boolean answerReceived;
    private UserVariable answerVariable;
    private boolean questionAsked;
    private Formula questionFormula;
    private Scope scope;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getQuestionFormula() {
        return this.questionFormula;
    }

    public final void setQuestionFormula(Formula formula) {
        this.questionFormula = formula;
    }

    public final UserVariable getAnswerVariable() {
        return this.answerVariable;
    }

    public final void setAnswerVariable(UserVariable userVariable) {
        this.answerVariable = userVariable;
    }

    public final boolean getQuestionAsked() {
        return this.questionAsked;
    }

    public final void setQuestionAsked(boolean z) {
        this.questionAsked = z;
    }

    private final void askQuestion() {
        String str = "";
        if (StageActivity.messageHandler == null) {
            return;
        }
        try {
            Formula formula = this.questionFormula;
            String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
            if (strInterpretString != null) {
                str = strInterpretString;
            }
        } catch (InterpretationException unused) {
            Log.e(getClass().getSimpleName(), "formula interpretation in ask brick failed");
        }
        StageActivity.messageHandler.obtainMessage(2, CollectionsKt.arrayListOf(BrickDialogManager.DialogType.ASK_DIALOG, this, str, "", "", "", "")).sendToTarget();
        this.questionAsked = true;
    }

    public final void setAnswerText(String answer) {
        Intrinsics.checkNotNullParameter(answer, "answer");
        UserVariable userVariable = this.answerVariable;
        if (userVariable == null) {
            return;
        }
        Intrinsics.checkNotNull(userVariable);
        userVariable.setValue(answer);
        this.answerReceived = true;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public boolean act(float delta) {
        if (!this.questionAsked) {
            askQuestion();
        }
        return this.answerReceived;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public void restart() {
        this.questionAsked = false;
        this.answerReceived = false;
        super.restart();
    }
}

package org.catrobat.catroid.content.actions;

import android.util.Log;
import androidx.core.app.NotificationCompat;
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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/BigAskAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: BigAskAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010(\u001a\u00020\u00042\u0006\u0010)\u001a\u00020*H\u0016J\b\u0010+\u001a\u00020,H\u0002J\b\u0010-\u001a\u00020,H\u0016J\u000e\u0010.\u001a\u00020,2\u0006\u0010/\u001a\u000200R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001c\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001c\u0010\u0011\u001a\u0004\u0018\u00010\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u000e\"\u0004\b\u0013\u0010\u0010R\u001a\u0010\u0014\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u001c\u0010\u0019\u001a\u0004\u0018\u00010\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u000e\"\u0004\b\u001b\u0010\u0010R\u001c\u0010\u001c\u001a\u0004\u0018\u00010\u001dX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u001f\"\u0004\b \u0010!R\u001c\u0010\"\u001a\u0004\u0018\u00010\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010\u000e\"\u0004\b$\u0010\u0010R\u001c\u0010%\u001a\u0004\u0018\u00010\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b&\u0010\u000e\"\u0004\b'\u0010\u0010¨\u00061"}, d2 = {"Lorg/catrobat/catroid/content/actions/BigAskAction;", "Lcom/badlogic/gdx/scenes/scene2d/Action;", "()V", "answerReceived", "", "answerVariable", "Lorg/catrobat/catroid/formulaeditor/UserVariable;", "getAnswerVariable", "()Lorg/catrobat/catroid/formulaeditor/UserVariable;", "setAnswerVariable", "(Lorg/catrobat/catroid/formulaeditor/UserVariable;)V", "canel", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getCanel", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setCanel", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", NotificationCompat.CATEGORY_MESSAGE, "getMsg", "setMsg", "questionAsked", "getQuestionAsked", "()Z", "setQuestionAsked", "(Z)V", "questionFormula", "getQuestionFormula", "setQuestionFormula", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "standardText", "getStandardText", "setStandardText", "sumb", "getSumb", "setSumb", "act", "delta", "", "askQuestion", "", "restart", "setAnswerText", ServerAuthenticationConstants.JSON_ANSWER, "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BigAskAction extends Action {
    private boolean answerReceived;
    private UserVariable answerVariable;
    private Formula canel;
    private Formula msg;
    private boolean questionAsked;
    private Formula questionFormula;
    private Scope scope;
    private Formula standardText;
    private Formula sumb;

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

    public final Formula getMsg() {
        return this.msg;
    }

    public final void setMsg(Formula formula) {
        this.msg = formula;
    }

    public final Formula getSumb() {
        return this.sumb;
    }

    public final void setSumb(Formula formula) {
        this.sumb = formula;
    }

    public final Formula getCanel() {
        return this.canel;
    }

    public final void setCanel(Formula formula) {
        this.canel = formula;
    }

    public final Formula getStandardText() {
        return this.standardText;
    }

    public final void setStandardText(Formula formula) {
        this.standardText = formula;
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
        String strInterpretString;
        String str = "";
        if (StageActivity.messageHandler == null) {
            return;
        }
        String strInterpretString2 = "Enter your answer";
        String strInterpretString3 = "Ok";
        String strInterpretString4 = "Canel";
        try {
            Formula formula = this.questionFormula;
            strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
            if (strInterpretString == null) {
                strInterpretString = "";
            }
            try {
                Formula formula2 = this.msg;
                strInterpretString2 = formula2 != null ? formula2.interpretString(this.scope) : null;
                if (strInterpretString2 == null) {
                    strInterpretString2 = "";
                }
                Formula formula3 = this.sumb;
                strInterpretString3 = formula3 != null ? formula3.interpretString(this.scope) : null;
                if (strInterpretString3 == null) {
                    strInterpretString3 = "";
                }
                Formula formula4 = this.canel;
                strInterpretString4 = formula4 != null ? formula4.interpretString(this.scope) : null;
                if (strInterpretString4 == null) {
                    strInterpretString4 = "";
                }
                Formula formula5 = this.standardText;
                String strInterpretString5 = formula5 != null ? formula5.interpretString(this.scope) : null;
                if (strInterpretString5 != null) {
                    str = strInterpretString5;
                }
            } catch (InterpretationException unused) {
                Log.e(getClass().getSimpleName(), "formula interpretation in big ask brick failed");
            }
        } catch (InterpretationException unused2) {
            strInterpretString = "";
        }
        BrickDialogManager.DialogType dialogType = BrickDialogManager.DialogType.ASK_2_DIALOG;
        StageActivity.messageHandler.obtainMessage(2, CollectionsKt.arrayListOf(dialogType, this, strInterpretString, strInterpretString2, strInterpretString3, strInterpretString4, str)).sendToTarget();
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

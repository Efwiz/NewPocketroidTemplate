package org.catrobat.catroid.content.actions;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.Action;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;
import org.catrobat.catroid.formulaeditor.SensorHandler;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.web.ServerAuthenticationConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/AskSpeechAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: AskSpeechAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0007\u0018\u0000 +2\u00020\u00012\u00020\u0002:\u0001+B\u0005¢\u0006\u0002\u0010\u0003J\u0010\u0010\u0019\u001a\u00020\u00052\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J\b\u0010\u001c\u001a\u00020\u001dH\u0002J\u0010\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!H\u0002J\b\u0010\"\u001a\u00020\u001fH\u0016J \u0010#\u001a\u00020\u00052\u0006\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020%2\u0006\u0010'\u001a\u00020\u001fH\u0016J\b\u0010(\u001a\u00020\u001dH\u0016J\u000e\u0010)\u001a\u00020\u001d2\u0006\u0010*\u001a\u00020!R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u000e\u0010\f\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u001c\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018¨\u0006,"}, d2 = {"Lorg/catrobat/catroid/content/actions/AskSpeechAction;", "Lcom/badlogic/gdx/scenes/scene2d/Action;", "Lorg/catrobat/catroid/stage/StageActivity$IntentListener;", "()V", "answerReceived", "", "answerVariable", "Lorg/catrobat/catroid/formulaeditor/UserVariable;", "getAnswerVariable", "()Lorg/catrobat/catroid/formulaeditor/UserVariable;", "setAnswerVariable", "(Lorg/catrobat/catroid/formulaeditor/UserVariable;)V", "questionAsked", "questionFormula", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getQuestionFormula", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setQuestionFormula", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "act", "delta", "", "askQuestion", "", "createRecognitionIntent", "Landroid/content/Intent;", "question", "", "getTargetIntent", "onIntentResult", "requestCode", "", "resultCode", "data", "restart", "setAnswerText", ServerAuthenticationConstants.JSON_ANSWER, "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AskSpeechAction extends Action implements StageActivity.IntentListener {
    private static final String TAG = "AskSpeechAction";
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

    private final void askQuestion() {
        Message messageObtainMessage;
        Handler handler = StageActivity.messageHandler;
        if (handler != null && (messageObtainMessage = handler.obtainMessage(0, CollectionsKt.arrayListOf(this))) != null) {
            messageObtainMessage.sendToTarget();
        }
        this.questionAsked = true;
    }

    private final Intent createRecognitionIntent(String question) {
        Intent intent = new Intent("android.speech.action.RECOGNIZE_SPEECH");
        intent.putExtra("android.speech.extra.LANGUAGE_MODEL", "free_form");
        intent.putExtra("android.speech.extra.LANGUAGE", SensorHandler.getListeningLanguageSensor());
        if (!StringsKt.isBlank(question)) {
            intent.putExtra("android.speech.extra.PROMPT", question);
        }
        return intent;
    }

    public final void setAnswerText(String answer) {
        Intrinsics.checkNotNullParameter(answer, "answer");
        UserVariable userVariable = this.answerVariable;
        if (userVariable != null) {
            userVariable.setValue(answer);
        }
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

    @Override // org.catrobat.catroid.stage.StageActivity.IntentListener
    /* JADX INFO: renamed from: getTargetIntent */
    public Intent get$intent() {
        String str = "";
        try {
            Formula formula = this.questionFormula;
            String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
            if (strInterpretString != null) {
                str = strInterpretString;
            }
        } catch (InterpretationException unused) {
            Log.e(TAG, "Formula interpretation in ask brick failed");
        }
        return createRecognitionIntent(str);
    }

    @Override // org.catrobat.catroid.stage.StageActivity.IntentListener
    public boolean onIntentResult(int requestCode, int resultCode, Intent data) {
        String str;
        Intrinsics.checkNotNullParameter(data, "data");
        String str2 = "";
        if (resultCode != -1) {
            if (resultCode == 0 || resultCode == 1) {
                setAnswerText("");
            } else {
                Log.e(TAG, "unhandled speech recognizer resultCode " + resultCode);
            }
            return false;
        }
        ArrayList<String> stringArrayListExtra = data.getStringArrayListExtra("android.speech.extra.RESULTS");
        Log.d(TAG, "Speech recognition results: " + stringArrayListExtra);
        if (stringArrayListExtra != null && (str = (String) CollectionsKt.getOrNull(stringArrayListExtra, 0)) != null) {
            str2 = str;
        }
        setAnswerText(str2);
        return true;
    }
}

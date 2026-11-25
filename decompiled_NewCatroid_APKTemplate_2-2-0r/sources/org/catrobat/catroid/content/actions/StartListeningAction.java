package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.stage.OnSpeechRecognitionResultCallback;
import org.catrobat.catroid.stage.SpeechRecognitionHolderFactory;
import org.catrobat.catroid.stage.SpeechRecognitionHolderInterface;
import org.catrobat.catroid.stage.StageActivity;
import org.koin.java.KoinJavaComponent;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/StartListeningAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: StartListeningAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\u0018\u0000 \u001a2\u00020\u00012\u00020\u0002:\u0001\u001aB\u0005¢\u0006\u0002\u0010\u0003J\u0010\u0010\u000f\u001a\u00020\u00052\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\b\u0010\u0016\u001a\u00020\u0013H\u0002J\b\u0010\u0017\u001a\u00020\u0013H\u0016J\u000e\u0010\u0018\u001a\u00020\u00132\u0006\u0010\u0019\u001a\u00020\u0015R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e¨\u0006\u001b"}, d2 = {"Lorg/catrobat/catroid/content/actions/StartListeningAction;", "Lcom/badlogic/gdx/scenes/scene2d/Action;", "Lorg/catrobat/catroid/stage/OnSpeechRecognitionResultCallback;", "()V", "listeningRequested", "", "speechRecognitionHolder", "Lorg/catrobat/catroid/stage/SpeechRecognitionHolderInterface;", "spokenWordsReceived", "userVariable", "Lorg/catrobat/catroid/formulaeditor/UserVariable;", "getUserVariable", "()Lorg/catrobat/catroid/formulaeditor/UserVariable;", "setUserVariable", "(Lorg/catrobat/catroid/formulaeditor/UserVariable;)V", "act", "delta", "", "onResult", "", "spokenWords", "", "requestStartListening", "restart", "toast", "value", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class StartListeningAction extends Action implements OnSpeechRecognitionResultCallback {
    private static final String TAG = "SpeechRecognitionBrick";
    private boolean listeningRequested;
    private final SpeechRecognitionHolderInterface speechRecognitionHolder = ((SpeechRecognitionHolderFactory) KoinJavaComponent.get$default(SpeechRecognitionHolderFactory.class, null, null, 6, null)).getInstance();
    private boolean spokenWordsReceived;
    private UserVariable userVariable;

    public final UserVariable getUserVariable() {
        return this.userVariable;
    }

    public final void setUserVariable(UserVariable userVariable) {
        this.userVariable = userVariable;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public boolean act(float delta) {
        if (!this.listeningRequested) {
            requestStartListening();
        }
        return this.spokenWordsReceived;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public void restart() {
        this.listeningRequested = false;
        this.spokenWordsReceived = false;
        super.restart();
    }

    public final void toast(String value) {
        Intrinsics.checkNotNullParameter(value, "value");
        StageActivity.messageHandler.obtainMessage(3, new ArrayList(CollectionsKt.listOf(value))).sendToTarget();
    }

    private final void requestStartListening() {
        SpeechRecognitionHolderInterface speechRecognitionHolderInterface = this.speechRecognitionHolder;
        speechRecognitionHolderInterface.setCallback(this);
        speechRecognitionHolderInterface.startListening();
        this.listeningRequested = true;
    }

    @Override // org.catrobat.catroid.stage.OnSpeechRecognitionResultCallback
    public void onResult(String spokenWords) {
        Intrinsics.checkNotNullParameter(spokenWords, "spokenWords");
        toast("recived");
        UserVariable userVariable = this.userVariable;
        if (userVariable != null) {
            userVariable.setValue(spokenWords);
        }
        this.spokenWordsReceived = true;
    }
}

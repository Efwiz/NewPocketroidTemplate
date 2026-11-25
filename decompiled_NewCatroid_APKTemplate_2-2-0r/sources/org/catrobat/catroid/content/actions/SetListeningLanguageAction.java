package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.formulaeditor.SensorHandler;
import org.catrobat.catroid.stage.SpeechRecognitionHolderFactory;
import org.koin.java.KoinJavaComponent;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/SetListeningLanguageAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: SetListeningLanguageAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\rH\u0016J\b\u0010\u000e\u001a\u00020\u000fH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\n¨\u0006\u0010"}, d2 = {"Lorg/catrobat/catroid/content/actions/SetListeningLanguageAction;", "Lcom/badlogic/gdx/scenes/scene2d/Action;", "()V", "languageChanged", "", "listeningLanguageTag", "", "getListeningLanguageTag", "()Ljava/lang/String;", "setListeningLanguageTag", "(Ljava/lang/String;)V", "act", "delta", "", "changeLanguage", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SetListeningLanguageAction extends Action {
    private boolean languageChanged;
    public String listeningLanguageTag;

    public final String getListeningLanguageTag() {
        String str = this.listeningLanguageTag;
        if (str != null) {
            return str;
        }
        Intrinsics.throwUninitializedPropertyAccessException("listeningLanguageTag");
        return null;
    }

    public final void setListeningLanguageTag(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.listeningLanguageTag = str;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public boolean act(float delta) {
        if (!this.languageChanged) {
            changeLanguage();
        }
        return this.languageChanged;
    }

    private final void changeLanguage() {
        SensorHandler.setListeningLanguageSensor(getListeningLanguageTag());
        ((SpeechRecognitionHolderFactory) KoinJavaComponent.get$default(SpeechRecognitionHolderFactory.class, null, null, 6, null)).getInstance().forceSetLanguage();
        this.languageChanged = true;
    }
}

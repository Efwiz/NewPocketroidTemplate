package org.catrobat.catroid.stage;

import kotlin.Metadata;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/stage/SpeechRecognitionHolderInterface.class
  classes14.dex
 */
/* JADX INFO: compiled from: SpeechRecognitionHolderInterface.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\b\u0010\b\u001a\u00020\tH&J\b\u0010\n\u001a\u00020\tH&J\u0018\u0010\u000b\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH&J\b\u0010\u0010\u001a\u00020\tH&R\u001a\u0010\u0002\u001a\u0004\u0018\u00010\u0003X¦\u000e¢\u0006\f\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007¨\u0006\u0011"}, d2 = {"Lorg/catrobat/catroid/stage/SpeechRecognitionHolderInterface;", "", "callback", "Lorg/catrobat/catroid/stage/OnSpeechRecognitionResultCallback;", "getCallback", "()Lorg/catrobat/catroid/stage/OnSpeechRecognitionResultCallback;", "setCallback", "(Lorg/catrobat/catroid/stage/OnSpeechRecognitionResultCallback;)V", "destroy", "", "forceSetLanguage", "initSpeechRecognition", "stageActivity", "Lorg/catrobat/catroid/stage/StageActivity;", "stageResourceHolder", "Lorg/catrobat/catroid/stage/StageResourceHolder;", "startListening", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface SpeechRecognitionHolderInterface {
    void destroy();

    void forceSetLanguage();

    OnSpeechRecognitionResultCallback getCallback();

    void initSpeechRecognition(StageActivity stageActivity, StageResourceHolder stageResourceHolder);

    void setCallback(OnSpeechRecognitionResultCallback onSpeechRecognitionResultCallback);

    void startListening();
}

package org.catrobat.catroid.stage;

import android.content.Context;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.utils.MobileServiceAvailability;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/stage/SpeechRecognitionHolderFactory.class
  classes14.dex
 */
/* JADX INFO: compiled from: SpeechRecognitionHolderFactory.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00102\u00020\u0001:\u0001\u0010B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u000e\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\t\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\u0003@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lorg/catrobat/catroid/stage/SpeechRecognitionHolderFactory;", "", "gmsSpeechRecognitionHolder", "Lorg/catrobat/catroid/stage/SpeechRecognitionHolderInterface;", "hmsSpeechRecognitionHolder", "mobileServiceAvailability", "Lorg/catrobat/catroid/utils/MobileServiceAvailability;", "(Lorg/catrobat/catroid/stage/SpeechRecognitionHolderInterface;Lorg/catrobat/catroid/stage/SpeechRecognitionHolderInterface;Lorg/catrobat/catroid/utils/MobileServiceAvailability;)V", "<set-?>", "instance", "getInstance", "()Lorg/catrobat/catroid/stage/SpeechRecognitionHolderInterface;", "isRecognitionAvailable", "", "context", "Landroid/content/Context;", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SpeechRecognitionHolderFactory {
    private static final SpeechRecognitionHolderFactory$Companion$dummy$1 dummy = new SpeechRecognitionHolderInterface() { // from class: org.catrobat.catroid.stage.SpeechRecognitionHolderFactory$Companion$dummy$1
        private OnSpeechRecognitionResultCallback callback = new OnSpeechRecognitionResultCallback() { // from class: org.catrobat.catroid.stage.SpeechRecognitionHolderFactory$Companion$dummy$1$callback$1
            @Override // org.catrobat.catroid.stage.OnSpeechRecognitionResultCallback
            public void onResult(String spokenWords) {
                Intrinsics.checkNotNullParameter(spokenWords, "spokenWords");
            }
        };

        @Override // org.catrobat.catroid.stage.SpeechRecognitionHolderInterface
        public void destroy() {
        }

        @Override // org.catrobat.catroid.stage.SpeechRecognitionHolderInterface
        public void forceSetLanguage() {
        }

        @Override // org.catrobat.catroid.stage.SpeechRecognitionHolderInterface
        public void initSpeechRecognition(StageActivity stageActivity, StageResourceHolder stageResourceHolder) {
            Intrinsics.checkNotNullParameter(stageActivity, "stageActivity");
            Intrinsics.checkNotNullParameter(stageResourceHolder, "stageResourceHolder");
        }

        @Override // org.catrobat.catroid.stage.SpeechRecognitionHolderInterface
        public void startListening() {
        }

        @Override // org.catrobat.catroid.stage.SpeechRecognitionHolderInterface
        public OnSpeechRecognitionResultCallback getCallback() {
            return this.callback;
        }

        @Override // org.catrobat.catroid.stage.SpeechRecognitionHolderInterface
        public void setCallback(OnSpeechRecognitionResultCallback onSpeechRecognitionResultCallback) {
            this.callback = onSpeechRecognitionResultCallback;
        }
    };
    private final SpeechRecognitionHolderInterface gmsSpeechRecognitionHolder;
    private final SpeechRecognitionHolderInterface hmsSpeechRecognitionHolder;
    private SpeechRecognitionHolderInterface instance;
    private final MobileServiceAvailability mobileServiceAvailability;

    public SpeechRecognitionHolderFactory(SpeechRecognitionHolderInterface gmsSpeechRecognitionHolder, SpeechRecognitionHolderInterface hmsSpeechRecognitionHolder, MobileServiceAvailability mobileServiceAvailability) {
        Intrinsics.checkNotNullParameter(gmsSpeechRecognitionHolder, "gmsSpeechRecognitionHolder");
        Intrinsics.checkNotNullParameter(hmsSpeechRecognitionHolder, "hmsSpeechRecognitionHolder");
        Intrinsics.checkNotNullParameter(mobileServiceAvailability, "mobileServiceAvailability");
        this.gmsSpeechRecognitionHolder = gmsSpeechRecognitionHolder;
        this.hmsSpeechRecognitionHolder = hmsSpeechRecognitionHolder;
        this.mobileServiceAvailability = mobileServiceAvailability;
        this.instance = dummy;
    }

    public final SpeechRecognitionHolderInterface getInstance() {
        return this.instance;
    }

    public final boolean isRecognitionAvailable(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (this.mobileServiceAvailability.isGmsAvailable(context)) {
            this.instance = this.gmsSpeechRecognitionHolder;
            return true;
        }
        if (!this.mobileServiceAvailability.isHmsAvailable(context)) {
            return false;
        }
        this.instance = this.hmsSpeechRecognitionHolder;
        return true;
    }
}

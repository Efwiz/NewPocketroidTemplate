package org.catrobat.catroid.stage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.huawei.hms.mlsdk.asr.MLAsrListener;
import com.huawei.hms.mlsdk.asr.MLAsrRecognizer;
import java.lang.ref.WeakReference;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.formulaeditor.SensorHandler;
import org.catrobat.catroid.utils.ToastUtil;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/stage/HmsSpeechRecognitionHolder.class
  classes14.dex
 */
/* JADX INFO: compiled from: HmsSpeechRecognitionHolder.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000G\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002*\u0001\r\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0013\u001a\u00020\u0014H\u0016J\b\u0010\u0015\u001a\u00020\u0014H\u0016J\u0018\u0010\u0016\u001a\u00020\u00142\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aH\u0016J\b\u0010\u001b\u001a\u00020\u0014H\u0016R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u000eR\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001c"}, d2 = {"Lorg/catrobat/catroid/stage/HmsSpeechRecognitionHolder;", "Lorg/catrobat/catroid/stage/SpeechRecognitionHolderInterface;", "()V", "callback", "Lorg/catrobat/catroid/stage/OnSpeechRecognitionResultCallback;", "getCallback", "()Lorg/catrobat/catroid/stage/OnSpeechRecognitionResultCallback;", "setCallback", "(Lorg/catrobat/catroid/stage/OnSpeechRecognitionResultCallback;)V", "context", "Ljava/lang/ref/WeakReference;", "Landroid/content/Context;", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "org/catrobat/catroid/stage/HmsSpeechRecognitionHolder$listener$1", "Lorg/catrobat/catroid/stage/HmsSpeechRecognitionHolder$listener$1;", "speechIntent", "Landroid/content/Intent;", "speechRecognizer", "Lcom/huawei/hms/mlsdk/asr/MLAsrRecognizer;", "destroy", "", "forceSetLanguage", "initSpeechRecognition", "stageActivity", "Lorg/catrobat/catroid/stage/StageActivity;", "stageResourceHolder", "Lorg/catrobat/catroid/stage/StageResourceHolder;", "startListening", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class HmsSpeechRecognitionHolder implements SpeechRecognitionHolderInterface {
    private OnSpeechRecognitionResultCallback callback;
    private WeakReference<Context> context = new WeakReference<>(null);
    private final HmsSpeechRecognitionHolder$listener$1 listener = new MLAsrListener() { // from class: org.catrobat.catroid.stage.HmsSpeechRecognitionHolder$listener$1
        @Override // com.huawei.hms.mlsdk.asr.MLAsrListener
        public void onRecognizingResults(Bundle result) {
        }

        @Override // com.huawei.hms.mlsdk.asr.MLAsrListener
        public void onStartListening() {
        }

        @Override // com.huawei.hms.mlsdk.asr.MLAsrListener
        public void onStartingOfSpeech() {
        }

        @Override // com.huawei.hms.mlsdk.asr.MLAsrListener
        public void onState(int state, Bundle params) {
        }

        @Override // com.huawei.hms.mlsdk.asr.MLAsrListener
        public void onVoiceDataReceived(byte[] data, float energy, Bundle params) {
        }

        @Override // com.huawei.hms.mlsdk.asr.MLAsrListener
        public void onResults(Bundle result) {
            String string = result != null ? result.getString(MLAsrRecognizer.RESULTS_RECOGNIZED) : null;
            OnSpeechRecognitionResultCallback callback = this.this$0.getCallback();
            if (callback != null) {
                if (string == null) {
                    string = "";
                }
                callback.onResult(string);
            }
        }

        @Override // com.huawei.hms.mlsdk.asr.MLAsrListener
        public void onError(int error, String errorMessage) {
            if (error == 11202) {
                ToastUtil.showError((Context) this.this$0.context.get(), R.string.error_no_network_title);
            }
        }
    };
    private Intent speechIntent;
    private MLAsrRecognizer speechRecognizer;

    @Override // org.catrobat.catroid.stage.SpeechRecognitionHolderInterface
    public OnSpeechRecognitionResultCallback getCallback() {
        return this.callback;
    }

    @Override // org.catrobat.catroid.stage.SpeechRecognitionHolderInterface
    public void setCallback(OnSpeechRecognitionResultCallback onSpeechRecognitionResultCallback) {
        this.callback = onSpeechRecognitionResultCallback;
    }

    @Override // org.catrobat.catroid.stage.SpeechRecognitionHolderInterface
    public void forceSetLanguage() {
        Intent intent = this.speechIntent;
        if (intent != null) {
            intent.putExtra("LANGUAGE", SensorHandler.getListeningLanguageSensor());
        }
    }

    @Override // org.catrobat.catroid.stage.SpeechRecognitionHolderInterface
    public void initSpeechRecognition(StageActivity stageActivity, StageResourceHolder stageResourceHolder) {
        Intrinsics.checkNotNullParameter(stageActivity, "stageActivity");
        Intrinsics.checkNotNullParameter(stageResourceHolder, "stageResourceHolder");
        this.context = new WeakReference<>(stageActivity);
        this.speechIntent = new Intent("android.speech.action.RECOGNIZE_SPEECH").putExtra("LANGUAGE", SensorHandler.getListeningLanguageSensor()).putExtra("FEATURE", 12);
    }

    @Override // org.catrobat.catroid.stage.SpeechRecognitionHolderInterface
    public void startListening() {
        Context context = this.context.get();
        if (context != null) {
            MLAsrRecognizer mLAsrRecognizerCreateAsrRecognizer = MLAsrRecognizer.createAsrRecognizer(context);
            this.speechRecognizer = mLAsrRecognizerCreateAsrRecognizer;
            mLAsrRecognizerCreateAsrRecognizer.setAsrListener(this.listener);
            mLAsrRecognizerCreateAsrRecognizer.startRecognizing(this.speechIntent);
        }
    }

    @Override // org.catrobat.catroid.stage.SpeechRecognitionHolderInterface
    public void destroy() {
        MLAsrRecognizer mLAsrRecognizer = this.speechRecognizer;
        if (mLAsrRecognizer != null) {
            mLAsrRecognizer.destroy();
            this.speechRecognizer = null;
        }
    }
}

package org.catrobat.catroid.stage;

import android.os.Bundle;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.util.Pair;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.huawei.hms.mlsdk.tts.MLTtsAudioFragment;
import com.huawei.hms.mlsdk.tts.MLTtsCallback;
import com.huawei.hms.mlsdk.tts.MLTtsError;
import com.huawei.hms.mlsdk.tts.MLTtsWarn;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.utils.PcmToWavConverter;
import org.catrobat.catroid.utils.ShowTextUtils;
import org.catrobat.catroid.utils.Utils;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/stage/SpeechSynthesizer.class
  classes14.dex
 */
/* JADX INFO: compiled from: SpeechSynthesizer.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0019\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\"\u0010\u0014\u001a\u00020\u00152\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00150\u00172\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00150\u0017J\"\u0010\u0019\u001a\u00020\u00152\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00150\u00172\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00150\u0017J\u000e\u0010\u001a\u001a\u00020\u00152\u0006\u0010\u001b\u001a\u00020\u001cR\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\u0001X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u0001X\u0082\u000e¢\u0006\u0002\n\u0000R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\"\u0010\u000f\u001a\u0004\u0018\u00010\u000e2\b\u0010\r\u001a\u0004\u0018\u00010\u000e@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013¨\u0006\u001d"}, d2 = {"Lorg/catrobat/catroid/stage/SpeechSynthesizer;", "", "scope", "Lorg/catrobat/catroid/content/Scope;", "text", "Lorg/catrobat/catroid/formulaeditor/Formula;", "(Lorg/catrobat/catroid/content/Scope;Lorg/catrobat/catroid/formulaeditor/Formula;)V", "hashText", "", "interpretedText", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "getScope", "()Lorg/catrobat/catroid/content/Scope;", "<set-?>", "Ljava/io/File;", "speechFile", "getSpeechFile", "()Ljava/io/File;", "getText", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setHuaweiTextToSpeechListener", "", "onError", "Lkotlin/Function0;", "onDone", "setUtteranceProgressListener", "synthesize", "androidStringProvider", "Lorg/catrobat/catroid/utils/ShowTextUtils$AndroidStringProvider;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SpeechSynthesizer {
    private String hashText;
    private Object interpretedText;
    private Object listener;
    private final Scope scope;
    private File speechFile;
    private final Formula text;

    public SpeechSynthesizer(Scope scope, Formula formula) {
        this.scope = scope;
        this.text = formula;
    }

    public final Scope getScope() {
        return this.scope;
    }

    public final Formula getText() {
        return this.text;
    }

    public final File getSpeechFile() {
        return this.speechFile;
    }

    public final void synthesize(ShowTextUtils.AndroidStringProvider androidStringProvider) {
        String userFriendlyString;
        Intrinsics.checkNotNullParameter(androidStringProvider, "androidStringProvider");
        Object obj = this.listener;
        if (obj == null) {
            return;
        }
        Formula formula = this.text;
        if (formula != null) {
            userFriendlyString = formula.getUserFriendlyString(androidStringProvider, this.scope);
        } else {
            userFriendlyString = "";
        }
        this.interpretedText = userFriendlyString;
        String strMd5Checksum = Utils.md5Checksum(String.valueOf(userFriendlyString));
        this.hashText = strMd5Checksum;
        File file = new File(Constants.TEXT_TO_SPEECH_TMP_PATH);
        file.mkdirs();
        File file2 = new File(file, strMd5Checksum + Constants.DEFAULT_SOUND_EXTENSION);
        this.speechFile = file2;
        if (obj instanceof UtteranceProgressListener) {
            HashMap<String, String> map = new HashMap<>();
            map.put("utteranceId", this.hashText);
            TextToSpeechHolder.INSTANCE.getInstance().textToSpeech(String.valueOf(this.interpretedText), file2, (UtteranceProgressListener) obj, map);
        } else if (obj instanceof MLTtsCallback) {
            HuaweiTextToSpeechHolder.INSTANCE.getMlTtsEngine().setTtsCallback((MLTtsCallback) obj);
            HuaweiTextToSpeechHolder.INSTANCE.getInstance().textToSpeech(String.valueOf(this.interpretedText));
        }
    }

    public final void setUtteranceProgressListener(final Function0<Unit> onError, final Function0<Unit> onDone) {
        Intrinsics.checkNotNullParameter(onError, "onError");
        Intrinsics.checkNotNullParameter(onDone, "onDone");
        this.listener = new UtteranceProgressListener() { // from class: org.catrobat.catroid.stage.SpeechSynthesizer.setUtteranceProgressListener.1
            @Override // android.speech.tts.UtteranceProgressListener
            public void onStart(String utteranceId) {
                Intrinsics.checkNotNullParameter(utteranceId, "utteranceId");
            }

            @Override // android.speech.tts.UtteranceProgressListener
            public void onError(String utteranceId) {
                Intrinsics.checkNotNullParameter(utteranceId, "utteranceId");
                onError.invoke();
            }

            @Override // android.speech.tts.UtteranceProgressListener
            public void onDone(String utteranceId) {
                Intrinsics.checkNotNullParameter(utteranceId, "utteranceId");
                onDone.invoke();
            }
        };
    }

    public final void setHuaweiTextToSpeechListener(final Function0<Unit> onError, final Function0<Unit> onDone) {
        Intrinsics.checkNotNullParameter(onError, "onError");
        Intrinsics.checkNotNullParameter(onDone, "onDone");
        this.listener = new MLTtsCallback() { // from class: org.catrobat.catroid.stage.SpeechSynthesizer.setHuaweiTextToSpeechListener.1
            private File pcmAudioFile;

            @Override // com.huawei.hms.mlsdk.tts.MLTtsCallback
            public void onError(String taskId, MLTtsError err) {
                Intrinsics.checkNotNullParameter(taskId, "taskId");
                Intrinsics.checkNotNullParameter(err, "err");
                onError.invoke();
            }

            @Override // com.huawei.hms.mlsdk.tts.MLTtsCallback
            public void onWarn(String taskId, MLTtsWarn warn) {
                Intrinsics.checkNotNullParameter(taskId, "taskId");
                Intrinsics.checkNotNullParameter(warn, "warn");
                Log.w(SpeechSynthesizerKt.TAG, "Warning when synthesizing voice with message: " + warn.getWarnMsg());
            }

            @Override // com.huawei.hms.mlsdk.tts.MLTtsCallback
            public void onRangeStart(String taskId, int start, int end) {
                Intrinsics.checkNotNullParameter(taskId, "taskId");
                Log.d(SpeechSynthesizerKt.TAG, "Starting range start: " + start + " end: " + end);
            }

            @Override // com.huawei.hms.mlsdk.tts.MLTtsCallback
            public void onAudioAvailable(String taskId, MLTtsAudioFragment audioFragment, int offset, Pair<Integer, Integer> range, Bundle bundle) throws Throwable {
                if (taskId == null || audioFragment == null) {
                    return;
                }
                PcmToWavConverter pcmToWavConverter = PcmToWavConverter.INSTANCE;
                byte[] audioData = audioFragment.getAudioData();
                File file = this.pcmAudioFile;
                if (file == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("pcmAudioFile");
                    file = null;
                }
                pcmToWavConverter.writePcmToFile(audioData, file, true);
            }

            @Override // com.huawei.hms.mlsdk.tts.MLTtsCallback
            public void onEvent(String taskId, int eventId, Bundle bundle) throws IOException {
                File file;
                if (eventId == 5) {
                    File file2 = new File(Constants.TEXT_TO_SPEECH_TMP_PATH);
                    file2.mkdirs();
                    this.pcmAudioFile = new File(file2, "placeholder.pcm");
                    return;
                }
                if (eventId != 7) {
                    return;
                }
                PcmToWavConverter pcmToWavConverter = PcmToWavConverter.INSTANCE;
                File file3 = this.pcmAudioFile;
                File file4 = null;
                if (file3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("pcmAudioFile");
                    file = null;
                } else {
                    file = file3;
                }
                pcmToWavConverter.convertPcmToWav(file, this.getSpeechFile(), 1, MLTtsAudioFragment.SAMPLE_RATE_16K, 16);
                File file5 = this.pcmAudioFile;
                if (file5 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("pcmAudioFile");
                } else {
                    file4 = file5;
                }
                file4.delete();
                onDone.invoke();
            }
        };
    }
}

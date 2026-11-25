package org.catrobat.catroid.content;

import android.content.Context;
import android.media.AudioRecord;
import android.util.Log;
import androidx.core.content.ContextCompat;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/AudioAnalyzerController.class
  classes14.dex
 */
/* JADX INFO: compiled from: AudioAnalyzerController.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\u0018\u0000 \u001c2\u00020\u0001:\u0001\u001cB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0018\u001a\u00020\u0019H\u0002J\u0006\u0010\u001a\u001a\u00020\u0019J\u0006\u0010\u001b\u001a\u00020\u0019R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082D¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\bX\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\r\u001a\u00020\u000e@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u001e\u0010\u0012\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\b@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\bX\u0082D¢\u0006\u0002\n\u0000¨\u0006\u001d"}, d2 = {"Lorg/catrobat/catroid/content/AudioAnalyzerController;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "analyzeThread", "Ljava/lang/Thread;", "audioFormat", "", "audioRecord", "Landroid/media/AudioRecord;", "bufferSize", "channelConfig", "<set-?>", "", "currentFrequency", "getCurrentFrequency", "()F", "currentVolume", "getCurrentVolume", "()I", "isRecording", "", "sampleRate", "analyzeAudioStream", "", "start", "stop", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AudioAnalyzerController {
    private static final Companion Companion = new Companion(null);
    private static final String TAG = AudioAnalyzerController.class.getName();
    private Thread analyzeThread;
    private final int audioFormat;
    private AudioRecord audioRecord;
    private final int bufferSize;
    private final int channelConfig;
    private final Context context;
    private float currentFrequency;
    private int currentVolume;
    private boolean isRecording;
    private final int sampleRate;

    public AudioAnalyzerController(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.sampleRate = 44100;
        this.channelConfig = 16;
        this.audioFormat = 2;
        this.bufferSize = AudioRecord.getMinBufferSize(44100, 16, 2);
    }

    public final int getCurrentVolume() {
        return this.currentVolume;
    }

    public final float getCurrentFrequency() {
        return this.currentFrequency;
    }

    public final void start() {
        if (this.isRecording) {
            return;
        }
        if (ContextCompat.checkSelfPermission(this.context, "android.permission.RECORD_AUDIO") != 0) {
            Log.e(TAG, "Нет разрешения на использование микрофона. Поназапрещали всякого, твари :(");
            return;
        }
        try {
            AudioRecord audioRecord = new AudioRecord(1, this.sampleRate, this.channelConfig, this.audioFormat, this.bufferSize);
            this.audioRecord = audioRecord;
            audioRecord.startRecording();
            this.isRecording = true;
            Thread thread = new Thread(new Runnable() { // from class: org.catrobat.catroid.content.AudioAnalyzerController$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    AudioAnalyzerController.start$lambda$0(this.f$0);
                }
            });
            this.analyzeThread = thread;
            thread.start();
            Log.d(TAG, "Microphone started listening");
        } catch (SecurityException e) {
            Log.e(TAG, "Ошибка прав доступа: " + e.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void start$lambda$0(AudioAnalyzerController this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.analyzeAudioStream();
    }

    private final void analyzeAudioStream() {
        int i = this.bufferSize;
        short[] sArr = new short[i];
        while (this.isRecording) {
            AudioRecord audioRecord = this.audioRecord;
            int i2 = 0;
            int i3 = audioRecord != null ? audioRecord.read(sArr, 0, i) : 0;
            if (i3 > 0) {
                int i4 = 0;
                for (int i5 = 0; i5 < i3; i5++) {
                    int iAbs = Math.abs((int) sArr[i5]);
                    if (iAbs > i4) {
                        i4 = iAbs;
                    }
                }
                this.currentVolume = i4;
                for (int i6 = 1; i6 < i3; i6++) {
                    if (sArr[i6 - 1] < 0 && sArr[i6] >= 0) {
                        i2++;
                    }
                }
                if (i2 > 0) {
                    this.currentFrequency = (i2 * this.sampleRate) / i3;
                } else {
                    this.currentFrequency = 0.0f;
                }
                if (this.currentVolume < 500) {
                    this.currentFrequency = 0.0f;
                }
            }
        }
    }

    public final void stop() throws InterruptedException {
        this.isRecording = false;
        Thread thread = this.analyzeThread;
        if (thread != null) {
            thread.join(500L);
        }
        AudioRecord audioRecord = this.audioRecord;
        if (audioRecord != null) {
            audioRecord.stop();
            audioRecord.release();
        }
        this.audioRecord = null;
        this.currentVolume = 0;
        this.currentFrequency = 0.0f;
        Log.d(TAG, "Microphone stopped");
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/AudioAnalyzerController$Companion.class
      classes14.dex
     */
    /* JADX INFO: compiled from: AudioAnalyzerController.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0082\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0016\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0006"}, d2 = {"Lorg/catrobat/catroid/content/AudioAnalyzerController$Companion;", "", "()V", "TAG", "", JvmProtoBufUtil.PLATFORM_TYPE_ID, "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}

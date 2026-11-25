package org.catrobat.catroid.soundrecorder;

import android.media.MediaRecorder;
import android.util.Log;
import java.io.File;
import java.io.IOException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/soundrecorder/SoundRecorder.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SoundRecorder {
    public static final String RECORDING_EXTENSION = ".m4a";
    public static final String TAG = "SoundRecorder";
    private boolean isRecording;
    private String path;
    private MediaRecorder recorder = new MediaRecorder();

    public SoundRecorder(String path) {
        this.path = path;
    }

    public void start() throws IOException, RuntimeException {
        File file = new File(this.path);
        if (file.exists()) {
            file.delete();
        }
        File parentFile = file.getParentFile();
        if (!parentFile.exists() && !parentFile.mkdirs()) {
            throw new IOException("Path to file could not be created.");
        }
        this.recorder.reset();
        this.recorder.setAudioSource(1);
        this.recorder.setOutputFormat(2);
        this.recorder.setAudioEncoder(3);
        this.recorder.setOutputFile(this.path);
        this.recorder.prepare();
        this.recorder.start();
        this.isRecording = true;
    }

    public void stop() throws IOException {
        try {
            this.recorder.stop();
        } catch (RuntimeException unused) {
            Log.d(TAG, "Note that a RuntimeException is intentionally thrown to the application, if no valid audio/video data has been received when stop() is called. This happens if stop() is called immediately after start(). The failure lets the application take action accordingly to clean up the output file (delete the output file, for instance), since the output file is not properly constructed when this happens.");
        }
        this.recorder.reset();
        this.recorder.release();
        this.isRecording = false;
    }

    public String getPath() {
        return this.path;
    }

    public int getMaxAmplitude() {
        return this.recorder.getMaxAmplitude();
    }

    public boolean isRecording() {
        return this.isRecording;
    }
}

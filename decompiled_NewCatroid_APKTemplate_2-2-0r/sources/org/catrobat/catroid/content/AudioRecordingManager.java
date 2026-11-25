package org.catrobat.catroid.content;

import android.content.Context;
import android.media.MediaRecorder;
import android.os.Build;
import android.util.Log;
import java.io.File;
import java.io.IOException;
import org.catrobat.catroid.io.StorageOperations;
import org.catrobat.catroid.soundrecorder.SoundRecorder;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/AudioRecordingManager.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class AudioRecordingManager {
    private static final AudioRecordingManager INSTANCE = new AudioRecordingManager();
    private static final String TAG = "AudioRecordingManager";
    private MediaRecorder recorder;
    private File tempAudioFile;

    private AudioRecordingManager() {
    }

    public static AudioRecordingManager getInstance() {
        return INSTANCE;
    }

    public boolean startRecording(Context context) {
        if (this.recorder != null) {
            Log.w(TAG, "Recording is already in progress. Please stop the current one first.");
            return false;
        }
        try {
            this.tempAudioFile = File.createTempFile("recording_temp", SoundRecorder.RECORDING_EXTENSION, context.getCacheDir());
            if (Build.VERSION.SDK_INT >= 31) {
                this.recorder = new MediaRecorder(context);
            } else {
                this.recorder = new MediaRecorder();
            }
            this.recorder.setAudioSource(1);
            this.recorder.setOutputFormat(2);
            this.recorder.setAudioEncoder(3);
            this.recorder.setOutputFile(this.tempAudioFile.getAbsolutePath());
            this.recorder.prepare();
            this.recorder.start();
            Log.i(TAG, "Recording started to temporary file: " + this.tempAudioFile.getAbsolutePath());
            return true;
        } catch (IOException | IllegalStateException e) {
            Log.e(TAG, "Failed to start recording", e);
            MediaRecorder mediaRecorder = this.recorder;
            if (mediaRecorder != null) {
                mediaRecorder.release();
                this.recorder = null;
            }
            return false;
        }
    }

    public boolean stopRecordingAndSave(File finalFile) {
        MediaRecorder mediaRecorder = this.recorder;
        if (mediaRecorder == null) {
            Log.w(TAG, "No active recording to stop.");
            return false;
        }
        try {
            try {
                mediaRecorder.stop();
                this.recorder.release();
                Log.i(TAG, "Recording stopped.");
                File file = this.tempAudioFile;
                if (file != null) {
                    try {
                        if (file.exists()) {
                            try {
                                StorageOperations.copyFile(this.tempAudioFile, finalFile);
                                Log.i(TAG, "Recording saved to: " + finalFile.getAbsolutePath());
                                this.tempAudioFile.delete();
                                this.recorder = null;
                                this.tempAudioFile = null;
                                return true;
                            } catch (IOException e) {
                                Log.e(TAG, "Failed to copy temp file to final destination.", e);
                                this.tempAudioFile.delete();
                                this.recorder = null;
                                this.tempAudioFile = null;
                                return false;
                            }
                        }
                    } catch (Throwable th) {
                        this.tempAudioFile.delete();
                        throw th;
                    }
                }
            } catch (IllegalStateException e2) {
                Log.e(TAG, "Failed to stop recording", e2);
            }
            this.recorder = null;
            this.tempAudioFile = null;
            return false;
        } catch (Throwable th2) {
            this.recorder = null;
            this.tempAudioFile = null;
            throw th2;
        }
    }
}

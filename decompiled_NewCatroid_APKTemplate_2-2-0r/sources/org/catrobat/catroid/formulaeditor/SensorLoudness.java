package org.catrobat.catroid.formulaeditor;

import android.os.Handler;
import android.util.Log;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.catrobat.catroid.soundrecorder.SoundRecorder;
import org.eclipse.jgit.diff.DiffEntry;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/formulaeditor/SensorLoudness.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SensorLoudness {
    private static final double MAX_AMP_VALUE = 32767.0d;
    private static final double SCALE_RANGE = 100.0d;
    private static final String TAG = "SensorLoudness";
    private static final int UPDATE_INTERVAL = 50;
    private List<SensorCustomEventListener> listenerList = new ArrayList();
    private Double lastValue = Double.valueOf(0.0d);
    Runnable statusChecker = new Runnable() { // from class: org.catrobat.catroid.formulaeditor.SensorLoudness.1
        @Override // java.lang.Runnable
        public void run() {
            Double dValueOf = Double.valueOf(((double) SensorLoudness.this.recorder.getMaxAmplitude()) * 0.0030518509475997192d);
            if (!dValueOf.equals(SensorLoudness.this.lastValue) && !dValueOf.equals(Double.valueOf(0.0d))) {
                SensorLoudness.this.lastValue = dValueOf;
                SensorCustomEvent sensorCustomEvent = new SensorCustomEvent(Sensors.LOUDNESS, dValueOf);
                Iterator it = SensorLoudness.this.listenerList.iterator();
                while (it.hasNext()) {
                    ((SensorCustomEventListener) it.next()).onCustomSensorChanged(sensorCustomEvent);
                }
            }
            SensorLoudness.this.handler.postDelayed(SensorLoudness.this.statusChecker, 50L);
        }
    };
    private final Handler handler = new Handler();
    private SoundRecorder recorder = new SoundRecorder(DiffEntry.DEV_NULL);

    public synchronized boolean registerListener(SensorCustomEventListener listener) {
        if (this.listenerList.contains(listener)) {
            return true;
        }
        this.listenerList.add(listener);
        if (!this.recorder.isRecording()) {
            try {
                this.recorder.start();
                this.statusChecker.run();
            } catch (IOException e) {
                Log.d(TAG, "Could not start recorder", e);
                this.listenerList.remove(listener);
                this.recorder = new SoundRecorder(DiffEntry.DEV_NULL);
                return false;
            } catch (RuntimeException e2) {
                Log.d(TAG, "Could not start recorder", e2);
                this.listenerList.remove(listener);
                this.recorder = new SoundRecorder(DiffEntry.DEV_NULL);
                return false;
            }
        }
        return true;
    }

    public synchronized void unregisterListener(SensorCustomEventListener listener) {
        if (this.listenerList.contains(listener)) {
            this.listenerList.remove(listener);
            if (this.listenerList.size() == 0) {
                this.handler.removeCallbacks(this.statusChecker);
                if (this.recorder.isRecording()) {
                    try {
                        this.recorder.stop();
                    } catch (IOException e) {
                        Log.d(TAG, "Could not stop recorder", e);
                    }
                    this.recorder = new SoundRecorder(DiffEntry.DEV_NULL);
                }
                this.lastValue = Double.valueOf(0.0d);
            }
        }
    }

    public void setSoundRecorder(SoundRecorder soundRecorder) {
        this.recorder = soundRecorder;
    }

    public SoundRecorder getSoundRecorder() {
        return this.recorder;
    }
}

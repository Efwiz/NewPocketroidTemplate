package org.catrobat.catroid.pocketmusic.mididriver;

import android.os.Handler;
import org.catrobat.catroid.pocketmusic.note.NoteName;
import org.catrobat.catroid.pocketmusic.ui.PianoView;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/pocketmusic/mididriver/MidiRunnable.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class MidiRunnable implements Runnable {
    private final byte channel;
    private final long duration;
    private final Handler handler;
    private final MidiNotePlayer midiNotePlayer;
    private final NoteName noteName;
    private NoteName pianoRow;
    private final PianoView pianoView;
    private final MidiSignals signal;
    private long scheduledTime = 0;
    private boolean manualNoteOff = false;

    public MidiRunnable(MidiSignals signal, NoteName noteName, long duration, Handler handler, MidiNotePlayer midiNotePlayer, PianoView pianoView, byte channel) {
        this.signal = signal;
        this.noteName = noteName;
        this.duration = duration;
        this.handler = handler;
        this.midiNotePlayer = midiNotePlayer;
        this.pianoView = pianoView;
        this.channel = channel;
    }

    public MidiRunnable(MidiSignals signal, NoteName noteName, long duration, Handler handler, MidiNotePlayer midiNotePlayer, PianoView pianoView, byte channel, NoteName pianoRow) {
        this.signal = signal;
        this.noteName = noteName;
        this.duration = duration;
        this.handler = handler;
        this.midiNotePlayer = midiNotePlayer;
        this.pianoView = pianoView;
        this.channel = channel;
        this.pianoRow = pianoRow;
    }

    void setManualNoteOff(boolean manual) {
        this.manualNoteOff = manual;
    }

    void setScheduledTime(long time) {
        this.scheduledTime = time;
    }

    long getScheduledTime() {
        return this.scheduledTime;
    }

    @Override // java.lang.Runnable
    public void run() {
        NoteName noteName;
        this.midiNotePlayer.sendMidi((byte) (this.signal.getSignalByte() | this.channel), this.noteName.getMidi(), 127);
        PianoView pianoView = this.pianoView;
        if (pianoView != null && (noteName = this.pianoRow) != null) {
            pianoView.setKeyColor(noteName, MidiSignals.NOTE_ON.equals(this.signal));
        }
        if (!this.signal.equals(MidiSignals.NOTE_ON) || this.manualNoteOff) {
            return;
        }
        this.handler.postDelayed(new MidiRunnable(MidiSignals.NOTE_OFF, this.noteName, this.duration, this.handler, this.midiNotePlayer, this.pianoView, this.channel, this.pianoRow), this.duration);
    }

    public NoteName getNoteName() {
        return this.noteName;
    }
}

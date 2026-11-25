package org.catrobat.catroid.pocketmusic.mididriver;

import org.billthefarmer.mididriver.MidiDriver;
import org.catrobat.catroid.pocketmusic.note.MusicalInstrument;
import org.catrobat.catroid.pocketmusic.note.Project;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/pocketmusic/mididriver/MidiNotePlayer.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class MidiNotePlayer implements MidiDriver.OnMidiStartListener {
    private static boolean initialized = false;
    private final MidiDriver midiDriver;

    public MidiNotePlayer() {
        MidiDriver midiDriver = new MidiDriver();
        this.midiDriver = midiDriver;
        midiDriver.setOnMidiStartListener(this);
    }

    @Override // org.billthefarmer.mididriver.MidiDriver.OnMidiStartListener
    public void onMidiStart() {
        sendMidi(MidiSignals.PROGRAM_CHANGE.getSignalByte(), (byte) Project.DEFAULT_INSTRUMENT.getProgram());
        this.midiDriver.config();
    }

    public void start() {
        this.midiDriver.start();
        initialized = true;
    }

    public void stop() {
        this.midiDriver.stop();
    }

    private void sendMidi(int midiSignal, int message) {
        this.midiDriver.write(new byte[]{(byte) midiSignal, (byte) message});
    }

    public void sendMidi(int midiSignal, int note, int velocity) {
        this.midiDriver.write(new byte[]{(byte) midiSignal, (byte) note, (byte) velocity});
    }

    public void setInstrument(byte channel, MusicalInstrument instrument) {
        sendMidi((byte) (channel | MidiSignals.PROGRAM_CHANGE.getSignalByte()), (byte) instrument.getProgram());
    }

    public void setVolume(byte channel, int volume) {
        sendMidi((byte) (channel | MidiSignals.CONTROL_CHANGE.getSignalByte()), MidiSignals.CHANNEL_VOLUME.getSignalByte(), volume);
    }

    public static boolean isInitialized() {
        return initialized;
    }
}

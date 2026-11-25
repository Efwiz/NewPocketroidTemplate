package org.catrobat.catroid.pocketmusic.mididriver;

import org.billthefarmer.mididriver.MidiConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/pocketmusic/mididriver/MidiSignals.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public enum MidiSignals {
    CHANNEL_VOLUME((byte) 7),
    MIDI_TIME_CODE(MidiConstants.MIDI_TIME_CODE),
    SONG_POSITION_POBYTEER(MidiConstants.SONG_POSITION_POBYTEER),
    SONG_SELECT(MidiConstants.SONG_SELECT),
    TUNE_REQUEST((byte) -10),
    END_OF_EXCLUSIVE((byte) -9),
    TIMING_CLOCK((byte) -8),
    START((byte) -6),
    CONTINUE((byte) -5),
    STOP((byte) -4),
    ACTIVE_SENSING((byte) -2),
    SYSTEM_RESET((byte) -1),
    NOTE_OFF((byte) -128),
    NOTE_ON((byte) -112),
    POLY_PRESSURE((byte) -96),
    CONTROL_CHANGE((byte) -80),
    PROGRAM_CHANGE((byte) -64),
    CHANNEL_PRESSURE((byte) -48),
    PITCH_BEND((byte) -32);

    private byte signalByte;

    MidiSignals(byte signalByte) {
        this.signalByte = signalByte;
    }

    public byte getSignalByte() {
        return this.signalByte;
    }
}

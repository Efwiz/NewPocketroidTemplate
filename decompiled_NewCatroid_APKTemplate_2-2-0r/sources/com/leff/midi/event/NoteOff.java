package com.leff.midi.event;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:com/leff/midi/event/NoteOff.class
  classes10.dex
 */
/* JADX INFO: loaded from: classes10.dex */
public class NoteOff extends ChannelEvent {
    public NoteOff(long tick, int channel, int note, int velocity) {
        super(tick, 8, channel, note, velocity);
    }

    public NoteOff(long tick, long delta, int channel, int note, int velocity) {
        super(tick, delta, 8, channel, note, velocity);
    }

    public int getNoteValue() {
        return this.mValue1;
    }

    public int getVelocity() {
        return this.mValue2;
    }

    public void setNoteValue(int p) {
        this.mValue1 = p;
    }

    public void setVelocity(int v) {
        this.mValue2 = v;
    }
}

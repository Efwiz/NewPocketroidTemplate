package com.leff.midi.event;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:com/leff/midi/event/NoteAftertouch.class
  classes10.dex
 */
/* JADX INFO: loaded from: classes10.dex */
public class NoteAftertouch extends ChannelEvent {
    public NoteAftertouch(long tick, int channel, int note, int amount) {
        super(tick, 10, channel, note, amount);
    }

    public NoteAftertouch(long tick, long delta, int channel, int note, int amount) {
        super(tick, delta, 10, channel, note, amount);
    }

    public int getNoteValue() {
        return this.mValue1;
    }

    public int getAmount() {
        return this.mValue2;
    }

    public void setNoteValue(int p) {
        this.mValue1 = p;
    }

    public void setAmount(int a) {
        this.mValue2 = a;
    }
}

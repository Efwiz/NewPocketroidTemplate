package com.leff.midi.event.meta;

import com.leff.midi.event.MidiEvent;
import com.leff.midi.util.VariableLengthInt;
import java.io.IOException;
import java.io.OutputStream;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:com/leff/midi/event/meta/SequenceNumber.class
  classes10.dex
 */
/* JADX INFO: loaded from: classes10.dex */
public class SequenceNumber extends MetaEvent {
    private int mNumber;

    @Override // com.leff.midi.event.meta.MetaEvent, com.leff.midi.event.MidiEvent
    protected int getEventSize() {
        return 5;
    }

    public SequenceNumber(long tick, long delta, int number) {
        super(tick, delta, 0, new VariableLengthInt(2));
        this.mNumber = number;
    }

    public int getMostSignificantBits() {
        return this.mNumber >> 8;
    }

    public int getLeastSignificantBits() {
        return this.mNumber & 255;
    }

    public int getSequenceNumber() {
        return this.mNumber;
    }

    @Override // com.leff.midi.event.meta.MetaEvent
    public void writeToFile(OutputStream out) throws IOException {
        super.writeToFile(out);
        out.write(2);
        out.write(getMostSignificantBits());
        out.write(getLeastSignificantBits());
    }

    public static MetaEvent parseSequenceNumber(long tick, long delta, MetaEvent.MetaEventData info) {
        if (info.length.getValue() != 2) {
            return new GenericMetaEvent(tick, delta, info);
        }
        return new SequenceNumber(tick, delta, (info.data[0] << 8) + info.data[1]);
    }

    @Override // java.lang.Comparable
    public int compareTo(MidiEvent other) {
        if (this.mTick != other.getTick()) {
            return this.mTick < other.getTick() ? -1 : 1;
        }
        if (this.mDelta.getValue() != other.getDelta()) {
            return ((long) this.mDelta.getValue()) < other.getDelta() ? 1 : -1;
        }
        if (!(other instanceof SequenceNumber)) {
            return 1;
        }
        int i = this.mNumber;
        int i2 = ((SequenceNumber) other).mNumber;
        if (i != i2) {
            return i < i2 ? -1 : 1;
        }
        return 0;
    }
}

package com.leff.midi.event;

import com.leff.midi.util.VariableLengthInt;
import java.io.IOException;
import java.io.OutputStream;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:com/leff/midi/event/SystemExclusiveEvent.class
  classes10.dex
 */
/* JADX INFO: loaded from: classes10.dex */
public class SystemExclusiveEvent extends MidiEvent {
    private byte[] mData;
    private VariableLengthInt mLength;
    private int mType;

    @Override // com.leff.midi.event.MidiEvent
    public boolean requiresStatusByte(MidiEvent prevEvent) {
        return true;
    }

    public SystemExclusiveEvent(int type, long tick, byte[] data) {
        this(type, tick, 0L, data);
    }

    public SystemExclusiveEvent(int type, long tick, long delta, byte[] data) {
        super(tick, delta);
        int i = type & 255;
        this.mType = i;
        if (i != 240 && i != 247) {
            this.mType = 240;
        }
        this.mLength = new VariableLengthInt(data.length);
        this.mData = data;
    }

    public byte[] getData() {
        return this.mData;
    }

    public void setData(byte[] data) {
        this.mLength.setValue(data.length);
        this.mData = data;
    }

    @Override // com.leff.midi.event.MidiEvent
    public void writeToFile(OutputStream out, boolean writeType) throws IOException {
        super.writeToFile(out, writeType);
        out.write(this.mType);
        out.write(this.mLength.getBytes());
        out.write(this.mData);
    }

    @Override // java.lang.Comparable
    public int compareTo(MidiEvent other) {
        if (this.mTick < other.mTick) {
            return -1;
        }
        if (this.mTick > other.mTick) {
            return 1;
        }
        if (this.mDelta.getValue() > other.mDelta.getValue()) {
            return -1;
        }
        if (this.mDelta.getValue() >= other.mDelta.getValue() && (other instanceof SystemExclusiveEvent)) {
            return new String(this.mData).compareTo(new String(((SystemExclusiveEvent) other).mData));
        }
        return 1;
    }

    @Override // com.leff.midi.event.MidiEvent
    protected int getEventSize() {
        return this.mLength.getByteCount() + 1 + this.mData.length;
    }
}

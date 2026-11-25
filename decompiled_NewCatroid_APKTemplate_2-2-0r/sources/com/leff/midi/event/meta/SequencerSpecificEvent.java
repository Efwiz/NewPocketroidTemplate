package com.leff.midi.event.meta;

import com.leff.midi.event.MidiEvent;
import com.leff.midi.util.MidiUtil;
import com.leff.midi.util.VariableLengthInt;
import java.io.IOException;
import java.io.OutputStream;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:com/leff/midi/event/meta/SequencerSpecificEvent.class
  classes10.dex
 */
/* JADX INFO: loaded from: classes10.dex */
public class SequencerSpecificEvent extends MetaEvent {
    private byte[] mData;

    public SequencerSpecificEvent(long tick, long delta, byte[] data) {
        super(tick, delta, 127, new VariableLengthInt(data.length));
        this.mData = data;
    }

    public void setData(byte[] data) {
        this.mData = data;
        this.mLength.setValue(this.mData.length);
    }

    public byte[] getData() {
        return this.mData;
    }

    @Override // com.leff.midi.event.meta.MetaEvent, com.leff.midi.event.MidiEvent
    protected int getEventSize() {
        return this.mLength.getByteCount() + 2 + this.mData.length;
    }

    @Override // com.leff.midi.event.meta.MetaEvent
    public void writeToFile(OutputStream out) throws IOException {
        super.writeToFile(out);
        out.write(this.mLength.getBytes());
        out.write(this.mData);
    }

    @Override // java.lang.Comparable
    public int compareTo(MidiEvent other) {
        if (this.mTick != other.getTick()) {
            return this.mTick < other.getTick() ? -1 : 1;
        }
        if (this.mDelta.getValue() != other.getDelta()) {
            return ((long) this.mDelta.getValue()) < other.getDelta() ? 1 : -1;
        }
        if (!(other instanceof SequencerSpecificEvent)) {
            return 1;
        }
        byte[] bArr = this.mData;
        return MidiUtil.bytesEqual(bArr, ((SequencerSpecificEvent) other).mData, 0, bArr.length) ? 0 : 1;
    }
}

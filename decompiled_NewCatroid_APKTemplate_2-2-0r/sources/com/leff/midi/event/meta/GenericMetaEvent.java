package com.leff.midi.event.meta;

import com.leff.midi.event.MidiEvent;
import java.io.IOException;
import java.io.OutputStream;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:com/leff/midi/event/meta/GenericMetaEvent.class
  classes10.dex
 */
/* JADX INFO: loaded from: classes10.dex */
public class GenericMetaEvent extends MetaEvent {
    private byte[] mData;

    protected GenericMetaEvent(long tick, long delta, MetaEvent.MetaEventData info) {
        super(tick, delta, info.type, info.length);
        this.mData = info.data;
        System.out.println("Warning: GenericMetaEvent used because type (" + info.type + ") wasn't recognized or unexpected data length (" + info.length.getValue() + ") for type.");
    }

    @Override // com.leff.midi.event.meta.MetaEvent, com.leff.midi.event.MidiEvent
    protected int getEventSize() {
        return this.mLength.getByteCount() + 2 + this.mLength.getValue();
    }

    @Override // com.leff.midi.event.meta.MetaEvent
    protected void writeToFile(OutputStream out) throws IOException {
        super.writeToFile(out);
        out.write(this.mLength.getBytes());
        out.write(this.mData);
    }

    @Override // java.lang.Comparable
    public int compareTo(MidiEvent other) {
        if (this.mTick != other.getTick()) {
            return this.mTick < other.getTick() ? -1 : 1;
        }
        return (((long) this.mDelta.getValue()) == other.getDelta() || ((long) this.mDelta.getValue()) < other.getDelta()) ? 1 : -1;
    }
}

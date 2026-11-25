package com.leff.midi.event.meta;

import com.leff.midi.event.MidiEvent;
import com.leff.midi.util.VariableLengthInt;
import java.io.IOException;
import java.io.OutputStream;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:com/leff/midi/event/meta/EndOfTrack.class
  classes10.dex
 */
/* JADX INFO: loaded from: classes10.dex */
public class EndOfTrack extends MetaEvent {
    @Override // com.leff.midi.event.meta.MetaEvent, com.leff.midi.event.MidiEvent
    protected int getEventSize() {
        return 3;
    }

    public EndOfTrack(long tick, long delta) {
        super(tick, delta, 47, new VariableLengthInt(0));
    }

    @Override // com.leff.midi.event.meta.MetaEvent
    public void writeToFile(OutputStream out) throws IOException {
        super.writeToFile(out);
        out.write(0);
    }

    @Override // java.lang.Comparable
    public int compareTo(MidiEvent other) {
        if (this.mTick != other.getTick()) {
            return this.mTick < other.getTick() ? -1 : 1;
        }
        if (this.mDelta.getValue() != other.getDelta()) {
            return ((long) this.mDelta.getValue()) < other.getDelta() ? 1 : -1;
        }
        return !(other instanceof EndOfTrack) ? 1 : 0;
    }
}

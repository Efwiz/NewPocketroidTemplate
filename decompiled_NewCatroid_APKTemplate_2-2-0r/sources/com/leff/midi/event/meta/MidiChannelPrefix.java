package com.leff.midi.event.meta;

import com.leff.midi.event.MidiEvent;
import com.leff.midi.util.VariableLengthInt;
import java.io.IOException;
import java.io.OutputStream;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:com/leff/midi/event/meta/MidiChannelPrefix.class
  classes10.dex
 */
/* JADX INFO: loaded from: classes10.dex */
public class MidiChannelPrefix extends MetaEvent {
    private int mChannel;

    @Override // com.leff.midi.event.meta.MetaEvent, com.leff.midi.event.MidiEvent
    protected int getEventSize() {
        return 4;
    }

    public MidiChannelPrefix(long tick, long delta, int channel) {
        super(tick, delta, 32, new VariableLengthInt(4));
        this.mChannel = channel;
    }

    public void setChannel(int c) {
        this.mChannel = c;
    }

    public int getChannel() {
        return this.mChannel;
    }

    @Override // com.leff.midi.event.meta.MetaEvent
    public void writeToFile(OutputStream out) throws IOException {
        super.writeToFile(out);
        out.write(1);
        out.write(this.mChannel);
    }

    public static MetaEvent parseMidiChannelPrefix(long tick, long delta, MetaEvent.MetaEventData info) {
        if (info.length.getValue() != 1) {
            return new GenericMetaEvent(tick, delta, info);
        }
        return new MidiChannelPrefix(tick, delta, info.data[0]);
    }

    @Override // java.lang.Comparable
    public int compareTo(MidiEvent other) {
        if (this.mTick != other.getTick()) {
            return this.mTick < other.getTick() ? -1 : 1;
        }
        if (this.mDelta.getValue() != other.getDelta()) {
            return ((long) this.mDelta.getValue()) < other.getDelta() ? 1 : -1;
        }
        if (!(other instanceof MidiChannelPrefix)) {
            return 1;
        }
        int i = this.mChannel;
        int i2 = ((MidiChannelPrefix) other).mChannel;
        if (i != i2) {
            return i < i2 ? -1 : 1;
        }
        return 0;
    }
}

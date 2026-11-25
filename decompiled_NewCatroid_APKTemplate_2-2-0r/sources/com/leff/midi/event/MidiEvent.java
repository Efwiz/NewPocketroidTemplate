package com.leff.midi.event;

import com.leff.midi.event.meta.MetaEvent;
import com.leff.midi.util.VariableLengthInt;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:com/leff/midi/event/MidiEvent.class
  classes10.dex
 */
/* JADX INFO: loaded from: classes10.dex */
public abstract class MidiEvent implements Comparable<MidiEvent> {
    private static int sChannel = -1;
    private static int sId = -1;
    private static int sType = -1;
    protected VariableLengthInt mDelta;
    protected long mTick;

    protected abstract int getEventSize();

    public MidiEvent(long tick, long delta) {
        this.mTick = tick;
        this.mDelta = new VariableLengthInt((int) delta);
    }

    public long getTick() {
        return this.mTick;
    }

    public long getDelta() {
        return this.mDelta.getValue();
    }

    public void setDelta(long d) {
        this.mDelta.setValue((int) d);
    }

    public int getSize() {
        return getEventSize() + this.mDelta.getByteCount();
    }

    public boolean requiresStatusByte(MidiEvent prevEvent) {
        return prevEvent == null || (this instanceof MetaEvent) || !getClass().equals(prevEvent.getClass());
    }

    public void writeToFile(OutputStream out, boolean writeType) throws IOException {
        out.write(this.mDelta.getBytes());
    }

    public static final MidiEvent parseEvent(long tick, long delta, InputStream in) throws IOException {
        boolean z = true;
        in.mark(1);
        if (verifyIdentifier(in.read())) {
            z = false;
        } else {
            in.reset();
        }
        int i = sType;
        if (i >= 8 && i <= 14) {
            return ChannelEvent.parseChannelEvent(tick, delta, i, sChannel, in);
        }
        int i2 = sId;
        if (i2 == 255) {
            return MetaEvent.parseMetaEvent(tick, delta, in);
        }
        if (i2 == 240 || i2 == 247) {
            byte[] bArr = new byte[new VariableLengthInt(in).getValue()];
            in.read(bArr);
            return new SystemExclusiveEvent(sId, tick, delta, bArr);
        }
        System.out.println("Unable to handle status byte, skipping: " + sId);
        if (!z) {
            return null;
        }
        in.read();
        return null;
    }

    private static boolean verifyIdentifier(int id2) {
        sId = id2;
        int i = id2 >> 4;
        int i2 = id2 & 15;
        if (i >= 8 && i <= 14) {
            sId = id2;
            sType = i;
            sChannel = i2;
            return true;
        }
        if (id2 == 255) {
            sId = id2;
            sType = -1;
            sChannel = -1;
            return true;
        }
        if (i != 15) {
            return false;
        }
        sId = id2;
        sType = i;
        sChannel = -1;
        return true;
    }

    public String toString() {
        return "" + this.mTick + " (" + this.mDelta.getValue() + "): " + getClass().getSimpleName();
    }
}

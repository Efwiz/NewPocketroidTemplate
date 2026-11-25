package com.leff.midi.event.meta;

import com.leff.midi.event.MidiEvent;
import com.leff.midi.util.MidiUtil;
import com.leff.midi.util.VariableLengthInt;
import java.io.IOException;
import java.io.OutputStream;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:com/leff/midi/event/meta/Tempo.class
  classes10.dex
 */
/* JADX INFO: loaded from: classes10.dex */
public class Tempo extends MetaEvent {
    public static final float DEFAULT_BPM = 120.0f;
    public static final int DEFAULT_MPQN = 500000;
    private float mBPM;
    private int mMPQN;

    @Override // com.leff.midi.event.meta.MetaEvent, com.leff.midi.event.MidiEvent
    protected int getEventSize() {
        return 6;
    }

    public Tempo() {
        this(0L, 0L, DEFAULT_MPQN);
    }

    public Tempo(long tick, long delta, int mpqn) {
        super(tick, delta, 81, new VariableLengthInt(3));
        setMpqn(mpqn);
    }

    public int getMpqn() {
        return this.mMPQN;
    }

    public float getBpm() {
        return this.mBPM;
    }

    public void setMpqn(int m) {
        this.mMPQN = m;
        this.mBPM = 6.0E7f / m;
    }

    public void setBpm(float b) {
        this.mBPM = b;
        this.mMPQN = (int) (6.0E7f / b);
    }

    @Override // com.leff.midi.event.meta.MetaEvent
    public void writeToFile(OutputStream out) throws IOException {
        super.writeToFile(out);
        out.write(3);
        out.write(MidiUtil.intToBytes(this.mMPQN, 3));
    }

    public static MetaEvent parseTempo(long tick, long delta, MetaEvent.MetaEventData info) {
        if (info.length.getValue() != 3) {
            return new GenericMetaEvent(tick, delta, info);
        }
        return new Tempo(tick, delta, MidiUtil.bytesToInt(info.data, 0, 3));
    }

    @Override // java.lang.Comparable
    public int compareTo(MidiEvent other) {
        if (this.mTick != other.getTick()) {
            return this.mTick < other.getTick() ? -1 : 1;
        }
        if (this.mDelta.getValue() != other.getDelta()) {
            return ((long) this.mDelta.getValue()) < other.getDelta() ? 1 : -1;
        }
        if (!(other instanceof Tempo)) {
            return 1;
        }
        int i = this.mMPQN;
        int i2 = ((Tempo) other).mMPQN;
        if (i != i2) {
            return i < i2 ? -1 : 1;
        }
        return 0;
    }
}

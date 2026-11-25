package com.leff.midi.event.meta;

import com.leff.midi.event.MidiEvent;
import com.leff.midi.util.VariableLengthInt;
import java.io.IOException;
import java.io.OutputStream;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:com/leff/midi/event/meta/TimeSignature.class
  classes10.dex
 */
/* JADX INFO: loaded from: classes10.dex */
public class TimeSignature extends MetaEvent {
    public static final int DEFAULT_DIVISION = 8;
    public static final int DEFAULT_METER = 24;
    public static final int METER_EIGHTH = 12;
    public static final int METER_HALF = 48;
    public static final int METER_QUARTER = 24;
    public static final int METER_WHOLE = 96;
    private int mDenominator;
    private int mDivision;
    private int mMeter;
    private int mNumerator;

    private int log2(int den) {
        if (den == 2) {
            return 1;
        }
        if (den == 4) {
            return 2;
        }
        if (den == 8) {
            return 3;
        }
        if (den != 16) {
            return den != 32 ? 0 : 5;
        }
        return 4;
    }

    @Override // com.leff.midi.event.meta.MetaEvent, com.leff.midi.event.MidiEvent
    protected int getEventSize() {
        return 7;
    }

    public TimeSignature() {
        this(0L, 0L, 4, 4, 24, 8);
    }

    public TimeSignature(long tick, long delta, int num, int den, int meter, int div) {
        super(tick, delta, 88, new VariableLengthInt(4));
        setTimeSignature(num, den, meter, div);
    }

    public void setTimeSignature(int num, int den, int meter, int div) {
        this.mNumerator = num;
        this.mDenominator = log2(den);
        this.mMeter = meter;
        this.mDivision = div;
    }

    public int getNumerator() {
        return this.mNumerator;
    }

    public int getDenominatorValue() {
        return this.mDenominator;
    }

    public int getRealDenominator() {
        return (int) Math.pow(2.0d, this.mDenominator);
    }

    public int getMeter() {
        return this.mMeter;
    }

    public int getDivision() {
        return this.mDivision;
    }

    @Override // com.leff.midi.event.meta.MetaEvent
    public void writeToFile(OutputStream out) throws IOException {
        super.writeToFile(out);
        out.write(4);
        out.write(this.mNumerator);
        out.write(this.mDenominator);
        out.write(this.mMeter);
        out.write(this.mDivision);
    }

    public static MetaEvent parseTimeSignature(long tick, long delta, MetaEvent.MetaEventData info) {
        if (info.length.getValue() != 4) {
            return new GenericMetaEvent(tick, delta, info);
        }
        return new TimeSignature(tick, delta, info.data[0], (int) Math.pow(2.0d, info.data[1]), info.data[2], info.data[3]);
    }

    @Override // com.leff.midi.event.MidiEvent
    public String toString() {
        return super.toString() + " " + this.mNumerator + "/" + getRealDenominator();
    }

    @Override // java.lang.Comparable
    public int compareTo(MidiEvent other) {
        if (this.mTick != other.getTick()) {
            return this.mTick < other.getTick() ? -1 : 1;
        }
        if (this.mDelta.getValue() != other.getDelta()) {
            return ((long) this.mDelta.getValue()) < other.getDelta() ? 1 : -1;
        }
        if (!(other instanceof TimeSignature)) {
            return 1;
        }
        TimeSignature timeSignature = (TimeSignature) other;
        int i = this.mNumerator;
        int i2 = timeSignature.mNumerator;
        if (i != i2) {
            return i < i2 ? -1 : 1;
        }
        int i3 = this.mDenominator;
        int i4 = timeSignature.mDenominator;
        if (i3 != i4) {
            return i3 < i4 ? -1 : 1;
        }
        return 0;
    }
}

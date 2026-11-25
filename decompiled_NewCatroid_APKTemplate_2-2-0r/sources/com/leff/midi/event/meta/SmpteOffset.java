package com.leff.midi.event.meta;

import com.leff.midi.event.MidiEvent;
import com.leff.midi.util.VariableLengthInt;
import java.io.IOException;
import java.io.OutputStream;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:com/leff/midi/event/meta/SmpteOffset.class
  classes10.dex
 */
/* JADX INFO: loaded from: classes10.dex */
public class SmpteOffset extends MetaEvent {
    public static final int FRAME_RATE_24 = 0;
    public static final int FRAME_RATE_25 = 1;
    public static final int FRAME_RATE_30 = 3;
    public static final int FRAME_RATE_30_DROP = 2;
    private FrameRate mFrameRate;
    private int mFrames;
    private int mHours;
    private int mMinutes;
    private int mSeconds;
    private int mSubFrames;

    @Override // com.leff.midi.event.meta.MetaEvent, com.leff.midi.event.MidiEvent
    protected int getEventSize() {
        return 8;
    }

    public SmpteOffset(long tick, long delta, FrameRate fps, int hour, int min, int sec, int fr, int subfr) {
        super(tick, delta, 84, new VariableLengthInt(5));
        this.mFrameRate = fps;
        this.mHours = hour;
        this.mMinutes = min;
        this.mSeconds = sec;
        this.mFrames = fr;
        this.mSubFrames = subfr;
    }

    public void setFrameRate(FrameRate fps) {
        this.mFrameRate = fps;
    }

    public FrameRate getFrameRate() {
        return this.mFrameRate;
    }

    public void setHours(int h) {
        this.mHours = h;
    }

    public int getHours() {
        return this.mHours;
    }

    public void setMinutes(int m) {
        this.mMinutes = m;
    }

    public int getMinutes() {
        return this.mMinutes;
    }

    public void setSeconds(int s) {
        this.mSeconds = s;
    }

    public int getSeconds() {
        return this.mSeconds;
    }

    public void setFrames(int f) {
        this.mFrames = f;
    }

    public int getFrames() {
        return this.mFrames;
    }

    public void setSubFrames(int s) {
        this.mSubFrames = s;
    }

    public int getSubFrames() {
        return this.mSubFrames;
    }

    @Override // com.leff.midi.event.meta.MetaEvent
    public void writeToFile(OutputStream out) throws IOException {
        super.writeToFile(out);
        out.write(5);
        out.write(this.mHours);
        out.write(this.mMinutes);
        out.write(this.mSeconds);
        out.write(this.mFrames);
        out.write(this.mSubFrames);
    }

    public static MetaEvent parseSmpteOffset(long tick, long delta, MetaEvent.MetaEventData info) {
        if (info.length.getValue() != 5) {
            return new GenericMetaEvent(tick, delta, info);
        }
        byte b = info.data[0];
        return new SmpteOffset(tick, delta, FrameRate.fromInt(b >> 5), b & 31, info.data[1], info.data[2], info.data[3], info.data[4]);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:com/leff/midi/event/meta/SmpteOffset$FrameRate.class
      classes10.dex
     */
    public enum FrameRate {
        FRAME_RATE_24(0),
        FRAME_RATE_25(1),
        FRAME_RATE_30_DROP(2),
        FRAME_RATE_30(3);

        public final int value;

        FrameRate(int v) {
            this.value = v;
        }

        public static FrameRate fromInt(int val) {
            if (val == 0) {
                return FRAME_RATE_24;
            }
            if (val == 1) {
                return FRAME_RATE_25;
            }
            if (val == 2) {
                return FRAME_RATE_30_DROP;
            }
            if (val != 3) {
                return null;
            }
            return FRAME_RATE_30;
        }
    }

    @Override // java.lang.Comparable
    public int compareTo(MidiEvent other) {
        if (this.mTick != other.getTick()) {
            return this.mTick < other.getTick() ? -1 : 1;
        }
        if (this.mDelta.getValue() != other.getDelta()) {
            return ((long) this.mDelta.getValue()) < other.getDelta() ? 1 : -1;
        }
        return !(other instanceof SmpteOffset) ? 1 : 0;
    }
}

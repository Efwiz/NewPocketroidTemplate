package com.leff.midi.event.meta;

import com.leff.midi.event.MidiEvent;
import com.leff.midi.util.VariableLengthInt;
import java.io.IOException;
import java.io.OutputStream;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:com/leff/midi/event/meta/KeySignature.class
  classes10.dex
 */
/* JADX INFO: loaded from: classes10.dex */
public class KeySignature extends MetaEvent {
    public static final int SCALE_MAJOR = 0;
    public static final int SCALE_MINOR = 1;
    private int mKey;
    private int mScale;

    @Override // com.leff.midi.event.meta.MetaEvent, com.leff.midi.event.MidiEvent
    protected int getEventSize() {
        return 5;
    }

    public KeySignature(long tick, long delta, int key, int scale) {
        super(tick, delta, 89, new VariableLengthInt(2));
        setKey(key);
        this.mScale = scale;
    }

    public void setKey(int key) {
        byte b = (byte) key;
        this.mKey = b;
        if (b < -7) {
            this.mKey = -7;
        } else if (b > 7) {
            this.mKey = 7;
        }
    }

    public int getKey() {
        return this.mKey;
    }

    public void setScale(int scale) {
        this.mScale = scale;
    }

    public int getScale() {
        return this.mScale;
    }

    @Override // com.leff.midi.event.meta.MetaEvent
    public void writeToFile(OutputStream out) throws IOException {
        super.writeToFile(out);
        out.write(2);
        out.write(this.mKey);
        out.write(this.mScale);
    }

    public static MetaEvent parseKeySignature(long tick, long delta, MetaEvent.MetaEventData info) {
        if (info.length.getValue() != 2) {
            return new GenericMetaEvent(tick, delta, info);
        }
        return new KeySignature(tick, delta, info.data[0], info.data[1]);
    }

    @Override // java.lang.Comparable
    public int compareTo(MidiEvent other) {
        if (this.mTick != other.getTick()) {
            return this.mTick < other.getTick() ? -1 : 1;
        }
        if (this.mDelta.getValue() != other.getDelta()) {
            return ((long) this.mDelta.getValue()) < other.getDelta() ? 1 : -1;
        }
        if (!(other instanceof KeySignature)) {
            return 1;
        }
        KeySignature keySignature = (KeySignature) other;
        int i = this.mKey;
        int i2 = keySignature.mKey;
        if (i != i2) {
            return i < i2 ? -1 : 1;
        }
        int i3 = this.mScale;
        int i4 = keySignature.mScale;
        if (i3 != i4) {
            return i < i4 ? -1 : 1;
        }
        return 0;
    }
}

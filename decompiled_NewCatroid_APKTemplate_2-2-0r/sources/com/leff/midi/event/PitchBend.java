package com.leff.midi.event;

import org.luaj.vm2.Lua;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:com/leff/midi/event/PitchBend.class
  classes10.dex
 */
/* JADX INFO: loaded from: classes10.dex */
public class PitchBend extends ChannelEvent {
    public PitchBend(long tick, int channel, int lsb, int msb) {
        super(tick, 14, channel, lsb, msb);
    }

    public PitchBend(long tick, long delta, int channel, int lsb, int msb) {
        super(tick, delta, 14, channel, lsb, msb);
    }

    public int getLeastSignificantBits() {
        return this.mValue1;
    }

    public int getMostSignificantBits() {
        return this.mValue2;
    }

    public int getBendAmount() {
        return ((this.mValue2 & 127) << 7) + this.mValue1;
    }

    public void setLeastSignificantBits(int p) {
        this.mValue1 = p & 127;
    }

    public void setMostSignificantBits(int p) {
        this.mValue2 = p & 127;
    }

    public void setBendAmount(int amount) {
        int i = amount & Lua.MASK_NOT_Bx;
        this.mValue1 = amount & 127;
        this.mValue2 = i >> 7;
    }
}

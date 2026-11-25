package com.leff.midi.event;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:com/leff/midi/event/ChannelAftertouch.class
  classes10.dex
 */
/* JADX INFO: loaded from: classes10.dex */
public class ChannelAftertouch extends ChannelEvent {
    public ChannelAftertouch(long tick, int channel, int amount) {
        super(tick, 13, channel, amount, 0);
    }

    public ChannelAftertouch(long tick, long delta, int channel, int amount) {
        super(tick, delta, 13, channel, amount, 0);
    }

    public int getAmount() {
        return this.mValue1;
    }

    public void setAmount(int p) {
        this.mValue1 = p;
    }
}

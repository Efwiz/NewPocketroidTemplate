package com.leff.midi.event;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:com/leff/midi/event/ChannelEvent.class
  classes10.dex
 */
/* JADX INFO: loaded from: classes10.dex */
public class ChannelEvent extends MidiEvent {
    public static final int CHANNEL_AFTERTOUCH = 13;
    public static final int CONTROLLER = 11;
    public static final int NOTE_AFTERTOUCH = 10;
    public static final int NOTE_OFF = 8;
    public static final int NOTE_ON = 9;
    public static final int PITCH_BEND = 14;
    public static final int PROGRAM_CHANGE = 12;
    private static HashMap<Integer, Integer> mOrderMap;
    protected int mChannel;
    protected int mType;
    protected int mValue1;
    protected int mValue2;

    protected ChannelEvent(long tick, int type, int channel, int param1, int param2) {
        this(tick, 0L, type, channel, param1, param2);
    }

    protected ChannelEvent(long tick, long delta, int type, int channel, int param1, int param2) {
        super(tick, delta);
        this.mType = type & 15;
        this.mChannel = channel & 15;
        this.mValue1 = param1 & 255;
        this.mValue2 = param2 & 255;
    }

    public int getType() {
        return this.mType;
    }

    public void setChannel(int c) {
        if (c < 0) {
            c = 0;
        } else if (c > 15) {
            c = 15;
        }
        this.mChannel = c;
    }

    public int getChannel() {
        return this.mChannel;
    }

    @Override // com.leff.midi.event.MidiEvent
    protected int getEventSize() {
        int i = this.mType;
        return (i == 12 || i == 13) ? 2 : 3;
    }

    @Override // java.lang.Comparable
    public int compareTo(MidiEvent other) {
        if (this.mTick != other.getTick()) {
            return this.mTick < other.getTick() ? -1 : 1;
        }
        if (this.mDelta.getValue() != other.mDelta.getValue()) {
            return this.mDelta.getValue() < other.mDelta.getValue() ? 1 : -1;
        }
        if (!(other instanceof ChannelEvent)) {
            return 1;
        }
        ChannelEvent channelEvent = (ChannelEvent) other;
        if (this.mType != channelEvent.getType()) {
            if (mOrderMap == null) {
                buildOrderMap();
            }
            return mOrderMap.get(Integer.valueOf(this.mType)).intValue() < mOrderMap.get(Integer.valueOf(channelEvent.getType())).intValue() ? -1 : 1;
        }
        int i = this.mValue1;
        int i2 = channelEvent.mValue1;
        if (i != i2) {
            return i < i2 ? -1 : 1;
        }
        int i3 = this.mValue2;
        int i4 = channelEvent.mValue2;
        if (i3 != i4) {
            return i3 < i4 ? -1 : 1;
        }
        if (this.mChannel != channelEvent.getChannel()) {
            return this.mChannel < channelEvent.getChannel() ? -1 : 1;
        }
        return 0;
    }

    @Override // com.leff.midi.event.MidiEvent
    public boolean requiresStatusByte(MidiEvent prevEvent) {
        if (prevEvent == null || !(prevEvent instanceof ChannelEvent)) {
            return true;
        }
        ChannelEvent channelEvent = (ChannelEvent) prevEvent;
        return (this.mType == channelEvent.getType() && this.mChannel == channelEvent.getChannel()) ? false : true;
    }

    @Override // com.leff.midi.event.MidiEvent
    public void writeToFile(OutputStream out, boolean writeType) throws IOException {
        super.writeToFile(out, writeType);
        if (writeType) {
            out.write((this.mType << 4) + this.mChannel);
        }
        out.write(this.mValue1);
        int i = this.mType;
        if (i == 12 || i == 13) {
            return;
        }
        out.write(this.mValue2);
    }

    public static ChannelEvent parseChannelEvent(long tick, long delta, int type, int channel, InputStream in) throws IOException {
        int i = in.read();
        int i2 = (type == 12 || type == 13) ? 0 : in.read();
        switch (type) {
            case 8:
                return new NoteOff(tick, delta, channel, i, i2);
            case 9:
                return new NoteOn(tick, delta, channel, i, i2);
            case 10:
                return new NoteAftertouch(tick, delta, channel, i, i2);
            case 11:
                return new Controller(tick, delta, channel, i, i2);
            case 12:
                return new ProgramChange(tick, delta, channel, i);
            case 13:
                return new ChannelAftertouch(tick, delta, channel, i);
            case 14:
                return new PitchBend(tick, delta, channel, i, i2);
            default:
                return new ChannelEvent(tick, delta, type, channel, i, i2);
        }
    }

    private static void buildOrderMap() {
        HashMap<Integer, Integer> map = new HashMap<>();
        mOrderMap = map;
        map.put(12, 0);
        mOrderMap.put(11, 1);
        mOrderMap.put(9, 2);
        mOrderMap.put(8, 3);
        mOrderMap.put(10, 4);
        mOrderMap.put(13, 5);
        mOrderMap.put(14, 6);
    }
}

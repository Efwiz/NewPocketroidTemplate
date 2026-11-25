package com.leff.midi;

import com.leff.midi.event.MidiEvent;
import com.leff.midi.event.NoteOn;
import com.leff.midi.event.meta.EndOfTrack;
import com.leff.midi.event.meta.Tempo;
import com.leff.midi.event.meta.TimeSignature;
import com.leff.midi.util.MidiUtil;
import com.leff.midi.util.VariableLengthInt;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.TreeSet;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:com/leff/midi/MidiTrack.class
  classes10.dex
 */
/* JADX INFO: loaded from: classes10.dex */
public class MidiTrack {
    public static final byte[] IDENTIFIER = {77, 84, 114, 107};
    private static final boolean VERBOSE = false;
    private boolean mClosed;
    private long mEndOfTrackDelta;
    private TreeSet<MidiEvent> mEvents;
    private int mSize;
    private boolean mSizeNeedsRecalculating;

    public static MidiTrack createTempoTrack() {
        MidiTrack midiTrack = new MidiTrack();
        midiTrack.insertEvent(new TimeSignature());
        midiTrack.insertEvent(new Tempo());
        return midiTrack;
    }

    public MidiTrack() {
        this.mEvents = new TreeSet<>();
        this.mSize = 0;
        this.mSizeNeedsRecalculating = false;
        this.mClosed = false;
        this.mEndOfTrackDelta = 0L;
    }

    public MidiTrack(InputStream in) throws IOException {
        this();
        byte[] bArr = new byte[4];
        in.read(bArr);
        if (!MidiUtil.bytesEqual(bArr, IDENTIFIER, 0, 4)) {
            System.err.println("Track identifier did not match MTrk!");
            return;
        }
        in.read(bArr);
        int iBytesToInt = MidiUtil.bytesToInt(bArr, 0, 4);
        this.mSize = iBytesToInt;
        byte[] bArr2 = new byte[iBytesToInt];
        in.read(bArr2);
        readTrackData(bArr2);
    }

    private void readTrackData(byte[] data) throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
        long value = 0;
        while (byteArrayInputStream.available() > 0) {
            VariableLengthInt variableLengthInt = new VariableLengthInt(byteArrayInputStream);
            value += (long) variableLengthInt.getValue();
            MidiEvent event = MidiEvent.parseEvent(value, variableLengthInt.getValue(), byteArrayInputStream);
            if (event == null) {
                System.out.println("Event skipped!");
            } else {
                if (event.getClass().equals(EndOfTrack.class)) {
                    this.mEndOfTrackDelta = event.getDelta();
                    return;
                }
                this.mEvents.add(event);
            }
        }
    }

    public TreeSet<MidiEvent> getEvents() {
        return this.mEvents;
    }

    public int getEventCount() {
        return this.mEvents.size();
    }

    public int getSize() {
        if (this.mSizeNeedsRecalculating) {
            recalculateSize();
        }
        return this.mSize;
    }

    public long getLengthInTicks() {
        if (this.mEvents.size() == 0) {
            return 0L;
        }
        return this.mEvents.last().getTick();
    }

    public long getEndOfTrackDelta() {
        return this.mEndOfTrackDelta;
    }

    public void setEndOfTrackDelta(long delta) {
        this.mEndOfTrackDelta = delta;
    }

    public void insertNote(int channel, int pitch, int velocity, long tick, long duration) {
        insertEvent(new NoteOn(tick, channel, pitch, velocity));
        insertEvent(new NoteOn(tick + duration, channel, pitch, 0));
    }

    public void insertEvent(MidiEvent newEvent) {
        MidiEvent midiEvent;
        if (newEvent == null) {
            return;
        }
        if (this.mClosed) {
            System.err.println("Error: Cannot add an event to a closed track.");
            return;
        }
        MidiEvent midiEvent2 = null;
        try {
            Class<?> cls = Class.forName("java.util.TreeSet");
            Method method = cls.getMethod("floor", Object.class);
            Method method2 = cls.getMethod("ceiling", Object.class);
            midiEvent = (MidiEvent) method.invoke(this.mEvents, newEvent);
            try {
                midiEvent2 = (MidiEvent) method2.invoke(this.mEvents, newEvent);
            } catch (Exception unused) {
                for (MidiEvent midiEvent3 : this.mEvents) {
                    if (midiEvent3.getTick() > newEvent.getTick()) {
                        midiEvent2 = midiEvent3;
                        break;
                    }
                    midiEvent = midiEvent3;
                }
            }
        } catch (Exception unused2) {
            midiEvent = null;
        }
        this.mEvents.add(newEvent);
        this.mSizeNeedsRecalculating = true;
        if (midiEvent != null) {
            newEvent.setDelta(newEvent.getTick() - midiEvent.getTick());
        } else {
            newEvent.setDelta(newEvent.getTick());
        }
        if (midiEvent2 != null) {
            midiEvent2.setDelta(midiEvent2.getTick() - newEvent.getTick());
        }
        this.mSize += newEvent.getSize();
        if (newEvent.getClass().equals(EndOfTrack.class)) {
            if (midiEvent2 != null) {
                throw new IllegalArgumentException("Attempting to insert EndOfTrack before an existing event. Use closeTrack() when finished with MidiTrack.");
            }
            this.mClosed = true;
        }
    }

    public boolean removeEvent(MidiEvent E) {
        MidiEvent midiEvent = null;
        MidiEvent midiEvent2 = null;
        MidiEvent midiEvent3 = null;
        for (MidiEvent midiEvent4 : this.mEvents) {
            if (E.equals(midiEvent2)) {
                midiEvent = midiEvent4;
                break;
            }
            midiEvent3 = midiEvent2;
            midiEvent2 = midiEvent4;
        }
        if (midiEvent == null) {
            return this.mEvents.remove(midiEvent2);
        }
        if (!this.mEvents.remove(midiEvent2)) {
            return false;
        }
        if (midiEvent3 != null) {
            midiEvent.setDelta(midiEvent.getTick() - midiEvent3.getTick());
            return true;
        }
        midiEvent.setDelta(midiEvent.getTick());
        return true;
    }

    public void closeTrack() {
        insertEvent(new EndOfTrack((this.mEvents.size() > 0 ? this.mEvents.last().getTick() : 0L) + this.mEndOfTrackDelta, 0L));
    }

    public void dumpEvents() {
        Iterator<MidiEvent> it = this.mEvents.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }

    private void recalculateSize() {
        this.mSize = 0;
        MidiEvent midiEvent = null;
        for (MidiEvent midiEvent2 : this.mEvents) {
            this.mSize += midiEvent2.getSize();
            if (midiEvent != null && !midiEvent2.requiresStatusByte(midiEvent)) {
                this.mSize--;
            }
            midiEvent = midiEvent2;
        }
        this.mSizeNeedsRecalculating = false;
    }

    public void writeToFile(OutputStream out) throws IOException {
        if (!this.mClosed) {
            closeTrack();
        }
        if (this.mSizeNeedsRecalculating) {
            recalculateSize();
        }
        out.write(IDENTIFIER);
        out.write(MidiUtil.intToBytes(this.mSize, 4));
        MidiEvent midiEvent = null;
        for (MidiEvent midiEvent2 : this.mEvents) {
            midiEvent2.writeToFile(out, midiEvent2.requiresStatusByte(midiEvent));
            midiEvent = midiEvent2;
        }
    }
}

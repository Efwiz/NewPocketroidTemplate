package com.leff.midi.event.meta;

import com.leff.midi.event.MidiEvent;
import com.leff.midi.util.VariableLengthInt;
import java.io.IOException;
import java.io.OutputStream;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:com/leff/midi/event/meta/TextualMetaEvent.class
  classes10.dex
 */
/* JADX INFO: loaded from: classes10.dex */
public abstract class TextualMetaEvent extends MetaEvent {
    protected String mText;

    protected TextualMetaEvent(long tick, long delta, int type, String text) {
        super(tick, delta, type, new VariableLengthInt(text.length()));
        setText(text);
    }

    protected void setText(String t) {
        this.mText = t;
        this.mLength.setValue(t.getBytes().length);
    }

    protected String getText() {
        return this.mText;
    }

    @Override // com.leff.midi.event.meta.MetaEvent, com.leff.midi.event.MidiEvent
    protected int getEventSize() {
        return this.mLength.getByteCount() + 2 + this.mLength.getValue();
    }

    @Override // com.leff.midi.event.meta.MetaEvent
    public void writeToFile(OutputStream out) throws IOException {
        super.writeToFile(out);
        out.write(this.mLength.getBytes());
        out.write(this.mText.getBytes());
    }

    @Override // java.lang.Comparable
    public int compareTo(MidiEvent other) {
        if (this.mTick != other.getTick()) {
            return this.mTick < other.getTick() ? -1 : 1;
        }
        if (this.mDelta.getValue() != other.getDelta()) {
            return ((long) this.mDelta.getValue()) < other.getDelta() ? 1 : -1;
        }
        if (other instanceof TextualMetaEvent) {
            return this.mText.compareTo(((TextualMetaEvent) other).mText);
        }
        return 1;
    }

    @Override // com.leff.midi.event.MidiEvent
    public String toString() {
        return super.toString() + ": " + this.mText;
    }
}

package com.leff.midi.event.meta;

import com.leff.midi.event.MidiEvent;
import com.leff.midi.util.VariableLengthInt;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:com/leff/midi/event/meta/MetaEvent.class
  classes10.dex
 */
/* JADX INFO: loaded from: classes10.dex */
public abstract class MetaEvent extends MidiEvent {
    public static final int COPYRIGHT_NOTICE = 2;
    public static final int CUE_POINT = 7;
    public static final int END_OF_TRACK = 47;
    public static final int INSTRUMENT_NAME = 4;
    public static final int KEY_SIGNATURE = 89;
    public static final int LYRICS = 5;
    public static final int MARKER = 6;
    public static final int MIDI_CHANNEL_PREFIX = 32;
    public static final int SEQUENCER_SPECIFIC = 127;
    public static final int SEQUENCE_NUMBER = 0;
    public static final int SMPTE_OFFSET = 84;
    public static final int TEMPO = 81;
    public static final int TEXT_EVENT = 1;
    public static final int TIME_SIGNATURE = 88;
    public static final int TRACK_NAME = 3;
    protected VariableLengthInt mLength;
    protected int mType;

    @Override // com.leff.midi.event.MidiEvent
    protected abstract int getEventSize();

    protected MetaEvent(long tick, long delta, int type, VariableLengthInt length) {
        super(tick, delta);
        this.mType = type & 255;
        this.mLength = length;
    }

    @Override // com.leff.midi.event.MidiEvent
    public void writeToFile(OutputStream out, boolean writeType) throws IOException {
        writeToFile(out);
    }

    protected void writeToFile(OutputStream out) throws IOException {
        super.writeToFile(out, true);
        out.write(255);
        out.write(this.mType);
    }

    public static MetaEvent parseMetaEvent(long tick, long delta, InputStream in) throws IOException {
        MetaEventData metaEventData = new MetaEventData(in);
        int i = metaEventData.type;
        if (i != 0 && i != 32 && i != 47 && i != 81 && i != 84 && i != 88 && i != 89) {
            String str = new String(metaEventData.data);
            int i2 = metaEventData.type;
            if (i2 != 127) {
                switch (i2) {
                    case 1:
                        return new Text(tick, delta, str);
                    case 2:
                        return new CopyrightNotice(tick, delta, str);
                    case 3:
                        return new TrackName(tick, delta, str);
                    case 4:
                        return new InstrumentName(tick, delta, str);
                    case 5:
                        return new Lyrics(tick, delta, str);
                    case 6:
                        return new Marker(tick, delta, str);
                    case 7:
                        return new CuePoint(tick, delta, str);
                    default:
                        return new GenericMetaEvent(tick, delta, metaEventData);
                }
            }
            return new SequencerSpecificEvent(tick, delta, metaEventData.data);
        }
        int i3 = metaEventData.type;
        if (i3 == 0) {
            return SequenceNumber.parseSequenceNumber(tick, delta, metaEventData);
        }
        if (i3 == 32) {
            return MidiChannelPrefix.parseMidiChannelPrefix(tick, delta, metaEventData);
        }
        if (i3 == 47) {
            return new EndOfTrack(tick, delta);
        }
        if (i3 == 81) {
            return Tempo.parseTempo(tick, delta, metaEventData);
        }
        if (i3 == 84) {
            return SmpteOffset.parseSmpteOffset(tick, delta, metaEventData);
        }
        if (i3 == 88) {
            return TimeSignature.parseTimeSignature(tick, delta, metaEventData);
        }
        if (i3 == 89) {
            return KeySignature.parseKeySignature(tick, delta, metaEventData);
        }
        System.out.println("Completely broken in MetaEvent.parseMetaEvent()");
        return null;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:com/leff/midi/event/meta/MetaEvent$MetaEventData.class
      classes10.dex
     */
    protected static class MetaEventData {
        public final byte[] data;
        public final VariableLengthInt length;
        public final int type;

        public MetaEventData(InputStream in) throws IOException {
            this.type = in.read();
            VariableLengthInt variableLengthInt = new VariableLengthInt(in);
            this.length = variableLengthInt;
            byte[] bArr = new byte[variableLengthInt.getValue()];
            this.data = bArr;
            if (variableLengthInt.getValue() > 0) {
                in.read(bArr);
            }
        }
    }
}

package org.catrobat.catroid.pocketmusic.note.midi;

import com.leff.midi.event.ChannelEvent;
import com.leff.midi.event.NoteOff;
import com.leff.midi.event.NoteOn;
import org.catrobat.catroid.pocketmusic.note.NoteEvent;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/pocketmusic/note/midi/NoteEventToMidiEventConverter.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class NoteEventToMidiEventConverter {
    private static final int DEFAULT_NOISE = 64;
    private static final int DEFAULT_SILENT = 0;

    public ChannelEvent convertNoteEvent(long tick, NoteEvent noteEvent, int channel) {
        if (noteEvent.isNoteOn()) {
            return new NoteOn(tick, channel, noteEvent.getNoteName().getMidi(), 64);
        }
        return new NoteOff(tick, channel, noteEvent.getNoteName().getMidi(), 0);
    }
}

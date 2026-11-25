package org.catrobat.catroid.pocketmusic.note.trackgrid;

import androidx.collection.SparseArrayCompat;
import java.util.ArrayList;
import java.util.HashMap;
import org.catrobat.catroid.pocketmusic.note.MusicalBeat;
import org.catrobat.catroid.pocketmusic.note.NoteEvent;
import org.catrobat.catroid.pocketmusic.note.NoteLength;
import org.catrobat.catroid.pocketmusic.note.NoteName;
import org.catrobat.catroid.pocketmusic.note.Track;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/pocketmusic/note/trackgrid/TrackToTrackGridConverter.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class TrackToTrackGridConverter {
    private TrackToTrackGridConverter() {
    }

    public static TrackGrid convertTrackToTrackGrid(Track track, MusicalBeat beat, int beatsPerMinute) {
        HashMap map = new HashMap();
        HashMap map2 = new HashMap();
        NoteLength noteLength = beat.getNoteLength();
        for (Long l : track.getSortedTicks()) {
            for (NoteEvent noteEvent : track.getNoteEventsForTick(l.longValue())) {
                NoteName noteName = noteEvent.getNoteName();
                if (map2.get(noteName) == null) {
                    map2.put(noteName, new GridRow(noteName, new SparseArrayCompat()));
                }
                if (noteEvent.isNoteOn()) {
                    map.put(noteName, l);
                } else {
                    long jLongValue = ((Long) map.get(noteName)).longValue();
                    NoteLength noteLengthFromTickDuration = NoteLength.getNoteLengthFromTickDuration(l.longValue() - jLongValue, beatsPerMinute);
                    int ticks = (int) (jLongValue / noteLength.toTicks(beatsPerMinute));
                    int iLongValue = ((((int) ((l.longValue() - jLongValue) / noteLength.toTicks(beatsPerMinute))) + ticks) - 1) / beat.getTopNumber();
                    GridRowPosition gridRowPosition = new GridRowPosition(ticks % beat.getTopNumber(), noteLengthFromTickDuration);
                    for (int topNumber = ticks / beat.getTopNumber(); topNumber <= iLongValue; topNumber++) {
                        if (((GridRow) map2.get(noteName)).getGridRowPositions().get(topNumber) == null) {
                            ((GridRow) map2.get(noteName)).getGridRowPositions().put(topNumber, new ArrayList());
                        }
                        ((GridRow) map2.get(noteName)).getGridRowPositions().get(topNumber).add(gridRowPosition);
                    }
                }
            }
        }
        return new TrackGrid(track.getKey(), track.getInstrument(), beat, new ArrayList(map2.values()));
    }
}

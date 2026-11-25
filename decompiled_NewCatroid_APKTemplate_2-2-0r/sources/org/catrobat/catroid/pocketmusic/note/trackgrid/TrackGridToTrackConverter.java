package org.catrobat.catroid.pocketmusic.note.trackgrid;

import androidx.collection.SparseArrayCompat;
import java.util.List;
import org.catrobat.catroid.pocketmusic.note.NoteEvent;
import org.catrobat.catroid.pocketmusic.note.Track;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/pocketmusic/note/trackgrid/TrackGridToTrackConverter.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class TrackGridToTrackConverter {
    private TrackGridToTrackConverter() {
    }

    public static Track convertTrackGridToTrack(TrackGrid trackGrid, int beatsPerMinute) {
        Track track = new Track(trackGrid.getKey(), trackGrid.getInstrument());
        for (GridRow gridRow : trackGrid.getGridRows()) {
            SparseArrayCompat<List<GridRowPosition>> gridRowPositions = gridRow.getGridRowPositions();
            for (int i = 0; i < gridRowPositions.size(); i++) {
                int iKeyAt = gridRowPositions.keyAt(i);
                for (GridRowPosition gridRowPosition : gridRowPositions.get(iKeyAt)) {
                    long startTicksInTrack = gridRowPosition.getStartTicksInTrack() + ((long) (trackGrid.getBeat().getTopNumber() * iKeyAt * beatsPerMinute * 8));
                    long ticks = gridRowPosition.getNoteLength().toTicks(beatsPerMinute);
                    track.addNoteEvent(startTicksInTrack, new NoteEvent(gridRow.getNoteName(), true));
                    track.addNoteEvent(startTicksInTrack + ticks, new NoteEvent(gridRow.getNoteName(), false));
                }
            }
        }
        return track;
    }
}

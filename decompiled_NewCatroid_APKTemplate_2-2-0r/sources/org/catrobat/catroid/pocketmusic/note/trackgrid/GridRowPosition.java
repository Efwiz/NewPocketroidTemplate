package org.catrobat.catroid.pocketmusic.note.trackgrid;

import java.util.List;
import org.catrobat.catroid.pocketmusic.note.NoteLength;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/pocketmusic/note/trackgrid/GridRowPosition.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class GridRowPosition {
    private int columnStartIndex;
    private final NoteLength noteLength;

    public GridRowPosition(int columnStartIndex, NoteLength noteLength) {
        this.columnStartIndex = columnStartIndex;
        this.noteLength = noteLength;
    }

    public int getColumnStartIndex() {
        return this.columnStartIndex;
    }

    public NoteLength getNoteLength() {
        return this.noteLength;
    }

    public void setColumnStartIndex(int columnStartIndex) {
        this.columnStartIndex = columnStartIndex;
    }

    public long getStartTicksInTrack() {
        return ((long) this.columnStartIndex) * this.noteLength.toTicks(60);
    }

    public int hashCode() {
        return ((713 + this.columnStartIndex) * 31) + this.noteLength.hashCode();
    }

    public boolean equals(Object o) {
        GridRowPosition gridRowPosition = (GridRowPosition) o;
        return gridRowPosition.columnStartIndex == this.columnStartIndex && gridRowPosition.noteLength.equals(this.noteLength);
    }

    public static int getGridRowPositionIndexInList(List<GridRowPosition> gridRowPositions, int columnStartIndex) {
        for (int i = 0; i < gridRowPositions.size(); i++) {
            if (gridRowPositions.get(i).getColumnStartIndex() == columnStartIndex) {
                return i;
            }
        }
        return -1;
    }
}

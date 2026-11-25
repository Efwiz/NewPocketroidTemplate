package org.catrobat.catroid.pocketmusic.note.trackgrid;

import androidx.collection.SparseArrayCompat;
import java.util.List;
import org.catrobat.catroid.pocketmusic.note.NoteName;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/pocketmusic/note/trackgrid/GridRow.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class GridRow {
    private final SparseArrayCompat<List<GridRowPosition>> gridRowPositions;
    private final NoteName noteName;

    public GridRow(NoteName noteName, SparseArrayCompat<List<GridRowPosition>> gridRowPositions) {
        this.noteName = noteName;
        this.gridRowPositions = gridRowPositions;
    }

    public NoteName getNoteName() {
        return this.noteName;
    }

    public SparseArrayCompat<List<GridRowPosition>> getGridRowPositions() {
        return this.gridRowPositions;
    }

    public int hashCode() {
        int iHashCode = 651 + this.noteName.hashCode();
        for (int i = 0; i < getGridRowPositions().size(); i++) {
            iHashCode += 31 * getGridRowPositions().valueAt(i).hashCode();
        }
        return iHashCode;
    }

    public boolean equals(Object o) {
        if (!(o instanceof GridRow)) {
            return false;
        }
        GridRow gridRow = (GridRow) o;
        if (!gridRow.noteName.equals(this.noteName) || gridRow.getGridRowPositions().size() != getGridRowPositions().size()) {
            return false;
        }
        for (int i = 0; i < gridRow.getGridRowPositions().size(); i++) {
            if (gridRow.getGridRowPositions().keyAt(i) != getGridRowPositions().keyAt(i) || gridRow.getGridRowPositions().valueAt(i).size() != getGridRowPositions().valueAt(i).size() || !gridRow.getGridRowPositions().valueAt(i).containsAll(getGridRowPositions().valueAt(i)) || !getGridRowPositions().valueAt(i).containsAll(gridRow.getGridRowPositions().valueAt(i))) {
                return false;
            }
        }
        return true;
    }

    public List<GridRowPosition> gridRowForTact(int tactPosition) {
        return this.gridRowPositions.get(tactPosition);
    }
}

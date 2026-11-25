package org.catrobat.catroid.pocketmusic.note.trackgrid;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import androidx.collection.SparseArrayCompat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.catrobat.catroid.pocketmusic.mididriver.MidiNotePlayer;
import org.catrobat.catroid.pocketmusic.mididriver.MidiPlayer;
import org.catrobat.catroid.pocketmusic.mididriver.MidiRunnable;
import org.catrobat.catroid.pocketmusic.mididriver.MidiSignals;
import org.catrobat.catroid.pocketmusic.note.MusicalBeat;
import org.catrobat.catroid.pocketmusic.note.MusicalInstrument;
import org.catrobat.catroid.pocketmusic.note.MusicalKey;
import org.catrobat.catroid.pocketmusic.note.NoteLength;
import org.catrobat.catroid.pocketmusic.note.NoteName;
import org.catrobat.catroid.pocketmusic.note.Project;
import org.catrobat.catroid.pocketmusic.ui.PianoView;
import org.catrobat.catroid.pocketmusic.ui.TrackRowView;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/pocketmusic/note/trackgrid/TrackGrid.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class TrackGrid {
    private static final int INDEX_TO_COUNT_OFFSET = 1;
    private static final int SOUND_OFFSET = 10;
    private final MusicalBeat beat;
    private final List<GridRow> gridRows;
    private final MusicalInstrument instrument;
    private final MusicalKey key;
    private List<MidiRunnable> playRunnables = new ArrayList();
    private Handler handler = new Handler(Looper.getMainLooper());
    private MidiNotePlayer midiDriver = new MidiNotePlayer();

    public TrackGrid(MusicalKey key, MusicalInstrument instrument, MusicalBeat beat, List<GridRow> gridRows) {
        this.key = key;
        this.instrument = instrument;
        this.beat = beat;
        this.gridRows = gridRows;
    }

    public MusicalKey getKey() {
        return this.key;
    }

    public MusicalInstrument getInstrument() {
        return this.instrument;
    }

    public MusicalBeat getBeat() {
        return this.beat;
    }

    public List<GridRow> getGridRows() {
        return this.gridRows;
    }

    public int hashCode() {
        return ((((((5332 + this.key.hashCode()) * 31) + this.instrument.hashCode()) * 31) + this.beat.hashCode()) * 31) + this.gridRows.hashCode();
    }

    public boolean equals(Object o) {
        TrackGrid trackGrid = (TrackGrid) o;
        return trackGrid.gridRows.containsAll(this.gridRows) && this.gridRows.containsAll(trackGrid.gridRows) && trackGrid.beat.equals(this.beat) && trackGrid.instrument.equals(this.instrument) && trackGrid.key.equals(this.key);
    }

    public GridRow getGridRowForNoteName(NoteName noteName) {
        for (GridRow gridRow : this.gridRows) {
            if (gridRow.getNoteName().equals(noteName)) {
                return gridRow;
            }
        }
        return null;
    }

    public void updateGridRowPosition(NoteName noteName, int columnIndex, NoteLength noteLength, int tactIndex, boolean toggled) {
        GridRow gridRowForNoteName = getGridRowForNoteName(noteName);
        if (gridRowForNoteName == null) {
            gridRowForNoteName = createNewGridRow(noteName);
        }
        if (gridRowForNoteName.getGridRowPositions().indexOfKey(tactIndex) < 0) {
            appendNoteListAtPosition(gridRowForNoteName.getGridRowPositions(), tactIndex);
        }
        List<GridRowPosition> list = gridRowForNoteName.getGridRowPositions().get(tactIndex);
        int gridRowPositionIndexInList = GridRowPosition.getGridRowPositionIndexInList(list, columnIndex);
        if (toggled) {
            if (gridRowPositionIndexInList == -1) {
                Log.d("TrackGrid", String.format("Added GridRowPosition with name %s on Tact %d with columnIndex %d and noteLength %s. ", noteName.name(), Integer.valueOf(tactIndex), Integer.valueOf(columnIndex), noteLength.toString()));
                list.add(new GridRowPosition(columnIndex, noteLength));
                this.handler.post(new MidiRunnable(MidiSignals.NOTE_ON, NoteName.getNoteNameFromMidiValue(MidiPlayer.getAdjustedMidiValue(Project.DEFAULT_INSTRUMENT, noteName.getMidi())), NoteLength.QUARTER.toMilliseconds(60), this.handler, this.midiDriver, null, (byte) 0));
                return;
            }
            return;
        }
        if (gridRowPositionIndexInList >= 0) {
            list.remove(gridRowPositionIndexInList);
            Log.d("TrackGrid", String.format("Removed GridRowPosition with name %s on Tact %d with columnIndex %d and noteLength %s.", noteName.name(), Integer.valueOf(tactIndex), Integer.valueOf(columnIndex), noteLength.toString()));
            if (list.isEmpty()) {
                gridRowForNoteName.getGridRowPositions().remove(tactIndex);
            }
        }
    }

    private void appendNoteListAtPosition(SparseArrayCompat<List<GridRowPosition>> array, int tactIndex) {
        array.append(tactIndex, new ArrayList(this.beat.getTopNumber()));
    }

    private GridRow createNewGridRow(NoteName noteName) {
        GridRow gridRow = new GridRow(noteName, new SparseArrayCompat());
        this.gridRows.add(gridRow);
        return gridRow;
    }

    public int getTactCount() {
        Iterator<GridRow> it = this.gridRows.iterator();
        int i = 0;
        while (it.hasNext()) {
            SparseArrayCompat<List<GridRowPosition>> gridRowPositions = it.next().getGridRowPositions();
            for (int i2 = 0; i2 < gridRowPositions.size(); i2++) {
                int iKeyAt = gridRowPositions.keyAt(i2);
                if (iKeyAt > i) {
                    i = iKeyAt;
                }
            }
        }
        return i + 1;
    }

    public void startPlayback(PianoView pianoView) {
        this.playRunnables.clear();
        long milliseconds = NoteLength.QUARTER.toMilliseconds(60);
        long jUptimeMillis = SystemClock.uptimeMillis();
        Iterator<GridRow> it = this.gridRows.iterator();
        while (it.hasNext()) {
            GridRow next = it.next();
            for (int i = 0; i < next.getGridRowPositions().size(); i++) {
                int iKeyAt = next.getGridRowPositions().keyAt(i);
                long j = ((long) TrackRowView.quarterCount) * milliseconds * ((long) iKeyAt);
                for (GridRowPosition gridRowPosition : next.getGridRowPositions().get(iKeyAt)) {
                    MidiRunnable midiRunnable = new MidiRunnable(MidiSignals.NOTE_ON, NoteName.getNoteNameFromMidiValue(MidiPlayer.getAdjustedMidiValue(Project.DEFAULT_INSTRUMENT, next.getNoteName().getMidi())), milliseconds - 10, this.handler, this.midiDriver, pianoView, (byte) 0, next.getNoteName());
                    this.handler.postAtTime(midiRunnable, jUptimeMillis + j + (((long) gridRowPosition.getColumnStartIndex()) * milliseconds) + 10);
                    this.playRunnables.add(midiRunnable);
                    it = it;
                    next = next;
                }
            }
        }
    }

    public void stopPlayback(PianoView pianoView) {
        for (MidiRunnable midiRunnable : this.playRunnables) {
            this.handler.removeCallbacks(midiRunnable);
            this.handler.post(new MidiRunnable(MidiSignals.NOTE_OFF, NoteName.getNoteNameFromMidiValue(MidiPlayer.getAdjustedMidiValue(Project.DEFAULT_INSTRUMENT, midiRunnable.getNoteName().getMidi())), 0L, this.handler, this.midiDriver, pianoView, (byte) 0, midiRunnable.getNoteName()));
        }
        this.playRunnables.clear();
    }
}

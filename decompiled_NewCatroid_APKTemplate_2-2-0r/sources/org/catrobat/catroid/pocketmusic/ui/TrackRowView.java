package org.catrobat.catroid.pocketmusic.ui;

import android.content.Context;
import android.widget.TableRow;
import androidx.core.content.ContextCompat;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.pocketmusic.note.MusicalBeat;
import org.catrobat.catroid.pocketmusic.note.NoteLength;
import org.catrobat.catroid.pocketmusic.note.NoteName;
import org.catrobat.catroid.pocketmusic.note.trackgrid.GridRow;
import org.catrobat.catroid.pocketmusic.note.trackgrid.GridRowPosition;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/pocketmusic/ui/TrackRowView.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class TrackRowView extends TableRow {
    public static final int INITIAL_QUARTER_COUNT = 4;
    public static int quarterCount;
    public boolean allowOnlySingleNote;
    private final MusicalBeat beat;
    private GridRow gridRow;
    private boolean isBlackRow;
    private NoteName noteName;
    private NotePickerView notePickerView;
    private List<NoteView> noteViews;
    private int tactPosition;
    private TrackView trackView;

    public TrackRowView(Context context) {
        this(context, MusicalBeat.BEAT_4_4, false, NoteName.DEFAULT_NOTE_NAME, null);
    }

    public TrackRowView(Context context, MusicalBeat beat, boolean isBlackRow, NoteName noteName, TrackView trackView) {
        super(context);
        this.tactPosition = 0;
        this.notePickerView = null;
        this.allowOnlySingleNote = false;
        this.beat = beat;
        this.noteName = noteName;
        this.trackView = trackView;
        quarterCount = 4;
        this.noteViews = new ArrayList(4);
        setBlackRow(isBlackRow);
        initializeRow();
        setWeightSum(4.0f);
        updateGridRow();
    }

    public TrackRowView(Context context, MusicalBeat beat, boolean isBlackRow, NotePickerView notePickerView, NoteName noteName, int quarterCount2) {
        super(context);
        this.tactPosition = 0;
        this.trackView = null;
        this.allowOnlySingleNote = false;
        this.beat = beat;
        this.noteName = noteName;
        this.notePickerView = notePickerView;
        quarterCount = quarterCount2;
        this.noteViews = new ArrayList(quarterCount2);
        this.allowOnlySingleNote = true;
        setBlackRow(isBlackRow);
        initializeRow();
        setWeightSum(quarterCount2);
        updateGridRow();
    }

    public void setTactPosition(int tactPosition, GridRow gridRow) {
        this.gridRow = gridRow;
        this.tactPosition = tactPosition;
        refreshNoteViews();
    }

    private void refreshNoteViews() {
        int color;
        int color2;
        if (this.tactPosition % 2 == 0) {
            color = ContextCompat.getColor(getContext(), R.color.pocketmusic_odd_bright);
            color2 = ContextCompat.getColor(getContext(), R.color.pocketmusic_odd_dusk);
        } else {
            color = ContextCompat.getColor(getContext(), R.color.pocketmusic_even_bright);
            color2 = ContextCompat.getColor(getContext(), R.color.pocketmusic_even_dusk);
        }
        for (NoteView noteView : this.noteViews) {
            noteView.setNoteActive(false, false);
            if (this.isBlackRow) {
                noteView.setBackgroundColor(color2);
            } else {
                noteView.setBackgroundColor(color);
            }
        }
        updateGridRow();
    }

    public void updateGridRow() {
        List<GridRowPosition> gridRowsForCurrentTact;
        GridRow gridRow = this.gridRow;
        if (gridRow == null || gridRow.getGridRowPositions().size() == 0 || (gridRowsForCurrentTact = getGridRowsForCurrentTact()) == null) {
            return;
        }
        for (int i = 0; i < gridRowsForCurrentTact.size(); i++) {
            setNoteForGridRowPosition(gridRowsForCurrentTact.get(i));
        }
    }

    private void setNoteForGridRowPosition(GridRowPosition position) {
        if (position != null) {
            long jLongValue = new BigDecimal(position.getNoteLength().toMilliseconds(1)).divide(new BigDecimal(this.beat.getNoteLength().toMilliseconds(1)), 4).longValue();
            for (int columnStartIndex = position.getColumnStartIndex(); columnStartIndex < ((long) position.getColumnStartIndex()) + jLongValue; columnStartIndex++) {
                this.noteViews.get(columnStartIndex).setNoteActive(true, false);
            }
        }
    }

    private void initializeRow() {
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(0, -1, 1.0f);
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.pocketmusic_trackrow_margin);
        layoutParams.bottomMargin = dimensionPixelSize;
        layoutParams.rightMargin = dimensionPixelSize;
        layoutParams.topMargin = dimensionPixelSize;
        layoutParams.leftMargin = dimensionPixelSize;
        for (int i = 0; i < quarterCount; i++) {
            this.noteViews.add(new NoteView(getContext(), this, i));
            addView(this.noteViews.get(i), layoutParams);
        }
    }

    public void updateGridRowPosition(int columnIndex, NoteLength noteLength, boolean toggled) {
        TrackView trackView = this.trackView;
        if (trackView != null) {
            trackView.updateGridRowPosition(this.noteName, columnIndex, noteLength, toggled);
            return;
        }
        NotePickerView notePickerView = this.notePickerView;
        if (notePickerView != null) {
            notePickerView.updateGridRowPosition(this.noteName, columnIndex, noteLength, toggled);
        }
    }

    private List<GridRowPosition> getGridRowsForCurrentTact() {
        return this.gridRow.gridRowForTact(this.tactPosition);
    }

    public List<NoteView> getNoteViews() {
        return this.noteViews;
    }

    public int getTactCount() {
        return quarterCount;
    }

    public void setBlackRow(boolean blackRow) {
        this.isBlackRow = blackRow;
    }

    public void alertNoteChanged() {
        NotePickerView notePickerView = this.notePickerView;
        if (notePickerView != null) {
            notePickerView.disableAllNotes();
            this.notePickerView.setSelectedNote(this.gridRow.getNoteName().getMidi());
            this.notePickerView.onNoteChanged();
        }
    }

    public void disableOwnNotes() {
        for (NoteView noteView : this.noteViews) {
            if (noteView.isToggled()) {
                noteView.setNoteActive(false, true);
            }
        }
    }

    public static int getMidiValueForRow(int row) {
        return ((row * 130) / 13) + 10;
    }
}

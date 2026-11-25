package org.catrobat.catroid.pocketmusic.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TableLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.catrobat.catroid.pocketmusic.note.MusicalBeat;
import org.catrobat.catroid.pocketmusic.note.MusicalInstrument;
import org.catrobat.catroid.pocketmusic.note.MusicalKey;
import org.catrobat.catroid.pocketmusic.note.NoteLength;
import org.catrobat.catroid.pocketmusic.note.NoteName;
import org.catrobat.catroid.pocketmusic.note.trackgrid.TrackGrid;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/pocketmusic/ui/NotePickerView.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class NotePickerView extends TableLayout {
    public static final int ROW_COUNT = 13;
    private int initialNote;
    private OnNoteChangedListener listener;
    private int selectedNote;
    private int tactPosition;
    private TrackGrid trackGrid;
    private List<TrackRowView> trackRowViews;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/pocketmusic/ui/NotePickerView$OnNoteChangedListener.class
      classes14.dex
     */
    public interface OnNoteChangedListener {
        void noteChanged(int color);
    }

    public NotePickerView(Context context, AttributeSet attrs) {
        this(context, attrs, new TrackGrid(MusicalKey.VIOLIN, MusicalInstrument.ACCORDION, MusicalBeat.BEAT_4_4, new ArrayList()));
    }

    public NotePickerView(Context context, AttributeSet attrs, TrackGrid trackGrid) {
        super(context, attrs);
        this.trackRowViews = new ArrayList(13);
        this.tactPosition = 0;
        setStretchAllColumns(true);
        setClickable(true);
        this.trackGrid = trackGrid;
        initializeRows();
        setWeightSum(13.0f);
    }

    private void initializeRows() {
        if (!this.trackRowViews.isEmpty()) {
            removeAllViews();
            this.trackRowViews.clear();
        }
        TableLayout.LayoutParams layoutParams = new TableLayout.LayoutParams(-1, 0, 1.0f);
        for (int i = 0; i < 13; i++) {
            boolean z = Arrays.binarySearch(TrackView.BLACK_KEY_INDICES, i) > -1;
            NoteName noteNameFromMidiValue = NoteName.getNoteNameFromMidiValue(TrackRowView.getMidiValueForRow(i));
            TrackRowView trackRowView = new TrackRowView(getContext(), this.trackGrid.getBeat(), z, this, noteNameFromMidiValue, 1);
            this.trackGrid.updateGridRowPosition(noteNameFromMidiValue, 0, NoteLength.QUARTER, 0, false);
            trackRowView.setTactPosition(this.tactPosition, this.trackGrid.getGridRowForNoteName(noteNameFromMidiValue));
            this.trackRowViews.add(trackRowView);
            addView(this.trackRowViews.get(i), layoutParams);
        }
    }

    public void disableAllNotes() {
        for (int i = 0; i < 13; i++) {
            this.trackRowViews.get(i).disableOwnNotes();
        }
    }

    public void updateGridRowPosition(NoteName noteName, int columnIndex, NoteLength noteLength, boolean toggled) {
        this.trackGrid.updateGridRowPosition(noteName, columnIndex, noteLength, this.tactPosition, toggled);
    }

    public void setOnNoteChangedListener(OnNoteChangedListener listener) {
        this.listener = listener;
    }

    public void onNoteChanged() {
        OnNoteChangedListener onNoteChangedListener = this.listener;
        if (onNoteChangedListener != null) {
            onNoteChangedListener.noteChanged(getSelectedNote());
        }
    }

    public int getSelectedNote() {
        return this.selectedNote;
    }

    public void setSelectedNote(int note) {
        updateTrackRowViews(note);
        this.selectedNote = note;
    }

    public void setInitialNote(int initialNote) {
        updateTrackRowViews(initialNote);
        this.initialNote = initialNote;
    }

    private void updateTrackRowViews(int noteToBeSet) {
        for (int i = 0; i < 13; i++) {
            if (TrackRowView.getMidiValueForRow(i) == noteToBeSet) {
                this.trackRowViews.get(i).getNoteViews().get(0).setNoteActive(true, true);
                return;
            }
        }
    }

    public int getInitialNote() {
        return this.initialNote;
    }
}

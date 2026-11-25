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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/pocketmusic/ui/TrackView.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class TrackView extends TableLayout {
    public static final int HIGHEST_MIDI = 130;
    public static final int ROW_COUNT = 13;
    private int tactPosition;
    private TrackGrid trackGrid;
    private List<TrackRowView> trackRowViews;
    public static final int[] BLACK_KEY_INDICES = {1, 3, 6, 8, 10};
    public static final int[] WHITE_KEY_INDICES = {0, 2, 4, 5, 7, 9, 11, 12};

    public TrackView(Context context, AttributeSet attrs) {
        this(context, attrs, new TrackGrid(MusicalKey.VIOLIN, MusicalInstrument.ACCORDION, MusicalBeat.BEAT_4_4, new ArrayList()));
    }

    public TrackView(Context context, TrackGrid trackGrid) {
        this(context, null, trackGrid);
    }

    public TrackView(Context context, AttributeSet attrs, TrackGrid trackGrid) {
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
            boolean z = Arrays.binarySearch(BLACK_KEY_INDICES, i) > -1;
            NoteName noteNameFromMidiValue = NoteName.getNoteNameFromMidiValue(TrackRowView.getMidiValueForRow(i));
            TrackRowView trackRowView = new TrackRowView(getContext(), this.trackGrid.getBeat(), z, noteNameFromMidiValue, this);
            trackRowView.setTactPosition(this.tactPosition, this.trackGrid.getGridRowForNoteName(noteNameFromMidiValue));
            this.trackRowViews.add(trackRowView);
            addView(this.trackRowViews.get(i), layoutParams);
        }
    }

    public List<TrackRowView> getTrackRowViews() {
        return this.trackRowViews;
    }

    public void updateDataForTactPosition(int tactPosition) {
        this.tactPosition = tactPosition;
        for (int i = 0; i < 13; i++) {
            this.trackRowViews.get(i).setTactPosition(tactPosition, this.trackGrid.getGridRowForNoteName(NoteName.getNoteNameFromMidiValue(TrackRowView.getMidiValueForRow(i))));
        }
    }

    public void updateGridRowPosition(NoteName noteName, int columnIndex, NoteLength noteLength, boolean toggled) {
        this.trackGrid.updateGridRowPosition(noteName, columnIndex, noteLength, this.tactPosition, toggled);
    }
}

package org.catrobat.catroid.pocketmusic.ui;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import androidx.core.content.ContextCompat;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.pocketmusic.note.NoteLength;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/pocketmusic/ui/NoteView.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class NoteView extends ImageView implements View.OnClickListener {
    private static final int FULL_VISIBLE = 255;
    private static final int HIDDEN = 0;
    private final int horizontalIndexInGridRowPosition;
    private Drawable noteDrawable;
    private boolean toggled;
    private TrackRowView trackRowView;

    public NoteView(Context context) {
        this(context, null, 0);
    }

    public NoteView(Context context, TrackRowView trackRowView, int horizontalIndexInGridRowPosition) {
        super(context);
        setOnClickListener(this);
        setAdjustViewBounds(true);
        setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        initNoteDrawable();
        this.trackRowView = trackRowView;
        this.horizontalIndexInGridRowPosition = horizontalIndexInGridRowPosition;
    }

    private void initNoteDrawable() {
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_pocketmusic_note_toggle);
        this.noteDrawable = drawable;
        drawable.setColorFilter(ContextCompat.getColor(getContext(), R.color.orange), PorterDuff.Mode.SRC_IN);
        this.noteDrawable.mutate();
        this.noteDrawable.setAlpha(0);
        setImageDrawable(this.noteDrawable);
    }

    public void setNoteActive(boolean active, boolean updateData) {
        if (this.toggled != active) {
            this.toggled = active;
            showNote();
            if (updateData) {
                updateGridRow();
            }
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        if (this.trackRowView.allowOnlySingleNote) {
            this.trackRowView.alertNoteChanged();
        } else {
            toggle();
        }
    }

    public void toggle() {
        this.toggled = !this.toggled;
        showNote();
        updateGridRow();
    }

    private void updateGridRow() {
        this.trackRowView.updateGridRowPosition(this.horizontalIndexInGridRowPosition, NoteLength.QUARTER, this.toggled);
    }

    private void showNote() {
        if (this.toggled) {
            this.noteDrawable.setAlpha(255);
        } else {
            this.noteDrawable.setAlpha(0);
        }
        invalidate();
    }

    public boolean isToggled() {
        return this.toggled;
    }
}

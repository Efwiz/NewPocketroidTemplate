package org.catrobat.catroid.pocketmusic.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.appcompat.app.AppCompatDialogFragment;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.pocketmusic.mididriver.MidiNotePlayer;
import org.catrobat.catroid.pocketmusic.note.MusicalInstrument;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/pocketmusic/ui/NotePickerDialog.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class NotePickerDialog extends AppCompatDialogFragment implements NotePickerView.OnNoteChangedListener {
    private static final String CURRENT_NOTE = "CurrentNote";
    private static final String INITIAL_NOTE = "InitialNote";
    private static final String SHOW_ACTION_BAR = "ShowActionBar";
    private Button buttonApply;
    private Button buttonCancel;
    private MidiNotePlayer midiDriver;
    private NotePickerView notePickerView;
    private int noteToApply;
    public List<OnNotePickedListener> onNotePickedListener = new ArrayList();

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/pocketmusic/ui/NotePickerDialog$OnNotePickedListener.class
      classes14.dex
     */
    public interface OnNotePickedListener {
        void noteChanged(int color);
    }

    public static NotePickerDialog newInstance(int initialNote) {
        return newInstance(initialNote, false);
    }

    public static NotePickerDialog newInstance(int initialNote, boolean showActionBar) {
        NotePickerDialog notePickerDialog = new NotePickerDialog();
        Bundle bundle = new Bundle();
        bundle.putInt(INITIAL_NOTE, initialNote);
        bundle.putInt(CURRENT_NOTE, initialNote);
        bundle.putBoolean(SHOW_ACTION_BAR, showActionBar);
        notePickerDialog.setArguments(bundle);
        return notePickerDialog;
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.midiDriver = new MidiNotePlayer();
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_notepicker, container, false);
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.midiDriver = new MidiNotePlayer();
        if (!MidiNotePlayer.isInitialized()) {
            this.midiDriver.start();
        } else {
            this.midiDriver.setInstrument((byte) 0, MusicalInstrument.ACOUSTIC_GRAND_PIANO);
        }
        NotePickerView notePickerView = (NotePickerView) view.findViewById(R.id.note_picker_view);
        this.notePickerView = notePickerView;
        notePickerView.setOnNoteChangedListener(this);
        this.buttonApply = (Button) view.findViewById(R.id.note_picker_button_ok);
        this.buttonCancel = (Button) view.findViewById(R.id.note_picker_button_cancel);
        this.buttonApply.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.pocketmusic.ui.NotePickerDialog$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m5434xc9ce2f33(view2);
            }
        });
        this.buttonCancel.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.pocketmusic.ui.NotePickerDialog$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m5435xe3e9add2(view2);
            }
        });
        if (savedInstanceState != null) {
            this.notePickerView.setSelectedNote(savedInstanceState.getInt(CURRENT_NOTE, TrackRowView.getMidiValueForRow(0)));
            this.notePickerView.setInitialNote(savedInstanceState.getInt(INITIAL_NOTE, TrackRowView.getMidiValueForRow(0)));
        } else {
            Bundle arguments = getArguments();
            this.notePickerView.setSelectedNote(arguments.getInt(CURRENT_NOTE, TrackRowView.getMidiValueForRow(0)));
            this.notePickerView.setInitialNote(arguments.getInt(INITIAL_NOTE, TrackRowView.getMidiValueForRow(0)));
        }
        this.noteToApply = this.notePickerView.getInitialNote();
    }

    /* JADX INFO: renamed from: lambda$onViewCreated$0$org-catrobat-catroid-pocketmusic-ui-NotePickerDialog, reason: not valid java name */
    /* synthetic */ void m5434xc9ce2f33(View view) {
        updateNoteChange(this.noteToApply);
        dismiss();
    }

    /* JADX INFO: renamed from: lambda$onViewCreated$1$org-catrobat-catroid-pocketmusic-ui-NotePickerDialog, reason: not valid java name */
    /* synthetic */ void m5435xe3e9add2(View view) {
        dismiss();
    }

    @Override // androidx.appcompat.app.AppCompatDialogFragment, androidx.fragment.app.DialogFragment
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialogOnCreateDialog = super.onCreateDialog(savedInstanceState);
        dialogOnCreateDialog.setTitle(R.string.note_picker_title);
        return dialogOnCreateDialog;
    }

    @Override // androidx.fragment.app.DialogFragment
    public void dismiss() {
        if (getShowsDialog()) {
            super.dismiss();
        } else {
            getActivity().getSupportFragmentManager().popBackStack();
        }
    }

    public void addOnNotePickedListener(OnNotePickedListener listener) {
        this.onNotePickedListener.add(listener);
    }

    @Override // org.catrobat.catroid.pocketmusic.ui.NotePickerView.OnNoteChangedListener
    public void noteChanged(int note) {
        this.noteToApply = note;
    }

    private void updateNoteChange(int note) {
        Iterator<OnNotePickedListener> it = this.onNotePickedListener.iterator();
        while (it.hasNext()) {
            it.next().noteChanged(note);
        }
    }
}

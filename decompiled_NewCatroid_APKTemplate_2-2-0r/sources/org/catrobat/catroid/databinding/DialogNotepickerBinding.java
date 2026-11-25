package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.pocketmusic.ui.NotePickerView;
import org.catrobat.catroid.pocketmusic.ui.PianoView;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/DialogNotepickerBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class DialogNotepickerBinding implements ViewBinding {
    public final PianoView musicdroidPianoNotepicker;
    public final Button notePickerButtonCancel;
    public final Button notePickerButtonOk;
    public final NotePickerView notePickerView;
    private final RelativeLayout rootView;

    private DialogNotepickerBinding(RelativeLayout rootView, PianoView musicdroidPianoNotepicker, Button notePickerButtonCancel, Button notePickerButtonOk, NotePickerView notePickerView) {
        this.rootView = rootView;
        this.musicdroidPianoNotepicker = musicdroidPianoNotepicker;
        this.notePickerButtonCancel = notePickerButtonCancel;
        this.notePickerButtonOk = notePickerButtonOk;
        this.notePickerView = notePickerView;
    }

    @Override // androidx.viewbinding.ViewBinding
    public RelativeLayout getRoot() {
        return this.rootView;
    }

    public static DialogNotepickerBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static DialogNotepickerBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.dialog_notepicker, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static DialogNotepickerBinding bind(View rootView) {
        int i = R.id.musicdroid_piano_notepicker;
        PianoView pianoView = (PianoView) ViewBindings.findChildViewById(rootView, R.id.musicdroid_piano_notepicker);
        if (pianoView != null) {
            i = R.id.note_picker_button_cancel;
            Button button = (Button) ViewBindings.findChildViewById(rootView, R.id.note_picker_button_cancel);
            if (button != null) {
                i = R.id.note_picker_button_ok;
                Button button2 = (Button) ViewBindings.findChildViewById(rootView, R.id.note_picker_button_ok);
                if (button2 != null) {
                    i = R.id.note_picker_view;
                    NotePickerView notePickerView = (NotePickerView) ViewBindings.findChildViewById(rootView, R.id.note_picker_view);
                    if (notePickerView != null) {
                        return new DialogNotepickerBinding((RelativeLayout) rootView, pianoView, button, button2, notePickerView);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}

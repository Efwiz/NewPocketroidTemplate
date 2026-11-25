package org.catrobat.catroid.pocketmusic.note;

import com.gaurav.avnc.vnc.XKeySym;
import java.io.Serializable;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/pocketmusic/note/NoteEvent.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class NoteEvent implements Serializable {
    private static final long serialVersionUID = 7483022549872527955L;
    private NoteName noteName;
    private boolean noteOn;

    public NoteEvent(NoteName noteName, boolean noteOn) {
        this.noteName = noteName;
        this.noteOn = noteOn;
    }

    public NoteEvent(NoteEvent noteEvent) {
        this.noteName = noteEvent.getNoteName();
        this.noteOn = noteEvent.isNoteOn();
    }

    public NoteName getNoteName() {
        return this.noteName;
    }

    public boolean isNoteOn() {
        return this.noteOn;
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof NoteEvent)) {
            return false;
        }
        NoteEvent noteEvent = (NoteEvent) obj;
        return this.noteName.equals(noteEvent.getNoteName()) && this.noteOn == noteEvent.isNoteOn();
    }

    public int hashCode() {
        return ((XKeySym.XK_Nacute + this.noteName.hashCode()) * 31) + (this.noteOn ? 1 : 0);
    }

    public String toString() {
        return "[NoteEvent] noteName= " + this.noteName + " noteOn=" + this.noteOn;
    }
}

package org.catrobat.catroid.pocketmusic.note.midi;

import com.leff.midi.MidiFile;
import com.leff.midi.MidiTrack;
import com.leff.midi.event.MidiEvent;
import com.leff.midi.event.NoteOff;
import com.leff.midi.event.NoteOn;
import com.leff.midi.event.ProgramChange;
import com.leff.midi.event.meta.Tempo;
import com.leff.midi.event.meta.Text;
import com.leff.midi.event.meta.TimeSignature;
import com.leff.midi.event.meta.TrackName;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.catrobat.catroid.pocketmusic.note.MusicalBeat;
import org.catrobat.catroid.pocketmusic.note.MusicalInstrument;
import org.catrobat.catroid.pocketmusic.note.MusicalKey;
import org.catrobat.catroid.pocketmusic.note.NoteEvent;
import org.catrobat.catroid.pocketmusic.note.NoteName;
import org.catrobat.catroid.pocketmusic.note.Project;
import org.catrobat.catroid.pocketmusic.note.Track;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/pocketmusic/note/midi/MidiToProjectConverter.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class MidiToProjectConverter {
    private int beatsPerMinute = 60;
    private MusicalBeat beat = Project.DEFAULT_BEAT;
    private List<Track> tracks = new ArrayList();
    private List<String> trackNames = new ArrayList();

    public Project convertMidiFileToProject(File file) throws IOException, MidiException {
        MidiFile midiFile = new MidiFile(file);
        validateMidiFile(midiFile);
        return convertMidi(file.getName().split(ProjectToMidiConverter.MIDI_FILE_EXTENSION)[0], midiFile);
    }

    private void validateMidiFile(MidiFile midiFile) throws MidiException {
        if (midiFile.getTrackCount() > 0) {
            Iterator<MidiEvent> it = midiFile.getTracks().get(0).getEvents().iterator();
            if (it.hasNext()) {
                MidiEvent next = it.next();
                if ((next instanceof Text) && ((Text) next).getText().equals(ProjectToMidiConverter.MIDI_FILE_IDENTIFIER)) {
                    return;
                }
            }
        }
        throw new MidiException("Unsupported MIDI!");
    }

    private Project convertMidi(String name2, MidiFile midi) {
        Iterator<MidiTrack> it = midi.getTracks().iterator();
        while (it.hasNext()) {
            createTrack(it.next());
        }
        Project project = new Project(name2, this.beat, this.beatsPerMinute);
        int i = 0;
        for (Track track : this.tracks) {
            if (track.size() > 0) {
                String str = this.trackNames.get(i);
                i++;
                project.putTrack(str, track);
            }
        }
        return project;
    }

    private void createTrack(MidiTrack midiTrack) {
        Track track = new Track(MusicalKey.VIOLIN, getInstrumentFromMidiTrack(midiTrack));
        for (MidiEvent midiEvent : midiTrack.getEvents()) {
            if (midiEvent instanceof TrackName) {
                this.trackNames.add(((TrackName) midiEvent).getTrackName());
            }
            if (midiEvent instanceof NoteOn) {
                NoteOn noteOn = (NoteOn) midiEvent;
                track.addNoteEvent(noteOn.getTick(), new NoteEvent(NoteName.getNoteNameFromMidiValue(noteOn.getNoteValue()), true));
            } else if (midiEvent instanceof NoteOff) {
                NoteOff noteOff = (NoteOff) midiEvent;
                track.addNoteEvent(noteOff.getTick(), new NoteEvent(NoteName.getNoteNameFromMidiValue(noteOff.getNoteValue()), false));
            } else if (midiEvent instanceof Tempo) {
                this.beatsPerMinute = (int) ((Tempo) midiEvent).getBpm();
            } else if (midiEvent instanceof TimeSignature) {
                TimeSignature timeSignature = (TimeSignature) midiEvent;
                this.beat = MusicalBeat.convertToMusicalBeat(timeSignature.getNumerator(), timeSignature.getRealDenominator());
            }
        }
        this.tracks.add(track);
    }

    private MusicalInstrument getInstrumentFromMidiTrack(MidiTrack midiTrack) {
        MusicalInstrument musicalInstrument = Project.DEFAULT_INSTRUMENT;
        for (MidiEvent midiEvent : midiTrack.getEvents()) {
            if (midiEvent instanceof ProgramChange) {
                return MusicalInstrument.getInstrumentFromProgram(((ProgramChange) midiEvent).getProgramNumber());
            }
        }
        return musicalInstrument;
    }
}

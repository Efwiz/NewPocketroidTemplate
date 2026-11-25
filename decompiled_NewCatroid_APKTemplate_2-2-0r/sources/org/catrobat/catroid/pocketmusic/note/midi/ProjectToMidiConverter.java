package org.catrobat.catroid.pocketmusic.note.midi;

import com.leff.midi.MidiFile;
import com.leff.midi.MidiTrack;
import com.leff.midi.event.ProgramChange;
import com.leff.midi.event.meta.Tempo;
import com.leff.midi.event.meta.Text;
import com.leff.midi.event.meta.TimeSignature;
import com.leff.midi.event.meta.TrackName;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.catrobat.catroid.pocketmusic.note.MusicalBeat;
import org.catrobat.catroid.pocketmusic.note.NoteEvent;
import org.catrobat.catroid.pocketmusic.note.Project;
import org.catrobat.catroid.pocketmusic.note.Track;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/pocketmusic/note/midi/ProjectToMidiConverter.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ProjectToMidiConverter {
    private static final int MAX_CHANNEL = 16;
    public static final String MIDI_FILE_EXTENSION = ".midi";
    public static final String MIDI_FILE_IDENTIFIER = "Musicdroid Midi File";
    private final File midiFolder;
    private NoteEventToMidiEventConverter eventConverter = new NoteEventToMidiEventConverter();
    private int nextChannel = 0;

    public ProjectToMidiConverter(File midiFolder) {
        this.midiFolder = midiFolder;
    }

    public void writeProjectAsMidi(Project project) throws IOException, MidiException {
        MidiFile midiFileConvertProject = convertProject(project);
        checkMidiFolder();
        midiFileConvertProject.writeToFile(getMidiFileFromProjectName(project.getName()));
    }

    private void checkMidiFolder() throws IOException {
        if (!this.midiFolder.exists() && !this.midiFolder.mkdir()) {
            throw new IOException("Could not create folder: " + this.midiFolder);
        }
    }

    public File getMidiFileFromProjectName(String name2) throws IOException {
        checkMidiFolder();
        return new File(this.midiFolder + File.separator + name2 + MIDI_FILE_EXTENSION);
    }

    public static String removeMidiExtensionFromString(String input) {
        return input.split(MIDI_FILE_EXTENSION)[0];
    }

    public void writeProjectAsMidi(Project project, File file) throws IOException, MidiException {
        convertProject(project).writeToFile(file);
    }

    private MidiFile convertProject(Project project) throws MidiException {
        Iterator<String> it = project.getTrackNames().iterator();
        while (it.hasNext()) {
            if (project.getTrack(it.next()).size() == 0) {
                throw new MidiException("Cannot save a project with an empty track!");
            }
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(createTempoTrackWithMetaInfo(project.getBeat(), project.getBeatsPerMinute()));
        for (String str : project.getTrackNames()) {
            arrayList.add(createNoteTrack(str, project.getTrack(str), getNextChannel()));
        }
        return new MidiFile(480, arrayList);
    }

    private int getNextChannel() throws MidiException {
        int i = this.nextChannel;
        if (i >= 16) {
            throw new MidiException("You cannot have more than 16 channels!");
        }
        this.nextChannel = i + 1;
        return i;
    }

    private MidiTrack createTempoTrackWithMetaInfo(MusicalBeat beat, int beatsPerMinute) {
        MidiTrack midiTrack = new MidiTrack();
        midiTrack.insertEvent(new Text(0L, 0L, MIDI_FILE_IDENTIFIER));
        Tempo tempo = new Tempo();
        tempo.setBpm(beatsPerMinute);
        midiTrack.insertEvent(tempo);
        TimeSignature timeSignature = new TimeSignature();
        timeSignature.setTimeSignature(beat.getTopNumber(), beat.getBottomNumber(), 24, 8);
        midiTrack.insertEvent(timeSignature);
        return midiTrack;
    }

    private MidiTrack createNoteTrack(String trackName, Track track, int channel) throws MidiException {
        MidiTrack midiTrack = new MidiTrack();
        midiTrack.insertEvent(new TrackName(0L, channel, trackName));
        midiTrack.insertEvent(new ProgramChange(0L, channel, track.getInstrument().getProgram()));
        Iterator<Long> it = track.getSortedTicks().iterator();
        while (it.hasNext()) {
            long jLongValue = it.next().longValue();
            Iterator<NoteEvent> it2 = track.getNoteEventsForTick(jLongValue).iterator();
            while (it2.hasNext()) {
                midiTrack.insertEvent(this.eventConverter.convertNoteEvent(jLongValue, it2.next(), channel));
            }
        }
        return midiTrack;
    }
}

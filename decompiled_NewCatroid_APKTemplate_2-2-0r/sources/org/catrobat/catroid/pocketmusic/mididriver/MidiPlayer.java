package org.catrobat.catroid.pocketmusic.mididriver;

import android.os.Handler;
import android.os.Looper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.pocketmusic.note.Drum;
import org.catrobat.catroid.pocketmusic.note.MusicalInstrument;
import org.catrobat.catroid.pocketmusic.note.NoteEvent;
import org.catrobat.catroid.pocketmusic.note.NoteName;
import org.catrobat.catroid.pocketmusic.note.Project;
import org.catrobat.catroid.pocketmusic.note.Track;
import org.catrobat.catroid.pocketmusic.note.midi.MidiException;
import org.catrobat.catroid.pocketmusic.note.midi.MidiToProjectConverter;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/pocketmusic/mididriver/MidiPlayer.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class MidiPlayer {
    public static final byte DRUM_CHANNEL = 9;
    public static final byte MAX_CHANNELS = 15;
    private byte channel;
    private Handler handler;
    private boolean paused;
    private Project project;
    private String sourceFilePath;
    private long startTime;
    private long startTimeOffset;
    private Sprite startedBySprite;
    private float tempo;
    private float volume;
    private static MidiNotePlayer notePlayer = new MidiNotePlayer();
    private static byte channelCounter = 0;
    private List<MidiRunnable> playRunnables = new ArrayList();
    private MusicalInstrument instrument = Project.DEFAULT_INSTRUMENT;

    public MidiPlayer() throws MidiException {
        byte b = channelCounter;
        if (b > 15) {
            throw new MidiException("Number of midi channels exceeded");
        }
        if (b == 9) {
            channelCounter = (byte) (b + 1);
        }
        this.handler = new Handler(Looper.getMainLooper());
        reset();
        byte b2 = channelCounter;
        this.channel = b2;
        channelCounter = (byte) (b2 + 1);
    }

    public void reset() {
        this.playRunnables.clear();
        this.project = null;
        this.startedBySprite = null;
        this.sourceFilePath = null;
        this.startTimeOffset = 0L;
        this.instrument = Project.DEFAULT_INSTRUMENT;
        this.tempo = 100.0f;
        this.volume = 70.0f;
        this.paused = false;
    }

    public void setPathToSoundFile(String sourceFilePath) throws IOException, MidiException {
        this.sourceFilePath = sourceFilePath;
        this.project = new MidiToProjectConverter().convertMidiFileToProject(new File(sourceFilePath));
    }

    public void setStartedBySprite(Sprite startedBySprite) {
        this.startedBySprite = startedBySprite;
    }

    public boolean seekTo(long startTimeInMilSeconds) {
        if (this.project == null) {
            return false;
        }
        this.startTimeOffset = startTimeInMilSeconds;
        return true;
    }

    public boolean start() {
        return start(this.instrument);
    }

    private boolean start(MusicalInstrument instrument) {
        MidiRunnable midiRunnable;
        MusicalInstrument musicalInstrument = instrument;
        if (this.project == null) {
            return false;
        }
        stopPlaying();
        if (!MidiNotePlayer.isInitialized()) {
            notePlayer.start();
        }
        notePlayer.setInstrument(this.channel, musicalInstrument);
        notePlayer.setVolume(this.channel, (int) this.volume);
        long playLengthInMilliseconds = getPlayLengthInMilliseconds();
        this.startTime = System.currentTimeMillis();
        Iterator<String> it = this.project.getTrackNames().iterator();
        while (it.hasNext()) {
            Track track = this.project.getTrack(it.next());
            Iterator<Long> it2 = track.getSortedTicks().iterator();
            while (it2.hasNext()) {
                long jLongValue = it2.next().longValue();
                if (jLongValue >= this.startTimeOffset) {
                    for (NoteEvent noteEvent : track.getNoteEventsForTick(jLongValue)) {
                        int adjustedMidiValue = getAdjustedMidiValue(musicalInstrument, noteEvent.getNoteName().getMidi());
                        if (noteEvent.isNoteOn()) {
                            midiRunnable = midiRunnable;
                            MidiRunnable midiRunnable2 = new MidiRunnable(MidiSignals.NOTE_ON, NoteName.getNoteNameFromMidiValue(adjustedMidiValue), playLengthInMilliseconds, this.handler, notePlayer, null, this.channel);
                            midiRunnable.setManualNoteOff(true);
                        } else {
                            midiRunnable = new MidiRunnable(MidiSignals.NOTE_OFF, NoteName.getNoteNameFromMidiValue(adjustedMidiValue), playLengthInMilliseconds, this.handler, notePlayer, null, this.channel);
                        }
                        MidiRunnable midiRunnable3 = midiRunnable;
                        long j = jLongValue;
                        float f = j;
                        midiRunnable3.setScheduledTime(this.startTime + ((long) (f / (this.tempo / 60.0f))));
                        this.handler.postDelayed(midiRunnable3, (long) (f / (this.tempo / 60.0f)));
                        this.playRunnables.add(midiRunnable3);
                        musicalInstrument = instrument;
                        jLongValue = j;
                    }
                    musicalInstrument = instrument;
                }
            }
            musicalInstrument = instrument;
        }
        return true;
    }

    void playDrumForBeats(Drum drum, float beats) {
        stopPlaying();
        if (!MidiNotePlayer.isInitialized()) {
            notePlayer.start();
        }
        notePlayer.setVolume((byte) 9, (int) this.volume);
        long playLengthInMilliseconds = getPlayLengthInMilliseconds();
        this.startTime = System.currentTimeMillis();
        MidiRunnable midiRunnable = new MidiRunnable(MidiSignals.NOTE_ON, NoteName.getNoteNameFromMidiValue(drum.getProgram()), 0L, this.handler, notePlayer, null, (byte) 9);
        midiRunnable.setManualNoteOff(true);
        midiRunnable.setScheduledTime(this.startTime);
        this.handler.post(midiRunnable);
        this.playRunnables.add(midiRunnable);
        MidiRunnable midiRunnable2 = new MidiRunnable(MidiSignals.NOTE_OFF, NoteName.getNoteNameFromMidiValue(drum.getProgram()), 0L, this.handler, notePlayer, null, (byte) 9);
        long j = (long) (beats * playLengthInMilliseconds);
        midiRunnable2.setScheduledTime(this.startTime + j);
        this.handler.postDelayed(midiRunnable2, j);
        this.playRunnables.add(midiRunnable2);
    }

    public void stopPlaying() {
        for (MidiRunnable midiRunnable : this.playRunnables) {
            this.handler.removeCallbacks(midiRunnable);
            this.handler.post(new MidiRunnable(MidiSignals.NOTE_OFF, midiRunnable.getNoteName(), 0L, this.handler, notePlayer, null, this.channel));
        }
    }

    public void pause() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        for (MidiRunnable midiRunnable : new ArrayList(this.playRunnables)) {
            if (midiRunnable.getScheduledTime() > jCurrentTimeMillis) {
                this.handler.removeCallbacks(midiRunnable);
            } else {
                this.playRunnables.remove(midiRunnable);
            }
            this.handler.post(new MidiRunnable(MidiSignals.NOTE_OFF, midiRunnable.getNoteName(), 0L, this.handler, notePlayer, null, this.channel));
        }
        this.paused = true;
    }

    public void playNoteForBeats(int midiValue, float beats) {
        stopPlaying();
        if (!MidiNotePlayer.isInitialized()) {
            notePlayer.start();
        }
        notePlayer.setInstrument(this.channel, this.instrument);
        notePlayer.setVolume(this.channel, (int) this.volume);
        long playLengthInMilliseconds = getPlayLengthInMilliseconds();
        this.startTime = System.currentTimeMillis();
        int adjustedMidiValue = getAdjustedMidiValue(this.instrument, midiValue);
        MidiRunnable midiRunnable = new MidiRunnable(MidiSignals.NOTE_ON, NoteName.getNoteNameFromMidiValue(adjustedMidiValue), 0L, this.handler, notePlayer, null, this.channel);
        midiRunnable.setManualNoteOff(true);
        midiRunnable.setScheduledTime(this.startTime);
        this.handler.post(midiRunnable);
        this.playRunnables.add(midiRunnable);
        MidiRunnable midiRunnable2 = new MidiRunnable(MidiSignals.NOTE_OFF, NoteName.getNoteNameFromMidiValue(adjustedMidiValue), 0L, this.handler, notePlayer, null, this.channel);
        long j = (long) (playLengthInMilliseconds * beats);
        midiRunnable2.setScheduledTime(this.startTime + j);
        this.handler.postDelayed(midiRunnable2, j);
        this.playRunnables.add(midiRunnable2);
    }

    public static int getAdjustedMidiValue(MusicalInstrument instrument, int originalMidiValue) {
        float f;
        if (originalMidiValue >= 130 || originalMidiValue <= 0) {
            f = originalMidiValue >= 130 ? 1.0f : 0.0f;
        } else {
            f = 130.0f / originalMidiValue;
        }
        int lowEnd = instrument.getLowEnd();
        int highEnd = instrument.getHighEnd();
        int i = ((int) ((1.0f / f) * (highEnd - lowEnd))) + lowEnd;
        if (i < lowEnd) {
            return lowEnd;
        }
        return i > highEnd ? highEnd : i;
    }

    public void resume() {
        if (this.paused) {
            long jCurrentTimeMillis = System.currentTimeMillis();
            long scheduledTime = this.playRunnables.get(0).getScheduledTime();
            for (MidiRunnable midiRunnable : this.playRunnables) {
                long scheduledTime2 = midiRunnable.getScheduledTime() - scheduledTime;
                this.handler.postDelayed(midiRunnable, scheduledTime2);
                midiRunnable.setScheduledTime(scheduledTime2 + jCurrentTimeMillis);
            }
            this.paused = false;
        }
    }

    public boolean isPlaying() {
        return this.handler.hasMessages(0);
    }

    public boolean isPaused() {
        return this.paused;
    }

    public void setInstrument(MusicalInstrument instrument) {
        this.instrument = instrument;
    }

    public void setTempo(float tempo) {
        this.tempo = tempo;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public String getPathToSoundFile() {
        return this.sourceFilePath;
    }

    public Sprite getStartedBySprite() {
        return this.startedBySprite;
    }

    public static MidiNotePlayer getNotePlayer() {
        return notePlayer;
    }

    public MusicalInstrument getInstrument() {
        return this.instrument;
    }

    public float getTempo() {
        return this.tempo;
    }

    public float getVolume() {
        return this.volume;
    }

    public long getCurrentPosition() {
        return System.currentTimeMillis() - this.startTime;
    }

    public long getStartTimeOffset() {
        return this.startTimeOffset;
    }

    public List<MidiRunnable> getPlayRunnables() {
        return this.playRunnables;
    }

    public byte getChannel() {
        return this.channel;
    }

    public long getPlayLengthInMilliseconds() {
        return (long) (60000.0f / this.tempo);
    }

    public static void resetChannelCounter() {
        channelCounter = (byte) 0;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Handler getHandler() {
        return this.handler;
    }
}

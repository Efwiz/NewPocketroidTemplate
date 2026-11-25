package org.catrobat.catroid.pocketmusic.mididriver;

import android.util.Log;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import name.antonsmirnov.firmata.FormatHelper;
import org.catrobat.catroid.content.SoundFilePathWithSprite;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.pocketmusic.note.Drum;
import org.catrobat.catroid.pocketmusic.note.MusicalInstrument;
import org.catrobat.catroid.pocketmusic.note.Project;
import org.catrobat.catroid.pocketmusic.note.midi.MidiException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/pocketmusic/mididriver/MidiSoundManager.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class MidiSoundManager {
    private static final MidiSoundManager INSTANCE = new MidiSoundManager();
    public static final int MAX_MIDI_PLAYERS = 15;
    private static final int MAX_TEMPO_PERCENT = 500;
    private static final int MIN_TEMPO_PERCENT = 20;
    private static final String TAG = "MidiSoundManager";
    private MusicalInstrument instrument = Project.DEFAULT_INSTRUMENT;
    private float tempo = 60.0f;
    private float volume = 70.0f;
    private final List<MidiPlayer> midiPlayers = new ArrayList(15);
    private Set<SoundFilePathWithSprite> startedSoundfilePaths = new HashSet();
    long pausedUntil = 0;

    public static MidiSoundManager getInstance() {
        return INSTANCE;
    }

    public void playSoundFile(String soundFilePath, Sprite sprite) {
        playSoundFileWithStartTime(soundFilePath, sprite, 0);
    }

    public void playSoundFileWithStartTime(String soundFilePath, Sprite sprite, int startTimeInMilSeconds) {
        MidiPlayer availableMidiPlayer = getAvailableMidiPlayer();
        if (availableMidiPlayer != null) {
            try {
                availableMidiPlayer.setStartedBySprite(sprite);
                availableMidiPlayer.setPathToSoundFile(soundFilePath);
                availableMidiPlayer.setInstrument(this.instrument);
                availableMidiPlayer.setTempo(this.tempo);
                availableMidiPlayer.setVolume((this.volume * 127.0f) / 100.0f);
                long j = startTimeInMilSeconds;
                if (this.pausedUntil > System.currentTimeMillis() + j) {
                    availableMidiPlayer.seekTo((this.pausedUntil - System.currentTimeMillis()) + j);
                } else {
                    availableMidiPlayer.seekTo(j);
                }
                availableMidiPlayer.start();
            } catch (Exception e) {
                Log.e(TAG, "Couldn't play sound file '" + soundFilePath + FormatHelper.QUOTE, e);
            }
            this.startedSoundfilePaths.add(new SoundFilePathWithSprite(soundFilePath, sprite));
        }
    }

    public void playDrumForBeats(Drum drum, float beats, Sprite sprite) {
        MidiPlayer availableMidiPlayer = getAvailableMidiPlayer();
        if (availableMidiPlayer != null) {
            try {
                availableMidiPlayer.setStartedBySprite(sprite);
                availableMidiPlayer.setTempo(this.tempo);
                availableMidiPlayer.setVolume((this.volume * 127.0f) / 100.0f);
                availableMidiPlayer.playDrumForBeats(drum, beats);
            } catch (Exception e) {
                Log.e(TAG, "Couldn't play drums", e);
            }
        }
    }

    private MidiPlayer getAvailableMidiPlayer() {
        for (MidiPlayer midiPlayer : this.midiPlayers) {
            if (!midiPlayer.isPlaying()) {
                midiPlayer.reset();
                return midiPlayer;
            }
        }
        if (this.midiPlayers.size() < 15) {
            try {
                MidiPlayer midiPlayer2 = new MidiPlayer();
                this.midiPlayers.add(midiPlayer2);
                return midiPlayer2;
            } catch (MidiException e) {
                Log.e(TAG, "No midi channels available", e);
                return null;
            }
        }
        Log.d(TAG, "All MidiPlayer instances in use");
        return null;
    }

    public void stopSameSoundInSprite(String pathToSoundFile, Sprite sprite) {
        for (MidiPlayer midiPlayer : this.midiPlayers) {
            if (midiPlayer.isPlaying() && midiPlayer.getStartedBySprite() == sprite && midiPlayer.getPathToSoundFile().equals(pathToSoundFile)) {
                midiPlayer.stopPlaying();
            }
        }
    }

    public void stopAllSounds() {
        for (MidiPlayer midiPlayer : this.midiPlayers) {
            if (midiPlayer.isPlaying()) {
                midiPlayer.stopPlaying();
            }
        }
    }

    public void pause() {
        for (MidiPlayer midiPlayer : this.midiPlayers) {
            if (midiPlayer.isPlaying()) {
                midiPlayer.pause();
            } else {
                midiPlayer.reset();
            }
        }
    }

    public void playNoteForBeats(int midiValue, float beats) {
        MidiPlayer availableMidiPlayer = getAvailableMidiPlayer();
        if (availableMidiPlayer != null) {
            availableMidiPlayer.setInstrument(this.instrument);
            availableMidiPlayer.setTempo(this.tempo);
            availableMidiPlayer.setVolume((this.volume * 127.0f) / 100.0f);
            availableMidiPlayer.playNoteForBeats(midiValue, beats);
        }
    }

    public long getDurationForBeats(float beats) {
        return (long) ((60000.0f / this.tempo) * beats);
    }

    public void resume() {
        for (MidiPlayer midiPlayer : this.midiPlayers) {
            if (!midiPlayer.isPlaying()) {
                midiPlayer.resume();
            }
        }
    }

    public boolean isSoundInSpritePlaying(Sprite sprite, String soundFilePath) {
        for (MidiPlayer midiPlayer : this.midiPlayers) {
            if (midiPlayer.isPlaying() && midiPlayer.getStartedBySprite() == sprite && midiPlayer.getPathToSoundFile().equals(soundFilePath)) {
                return true;
            }
        }
        return false;
    }

    public void setInstrument(MusicalInstrument instrumentParam) {
        this.instrument = instrumentParam;
    }

    public MusicalInstrument getInstrument() {
        return this.instrument;
    }

    public void setTempo(float tempo) {
        if (tempo > 500.0f) {
            this.tempo = 500.0f;
        } else if (tempo < 20.0f) {
            this.tempo = 20.0f;
        } else {
            this.tempo = tempo;
        }
    }

    public float getTempo() {
        return this.tempo;
    }

    public void setVolume(float volume) {
        if (volume > 100.0f) {
            this.volume = 100.0f;
        } else if (volume < 0.0f) {
            this.volume = 0.0f;
        } else {
            this.volume = volume;
        }
        Iterator<MidiPlayer> it = this.midiPlayers.iterator();
        while (it.hasNext()) {
            it.next().setVolume((this.volume * 127.0f) / 100.0f);
        }
    }

    public void reset() {
        setTempo(60.0f);
    }

    public float getVolume() {
        return this.volume;
    }

    public List<MidiPlayer> getMidiPlayers() {
        return this.midiPlayers;
    }

    public Set<SoundFilePathWithSprite> getStartedSoundfilePaths() {
        return this.startedSoundfilePaths;
    }
}

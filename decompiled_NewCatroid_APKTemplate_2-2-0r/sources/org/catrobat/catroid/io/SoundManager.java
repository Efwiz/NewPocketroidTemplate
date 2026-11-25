package org.catrobat.catroid.io;

import android.util.Log;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import name.antonsmirnov.firmata.FormatHelper;
import org.catrobat.catroid.content.MediaPlayerWithSoundDetails;
import org.catrobat.catroid.content.SoundBackup;
import org.catrobat.catroid.content.SoundFilePathWithSprite;
import org.catrobat.catroid.content.Sprite;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/io/SoundManager.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SoundManager {
    private static final SoundManager INSTANCE = new SoundManager();
    public static final int MAX_MEDIA_PLAYERS = 7;
    private static final String TAG = "SoundManager";
    private final List<MediaPlayerWithSoundDetails> mediaPlayers = new ArrayList(7);
    private float volume = 70.0f;
    private final Set<SoundFilePathWithSprite> recentlyStoppedSoundfilePaths = new HashSet();
    private final Map<String, MediaPlayerWithSoundDetails> preparedSounds = new HashMap();
    private final Map<String, Float> soundVolumes = new HashMap();

    public static SoundManager getInstance() {
        return INSTANCE;
    }

    private String getVolumeKey(String soundFilePath, Sprite sprite) {
        return (sprite != null ? sprite.getSpriteId() : "global") + "::" + soundFilePath;
    }

    public synchronized float getVolumeForSound(String soundFilePath, Sprite sprite) {
        String volumeKey = getVolumeKey(soundFilePath, sprite);
        if (this.soundVolumes.containsKey(volumeKey)) {
            return this.soundVolumes.get(volumeKey).floatValue();
        }
        return this.volume;
    }

    public synchronized void playSoundFile(String soundFilePath, Sprite sprite) {
        playSoundFileWithStartTime(soundFilePath, sprite, 0);
    }

    /* JADX WARN: Code duplicated, block: B:5:0x0007 A[PHI: r0
      0x0007: PHI (r0v6 float) = (r0v0 float), (r0v1 float) binds: [B:4:0x0005, B:7:0x000c] A[DONT_GENERATE, DONT_INLINE]] */
    public synchronized void setVolumeForSound(String soundFilePath, Sprite sprite, float volume) {
        float f = 100.0f;
        if (volume > 100.0f) {
            volume = f;
        } else {
            f = 0.0f;
            if (volume < 0.0f) {
                volume = f;
            }
        }
        this.soundVolumes.put(getVolumeKey(soundFilePath, sprite), Float.valueOf(volume));
        float f2 = volume * 0.01f;
        for (MediaPlayerWithSoundDetails mediaPlayerWithSoundDetails : this.mediaPlayers) {
            if (mediaPlayerWithSoundDetails.isPlaying() && mediaPlayerWithSoundDetails.getStartedBySprite() == sprite && mediaPlayerWithSoundDetails.getPathToSoundFile().equals(soundFilePath)) {
                mediaPlayerWithSoundDetails.setVolume(f2, f2);
            }
        }
    }

    public synchronized void playSoundFileWithStartTime(String soundFilePath, Sprite sprite, int startTimeInMilSeconds) {
        stopSameSoundInSprite(soundFilePath, sprite);
        MediaPlayerWithSoundDetails availableMediaPlayer = getAvailableMediaPlayer();
        if (availableMediaPlayer != null) {
            try {
                availableMediaPlayer.setStartedBySprite(sprite);
                availableMediaPlayer.setPathToSoundFile(soundFilePath);
                availableMediaPlayer.setDataSource(soundFilePath);
                availableMediaPlayer.prepare();
                availableMediaPlayer.seekTo(startTimeInMilSeconds);
                float volumeForSound = getVolumeForSound(soundFilePath, sprite) * 0.01f;
                availableMediaPlayer.setVolume(volumeForSound, volumeForSound);
                availableMediaPlayer.start();
            } catch (Exception e) {
                Log.e(TAG, "Couldn't play sound file '" + soundFilePath + FormatHelper.QUOTE, e);
            }
        }
    }

    public synchronized void stopSameSoundInSprite(String pathToSoundFile, Sprite sprite) {
        for (MediaPlayerWithSoundDetails mediaPlayerWithSoundDetails : this.mediaPlayers) {
            if (mediaPlayerWithSoundDetails.isPlaying() && mediaPlayerWithSoundDetails.getStartedBySprite() == sprite && mediaPlayerWithSoundDetails.getPathToSoundFile().equals(pathToSoundFile)) {
                mediaPlayerWithSoundDetails.stop();
                this.recentlyStoppedSoundfilePaths.add(new SoundFilePathWithSprite(mediaPlayerWithSoundDetails.getPathToSoundFile(), sprite));
            }
        }
    }

    public synchronized Set<SoundFilePathWithSprite> getRecentlyStoppedSoundfilePaths() {
        return this.recentlyStoppedSoundfilePaths;
    }

    public synchronized float getDurationOfSoundFile(String pathToSoundfile) {
        float duration;
        MediaPlayerWithSoundDetails availableMediaPlayer = getAvailableMediaPlayer();
        duration = 0.0f;
        if (availableMediaPlayer != null) {
            try {
                availableMediaPlayer.setDataSource(pathToSoundfile);
                availableMediaPlayer.prepare();
                duration = availableMediaPlayer.getDuration();
                availableMediaPlayer.stop();
            } catch (Exception e) {
                Log.e(TAG, "Couldn't play sound file '" + pathToSoundfile + FormatHelper.QUOTE, e);
            }
        }
        return duration;
    }

    private MediaPlayerWithSoundDetails getAvailableMediaPlayer() {
        for (MediaPlayerWithSoundDetails mediaPlayerWithSoundDetails : this.mediaPlayers) {
            if (!mediaPlayerWithSoundDetails.isPlaying()) {
                mediaPlayerWithSoundDetails.reset();
                return mediaPlayerWithSoundDetails;
            }
        }
        if (this.mediaPlayers.size() < 7) {
            MediaPlayerWithSoundDetails mediaPlayerWithSoundDetails2 = new MediaPlayerWithSoundDetails();
            this.mediaPlayers.add(mediaPlayerWithSoundDetails2);
            float f = this.volume * 0.01f;
            mediaPlayerWithSoundDetails2.setVolume(f, f);
            return mediaPlayerWithSoundDetails2;
        }
        Log.d(TAG, "All MediaPlayer instances in use");
        return null;
    }

    /* JADX WARN: Code duplicated, block: B:5:0x0007 A[PHI: r0
      0x0007: PHI (r0v3 float) = (r0v0 float), (r0v1 float) binds: [B:4:0x0005, B:7:0x000c] A[DONT_GENERATE, DONT_INLINE]] */
    public synchronized void setVolume(float volume) {
        float f = 100.0f;
        if (volume > 100.0f) {
            volume = f;
        } else {
            f = 0.0f;
            if (volume < 0.0f) {
                volume = f;
            }
        }
        this.volume = volume;
        float f2 = volume * 0.01f;
        for (MediaPlayerWithSoundDetails mediaPlayerWithSoundDetails : this.mediaPlayers) {
            if (mediaPlayerWithSoundDetails.isPlaying()) {
                float volumeForSound = getVolumeForSound(mediaPlayerWithSoundDetails.getPathToSoundFile(), mediaPlayerWithSoundDetails.getStartedBySprite()) * 0.01f;
                mediaPlayerWithSoundDetails.setVolume(volumeForSound, volumeForSound);
            } else {
                mediaPlayerWithSoundDetails.setVolume(f2, f2);
            }
        }
    }

    public synchronized float getVolume() {
        return this.volume;
    }

    public synchronized void clear() {
        for (MediaPlayerWithSoundDetails mediaPlayerWithSoundDetails : this.mediaPlayers) {
            mediaPlayerWithSoundDetails.reset();
            mediaPlayerWithSoundDetails.release();
        }
        this.mediaPlayers.clear();
        Iterator<MediaPlayerWithSoundDetails> it = this.preparedSounds.values().iterator();
        while (it.hasNext()) {
            it.next().release();
        }
        this.preparedSounds.clear();
        this.recentlyStoppedSoundfilePaths.clear();
        this.soundVolumes.clear();
    }

    public synchronized void pause() {
        for (MediaPlayerWithSoundDetails mediaPlayerWithSoundDetails : this.mediaPlayers) {
            if (mediaPlayerWithSoundDetails.isPlaying()) {
                mediaPlayerWithSoundDetails.pause();
            } else {
                mediaPlayerWithSoundDetails.reset();
            }
        }
    }

    public synchronized void resume() {
        for (MediaPlayerWithSoundDetails mediaPlayerWithSoundDetails : this.mediaPlayers) {
            if (!mediaPlayerWithSoundDetails.isPlaying()) {
                mediaPlayerWithSoundDetails.start();
            }
        }
    }

    public synchronized void stopAllSounds() {
        for (MediaPlayerWithSoundDetails mediaPlayerWithSoundDetails : this.mediaPlayers) {
            if (mediaPlayerWithSoundDetails.isPlaying()) {
                mediaPlayerWithSoundDetails.stop();
            }
        }
    }

    public List<SoundBackup> getPlayingSoundBackups() {
        ArrayList arrayList = new ArrayList();
        for (MediaPlayerWithSoundDetails mediaPlayerWithSoundDetails : this.mediaPlayers) {
            if (mediaPlayerWithSoundDetails.isPlaying()) {
                arrayList.add(new SoundBackup(mediaPlayerWithSoundDetails.getPathToSoundFile(), mediaPlayerWithSoundDetails.getStartedBySprite(), mediaPlayerWithSoundDetails.getCurrentPosition()));
            }
        }
        return arrayList;
    }

    public synchronized boolean prepareSound(String cacheName, String soundFilePath, Sprite sprite) {
        MediaPlayerWithSoundDetails mediaPlayerWithSoundDetails;
        if (this.preparedSounds.containsKey(cacheName) && (mediaPlayerWithSoundDetails = this.preparedSounds.get(cacheName)) != null) {
            mediaPlayerWithSoundDetails.release();
        }
        try {
            MediaPlayerWithSoundDetails mediaPlayerWithSoundDetails2 = new MediaPlayerWithSoundDetails();
            mediaPlayerWithSoundDetails2.setStartedBySprite(sprite);
            mediaPlayerWithSoundDetails2.setPathToSoundFile(soundFilePath);
            mediaPlayerWithSoundDetails2.setDataSource(soundFilePath);
            mediaPlayerWithSoundDetails2.prepare();
            float f = this.volume * 0.01f;
            mediaPlayerWithSoundDetails2.setVolume(f, f);
            this.preparedSounds.put(cacheName, mediaPlayerWithSoundDetails2);
        } catch (Exception e) {
            Log.e(TAG, "Couldn't prepare sound file '" + soundFilePath + "' for cache name '" + cacheName + FormatHelper.QUOTE, e);
            return false;
        }
        return true;
    }

    public synchronized boolean playSoundFromCache(String cacheName) {
        MediaPlayerWithSoundDetails mediaPlayerWithSoundDetails = this.preparedSounds.get(cacheName);
        if (mediaPlayerWithSoundDetails == null) {
            Log.e(TAG, "No prepared sound found for cache name: " + cacheName);
            return false;
        }
        if (mediaPlayerWithSoundDetails.isPlaying()) {
            mediaPlayerWithSoundDetails.stop();
            try {
                mediaPlayerWithSoundDetails.prepare();
            } catch (Exception e) {
                Log.e(TAG, "Could not re-prepare sound from cache", e);
                return false;
            }
        }
        mediaPlayerWithSoundDetails.seekTo(0);
        mediaPlayerWithSoundDetails.start();
        return true;
    }

    public List<MediaPlayerWithSoundDetails> getMediaPlayers() {
        return this.mediaPlayers;
    }
}

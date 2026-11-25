package org.catrobat.catroid.content.actions;

import java.util.Set;
import org.catrobat.catroid.content.MediaPlayerWithSoundDetails;
import org.catrobat.catroid.content.SoundFilePathWithSprite;
import org.catrobat.catroid.io.SoundManager;
import org.catrobat.catroid.pocketmusic.mididriver.MidiPlayer;
import org.catrobat.catroid.pocketmusic.mididriver.MidiSoundManager;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/WaitForSoundAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class WaitForSoundAction extends WaitAction {
    public static final String TAG = "WaitForSoundAction";
    private String soundFilePath;
    private SoundManager soundManager = SoundManager.getInstance();
    private MidiSoundManager midiSoundManager = MidiSoundManager.getInstance();
    private boolean soundStopped = false;

    public void setSoundFilePath(String soundFilePath) {
        this.soundFilePath = soundFilePath;
    }

    @Override // org.catrobat.catroid.content.actions.WaitAction, com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        if (this.soundFilePath != null && !this.midiSoundManager.getStartedSoundfilePaths().isEmpty()) {
            SoundFilePathWithSprite soundFilePathWithSprite = new SoundFilePathWithSprite(this.soundFilePath, this.scope.getSprite());
            Set<SoundFilePathWithSprite> startedSoundfilePaths = this.midiSoundManager.getStartedSoundfilePaths();
            if (startedSoundfilePaths.contains(soundFilePathWithSprite) && !this.midiSoundManager.isSoundInSpritePlaying(this.scope.getSprite(), this.soundFilePath)) {
                startedSoundfilePaths.remove(soundFilePathWithSprite);
                finish();
                this.soundStopped = true;
                return;
            }
        }
        if (this.soundFilePath == null || this.soundManager.getRecentlyStoppedSoundfilePaths().isEmpty()) {
            return;
        }
        SoundFilePathWithSprite soundFilePathWithSprite2 = new SoundFilePathWithSprite(this.soundFilePath, this.scope.getSprite());
        Set<SoundFilePathWithSprite> recentlyStoppedSoundfilePaths = this.soundManager.getRecentlyStoppedSoundfilePaths();
        if (recentlyStoppedSoundfilePaths.contains(soundFilePathWithSprite2)) {
            recentlyStoppedSoundfilePaths.remove(soundFilePathWithSprite2);
            finish();
            this.soundStopped = true;
        }
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void end() {
        for (MediaPlayerWithSoundDetails mediaPlayerWithSoundDetails : this.soundManager.getMediaPlayers()) {
            if (mediaPlayerWithSoundDetails.isPlaying() && mediaPlayerWithSoundDetails.getStartedBySprite() == this.scope.getSprite() && mediaPlayerWithSoundDetails.getPathToSoundFile().equals(this.soundFilePath) && !this.soundStopped) {
                restart();
                setTime(mediaPlayerWithSoundDetails.getCurrentPosition());
            }
        }
        for (MidiPlayer midiPlayer : this.midiSoundManager.getMidiPlayers()) {
            if (midiPlayer.isPlaying() && midiPlayer.getStartedBySprite() == this.scope.getSprite() && midiPlayer.getPathToSoundFile().equals(this.soundFilePath) && !this.soundStopped) {
                restart();
                setTime(midiPlayer.getCurrentPosition());
            }
        }
    }

    public void setSoundManager(SoundManager soundManager) {
        this.soundManager = soundManager;
    }

    public void setMidiSoundManager(MidiSoundManager midiSoundManager) {
        this.midiSoundManager = midiSoundManager;
    }
}

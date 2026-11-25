package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.io.SoundManager;
import org.catrobat.catroid.pocketmusic.mididriver.MidiSoundManager;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/StopAllSoundsAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class StopAllSoundsAction extends TemporalAction {
    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        SoundManager.getInstance().stopAllSounds();
        MidiSoundManager.getInstance().stopAllSounds();
    }
}

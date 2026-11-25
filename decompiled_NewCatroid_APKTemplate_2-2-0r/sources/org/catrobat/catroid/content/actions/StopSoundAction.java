package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import java.io.File;
import kotlin.Metadata;
import org.catrobat.catroid.common.SoundInfo;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.io.SoundManager;
import org.catrobat.catroid.pocketmusic.mididriver.MidiSoundManager;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/StopSoundAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: StopSoundAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e¨\u0006\u0013"}, d2 = {"Lorg/catrobat/catroid/content/actions/StopSoundAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "sound", "Lorg/catrobat/catroid/common/SoundInfo;", "getSound", "()Lorg/catrobat/catroid/common/SoundInfo;", "setSound", "(Lorg/catrobat/catroid/common/SoundInfo;)V", "sprite", "Lorg/catrobat/catroid/content/Sprite;", "getSprite", "()Lorg/catrobat/catroid/content/Sprite;", "setSprite", "(Lorg/catrobat/catroid/content/Sprite;)V", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class StopSoundAction extends TemporalAction {
    private SoundInfo sound;
    private Sprite sprite;

    public final Sprite getSprite() {
        return this.sprite;
    }

    public final void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public final SoundInfo getSound() {
        return this.sound;
    }

    public final void setSound(SoundInfo soundInfo) {
        this.sound = soundInfo;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        File file;
        SoundInfo soundInfo = this.sound;
        if (soundInfo == null || (file = soundInfo.getFile()) == null) {
            return;
        }
        SoundManager.getInstance().stopSameSoundInSprite(file.getAbsolutePath(), this.sprite);
        MidiSoundManager.getInstance().stopSameSoundInSprite(file.getAbsolutePath(), this.sprite);
    }
}

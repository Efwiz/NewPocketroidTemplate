package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import kotlin.Metadata;
import org.catrobat.catroid.common.SoundInfo;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;
import org.catrobat.catroid.io.SoundManager;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/SetSoundVolumeAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: SetSoundVolumeAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014¨\u0006\u0019"}, d2 = {"Lorg/catrobat/catroid/content/actions/SetSoundVolumeAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "sound", "Lorg/catrobat/catroid/common/SoundInfo;", "getSound", "()Lorg/catrobat/catroid/common/SoundInfo;", "setSound", "(Lorg/catrobat/catroid/common/SoundInfo;)V", "volume", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getVolume", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setVolume", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SetSoundVolumeAction extends TemporalAction {
    private Scope scope;
    private SoundInfo sound;
    private Formula volume;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final SoundInfo getSound() {
        return this.sound;
    }

    public final void setSound(SoundInfo soundInfo) {
        this.sound = soundInfo;
    }

    public final Formula getVolume() {
        return this.volume;
    }

    public final void setVolume(Formula formula) {
        this.volume = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        Scope scope = this.scope;
        Sprite sprite = scope != null ? scope.getSprite() : null;
        SoundInfo soundInfo = this.sound;
        if (sprite == null || soundInfo == null) {
            return;
        }
        try {
            Formula formula = this.volume;
            Float fInterpretFloat = formula != null ? formula.interpretFloat(this.scope) : null;
            SoundManager.getInstance().setVolumeForSound(soundInfo.getFile().getAbsolutePath(), sprite, fInterpretFloat == null ? 100.0f : fInterpretFloat.floatValue());
        } catch (InterpretationException e) {
            Log.d(getClass().getSimpleName(), "Formula interpretation failed.", e);
        }
    }
}

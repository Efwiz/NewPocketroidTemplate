package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import kotlin.Metadata;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.raptor.ThreeDManager;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.stage.StageListener;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/PlaySoundAtPositionAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: PlaySoundAtPositionAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020*H\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0006\"\u0004\b\u0011\u0010\bR\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0006\"\u0004\b\u0014\u0010\bR\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0006\"\u0004\b\u0017\u0010\bR\u001c\u0010\u0018\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u0006\"\u0004\b\u001a\u0010\bR\u001c\u0010\u001b\u001a\u0004\u0018\u00010\u001cX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R\u001c\u0010!\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\u0006\"\u0004\b#\u0010\bR\u001c\u0010$\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b%\u0010\u0006\"\u0004\b&\u0010\b¨\u0006+"}, d2 = {"Lorg/catrobat/catroid/content/actions/PlaySoundAtPositionAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "instanceName", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getInstanceName", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setInstanceName", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "loop", "", "getLoop", "()Z", "setLoop", "(Z)V", "pitch", "getPitch", "setPitch", "posX", "getPosX", "setPosX", "posY", "getPosY", "setPosY", "posZ", "getPosZ", "setPosZ", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "soundName", "getSoundName", "setSoundName", "volume", "getVolume", "setVolume", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PlaySoundAtPositionAction extends TemporalAction {
    private Formula instanceName;
    private boolean loop;
    private Formula pitch;
    private Formula posX;
    private Formula posY;
    private Formula posZ;
    private Scope scope;
    private Formula soundName;
    private Formula volume;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getSoundName() {
        return this.soundName;
    }

    public final void setSoundName(Formula formula) {
        this.soundName = formula;
    }

    public final Formula getInstanceName() {
        return this.instanceName;
    }

    public final void setInstanceName(Formula formula) {
        this.instanceName = formula;
    }

    public final Formula getPosX() {
        return this.posX;
    }

    public final void setPosX(Formula formula) {
        this.posX = formula;
    }

    public final Formula getPosY() {
        return this.posY;
    }

    public final void setPosY(Formula formula) {
        this.posY = formula;
    }

    public final Formula getPosZ() {
        return this.posZ;
    }

    public final void setPosZ(Formula formula) {
        this.posZ = formula;
    }

    public final Formula getVolume() {
        return this.volume;
    }

    public final void setVolume(Formula formula) {
        this.volume = formula;
    }

    public final Formula getPitch() {
        return this.pitch;
    }

    public final void setPitch(Formula formula) {
        this.pitch = formula;
    }

    public final boolean getLoop() {
        return this.loop;
    }

    public final void setLoop(boolean z) {
        this.loop = z;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        String str;
        Float fInterpretFloat;
        Float fInterpretFloat2;
        StageListener activeStageListener = StageActivity.getActiveStageListener();
        ThreeDManager threeDManager = activeStageListener != null ? activeStageListener.getThreeDManager() : null;
        if (threeDManager == null) {
            return;
        }
        Formula formula = this.soundName;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        Formula formula2 = this.instanceName;
        String strInterpretString2 = formula2 != null ? formula2.interpretString(this.scope) : null;
        String str2 = strInterpretString;
        if (str2 == null || str2.length() == 0 || (str = strInterpretString2) == null || str.length() == 0) {
            return;
        }
        Formula formula3 = this.posX;
        Float fInterpretFloat3 = formula3 != null ? formula3.interpretFloat(this.scope) : null;
        float fFloatValue = fInterpretFloat3 == null ? 0.0f : fInterpretFloat3.floatValue();
        Formula formula4 = this.posY;
        Float fInterpretFloat4 = formula4 != null ? formula4.interpretFloat(this.scope) : null;
        float fFloatValue2 = fInterpretFloat4 == null ? 0.0f : fInterpretFloat4.floatValue();
        Formula formula5 = this.posZ;
        Float fInterpretFloat5 = formula5 != null ? formula5.interpretFloat(this.scope) : null;
        float fFloatValue3 = fInterpretFloat5 == null ? 0.0f : fInterpretFloat5.floatValue();
        Formula formula6 = this.volume;
        float fFloatValue4 = ((formula6 == null || (fInterpretFloat2 = formula6.interpretFloat(this.scope)) == null) ? 100.0f : fInterpretFloat2.floatValue()) / 100.0f;
        Formula formula7 = this.pitch;
        threeDManager.playSoundAt(strInterpretString2, strInterpretString, fFloatValue, fFloatValue2, fFloatValue3, fFloatValue4, ((formula7 == null || (fInterpretFloat = formula7.interpretFloat(this.scope)) == null) ? 100.0f : fInterpretFloat.floatValue()) / 100.0f, this.loop);
    }
}

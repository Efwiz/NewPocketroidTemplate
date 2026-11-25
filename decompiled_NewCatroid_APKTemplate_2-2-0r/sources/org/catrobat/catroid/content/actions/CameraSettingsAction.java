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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/CameraSettingsAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: CameraSettingsAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0006\"\u0004\b\u0011\u0010\bR\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0006\"\u0004\b\u0014\u0010\b¨\u0006\u0019"}, d2 = {"Lorg/catrobat/catroid/content/actions/CameraSettingsAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "fov", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getFov", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setFov", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "shakeDuration", "getShakeDuration", "setShakeDuration", "shakeIntensity", "getShakeIntensity", "setShakeIntensity", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CameraSettingsAction extends TemporalAction {
    private Formula fov;
    private Scope scope;
    private Formula shakeDuration;
    private Formula shakeIntensity;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getFov() {
        return this.fov;
    }

    public final void setFov(Formula formula) {
        this.fov = formula;
    }

    public final Formula getShakeIntensity() {
        return this.shakeIntensity;
    }

    public final void setShakeIntensity(Formula formula) {
        this.shakeIntensity = formula;
    }

    public final Formula getShakeDuration() {
        return this.shakeDuration;
    }

    public final void setShakeDuration(Formula formula) {
        this.shakeDuration = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        Double dInterpretDouble;
        Double dInterpretDouble2;
        StageListener activeStageListener = StageActivity.getActiveStageListener();
        ThreeDManager threeDManager = activeStageListener != null ? activeStageListener.getThreeDManager() : null;
        if (threeDManager == null) {
            return;
        }
        Formula formula = this.fov;
        if (formula != null) {
            threeDManager.setCameraFov((float) formula.interpretDouble(this.scope).doubleValue());
        }
        Formula formula2 = this.shakeIntensity;
        float fDoubleValue = (formula2 == null || (dInterpretDouble2 = formula2.interpretDouble(this.scope)) == null) ? 0.0f : (float) dInterpretDouble2.doubleValue();
        Formula formula3 = this.shakeDuration;
        float fDoubleValue2 = (formula3 == null || (dInterpretDouble = formula3.interpretDouble(this.scope)) == null) ? 0.0f : (float) dInterpretDouble.doubleValue();
        if (fDoubleValue > 0.0f) {
            threeDManager.startCameraShake(fDoubleValue, fDoubleValue2);
        }
    }
}

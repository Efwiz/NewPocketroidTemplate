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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/RotateCameraByAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: RotateCameraByAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001c\u0010\f\u001a\u0004\u0018\u00010\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0006\"\u0004\b\u0014\u0010\b¨\u0006\u0019"}, d2 = {"Lorg/catrobat/catroid/content/actions/RotateCameraByAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "pitch", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getPitch", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setPitch", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "roll", "getRoll", "setRoll", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "yaw", "getYaw", "setYaw", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RotateCameraByAction extends TemporalAction {
    private Formula pitch;
    private Formula roll;
    private Scope scope;
    private Formula yaw;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getPitch() {
        return this.pitch;
    }

    public final void setPitch(Formula formula) {
        this.pitch = formula;
    }

    public final Formula getYaw() {
        return this.yaw;
    }

    public final void setYaw(Formula formula) {
        this.yaw = formula;
    }

    public final Formula getRoll() {
        return this.roll;
    }

    public final void setRoll(Formula formula) {
        this.roll = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        Double dInterpretDouble;
        Double dInterpretDouble2;
        Double dInterpretDouble3;
        StageListener activeStageListener = StageActivity.getActiveStageListener();
        ThreeDManager threeDManager = activeStageListener != null ? activeStageListener.getThreeDManager() : null;
        if (threeDManager == null) {
            return;
        }
        Formula formula = this.pitch;
        float fDoubleValue = 0.0f;
        float fDoubleValue2 = (formula == null || (dInterpretDouble3 = formula.interpretDouble(this.scope)) == null) ? 0.0f : (float) dInterpretDouble3.doubleValue();
        Formula formula2 = this.yaw;
        float fDoubleValue3 = (formula2 == null || (dInterpretDouble2 = formula2.interpretDouble(this.scope)) == null) ? 0.0f : (float) dInterpretDouble2.doubleValue();
        Formula formula3 = this.roll;
        if (formula3 != null && (dInterpretDouble = formula3.interpretDouble(this.scope)) != null) {
            fDoubleValue = (float) dInterpretDouble.doubleValue();
        }
        threeDManager.rotateCameraRelative(fDoubleValue2, fDoubleValue3, fDoubleValue);
    }
}

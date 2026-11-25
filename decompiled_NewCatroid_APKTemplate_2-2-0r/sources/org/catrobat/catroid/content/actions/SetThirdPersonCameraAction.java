package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import kotlin.Metadata;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.raptor.ThreeDManager;
import org.catrobat.catroid.stage.StageActivity;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/SetThirdPersonCameraAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: SetThirdPersonCameraAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001c\u0010\f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u0006\"\u0004\b\u000e\u0010\bR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0006\"\u0004\b\u0011\u0010\bR\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017¨\u0006\u001c"}, d2 = {"Lorg/catrobat/catroid/content/actions/SetThirdPersonCameraAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "distance", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getDistance", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setDistance", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "height", "getHeight", "setHeight", "objectId", "getObjectId", "setObjectId", "pitch", "getPitch", "setPitch", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SetThirdPersonCameraAction extends TemporalAction {
    private Formula distance;
    private Formula height;
    private Formula objectId;
    private Formula pitch;
    private Scope scope;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getObjectId() {
        return this.objectId;
    }

    public final void setObjectId(Formula formula) {
        this.objectId = formula;
    }

    public final Formula getDistance() {
        return this.distance;
    }

    public final void setDistance(Formula formula) {
        this.distance = formula;
    }

    public final Formula getHeight() {
        return this.height;
    }

    public final void setHeight(Formula formula) {
        this.height = formula;
    }

    public final Formula getPitch() {
        return this.pitch;
    }

    public final void setPitch(Formula formula) {
        this.pitch = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        Double dInterpretDouble;
        Double dInterpretDouble2;
        Double dInterpretDouble3;
        ThreeDManager threeDManager = StageActivity.getActiveStageListener().getThreeDManager();
        if (threeDManager == null) {
            return;
        }
        Formula formula = this.objectId;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        if (strInterpretString == null) {
            return;
        }
        Formula formula2 = this.distance;
        float fDoubleValue = (formula2 == null || (dInterpretDouble3 = formula2.interpretDouble(this.scope)) == null) ? 10.0f : (float) dInterpretDouble3.doubleValue();
        Formula formula3 = this.height;
        float fDoubleValue2 = (formula3 == null || (dInterpretDouble2 = formula3.interpretDouble(this.scope)) == null) ? 2.0f : (float) dInterpretDouble2.doubleValue();
        Formula formula4 = this.pitch;
        threeDManager.setThirdPersonCamera(strInterpretString, fDoubleValue, fDoubleValue2, (formula4 == null || (dInterpretDouble = formula4.interpretDouble(this.scope)) == null) ? 20.0f : (float) dInterpretDouble.doubleValue());
    }
}

package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import kotlin.Metadata;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.raptor.ThreeDManager;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.stage.StageListener;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/SetDirectionalLigh2tAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: SetDirectionalLight2Action.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001c\u0010\f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u0006\"\u0004\b\u000e\u0010\bR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0006\"\u0004\b\u0011\u0010\bR\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017¨\u0006\u001c"}, d2 = {"Lorg/catrobat/catroid/content/actions/SetDirectionalLigh2tAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "dirX", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getDirX", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setDirX", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "dirY", "getDirY", "setDirY", "dirZ", "getDirZ", "setDirZ", "intensity", "getIntensity", "setIntensity", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SetDirectionalLigh2tAction extends TemporalAction {
    private Formula dirX;
    private Formula dirY;
    private Formula dirZ;
    private Formula intensity;
    private Scope scope;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getDirX() {
        return this.dirX;
    }

    public final void setDirX(Formula formula) {
        this.dirX = formula;
    }

    public final Formula getDirY() {
        return this.dirY;
    }

    public final void setDirY(Formula formula) {
        this.dirY = formula;
    }

    public final Formula getDirZ() {
        return this.dirZ;
    }

    public final void setDirZ(Formula formula) {
        this.dirZ = formula;
    }

    public final Formula getIntensity() {
        return this.intensity;
    }

    public final void setIntensity(Formula formula) {
        this.intensity = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        StageListener stageListener;
        StageActivity stageActivity = StageActivity.activeStageActivity.get();
        ThreeDManager threeDManager = (stageActivity == null || (stageListener = stageActivity.stageListener) == null) ? null : stageListener.getThreeDManager();
        if (threeDManager == null) {
            return;
        }
        try {
            Formula formula = this.dirX;
            Float fInterpretFloat = formula != null ? formula.interpretFloat(this.scope) : null;
            float fFloatValue = fInterpretFloat == null ? -1.0f : fInterpretFloat.floatValue();
            Formula formula2 = this.dirY;
            Float fInterpretFloat2 = formula2 != null ? formula2.interpretFloat(this.scope) : null;
            float fFloatValue2 = fInterpretFloat2 == null ? -0.8f : fInterpretFloat2.floatValue();
            Formula formula3 = this.dirZ;
            Float fInterpretFloat3 = formula3 != null ? formula3.interpretFloat(this.scope) : null;
            float fFloatValue3 = fInterpretFloat3 == null ? -0.2f : fInterpretFloat3.floatValue();
            Formula formula4 = this.intensity;
            Float fInterpretFloat4 = formula4 != null ? formula4.interpretFloat(this.scope) : null;
            float fFloatValue4 = fInterpretFloat4 == null ? 5.0f : fInterpretFloat4.floatValue();
            Log.d("SetDirLight", "X: " + fFloatValue + "  Y: " + fFloatValue2 + "  Z: " + fFloatValue3 + "     Int: " + fFloatValue4);
            threeDManager.setRealisticSunLight(fFloatValue, fFloatValue2, fFloatValue3, fFloatValue4);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

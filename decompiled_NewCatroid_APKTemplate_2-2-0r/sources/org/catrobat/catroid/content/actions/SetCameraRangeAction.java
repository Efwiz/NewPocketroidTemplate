package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import kotlin.Metadata;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.raptor.ThreeDManager;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.stage.StageListener;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/SetCameraRangeAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: SetCameraRangeAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001c\u0010\f\u001a\u0004\u0018\u00010\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011¨\u0006\u0016"}, d2 = {"Lorg/catrobat/catroid/content/actions/SetCameraRangeAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "far", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getFar", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setFar", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "near", "getNear", "setNear", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SetCameraRangeAction extends TemporalAction {
    private Formula far;
    private Formula near;
    private Scope scope;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getNear() {
        return this.near;
    }

    public final void setNear(Formula formula) {
        this.near = formula;
    }

    public final Formula getFar() {
        return this.far;
    }

    public final void setFar(Formula formula) {
        this.far = formula;
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
            Formula formula = this.near;
            Float fInterpretFloat = formula != null ? formula.interpretFloat(this.scope) : null;
            float fFloatValue = fInterpretFloat == null ? 1.0f : fInterpretFloat.floatValue();
            Formula formula2 = this.far;
            Float fInterpretFloat2 = formula2 != null ? formula2.interpretFloat(this.scope) : null;
            float fFloatValue2 = fInterpretFloat2 == null ? 1000.0f : fInterpretFloat2.floatValue();
            Camera camera = threeDManager.getCamera();
            if (camera != null) {
                camera.near = fFloatValue;
                camera.far = fFloatValue2;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import kotlin.Metadata;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.raptor.SceneManager;
import org.catrobat.catroid.raptor.ThreeDManager;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.stage.StageListener;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/SetHingeMotorAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: SetHingeMotorAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001c\u0010\f\u001a\u0004\u0018\u00010\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0006\"\u0004\b\u0014\u0010\b¨\u0006\u0019"}, d2 = {"Lorg/catrobat/catroid/content/actions/SetHingeMotorAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "constraintId", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getConstraintId", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setConstraintId", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "maxForce", "getMaxForce", "setMaxForce", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "targetAngle", "getTargetAngle", "setTargetAngle", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SetHingeMotorAction extends TemporalAction {
    private Formula constraintId;
    private Formula maxForce;
    private Scope scope;
    private Formula targetAngle;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getConstraintId() {
        return this.constraintId;
    }

    public final void setConstraintId(Formula formula) {
        this.constraintId = formula;
    }

    public final Formula getTargetAngle() {
        return this.targetAngle;
    }

    public final void setTargetAngle(Formula formula) {
        this.targetAngle = formula;
    }

    public final Formula getMaxForce() {
        return this.maxForce;
    }

    public final void setMaxForce(Formula formula) {
        this.maxForce = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        SceneManager sceneManager;
        Formula formula = this.constraintId;
        ThreeDManager threeDManager = null;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        if (strInterpretString == null) {
            strInterpretString = "hinge1";
        }
        Formula formula2 = this.targetAngle;
        Float fInterpretFloat = formula2 != null ? formula2.interpretFloat(this.scope) : null;
        float fFloatValue = fInterpretFloat == null ? 0.0f : fInterpretFloat.floatValue();
        Formula formula3 = this.maxForce;
        Float fInterpretFloat2 = formula3 != null ? formula3.interpretFloat(this.scope) : null;
        float fFloatValue2 = fInterpretFloat2 == null ? 10.0f : fInterpretFloat2.floatValue();
        StageListener activeStageListener = StageActivity.getActiveStageListener();
        if (activeStageListener != null && (sceneManager = activeStageListener.sceneManager) != null) {
            threeDManager = sceneManager.engine;
        }
        if (threeDManager != null) {
            threeDManager.setHingeMotorTarget(strInterpretString, fFloatValue, fFloatValue2);
        }
    }
}

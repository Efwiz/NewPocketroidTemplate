package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import kotlin.Metadata;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.raptor.AIComponent;
import org.catrobat.catroid.raptor.ThreeDManager;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.stage.StageListener;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/SetAIAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: SetAIAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020'H\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001c\u0010\f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u0006\"\u0004\b\u000e\u0010\bR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0006\"\u0004\b\u0011\u0010\bR\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u001c\u0010\u0018\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u0006\"\u0004\b\u001a\u0010\bR\u001c\u0010\u001b\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u0006\"\u0004\b\u001d\u0010\bR\u001c\u0010\u001e\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\u0006\"\u0004\b \u0010\bR\u001c\u0010!\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\u0006\"\u0004\b#\u0010\b¨\u0006("}, d2 = {"Lorg/catrobat/catroid/content/actions/SetAIAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "avoid", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getAvoid", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setAvoid", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "mode", "getMode", "setMode", "objectId", "getObjectId", "setObjectId", "range", "getRange", "setRange", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "speed", "getSpeed", "setSpeed", "step", "getStep", "setStep", "stopDist", "getStopDist", "setStopDist", "targetId", "getTargetId", "setTargetId", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SetAIAction extends TemporalAction {
    private Formula avoid;
    private Formula mode;
    private Formula objectId;
    private Formula range;
    private Scope scope;
    private Formula speed;
    private Formula step;
    private Formula stopDist;
    private Formula targetId;

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

    public final Formula getMode() {
        return this.mode;
    }

    public final void setMode(Formula formula) {
        this.mode = formula;
    }

    public final Formula getTargetId() {
        return this.targetId;
    }

    public final void setTargetId(Formula formula) {
        this.targetId = formula;
    }

    public final Formula getSpeed() {
        return this.speed;
    }

    public final void setSpeed(Formula formula) {
        this.speed = formula;
    }

    public final Formula getStopDist() {
        return this.stopDist;
    }

    public final void setStopDist(Formula formula) {
        this.stopDist = formula;
    }

    public final Formula getRange() {
        return this.range;
    }

    public final void setRange(Formula formula) {
        this.range = formula;
    }

    public final Formula getStep() {
        return this.step;
    }

    public final void setStep(Formula formula) {
        this.step = formula;
    }

    public final Formula getAvoid() {
        return this.avoid;
    }

    public final void setAvoid(Formula formula) {
        this.avoid = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        AIComponent.Mode mode;
        String strInterpretString;
        Double dInterpretDouble;
        Double dInterpretDouble2;
        Double dInterpretDouble3;
        Double dInterpretDouble4;
        Double dInterpretDouble5;
        StageListener activeStageListener = StageActivity.getActiveStageListener();
        ThreeDManager threeDManager = activeStageListener != null ? activeStageListener.getThreeDManager() : null;
        if (threeDManager == null) {
            return;
        }
        Formula formula = this.objectId;
        String strInterpretString2 = formula != null ? formula.interpretString(this.scope) : null;
        if (strInterpretString2 == null) {
            return;
        }
        AIComponent aIComponent = new AIComponent();
        Formula formula2 = this.mode;
        int iDoubleValue = (formula2 == null || (dInterpretDouble5 = formula2.interpretDouble(this.scope)) == null) ? 0 : (int) dInterpretDouble5.doubleValue();
        if (iDoubleValue == 1) {
            mode = AIComponent.Mode.FOLLOW;
        } else if (iDoubleValue == 2) {
            mode = AIComponent.Mode.MOVE_TO;
        } else {
            mode = AIComponent.Mode.OFF;
        }
        aIComponent.mode = mode;
        Formula formula3 = this.targetId;
        if (formula3 == null || (strInterpretString = formula3.interpretString(this.scope)) == null) {
            strInterpretString = "";
        }
        aIComponent.targetId = strInterpretString;
        Formula formula4 = this.speed;
        float fDoubleValue = 5.0f;
        aIComponent.speed = (formula4 == null || (dInterpretDouble4 = formula4.interpretDouble(this.scope)) == null) ? 5.0f : (float) dInterpretDouble4.doubleValue();
        Formula formula5 = this.stopDist;
        aIComponent.stopDistance = (formula5 == null || (dInterpretDouble3 = formula5.interpretDouble(this.scope)) == null) ? 1.2f : (float) dInterpretDouble3.doubleValue();
        Formula formula6 = this.range;
        if (formula6 != null && (dInterpretDouble2 = formula6.interpretDouble(this.scope)) != null) {
            fDoubleValue = (float) dInterpretDouble2.doubleValue();
        }
        aIComponent.detectionRange = fDoubleValue;
        Formula formula7 = this.step;
        aIComponent.stepHeight = (formula7 == null || (dInterpretDouble = formula7.interpretDouble(this.scope)) == null) ? 0.7f : (float) dInterpretDouble.doubleValue();
        Formula formula8 = this.avoid;
        Double dInterpretDouble6 = formula8 != null ? formula8.interpretDouble(this.scope) : null;
        aIComponent.avoidObstacles = (dInterpretDouble6 == null ? 1.0d : dInterpretDouble6.doubleValue()) > 0.5d;
        threeDManager.setAI(strInterpretString2, aIComponent);
    }
}

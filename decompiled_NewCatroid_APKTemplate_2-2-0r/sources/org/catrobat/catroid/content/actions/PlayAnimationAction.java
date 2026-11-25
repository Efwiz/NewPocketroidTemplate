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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/PlayAnimationAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: PlayAnimationAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eH\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001c\u0010\f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u0006\"\u0004\b\u000e\u0010\bR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0006\"\u0004\b\u0017\u0010\bR\u001c\u0010\u0018\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u0006\"\u0004\b\u001a\u0010\b¨\u0006\u001f"}, d2 = {"Lorg/catrobat/catroid/content/actions/PlayAnimationAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "animationName", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getAnimationName", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setAnimationName", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "loops", "getLoops", "setLoops", "objectId", "getObjectId", "setObjectId", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "speed", "getSpeed", "setSpeed", "transitionTime", "getTransitionTime", "setTransitionTime", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PlayAnimationAction extends TemporalAction {
    private Formula animationName;
    private Formula loops;
    private Formula objectId;
    private Scope scope;
    private Formula speed;
    private Formula transitionTime;

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

    public final Formula getAnimationName() {
        return this.animationName;
    }

    public final void setAnimationName(Formula formula) {
        this.animationName = formula;
    }

    public final Formula getLoops() {
        return this.loops;
    }

    public final void setLoops(Formula formula) {
        this.loops = formula;
    }

    public final Formula getSpeed() {
        return this.speed;
    }

    public final void setSpeed(Formula formula) {
        this.speed = formula;
    }

    public final Formula getTransitionTime() {
        return this.transitionTime;
    }

    public final void setTransitionTime(Formula formula) {
        this.transitionTime = formula;
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
            Formula formula = this.objectId;
            String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
            if (strInterpretString == null) {
                strInterpretString = "";
            }
            if (strInterpretString.length() == 0) {
                return;
            }
            Formula formula2 = this.animationName;
            String strInterpretString2 = formula2 != null ? formula2.interpretString(this.scope) : null;
            if (strInterpretString2 == null) {
                strInterpretString2 = "";
            }
            Formula formula3 = this.loops;
            Integer numInterpretInteger = formula3 != null ? formula3.interpretInteger(this.scope) : null;
            int iIntValue = numInterpretInteger == null ? -1 : numInterpretInteger.intValue();
            Formula formula4 = this.speed;
            Float fInterpretFloat = formula4 != null ? formula4.interpretFloat(this.scope) : null;
            float fFloatValue = fInterpretFloat == null ? 1.0f : fInterpretFloat.floatValue();
            Formula formula5 = this.transitionTime;
            Float fInterpretFloat2 = formula5 != null ? formula5.interpretFloat(this.scope) : null;
            threeDManager.playAnimation(strInterpretString, strInterpretString2, iIntValue, fFloatValue, fInterpretFloat2 == null ? 0.2f : fInterpretFloat2.floatValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

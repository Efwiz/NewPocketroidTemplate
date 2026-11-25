package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import kotlin.Metadata;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.fast2d.FastTwoDManager;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.stage.StageListener;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/Fast2DSetScaleAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: Fast2DSetScaleAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0006\"\u0004\b\u0011\u0010\bR\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0006\"\u0004\b\u0014\u0010\b¨\u0006\u0019"}, d2 = {"Lorg/catrobat/catroid/content/actions/Fast2DSetScaleAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "entityId", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getEntityId", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setEntityId", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "sx", "getSx", "setSx", "sy", "getSy", "setSy", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Fast2DSetScaleAction extends TemporalAction {
    private Formula entityId;
    private Scope scope;
    private Formula sx;
    private Formula sy;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getEntityId() {
        return this.entityId;
    }

    public final void setEntityId(Formula formula) {
        this.entityId = formula;
    }

    public final Formula getSx() {
        return this.sx;
    }

    public final void setSx(Formula formula) {
        this.sx = formula;
    }

    public final Formula getSy() {
        return this.sy;
    }

    public final void setSy(Formula formula) {
        this.sy = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        StageListener stageListener;
        FastTwoDManager fastTwoDManager;
        Formula formula = this.entityId;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        if (strInterpretString == null) {
            return;
        }
        Formula formula2 = this.sx;
        Float fInterpretFloat = formula2 != null ? formula2.interpretFloat(this.scope) : null;
        float fFloatValue = fInterpretFloat == null ? 1.0f : fInterpretFloat.floatValue();
        Formula formula3 = this.sy;
        Float fInterpretFloat2 = formula3 != null ? formula3.interpretFloat(this.scope) : null;
        float fFloatValue2 = fInterpretFloat2 != null ? fInterpretFloat2.floatValue() : 1.0f;
        StageActivity stageActivity = StageActivity.activeStageActivity.get();
        if (stageActivity == null || (stageListener = stageActivity.stageListener) == null || (fastTwoDManager = stageListener.fastTwoDManager) == null) {
            return;
        }
        fastTwoDManager.setScale(strInterpretString, fFloatValue, fFloatValue2);
    }
}

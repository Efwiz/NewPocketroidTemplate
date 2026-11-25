package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import kotlin.Metadata;
import kotlin.ranges.RangesKt;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.raptor.ThreeDManager;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.stage.StageListener;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/SetShadowQualityAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: SetShadowQualityAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0006\"\u0004\b\u0011\u0010\b¨\u0006\u0016"}, d2 = {"Lorg/catrobat/catroid/content/actions/SetShadowQualityAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "resolutionFormula", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getResolutionFormula", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setResolutionFormula", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "sizeFormula", "getSizeFormula", "setSizeFormula", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SetShadowQualityAction extends TemporalAction {
    private Formula resolutionFormula;
    private Scope scope;
    private Formula sizeFormula;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getSizeFormula() {
        return this.sizeFormula;
    }

    public final void setSizeFormula(Formula formula) {
        this.sizeFormula = formula;
    }

    public final Formula getResolutionFormula() {
        return this.resolutionFormula;
    }

    public final void setResolutionFormula(Formula formula) {
        this.resolutionFormula = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        ThreeDManager threeDManager;
        Float fInterpretFloat;
        StageListener activeStageListener = StageActivity.getActiveStageListener();
        if (activeStageListener == null || (threeDManager = activeStageListener.getThreeDManager()) == null) {
            return;
        }
        Formula formula = this.sizeFormula;
        Float fInterpretFloat2 = formula != null ? formula.interpretFloat(this.scope) : null;
        float fFloatValue = fInterpretFloat2 == null ? 100.0f : fInterpretFloat2.floatValue();
        Formula formula2 = this.resolutionFormula;
        threeDManager.setShadowSettings(RangesKt.coerceIn(fFloatValue, 1.0f, 2000.0f), RangesKt.coerceIn((formula2 == null || (fInterpretFloat = formula2.interpretFloat(this.scope)) == null) ? 2048 : (int) fInterpretFloat.floatValue(), 128, 4096));
    }
}

package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import kotlin.Metadata;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.stage.StageActivity;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/VmRelativeMouseMoveAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: VmRelativeMouseMoveAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001c\u0010\f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u0006\"\u0004\b\u000e\u0010\bR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014¨\u0006\u0019"}, d2 = {"Lorg/catrobat/catroid/content/actions/VmRelativeMouseMoveAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "dxFormula", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getDxFormula", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setDxFormula", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "dyFormula", "getDyFormula", "setDyFormula", "maskFormula", "getMaskFormula", "setMaskFormula", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class VmRelativeMouseMoveAction extends TemporalAction {
    private Formula dxFormula;
    private Formula dyFormula;
    private Formula maskFormula;
    private Scope scope;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getDxFormula() {
        return this.dxFormula;
    }

    public final void setDxFormula(Formula formula) {
        this.dxFormula = formula;
    }

    public final Formula getDyFormula() {
        return this.dyFormula;
    }

    public final void setDyFormula(Formula formula) {
        this.dyFormula = formula;
    }

    public final Formula getMaskFormula() {
        return this.maskFormula;
    }

    public final void setMaskFormula(Formula formula) {
        this.maskFormula = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        Formula formula = this.dxFormula;
        Float fInterpretFloat = formula != null ? formula.interpretFloat(this.scope) : null;
        float fFloatValue = fInterpretFloat == null ? 0.0f : fInterpretFloat.floatValue();
        Formula formula2 = this.dyFormula;
        Float fInterpretFloat2 = formula2 != null ? formula2.interpretFloat(this.scope) : null;
        float fFloatValue2 = fInterpretFloat2 != null ? fInterpretFloat2.floatValue() : 0.0f;
        Formula formula3 = this.maskFormula;
        Integer numInterpretInteger = formula3 != null ? formula3.interpretInteger(this.scope) : null;
        int iIntValue = numInterpretInteger == null ? 0 : numInterpretInteger.intValue();
        StageActivity stageActivity = StageActivity.activeStageActivity.get();
        if (stageActivity != null) {
            stageActivity.moveVmMouseRelative(fFloatValue, fFloatValue2, iIntValue);
        }
    }
}

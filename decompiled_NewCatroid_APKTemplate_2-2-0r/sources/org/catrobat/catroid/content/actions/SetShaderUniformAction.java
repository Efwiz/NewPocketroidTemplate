package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;
import org.catrobat.catroid.raptor.ThreeDManager;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.stage.StageListener;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/SetShaderUniformAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: SetShaderUniformAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0014R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\f\"\u0004\b\u0011\u0010\u000eR\u001c\u0010\u0012\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\f\"\u0004\b\u0014\u0010\u000eR\u001c\u0010\u0015\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\f\"\u0004\b\u0017\u0010\u000e¨\u0006\u001c"}, d2 = {"Lorg/catrobat/catroid/content/actions/SetShaderUniformAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "uniformName", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getUniformName", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setUniformName", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "valueX", "getValueX", "setValueX", "valueY", "getValueY", "setValueY", "valueZ", "getValueZ", "setValueZ", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SetShaderUniformAction extends TemporalAction {
    public Scope scope;
    private Formula uniformName;
    private Formula valueX;
    private Formula valueY;
    private Formula valueZ;

    public final Scope getScope() {
        Scope scope = this.scope;
        if (scope != null) {
            return scope;
        }
        Intrinsics.throwUninitializedPropertyAccessException("scope");
        return null;
    }

    public final void setScope(Scope scope) {
        Intrinsics.checkNotNullParameter(scope, "<set-?>");
        this.scope = scope;
    }

    public final Formula getUniformName() {
        return this.uniformName;
    }

    public final void setUniformName(Formula formula) {
        this.uniformName = formula;
    }

    public final Formula getValueX() {
        return this.valueX;
    }

    public final void setValueX(Formula formula) {
        this.valueX = formula;
    }

    public final Formula getValueY() {
        return this.valueY;
    }

    public final void setValueY(Formula formula) {
        this.valueY = formula;
    }

    public final Formula getValueZ() {
        return this.valueZ;
    }

    public final void setValueZ(Formula formula) {
        this.valueZ = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) throws InterpretationException {
        Float fInterpretFloat;
        StageListener stageListener;
        StageActivity stageActivity = StageActivity.activeStageActivity.get();
        ThreeDManager threeDManager = (stageActivity == null || (stageListener = stageActivity.stageListener) == null) ? null : stageListener.getThreeDManager();
        if (threeDManager == null) {
            return;
        }
        Formula formula = this.uniformName;
        String strInterpretString = formula != null ? formula.interpretString(getScope()) : null;
        String str = strInterpretString;
        if (str == null || str.length() == 0) {
            return;
        }
        boolean zUpdate$isValid = update$isValid(this.valueY, this);
        boolean zUpdate$isValid2 = update$isValid(this.valueZ, this);
        Formula formula2 = this.valueX;
        Float fInterpretFloat2 = formula2 != null ? formula2.interpretFloat(getScope()) : null;
        float fFloatValue = fInterpretFloat2 == null ? 0.0f : fInterpretFloat2.floatValue();
        if (zUpdate$isValid && zUpdate$isValid2) {
            Formula formula3 = this.valueY;
            Float fInterpretFloat3 = formula3 != null ? formula3.interpretFloat(getScope()) : null;
            float fFloatValue2 = fInterpretFloat3 == null ? 0.0f : fInterpretFloat3.floatValue();
            Formula formula4 = this.valueZ;
            fInterpretFloat = formula4 != null ? formula4.interpretFloat(getScope()) : null;
            threeDManager.setShaderUniform(strInterpretString, fFloatValue, fFloatValue2, fInterpretFloat != null ? fInterpretFloat.floatValue() : 0.0f);
            return;
        }
        if (zUpdate$isValid) {
            Formula formula5 = this.valueY;
            fInterpretFloat = formula5 != null ? formula5.interpretFloat(getScope()) : null;
            threeDManager.setShaderUniform(strInterpretString, fFloatValue, fInterpretFloat != null ? fInterpretFloat.floatValue() : 0.0f);
            return;
        }
        threeDManager.setShaderUniform(strInterpretString, fFloatValue);
    }

    private static final boolean update$isValid(Formula formula, SetShaderUniformAction setShaderUniformAction) throws InterpretationException {
        if (formula == null) {
            return false;
        }
        String strInterpretString = formula.interpretString(setShaderUniformAction.getScope());
        return !(strInterpretString == null || strInterpretString.length() == 0);
    }
}

package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.content.EasingFunctions;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/SetVariableEasingAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: SetVariableEasingAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\n\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0004J\u0010\u0010\u0015\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0004J\u0010\u0010\u0016\u001a\u00020\u00132\b\u0010\n\u001a\u0004\u0018\u00010\u000bJ\u0010\u0010\u0017\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0004J\u0010\u0010\u0018\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0004J\u000e\u0010\u0019\u001a\u00020\u00132\u0006\u0010\u001a\u001a\u00020\u000fJ\u0010\u0010\u001b\u001a\u00020\u00132\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011J\u0010\u0010\u001c\u001a\u00020\u00132\u0006\u0010\u001d\u001a\u00020\u001eH\u0014R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\bR\u0010\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001f"}, d2 = {"Lorg/catrobat/catroid/content/actions/SetVariableEasingAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "durationFormula", "Lorg/catrobat/catroid/formulaeditor/Formula;", "easingTypes", "", "Lorg/catrobat/catroid/content/EasingFunctions$EasingType;", "[Lorg/catrobat/catroid/content/EasingFunctions$EasingType;", "endFormula", "scope", "Lorg/catrobat/catroid/content/Scope;", "startFormula", "timeFormula", "typeIndex", "", "userVariable", "Lorg/catrobat/catroid/formulaeditor/UserVariable;", "setDurationFormula", "", "formula", "setEndFormula", "setScope", "setStartFormula", "setTimeFormula", "setTypeIndex", "index", "setUserVariable", ConfigConstants.CONFIG_KEY_UPDATE, "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SetVariableEasingAction extends TemporalAction {
    private Formula durationFormula;
    private final EasingFunctions.EasingType[] easingTypes = EasingFunctions.EasingType.values();
    private Formula endFormula;
    private Scope scope;
    private Formula startFormula;
    private Formula timeFormula;
    private int typeIndex;
    private UserVariable userVariable;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        if (this.userVariable == null) {
            return;
        }
        Formula formula = this.timeFormula;
        Float fInterpretFloat = formula != null ? formula.interpretFloat(this.scope) : null;
        float fFloatValue = fInterpretFloat == null ? 0.0f : fInterpretFloat.floatValue();
        Formula formula2 = this.durationFormula;
        Float fInterpretFloat2 = formula2 != null ? formula2.interpretFloat(this.scope) : null;
        float fFloatValue2 = fInterpretFloat2 == null ? 1.0f : fInterpretFloat2.floatValue();
        Formula formula3 = this.startFormula;
        Float fInterpretFloat3 = formula3 != null ? formula3.interpretFloat(this.scope) : null;
        float fFloatValue3 = fInterpretFloat3 == null ? 0.0f : fInterpretFloat3.floatValue();
        Formula formula4 = this.endFormula;
        Float fInterpretFloat4 = formula4 != null ? formula4.interpretFloat(this.scope) : null;
        float fFloatValue4 = fInterpretFloat4 != null ? fInterpretFloat4.floatValue() : 0.0f;
        int i = this.typeIndex;
        if (i < 0 || i >= this.easingTypes.length) {
            i = 0;
        }
        float fCalculate = EasingFunctions.calculate(this.easingTypes[i], fFloatValue, fFloatValue2, fFloatValue3, fFloatValue4);
        UserVariable userVariable = this.userVariable;
        Intrinsics.checkNotNull(userVariable);
        userVariable.setValue(Double.valueOf(fCalculate));
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final void setUserVariable(UserVariable userVariable) {
        this.userVariable = userVariable;
    }

    public final void setTypeIndex(int index) {
        this.typeIndex = index;
    }

    public final void setTimeFormula(Formula formula) {
        this.timeFormula = formula;
    }

    public final void setDurationFormula(Formula formula) {
        this.durationFormula = formula;
    }

    public final void setStartFormula(Formula formula) {
        this.startFormula = formula;
    }

    public final void setEndFormula(Formula formula) {
        this.endFormula = formula;
    }
}

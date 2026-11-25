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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/SetObjectShaderUniformAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: SetObjectShaderUniformAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eH\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001c\u0010\f\u001a\u0004\u0018\u00010\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0006\"\u0004\b\u0014\u0010\bR\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0006\"\u0004\b\u0017\u0010\bR\u001c\u0010\u0018\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u0006\"\u0004\b\u001a\u0010\b¨\u0006\u001f"}, d2 = {"Lorg/catrobat/catroid/content/actions/SetObjectShaderUniformAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "nameFormula", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getNameFormula", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setNameFormula", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "objectIdFormula", "getObjectIdFormula", "setObjectIdFormula", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "val1Formula", "getVal1Formula", "setVal1Formula", "val2Formula", "getVal2Formula", "setVal2Formula", "val3Formula", "getVal3Formula", "setVal3Formula", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SetObjectShaderUniformAction extends TemporalAction {
    private Formula nameFormula;
    private Formula objectIdFormula;
    private Scope scope;
    private Formula val1Formula;
    private Formula val2Formula;
    private Formula val3Formula;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getObjectIdFormula() {
        return this.objectIdFormula;
    }

    public final void setObjectIdFormula(Formula formula) {
        this.objectIdFormula = formula;
    }

    public final Formula getNameFormula() {
        return this.nameFormula;
    }

    public final void setNameFormula(Formula formula) {
        this.nameFormula = formula;
    }

    public final Formula getVal1Formula() {
        return this.val1Formula;
    }

    public final void setVal1Formula(Formula formula) {
        this.val1Formula = formula;
    }

    public final Formula getVal2Formula() {
        return this.val2Formula;
    }

    public final void setVal2Formula(Formula formula) {
        this.val2Formula = formula;
    }

    public final Formula getVal3Formula() {
        return this.val3Formula;
    }

    public final void setVal3Formula(Formula formula) {
        this.val3Formula = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        ThreeDManager threeDManager;
        int i;
        StageListener activeStageListener = StageActivity.getActiveStageListener();
        if (activeStageListener == null || (threeDManager = activeStageListener.getThreeDManager()) == null) {
            return;
        }
        Formula formula = this.objectIdFormula;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        if (strInterpretString == null) {
            strInterpretString = "";
        }
        Formula formula2 = this.nameFormula;
        String strInterpretString2 = formula2 != null ? formula2.interpretString(this.scope) : null;
        if (strInterpretString2 == null) {
            strInterpretString2 = "";
        }
        Formula formula3 = this.val1Formula;
        String strInterpretString3 = formula3 != null ? formula3.interpretString(this.scope) : null;
        if (strInterpretString3 == null) {
            strInterpretString3 = "";
        }
        Formula formula4 = this.val2Formula;
        String strInterpretString4 = formula4 != null ? formula4.interpretString(this.scope) : null;
        if (strInterpretString4 == null) {
            strInterpretString4 = "";
        }
        Formula formula5 = this.val3Formula;
        String strInterpretString5 = formula5 != null ? formula5.interpretString(this.scope) : null;
        String str = strInterpretString5 != null ? strInterpretString5 : "";
        Formula formula6 = this.val1Formula;
        Float fInterpretFloat = formula6 != null ? formula6.interpretFloat(this.scope) : null;
        float fFloatValue = fInterpretFloat == null ? 0.0f : fInterpretFloat.floatValue();
        Formula formula7 = this.val2Formula;
        Float fInterpretFloat2 = formula7 != null ? formula7.interpretFloat(this.scope) : null;
        float fFloatValue2 = fInterpretFloat2 == null ? 0.0f : fInterpretFloat2.floatValue();
        Formula formula8 = this.val3Formula;
        Float fInterpretFloat3 = formula8 != null ? formula8.interpretFloat(this.scope) : null;
        float fFloatValue3 = fInterpretFloat3 != null ? fInterpretFloat3.floatValue() : 0.0f;
        if (strInterpretString3.length() <= 0) {
            i = 0;
        } else if (strInterpretString4.length() > 0) {
            i = str.length() > 0 ? 3 : 2;
        } else {
            i = 1;
        }
        int i2 = i;
        if (i2 > 0) {
            threeDManager.setObjectShaderUniform(strInterpretString, strInterpretString2, fFloatValue, fFloatValue2, fFloatValue3, i2);
        }
    }
}

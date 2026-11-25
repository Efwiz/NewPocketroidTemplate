package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import kotlin.Metadata;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.ml.MLBridge;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/PtSetByIndexAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: PtSetByIndexAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001c\u0010\f\u001a\u0004\u0018\u00010\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0006\"\u0004\b\u0014\u0010\b¨\u0006\u0019"}, d2 = {"Lorg/catrobat/catroid/content/actions/PtSetByIndexAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "indexFormula", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getIndexFormula", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setIndexFormula", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "nameFormula", "getNameFormula", "setNameFormula", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "valueFormula", "getValueFormula", "setValueFormula", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PtSetByIndexAction extends TemporalAction {
    private Formula indexFormula;
    private Formula nameFormula;
    private Scope scope;
    private Formula valueFormula;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getNameFormula() {
        return this.nameFormula;
    }

    public final void setNameFormula(Formula formula) {
        this.nameFormula = formula;
    }

    public final Formula getIndexFormula() {
        return this.indexFormula;
    }

    public final void setIndexFormula(Formula formula) {
        this.indexFormula = formula;
    }

    public final Formula getValueFormula() {
        return this.valueFormula;
    }

    public final void setValueFormula(Formula formula) {
        this.valueFormula = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        Formula formula = this.nameFormula;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        if (strInterpretString == null) {
            strInterpretString = "t1";
        }
        Formula formula2 = this.indexFormula;
        Integer numInterpretInteger = formula2 != null ? formula2.interpretInteger(this.scope) : null;
        int iIntValue = numInterpretInteger == null ? 0 : numInterpretInteger.intValue();
        Formula formula3 = this.valueFormula;
        Float fInterpretFloat = formula3 != null ? formula3.interpretFloat(this.scope) : null;
        MLBridge.nativeSetTensorByIndex(strInterpretString, iIntValue, fInterpretFloat == null ? 0.0f : fInterpretFloat.floatValue());
    }
}

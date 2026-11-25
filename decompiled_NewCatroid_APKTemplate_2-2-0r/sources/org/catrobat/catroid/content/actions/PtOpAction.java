package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.reandroid.apkeditor.refactor.RefactorUtil;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.ml.MLBridge;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/PtOpAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: PtOpAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eH\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001a\u0010\f\u001a\u00020\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0006\"\u0004\b\u0014\u0010\bR\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u0016X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001a¨\u0006\u001f"}, d2 = {"Lorg/catrobat/catroid/content/actions/PtOpAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "aFormula", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getAFormula", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setAFormula", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "bFormula", "getBFormula", "setBFormula", "opType", "", "getOpType", "()Ljava/lang/String;", "setOpType", "(Ljava/lang/String;)V", "resFormula", "getResFormula", "setResFormula", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PtOpAction extends TemporalAction {
    private Formula aFormula;
    private Formula bFormula;
    private String opType = ProductAction.ACTION_ADD;
    private Formula resFormula;
    private Scope scope;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getResFormula() {
        return this.resFormula;
    }

    public final void setResFormula(Formula formula) {
        this.resFormula = formula;
    }

    public final Formula getAFormula() {
        return this.aFormula;
    }

    public final void setAFormula(Formula formula) {
        this.aFormula = formula;
    }

    public final Formula getBFormula() {
        return this.bFormula;
    }

    public final void setBFormula(Formula formula) {
        this.bFormula = formula;
    }

    public final String getOpType() {
        return this.opType;
    }

    public final void setOpType(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.opType = str;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        Formula formula = this.resFormula;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        if (strInterpretString == null) {
            strInterpretString = RefactorUtil.RES_DIR;
        }
        Formula formula2 = this.aFormula;
        String strInterpretString2 = formula2 != null ? formula2.interpretString(this.scope) : null;
        if (strInterpretString2 == null) {
            strInterpretString2 = "a";
        }
        Formula formula3 = this.bFormula;
        String strInterpretString3 = formula3 != null ? formula3.interpretString(this.scope) : null;
        if (strInterpretString3 == null) {
            strInterpretString3 = "";
        }
        MLBridge.nativeOp(strInterpretString, strInterpretString2, strInterpretString3, this.opType);
    }
}

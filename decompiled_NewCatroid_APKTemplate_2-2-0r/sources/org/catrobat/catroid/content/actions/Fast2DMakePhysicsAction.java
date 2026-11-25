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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/Fast2DMakePhysicsAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: Fast2DMakePhysicsAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 H\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001c\u0010\f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u0006\"\u0004\b\u000e\u0010\bR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0006\"\u0004\b\u0011\u0010\bR\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0006\"\u0004\b\u0013\u0010\bR\u001c\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u001c\u0010\u001a\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u0006\"\u0004\b\u001c\u0010\b¨\u0006!"}, d2 = {"Lorg/catrobat/catroid/content/actions/Fast2DMakePhysicsAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "bnc", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getBnc", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setBnc", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "den", "getDen", "setDen", "entityId", "getEntityId", "setEntityId", "fric", "getFric", "setFric", "isDyn", "setDyn", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "shape", "getShape", "setShape", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Fast2DMakePhysicsAction extends TemporalAction {
    private Formula bnc;
    private Formula den;
    private Formula entityId;
    private Formula fric;
    private Formula isDyn;
    private Scope scope;
    private Formula shape;

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

    /* JADX INFO: renamed from: isDyn, reason: from getter */
    public final Formula getIsDyn() {
        return this.isDyn;
    }

    public final void setDyn(Formula formula) {
        this.isDyn = formula;
    }

    public final Formula getShape() {
        return this.shape;
    }

    public final void setShape(Formula formula) {
        this.shape = formula;
    }

    public final Formula getDen() {
        return this.den;
    }

    public final void setDen(Formula formula) {
        this.den = formula;
    }

    public final Formula getFric() {
        return this.fric;
    }

    public final void setFric(Formula formula) {
        this.fric = formula;
    }

    public final Formula getBnc() {
        return this.bnc;
    }

    public final void setBnc(Formula formula) {
        this.bnc = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        StageListener stageListener;
        FastTwoDManager fastTwoDManager;
        Float fInterpretFloat;
        Float fInterpretFloat2;
        Float fInterpretFloat3;
        Formula formula = this.entityId;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        if (strInterpretString == null) {
            return;
        }
        Formula formula2 = this.isDyn;
        Float fInterpretFloat4 = formula2 != null ? formula2.interpretFloat(this.scope) : null;
        boolean z = (fInterpretFloat4 == null ? 1.0f : fInterpretFloat4.floatValue()) >= 0.5f;
        Formula formula3 = this.shape;
        String strInterpretString2 = formula3 != null ? formula3.interpretString(this.scope) : null;
        if (strInterpretString2 == null) {
            strInterpretString2 = "BOX";
        }
        String str = strInterpretString2;
        Formula formula4 = this.den;
        float fFloatValue = (formula4 == null || (fInterpretFloat3 = formula4.interpretFloat(this.scope)) == null) ? 1.0f : fInterpretFloat3.floatValue();
        Formula formula5 = this.fric;
        float fFloatValue2 = (formula5 == null || (fInterpretFloat2 = formula5.interpretFloat(this.scope)) == null) ? 0.5f : fInterpretFloat2.floatValue();
        Formula formula6 = this.bnc;
        float fFloatValue3 = (formula6 == null || (fInterpretFloat = formula6.interpretFloat(this.scope)) == null) ? 0.0f : fInterpretFloat.floatValue();
        StageActivity stageActivity = StageActivity.activeStageActivity.get();
        if (stageActivity == null || (stageListener = stageActivity.stageListener) == null || (fastTwoDManager = stageListener.fastTwoDManager) == null) {
            return;
        }
        fastTwoDManager.makePhysicsBody(strInterpretString, z, str, fFloatValue, fFloatValue2, fFloatValue3);
    }
}

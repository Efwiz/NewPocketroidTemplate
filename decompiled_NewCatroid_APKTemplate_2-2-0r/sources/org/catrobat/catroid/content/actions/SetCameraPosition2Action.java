package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import kotlin.Metadata;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.stage.StageListener;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/SetCameraPosition2Action.class
  classes14.dex
 */
/* JADX INFO: compiled from: SetCameraPosition2Action.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\f\"\u0004\b\u0011\u0010\u000e¨\u0006\u0016"}, d2 = {"Lorg/catrobat/catroid/content/actions/SetCameraPosition2Action;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "xFormula", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getXFormula", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setXFormula", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "yFormula", "getYFormula", "setYFormula", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SetCameraPosition2Action extends TemporalAction {
    private Scope scope;
    private Formula xFormula;
    private Formula yFormula;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getXFormula() {
        return this.xFormula;
    }

    public final void setXFormula(Formula formula) {
        this.xFormula = formula;
    }

    public final Formula getYFormula() {
        return this.yFormula;
    }

    public final void setYFormula(Formula formula) {
        this.yFormula = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        StageListener activeStageListener = StageActivity.getActiveStageListener();
        if (activeStageListener == null) {
            return;
        }
        Formula formula = this.xFormula;
        Float fInterpretFloat = formula != null ? formula.interpretFloat(this.scope) : null;
        float fFloatValue = fInterpretFloat == null ? 0.0f : fInterpretFloat.floatValue();
        Formula formula2 = this.yFormula;
        Float fInterpretFloat2 = formula2 != null ? formula2.interpretFloat(this.scope) : null;
        activeStageListener.setCameraPosition(fFloatValue, fInterpretFloat2 != null ? fInterpretFloat2.floatValue() : 0.0f);
    }
}

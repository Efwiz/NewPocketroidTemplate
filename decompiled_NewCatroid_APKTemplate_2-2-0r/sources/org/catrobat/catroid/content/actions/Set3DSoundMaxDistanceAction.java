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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/Set3DSoundMaxDistanceAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: Set3DSoundMaxDistanceAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001c\u0010\f\u001a\u0004\u0018\u00010\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011¨\u0006\u0016"}, d2 = {"Lorg/catrobat/catroid/content/actions/Set3DSoundMaxDistanceAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "distance", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getDistance", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setDistance", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "instanceName", "getInstanceName", "setInstanceName", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Set3DSoundMaxDistanceAction extends TemporalAction {
    private Formula distance;
    private Formula instanceName;
    private Scope scope;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getInstanceName() {
        return this.instanceName;
    }

    public final void setInstanceName(Formula formula) {
        this.instanceName = formula;
    }

    public final Formula getDistance() {
        return this.distance;
    }

    public final void setDistance(Formula formula) {
        this.distance = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        ThreeDManager threeDManager;
        Formula formula = this.instanceName;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        if (strInterpretString == null) {
            strInterpretString = "";
        }
        Formula formula2 = this.distance;
        Float fInterpretFloat = formula2 != null ? formula2.interpretFloat(this.scope) : null;
        float fFloatValue = fInterpretFloat == null ? 250.0f : fInterpretFloat.floatValue();
        StageListener activeStageListener = StageActivity.getActiveStageListener();
        if (activeStageListener == null || (threeDManager = activeStageListener.getThreeDManager()) == null) {
            return;
        }
        threeDManager.set3DSoundMaxDistance(strInterpretString, fFloatValue);
    }
}

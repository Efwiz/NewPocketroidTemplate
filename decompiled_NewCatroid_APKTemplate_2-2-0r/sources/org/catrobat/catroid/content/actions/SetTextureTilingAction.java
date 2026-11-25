package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import kotlin.Metadata;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.raptor.GameObject;
import org.catrobat.catroid.raptor.MaterialComponent;
import org.catrobat.catroid.raptor.SceneManager;
import org.catrobat.catroid.raptor.ThreeDManager;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.stage.StageListener;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/SetTextureTilingAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: SetTextureTilingAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001c\u0010\f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u0006\"\u0004\b\u000e\u0010\bR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014¨\u0006\u0019"}, d2 = {"Lorg/catrobat/catroid/content/actions/SetTextureTilingAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "objectId", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getObjectId", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setObjectId", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "scaleU", "getScaleU", "setScaleU", "scaleV", "getScaleV", "setScaleV", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SetTextureTilingAction extends TemporalAction {
    private Formula objectId;
    private Formula scaleU;
    private Formula scaleV;
    private Scope scope;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getObjectId() {
        return this.objectId;
    }

    public final void setObjectId(Formula formula) {
        this.objectId = formula;
    }

    public final Formula getScaleU() {
        return this.scaleU;
    }

    public final void setScaleU(Formula formula) {
        this.scaleU = formula;
    }

    public final Formula getScaleV() {
        return this.scaleV;
    }

    public final void setScaleV(Formula formula) {
        this.scaleV = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        ThreeDManager threeDManager;
        Double dInterpretDouble;
        Double dInterpretDouble2;
        StageListener activeStageListener = StageActivity.getActiveStageListener();
        SceneManager sceneManager = activeStageListener != null ? activeStageListener.sceneManager : null;
        if (sceneManager == null) {
            return;
        }
        Formula formula = this.objectId;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        if (strInterpretString == null) {
            return;
        }
        Formula formula2 = this.scaleU;
        float fDoubleValue = 1.0f;
        float fDoubleValue2 = (formula2 == null || (dInterpretDouble2 = formula2.interpretDouble(this.scope)) == null) ? 1.0f : (float) dInterpretDouble2.doubleValue();
        Formula formula3 = this.scaleV;
        if (formula3 != null && (dInterpretDouble = formula3.interpretDouble(this.scope)) != null) {
            fDoubleValue = (float) dInterpretDouble.doubleValue();
        }
        GameObject gameObjectFindGameObject = sceneManager.findGameObject(strInterpretString);
        if (gameObjectFindGameObject != null) {
            MaterialComponent materialComponent = (MaterialComponent) gameObjectFindGameObject.getComponent(MaterialComponent.class);
            if (materialComponent == null) {
                materialComponent = new MaterialComponent();
                gameObjectFindGameObject.addComponent(materialComponent);
            }
            materialComponent.uvScaleX = fDoubleValue2;
            materialComponent.uvScaleY = fDoubleValue;
        }
        StageListener activeStageListener2 = StageActivity.getActiveStageListener();
        if (activeStageListener2 == null || (threeDManager = activeStageListener2.getThreeDManager()) == null) {
            return;
        }
        threeDManager.setTextureTiling(strInterpretString, fDoubleValue2, fDoubleValue);
    }
}

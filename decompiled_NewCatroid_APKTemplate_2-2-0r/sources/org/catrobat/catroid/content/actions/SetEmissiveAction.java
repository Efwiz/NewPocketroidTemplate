package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.graphics.Color;
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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/SetEmissiveAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: SetEmissiveAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020$H\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001c\u0010\f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u0006\"\u0004\b\u000e\u0010\bR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0006\"\u0004\b\u0011\u0010\bR\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0006\"\u0004\b\u0014\u0010\bR\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0006\"\u0004\b\u0017\u0010\bR\u001c\u0010\u0018\u001a\u0004\u0018\u00010\u0019X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u001c\u0010\u001e\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\u0006\"\u0004\b \u0010\b¨\u0006%"}, d2 = {"Lorg/catrobat/catroid/content/actions/SetEmissiveAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "colorA", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getColorA", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setColorA", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "colorB", "getColorB", "setColorB", "colorG", "getColorG", "setColorG", "colorR", "getColorR", "setColorR", "intensity", "getIntensity", "setIntensity", "objectName", "getObjectName", "setObjectName", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "texturePath", "getTexturePath", "setTexturePath", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SetEmissiveAction extends TemporalAction {
    private Formula colorA;
    private Formula colorB;
    private Formula colorG;
    private Formula colorR;
    private Formula intensity;
    private Formula objectName;
    private Scope scope;
    private Formula texturePath;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getObjectName() {
        return this.objectName;
    }

    public final void setObjectName(Formula formula) {
        this.objectName = formula;
    }

    public final Formula getColorR() {
        return this.colorR;
    }

    public final void setColorR(Formula formula) {
        this.colorR = formula;
    }

    public final Formula getColorG() {
        return this.colorG;
    }

    public final void setColorG(Formula formula) {
        this.colorG = formula;
    }

    public final Formula getColorB() {
        return this.colorB;
    }

    public final void setColorB(Formula formula) {
        this.colorB = formula;
    }

    public final Formula getColorA() {
        return this.colorA;
    }

    public final void setColorA(Formula formula) {
        this.colorA = formula;
    }

    public final Formula getIntensity() {
        return this.intensity;
    }

    public final void setIntensity(Formula formula) {
        this.intensity = formula;
    }

    public final Formula getTexturePath() {
        return this.texturePath;
    }

    public final void setTexturePath(Formula formula) {
        this.texturePath = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        ThreeDManager threeDManager;
        Float fInterpretFloat;
        Float fInterpretFloat2;
        Float fInterpretFloat3;
        Float fInterpretFloat4;
        Formula formula = this.objectName;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        String str = strInterpretString;
        if (str == null || str.length() == 0) {
            return;
        }
        StageListener activeStageListener = StageActivity.getActiveStageListener();
        SceneManager sceneManager = activeStageListener != null ? activeStageListener.sceneManager : null;
        if (sceneManager == null) {
            return;
        }
        GameObject gameObjectFindObjectByName = sceneManager.findObjectByName(strInterpretString);
        Formula formula2 = this.colorR;
        float fFloatValue = ((formula2 == null || (fInterpretFloat4 = formula2.interpretFloat(this.scope)) == null) ? 255.0f : fInterpretFloat4.floatValue()) / 255.0f;
        Formula formula3 = this.colorG;
        float fFloatValue2 = ((formula3 == null || (fInterpretFloat3 = formula3.interpretFloat(this.scope)) == null) ? 255.0f : fInterpretFloat3.floatValue()) / 255.0f;
        Formula formula4 = this.colorB;
        float fFloatValue3 = ((formula4 == null || (fInterpretFloat2 = formula4.interpretFloat(this.scope)) == null) ? 255.0f : fInterpretFloat2.floatValue()) / 255.0f;
        Formula formula5 = this.colorA;
        float fFloatValue4 = ((formula5 == null || (fInterpretFloat = formula5.interpretFloat(this.scope)) == null) ? 255.0f : fInterpretFloat.floatValue()) / 255.0f;
        Formula formula6 = this.intensity;
        Float fInterpretFloat5 = formula6 != null ? formula6.interpretFloat(this.scope) : null;
        float fFloatValue5 = fInterpretFloat5 == null ? 1.0f : fInterpretFloat5.floatValue();
        Formula formula7 = this.texturePath;
        String strInterpretString2 = formula7 != null ? formula7.interpretString(this.scope) : null;
        if (strInterpretString2 == null) {
            strInterpretString2 = "";
        }
        Color color = new Color(fFloatValue, fFloatValue2, fFloatValue3, fFloatValue4);
        if (gameObjectFindObjectByName != null) {
            MaterialComponent materialComponent = (MaterialComponent) gameObjectFindObjectByName.getComponent(MaterialComponent.class);
            if (materialComponent == null) {
                materialComponent = new MaterialComponent();
                gameObjectFindObjectByName.addComponent(materialComponent);
            }
            materialComponent.emissiveColor.set(color);
            materialComponent.emissiveIntensity = fFloatValue5;
            String str2 = strInterpretString2;
            materialComponent.emissiveTexturePath = str2.length() != 0 ? str2 : null;
            sceneManager.setMaterialComponent(gameObjectFindObjectByName, materialComponent);
        }
        StageListener activeStageListener2 = StageActivity.getActiveStageListener();
        if (activeStageListener2 == null || (threeDManager = activeStageListener2.getThreeDManager()) == null) {
            return;
        }
        threeDManager.setObjectEmissive(strInterpretString, color, fFloatValue5, strInterpretString2);
    }
}

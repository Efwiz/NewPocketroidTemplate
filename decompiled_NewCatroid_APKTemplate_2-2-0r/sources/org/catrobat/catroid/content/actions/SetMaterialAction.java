package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import kotlin.Metadata;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.raptor.GameObject;
import org.catrobat.catroid.raptor.MaterialComponent;
import org.catrobat.catroid.raptor.SceneManager;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.stage.StageListener;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/SetMaterialAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: SetMaterialAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020-H\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001c\u0010\f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u0006\"\u0004\b\u000e\u0010\bR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0006\"\u0004\b\u0011\u0010\bR\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0006\"\u0004\b\u0014\u0010\bR\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0006\"\u0004\b\u0017\u0010\bR\u001c\u0010\u0018\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u0006\"\u0004\b\u001a\u0010\bR\u001c\u0010\u001b\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u0006\"\u0004\b\u001d\u0010\bR\u001c\u0010\u001e\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\u0006\"\u0004\b \u0010\bR\u001c\u0010!\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\u0006\"\u0004\b#\u0010\bR\u001c\u0010$\u001a\u0004\u0018\u00010%X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b&\u0010'\"\u0004\b(\u0010)¨\u0006."}, d2 = {"Lorg/catrobat/catroid/content/actions/SetMaterialAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "baseColorTexture", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getBaseColorTexture", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setBaseColorTexture", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "colorA", "getColorA", "setColorA", "colorB", "getColorB", "setColorB", "colorG", "getColorG", "setColorG", "colorR", "getColorR", "setColorR", "metallic", "getMetallic", "setMetallic", "metallicRoughnessTexture", "getMetallicRoughnessTexture", "setMetallicRoughnessTexture", "normalTexture", "getNormalTexture", "setNormalTexture", "objectName", "getObjectName", "setObjectName", "roughness", "getRoughness", "setRoughness", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SetMaterialAction extends TemporalAction {
    private Formula baseColorTexture;
    private Formula colorA;
    private Formula colorB;
    private Formula colorG;
    private Formula colorR;
    private Formula metallic;
    private Formula metallicRoughnessTexture;
    private Formula normalTexture;
    private Formula objectName;
    private Formula roughness;
    private Scope scope;

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

    public final Formula getMetallic() {
        return this.metallic;
    }

    public final void setMetallic(Formula formula) {
        this.metallic = formula;
    }

    public final Formula getRoughness() {
        return this.roughness;
    }

    public final void setRoughness(Formula formula) {
        this.roughness = formula;
    }

    public final Formula getBaseColorTexture() {
        return this.baseColorTexture;
    }

    public final void setBaseColorTexture(Formula formula) {
        this.baseColorTexture = formula;
    }

    public final Formula getNormalTexture() {
        return this.normalTexture;
    }

    public final void setNormalTexture(Formula formula) {
        this.normalTexture = formula;
    }

    public final Formula getMetallicRoughnessTexture() {
        return this.metallicRoughnessTexture;
    }

    public final void setMetallicRoughnessTexture(Formula formula) {
        this.metallicRoughnessTexture = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        String strInterpretString;
        String strInterpretString2;
        String strInterpretString3;
        Float fInterpretFloat;
        Float fInterpretFloat2;
        Formula formula = this.objectName;
        String strInterpretString4 = formula != null ? formula.interpretString(this.scope) : null;
        String str = strInterpretString4;
        if (str == null || str.length() == 0) {
            return;
        }
        StageListener activeStageListener = StageActivity.getActiveStageListener();
        SceneManager sceneManager = activeStageListener != null ? activeStageListener.sceneManager : null;
        if (sceneManager == null) {
            return;
        }
        GameObject gameObjectFindObjectByName = sceneManager.findObjectByName(strInterpretString4);
        MaterialComponent materialComponent = gameObjectFindObjectByName != null ? (MaterialComponent) gameObjectFindObjectByName.getComponent(MaterialComponent.class) : null;
        if (materialComponent == null) {
            materialComponent = new MaterialComponent();
        }
        Formula formula2 = this.colorR;
        Float fInterpretFloat3 = formula2 != null ? formula2.interpretFloat(this.scope) : null;
        Formula formula3 = this.colorG;
        Float fInterpretFloat4 = formula3 != null ? formula3.interpretFloat(this.scope) : null;
        Formula formula4 = this.colorB;
        Float fInterpretFloat5 = formula4 != null ? formula4.interpretFloat(this.scope) : null;
        Formula formula5 = this.colorA;
        Float fInterpretFloat6 = formula5 != null ? formula5.interpretFloat(this.scope) : null;
        if (fInterpretFloat3 != null && fInterpretFloat4 != null && fInterpretFloat5 != null && fInterpretFloat6 != null) {
            materialComponent.baseColor.set(fInterpretFloat3.floatValue() / 255.0f, fInterpretFloat4.floatValue() / 255.0f, fInterpretFloat5.floatValue() / 255.0f, fInterpretFloat6.floatValue() / 255.0f);
        }
        Formula formula6 = this.metallic;
        if (formula6 != null && (fInterpretFloat2 = formula6.interpretFloat(this.scope)) != null) {
            materialComponent.metallic = fInterpretFloat2.floatValue() / 100.0f;
        }
        Formula formula7 = this.roughness;
        if (formula7 != null && (fInterpretFloat = formula7.interpretFloat(this.scope)) != null) {
            materialComponent.roughness = fInterpretFloat.floatValue() / 100.0f;
        }
        Formula formula8 = this.baseColorTexture;
        if (formula8 != null && (strInterpretString3 = formula8.interpretString(this.scope)) != null) {
            materialComponent.baseColorTexturePath = strInterpretString3;
        }
        Formula formula9 = this.normalTexture;
        if (formula9 != null && (strInterpretString2 = formula9.interpretString(this.scope)) != null) {
            materialComponent.normalTexturePath = strInterpretString2;
        }
        Formula formula10 = this.metallicRoughnessTexture;
        if (formula10 != null && (strInterpretString = formula10.interpretString(this.scope)) != null) {
            materialComponent.metallicRoughnessTexturePath = strInterpretString;
        }
        if (gameObjectFindObjectByName == null) {
            StageActivity.getActiveStageListener().getThreeDManager().applyPBRMaterial(strInterpretString4, materialComponent);
        } else {
            sceneManager.setMaterialComponent(gameObjectFindObjectByName, materialComponent);
        }
    }
}

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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/SetPointLightAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: SetPointLightAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u001d\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020*H\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001c\u0010\f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u0006\"\u0004\b\u000e\u0010\bR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0006\"\u0004\b\u0011\u0010\bR\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0006\"\u0004\b\u0014\u0010\bR\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0006\"\u0004\b\u0017\u0010\bR\u001c\u0010\u0018\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u0006\"\u0004\b\u001a\u0010\bR\u001c\u0010\u001b\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u0006\"\u0004\b\u001d\u0010\bR\u001c\u0010\u001e\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\u0006\"\u0004\b \u0010\bR\u001c\u0010!\u001a\u0004\u0018\u00010\"X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010$\"\u0004\b%\u0010&¨\u0006+"}, d2 = {"Lorg/catrobat/catroid/content/actions/SetPointLightAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "colorB", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getColorB", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setColorB", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "colorG", "getColorG", "setColorG", "colorR", "getColorR", "setColorR", "intensity", "getIntensity", "setIntensity", "lightId", "getLightId", "setLightId", "posX", "getPosX", "setPosX", "posY", "getPosY", "setPosY", "posZ", "getPosZ", "setPosZ", "range", "getRange", "setRange", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SetPointLightAction extends TemporalAction {
    private Formula colorB;
    private Formula colorG;
    private Formula colorR;
    private Formula intensity;
    private Formula lightId;
    private Formula posX;
    private Formula posY;
    private Formula posZ;
    private Formula range;
    private Scope scope;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getLightId() {
        return this.lightId;
    }

    public final void setLightId(Formula formula) {
        this.lightId = formula;
    }

    public final Formula getPosX() {
        return this.posX;
    }

    public final void setPosX(Formula formula) {
        this.posX = formula;
    }

    public final Formula getPosY() {
        return this.posY;
    }

    public final void setPosY(Formula formula) {
        this.posY = formula;
    }

    public final Formula getPosZ() {
        return this.posZ;
    }

    public final void setPosZ(Formula formula) {
        this.posZ = formula;
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

    public final Formula getIntensity() {
        return this.intensity;
    }

    public final void setIntensity(Formula formula) {
        this.intensity = formula;
    }

    public final Formula getRange() {
        return this.range;
    }

    public final void setRange(Formula formula) {
        this.range = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        Integer numInterpretInteger;
        Integer numInterpretInteger2;
        Integer numInterpretInteger3;
        StageListener stageListener;
        StageActivity stageActivity = StageActivity.activeStageActivity.get();
        ThreeDManager threeDManager = (stageActivity == null || (stageListener = stageActivity.stageListener) == null) ? null : stageListener.getThreeDManager();
        if (threeDManager == null) {
            return;
        }
        try {
            Formula formula = this.lightId;
            String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
            if (strInterpretString == null) {
                strInterpretString = "";
            }
            String str = strInterpretString;
            if (str.length() == 0) {
                return;
            }
            Formula formula2 = this.posX;
            Float fInterpretFloat = formula2 != null ? formula2.interpretFloat(this.scope) : null;
            float fFloatValue = fInterpretFloat == null ? 0.0f : fInterpretFloat.floatValue();
            Formula formula3 = this.posY;
            Float fInterpretFloat2 = formula3 != null ? formula3.interpretFloat(this.scope) : null;
            float fFloatValue2 = fInterpretFloat2 == null ? 0.0f : fInterpretFloat2.floatValue();
            Formula formula4 = this.posZ;
            Float fInterpretFloat3 = formula4 != null ? formula4.interpretFloat(this.scope) : null;
            float fFloatValue3 = fInterpretFloat3 == null ? 0.0f : fInterpretFloat3.floatValue();
            Formula formula5 = this.colorR;
            int iIntValue = 255;
            float fIntValue = ((formula5 == null || (numInterpretInteger3 = formula5.interpretInteger(this.scope)) == null) ? 255 : numInterpretInteger3.intValue()) / 255.0f;
            Formula formula6 = this.colorG;
            float fIntValue2 = ((formula6 == null || (numInterpretInteger2 = formula6.interpretInteger(this.scope)) == null) ? 255 : numInterpretInteger2.intValue()) / 255.0f;
            Formula formula7 = this.colorB;
            if (formula7 != null && (numInterpretInteger = formula7.interpretInteger(this.scope)) != null) {
                iIntValue = numInterpretInteger.intValue();
            }
            float f = iIntValue / 255.0f;
            Formula formula8 = this.intensity;
            Float fInterpretFloat4 = formula8 != null ? formula8.interpretFloat(this.scope) : null;
            float fFloatValue4 = fInterpretFloat4 == null ? 1000.0f : fInterpretFloat4.floatValue();
            Formula formula9 = this.range;
            Float fInterpretFloat5 = formula9 != null ? formula9.interpretFloat(this.scope) : null;
            threeDManager.setPointLight(str, fFloatValue, fFloatValue2, fFloatValue3, fIntValue, fIntValue2, f, fFloatValue4, fInterpretFloat5 == null ? 0.0f : fInterpretFloat5.floatValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

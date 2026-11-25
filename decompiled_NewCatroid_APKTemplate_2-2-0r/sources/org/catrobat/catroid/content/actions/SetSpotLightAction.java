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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/SetSpotLightAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: SetSpotLightAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b,\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u00106\u001a\u0002072\u0006\u00108\u001a\u000209H\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001c\u0010\f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u0006\"\u0004\b\u000e\u0010\bR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0006\"\u0004\b\u0011\u0010\bR\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0006\"\u0004\b\u0014\u0010\bR\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0006\"\u0004\b\u0017\u0010\bR\u001c\u0010\u0018\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u0006\"\u0004\b\u001a\u0010\bR\u001c\u0010\u001b\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u0006\"\u0004\b\u001d\u0010\bR\u001c\u0010\u001e\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\u0006\"\u0004\b \u0010\bR\u001c\u0010!\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\u0006\"\u0004\b#\u0010\bR\u001c\u0010$\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b%\u0010\u0006\"\u0004\b&\u0010\bR\u001c\u0010'\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b(\u0010\u0006\"\u0004\b)\u0010\bR\u001c\u0010*\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b+\u0010\u0006\"\u0004\b,\u0010\bR\u001c\u0010-\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b.\u0010\u0006\"\u0004\b/\u0010\bR\u001c\u00100\u001a\u0004\u0018\u000101X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b2\u00103\"\u0004\b4\u00105¨\u0006:"}, d2 = {"Lorg/catrobat/catroid/content/actions/SetSpotLightAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "angle", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getAngle", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setAngle", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "colorB", "getColorB", "setColorB", "colorG", "getColorG", "setColorG", "colorR", "getColorR", "setColorR", "dirX", "getDirX", "setDirX", "dirY", "getDirY", "setDirY", "dirZ", "getDirZ", "setDirZ", "exponent", "getExponent", "setExponent", "intensity", "getIntensity", "setIntensity", "lightId", "getLightId", "setLightId", "posX", "getPosX", "setPosX", "posY", "getPosY", "setPosY", "posZ", "getPosZ", "setPosZ", "range", "getRange", "setRange", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SetSpotLightAction extends TemporalAction {
    private Formula angle;
    private Formula colorB;
    private Formula colorG;
    private Formula colorR;
    private Formula dirX;
    private Formula dirY;
    private Formula dirZ;
    private Formula exponent;
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

    public final Formula getDirX() {
        return this.dirX;
    }

    public final void setDirX(Formula formula) {
        this.dirX = formula;
    }

    public final Formula getDirY() {
        return this.dirY;
    }

    public final void setDirY(Formula formula) {
        this.dirY = formula;
    }

    public final Formula getDirZ() {
        return this.dirZ;
    }

    public final void setDirZ(Formula formula) {
        this.dirZ = formula;
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

    public final Formula getAngle() {
        return this.angle;
    }

    public final void setAngle(Formula formula) {
        this.angle = formula;
    }

    public final Formula getRange() {
        return this.range;
    }

    public final void setRange(Formula formula) {
        this.range = formula;
    }

    public final Formula getExponent() {
        return this.exponent;
    }

    public final void setExponent(Formula formula) {
        this.exponent = formula;
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
            Formula formula5 = this.dirX;
            Float fInterpretFloat4 = formula5 != null ? formula5.interpretFloat(this.scope) : null;
            float fFloatValue4 = fInterpretFloat4 == null ? 0.0f : fInterpretFloat4.floatValue();
            Formula formula6 = this.dirY;
            Float fInterpretFloat5 = formula6 != null ? formula6.interpretFloat(this.scope) : null;
            float fFloatValue5 = fInterpretFloat5 == null ? -1.0f : fInterpretFloat5.floatValue();
            Formula formula7 = this.dirZ;
            Float fInterpretFloat6 = formula7 != null ? formula7.interpretFloat(this.scope) : null;
            float fFloatValue6 = fInterpretFloat6 == null ? 0.0f : fInterpretFloat6.floatValue();
            Formula formula8 = this.colorR;
            int iIntValue = 255;
            float fIntValue = ((formula8 == null || (numInterpretInteger3 = formula8.interpretInteger(this.scope)) == null) ? 255 : numInterpretInteger3.intValue()) / 255.0f;
            Formula formula9 = this.colorG;
            float fIntValue2 = ((formula9 == null || (numInterpretInteger2 = formula9.interpretInteger(this.scope)) == null) ? 255 : numInterpretInteger2.intValue()) / 255.0f;
            Formula formula10 = this.colorB;
            if (formula10 != null && (numInterpretInteger = formula10.interpretInteger(this.scope)) != null) {
                iIntValue = numInterpretInteger.intValue();
            }
            float f = iIntValue / 255.0f;
            Formula formula11 = this.intensity;
            Float fInterpretFloat7 = formula11 != null ? formula11.interpretFloat(this.scope) : null;
            float fFloatValue7 = fInterpretFloat7 == null ? 1000.0f : fInterpretFloat7.floatValue();
            Formula formula12 = this.angle;
            Float fInterpretFloat8 = formula12 != null ? formula12.interpretFloat(this.scope) : null;
            float fFloatValue8 = fInterpretFloat8 == null ? 30.0f : fInterpretFloat8.floatValue();
            Formula formula13 = this.range;
            Float fInterpretFloat9 = formula13 != null ? formula13.interpretFloat(this.scope) : null;
            float fFloatValue9 = fInterpretFloat9 == null ? 0.0f : fInterpretFloat9.floatValue();
            Formula formula14 = this.exponent;
            Float fInterpretFloat10 = formula14 != null ? formula14.interpretFloat(this.scope) : null;
            threeDManager.setSpotLight(str, fFloatValue, fFloatValue2, fFloatValue3, fFloatValue4, fFloatValue5, fFloatValue6, fIntValue, fIntValue2, f, fFloatValue7, fFloatValue8, fInterpretFloat10 == null ? 1.0f : fInterpretFloat10.floatValue(), fFloatValue9);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import kotlin.Metadata;
import org.catrobat.catroid.content.RenderTextureManager;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/SetBufferCamera3DAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: SetBufferCamera3DAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020'H\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001c\u0010\f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u0006\"\u0004\b\u000e\u0010\bR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0006\"\u0004\b\u0011\u0010\bR\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u001c\u0010\u0018\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u0006\"\u0004\b\u001a\u0010\bR\u001c\u0010\u001b\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u0006\"\u0004\b\u001d\u0010\bR\u001c\u0010\u001e\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\u0006\"\u0004\b \u0010\bR\u001c\u0010!\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\u0006\"\u0004\b#\u0010\b¨\u0006("}, d2 = {"Lorg/catrobat/catroid/content/actions/SetBufferCamera3DAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "fovForm", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getFovForm", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setFovForm", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "nameFormula", "getNameFormula", "setNameFormula", "pitchForm", "getPitchForm", "setPitchForm", "rollForm", "getRollForm", "setRollForm", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "xForm", "getXForm", "setXForm", "yForm", "getYForm", "setYForm", "yawForm", "getYawForm", "setYawForm", "zForm", "getZForm", "setZForm", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SetBufferCamera3DAction extends TemporalAction {
    private Formula fovForm;
    private Formula nameFormula;
    private Formula pitchForm;
    private Formula rollForm;
    private Scope scope;
    private Formula xForm;
    private Formula yForm;
    private Formula yawForm;
    private Formula zForm;

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

    public final Formula getXForm() {
        return this.xForm;
    }

    public final Formula getYForm() {
        return this.yForm;
    }

    public final Formula getZForm() {
        return this.zForm;
    }

    public final void setXForm(Formula formula) {
        this.xForm = formula;
    }

    public final void setYForm(Formula formula) {
        this.yForm = formula;
    }

    public final void setZForm(Formula formula) {
        this.zForm = formula;
    }

    public final Formula getPitchForm() {
        return this.pitchForm;
    }

    public final Formula getRollForm() {
        return this.rollForm;
    }

    public final Formula getYawForm() {
        return this.yawForm;
    }

    public final void setPitchForm(Formula formula) {
        this.pitchForm = formula;
    }

    public final void setRollForm(Formula formula) {
        this.rollForm = formula;
    }

    public final void setYawForm(Formula formula) {
        this.yawForm = formula;
    }

    public final Formula getFovForm() {
        return this.fovForm;
    }

    public final void setFovForm(Formula formula) {
        this.fovForm = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        Formula formula = this.nameFormula;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        if (strInterpretString == null) {
            return;
        }
        Formula formula2 = this.xForm;
        Float fInterpretFloat = formula2 != null ? formula2.interpretFloat(this.scope) : null;
        float fFloatValue = fInterpretFloat == null ? 0.0f : fInterpretFloat.floatValue();
        Formula formula3 = this.yForm;
        Float fInterpretFloat2 = formula3 != null ? formula3.interpretFloat(this.scope) : null;
        float fFloatValue2 = fInterpretFloat2 == null ? 0.0f : fInterpretFloat2.floatValue();
        Formula formula4 = this.zForm;
        Float fInterpretFloat3 = formula4 != null ? formula4.interpretFloat(this.scope) : null;
        float fFloatValue3 = fInterpretFloat3 == null ? 0.0f : fInterpretFloat3.floatValue();
        Formula formula5 = this.yawForm;
        Float fInterpretFloat4 = formula5 != null ? formula5.interpretFloat(this.scope) : null;
        float fFloatValue4 = fInterpretFloat4 == null ? 0.0f : fInterpretFloat4.floatValue();
        Formula formula6 = this.pitchForm;
        Float fInterpretFloat5 = formula6 != null ? formula6.interpretFloat(this.scope) : null;
        float fFloatValue5 = fInterpretFloat5 == null ? 0.0f : fInterpretFloat5.floatValue();
        Formula formula7 = this.rollForm;
        Float fInterpretFloat6 = formula7 != null ? formula7.interpretFloat(this.scope) : null;
        float fFloatValue6 = fInterpretFloat6 == null ? 0.0f : fInterpretFloat6.floatValue();
        Formula formula8 = this.fovForm;
        Float fInterpretFloat7 = formula8 != null ? formula8.interpretFloat(this.scope) : null;
        RenderTextureManager.INSTANCE.setTargetCamera3D(strInterpretString, fFloatValue, fFloatValue2, fFloatValue3, fFloatValue4, fFloatValue5, fFloatValue6, fInterpretFloat7 == null ? 67.0f : fInterpretFloat7.floatValue());
    }
}

package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import kotlin.Metadata;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.raptor.ThreeDManager;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.stage.StageListener;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/CameraTouchControlAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: CameraTouchControlAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020'H\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001c\u0010\f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u0006\"\u0004\b\u000e\u0010\bR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0006\"\u0004\b\u0011\u0010\bR\u001a\u0010\u0012\u001a\u00020\u0013X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u001c\u0010\u0018\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u0006\"\u0004\b\u001a\u0010\bR\u001c\u0010\u001b\u001a\u0004\u0018\u00010\u001cX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R\u001c\u0010!\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\u0006\"\u0004\b#\u0010\b¨\u0006("}, d2 = {"Lorg/catrobat/catroid/content/actions/CameraTouchControlAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "areaH", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getAreaH", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setAreaH", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "areaW", "getAreaW", "setAreaW", "areaX", "getAreaX", "setAreaX", "areaY", "getAreaY", "setAreaY", "blockUI", "", "getBlockUI", "()Z", "setBlockUI", "(Z)V", CompilerOptions.ENABLED, "getEnabled", "setEnabled", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "sensitivity", "getSensitivity", "setSensitivity", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CameraTouchControlAction extends TemporalAction {
    private Formula areaH;
    private Formula areaW;
    private Formula areaX;
    private Formula areaY;
    private boolean blockUI = true;
    private Formula enabled;
    private Scope scope;
    private Formula sensitivity;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getEnabled() {
        return this.enabled;
    }

    public final void setEnabled(Formula formula) {
        this.enabled = formula;
    }

    public final Formula getSensitivity() {
        return this.sensitivity;
    }

    public final void setSensitivity(Formula formula) {
        this.sensitivity = formula;
    }

    public final boolean getBlockUI() {
        return this.blockUI;
    }

    public final void setBlockUI(boolean z) {
        this.blockUI = z;
    }

    public final Formula getAreaX() {
        return this.areaX;
    }

    public final void setAreaX(Formula formula) {
        this.areaX = formula;
    }

    public final Formula getAreaY() {
        return this.areaY;
    }

    public final void setAreaY(Formula formula) {
        this.areaY = formula;
    }

    public final Formula getAreaW() {
        return this.areaW;
    }

    public final void setAreaW(Formula formula) {
        this.areaW = formula;
    }

    public final Formula getAreaH() {
        return this.areaH;
    }

    public final void setAreaH(Formula formula) {
        this.areaH = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        Double dInterpretDouble;
        Double dInterpretDouble2;
        Double dInterpretDouble3;
        Double dInterpretDouble4;
        Double dInterpretDouble5;
        StageListener activeStageListener = StageActivity.getActiveStageListener();
        ThreeDManager threeDManager = activeStageListener != null ? activeStageListener.getThreeDManager() : null;
        if (threeDManager == null) {
            return;
        }
        Formula formula = this.enabled;
        Double dInterpretDouble6 = formula != null ? formula.interpretDouble(this.scope) : null;
        boolean z = (dInterpretDouble6 == null ? 1.0d : dInterpretDouble6.doubleValue()) > 0.5d;
        Formula formula2 = this.sensitivity;
        float fDoubleValue = (formula2 == null || (dInterpretDouble5 = formula2.interpretDouble(this.scope)) == null) ? 0.2f : (float) dInterpretDouble5.doubleValue();
        boolean z2 = this.blockUI;
        Formula formula3 = this.areaX;
        float fDoubleValue2 = (formula3 == null || (dInterpretDouble4 = formula3.interpretDouble(this.scope)) == null) ? 0.0f : (float) dInterpretDouble4.doubleValue();
        Formula formula4 = this.areaY;
        float fDoubleValue3 = (formula4 == null || (dInterpretDouble3 = formula4.interpretDouble(this.scope)) == null) ? 0.0f : (float) dInterpretDouble3.doubleValue();
        Formula formula5 = this.areaW;
        float fDoubleValue4 = (formula5 == null || (dInterpretDouble2 = formula5.interpretDouble(this.scope)) == null) ? 100.0f : (float) dInterpretDouble2.doubleValue();
        Formula formula6 = this.areaH;
        threeDManager.configureTouchRotation(z, fDoubleValue, z2, fDoubleValue2, fDoubleValue3, fDoubleValue4, (formula6 == null || (dInterpretDouble = formula6.interpretDouble(this.scope)) == null) ? 100.0f : (float) dInterpretDouble.doubleValue());
    }
}

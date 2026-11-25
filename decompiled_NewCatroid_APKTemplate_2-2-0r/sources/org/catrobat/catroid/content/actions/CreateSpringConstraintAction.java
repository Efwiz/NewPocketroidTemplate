package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import kotlin.Metadata;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.raptor.ThreeDManager;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.stage.StageListener;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/CreateSpringConstraintAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: CreateSpringConstraintAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u001d\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020*H\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001c\u0010\f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u0006\"\u0004\b\u000e\u0010\bR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0006\"\u0004\b\u0011\u0010\bR\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0006\"\u0004\b\u0014\u0010\bR\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0006\"\u0004\b\u0017\u0010\bR\u001c\u0010\u0018\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u0006\"\u0004\b\u001a\u0010\bR\u001c\u0010\u001b\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u0006\"\u0004\b\u001d\u0010\bR\u001c\u0010\u001e\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\u0006\"\u0004\b \u0010\bR\u001c\u0010!\u001a\u0004\u0018\u00010\"X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010$\"\u0004\b%\u0010&¨\u0006+"}, d2 = {"Lorg/catrobat/catroid/content/actions/CreateSpringConstraintAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "constraintId", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getConstraintId", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setConstraintId", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "objectIdA", "getObjectIdA", "setObjectIdA", "objectIdB", "getObjectIdB", "setObjectIdB", "pivotAx", "getPivotAx", "setPivotAx", "pivotAy", "getPivotAy", "setPivotAy", "pivotAz", "getPivotAz", "setPivotAz", "pivotBx", "getPivotBx", "setPivotBx", "pivotBy", "getPivotBy", "setPivotBy", "pivotBz", "getPivotBz", "setPivotBz", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CreateSpringConstraintAction extends TemporalAction {
    private Formula constraintId;
    private Formula objectIdA;
    private Formula objectIdB;
    private Formula pivotAx;
    private Formula pivotAy;
    private Formula pivotAz;
    private Formula pivotBx;
    private Formula pivotBy;
    private Formula pivotBz;
    private Scope scope;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getConstraintId() {
        return this.constraintId;
    }

    public final void setConstraintId(Formula formula) {
        this.constraintId = formula;
    }

    public final Formula getObjectIdA() {
        return this.objectIdA;
    }

    public final void setObjectIdA(Formula formula) {
        this.objectIdA = formula;
    }

    public final Formula getObjectIdB() {
        return this.objectIdB;
    }

    public final void setObjectIdB(Formula formula) {
        this.objectIdB = formula;
    }

    public final Formula getPivotAx() {
        return this.pivotAx;
    }

    public final Formula getPivotAy() {
        return this.pivotAy;
    }

    public final Formula getPivotAz() {
        return this.pivotAz;
    }

    public final void setPivotAx(Formula formula) {
        this.pivotAx = formula;
    }

    public final void setPivotAy(Formula formula) {
        this.pivotAy = formula;
    }

    public final void setPivotAz(Formula formula) {
        this.pivotAz = formula;
    }

    public final Formula getPivotBx() {
        return this.pivotBx;
    }

    public final Formula getPivotBy() {
        return this.pivotBy;
    }

    public final Formula getPivotBz() {
        return this.pivotBz;
    }

    public final void setPivotBx(Formula formula) {
        this.pivotBx = formula;
    }

    public final void setPivotBy(Formula formula) {
        this.pivotBy = formula;
    }

    public final void setPivotBz(Formula formula) {
        this.pivotBz = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        StageListener activeStageListener = StageActivity.getActiveStageListener();
        ThreeDManager threeDManager = activeStageListener != null ? activeStageListener.getThreeDManager() : null;
        if (threeDManager == null) {
            return;
        }
        Formula formula = this.constraintId;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        if (strInterpretString == null) {
            return;
        }
        Formula formula2 = this.objectIdA;
        String strInterpretString2 = formula2 != null ? formula2.interpretString(this.scope) : null;
        if (strInterpretString2 == null) {
            return;
        }
        Formula formula3 = this.objectIdB;
        String strInterpretString3 = formula3 != null ? formula3.interpretString(this.scope) : null;
        if (strInterpretString3 == null) {
            strInterpretString3 = "";
        }
        String str = strInterpretString3;
        Formula formula4 = this.pivotAx;
        Float fInterpretFloat = formula4 != null ? formula4.interpretFloat(this.scope) : null;
        float fFloatValue = fInterpretFloat == null ? 0.0f : fInterpretFloat.floatValue();
        Formula formula5 = this.pivotAy;
        Float fInterpretFloat2 = formula5 != null ? formula5.interpretFloat(this.scope) : null;
        float fFloatValue2 = fInterpretFloat2 == null ? 0.0f : fInterpretFloat2.floatValue();
        Formula formula6 = this.pivotAz;
        Float fInterpretFloat3 = formula6 != null ? formula6.interpretFloat(this.scope) : null;
        float fFloatValue3 = fInterpretFloat3 == null ? 0.0f : fInterpretFloat3.floatValue();
        Formula formula7 = this.pivotBx;
        Float fInterpretFloat4 = formula7 != null ? formula7.interpretFloat(this.scope) : null;
        float fFloatValue4 = fInterpretFloat4 == null ? 0.0f : fInterpretFloat4.floatValue();
        Formula formula8 = this.pivotBy;
        Float fInterpretFloat5 = formula8 != null ? formula8.interpretFloat(this.scope) : null;
        float fFloatValue5 = fInterpretFloat5 == null ? 0.0f : fInterpretFloat5.floatValue();
        Formula formula9 = this.pivotBz;
        Float fInterpretFloat6 = formula9 != null ? formula9.interpretFloat(this.scope) : null;
        threeDManager.createSpringConstraint(strInterpretString, strInterpretString2, str, new Matrix4().set(new Vector3(fFloatValue, fFloatValue2, fFloatValue3), new Quaternion()), new Matrix4().set(new Vector3(fFloatValue4, fFloatValue5, fInterpretFloat6 != null ? fInterpretFloat6.floatValue() : 0.0f), new Quaternion()));
    }
}

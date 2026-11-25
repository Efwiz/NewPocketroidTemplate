package org.catrobat.catroid.content.actions;

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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/CreatePointJointAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: CreatePointJointAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u001d\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020*H\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001c\u0010\f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u0006\"\u0004\b\u000e\u0010\bR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0006\"\u0004\b\u0011\u0010\bR\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0006\"\u0004\b\u0014\u0010\bR\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0006\"\u0004\b\u0017\u0010\bR\u001c\u0010\u0018\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u0006\"\u0004\b\u001a\u0010\bR\u001c\u0010\u001b\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u0006\"\u0004\b\u001d\u0010\bR\u001c\u0010\u001e\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\u0006\"\u0004\b \u0010\bR\u001c\u0010!\u001a\u0004\u0018\u00010\"X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010$\"\u0004\b%\u0010&¨\u0006+"}, d2 = {"Lorg/catrobat/catroid/content/actions/CreatePointJointAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "constraintName", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getConstraintName", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setConstraintName", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "objectA", "getObjectA", "setObjectA", "objectB", "getObjectB", "setObjectB", "pivotAX", "getPivotAX", "setPivotAX", "pivotAY", "getPivotAY", "setPivotAY", "pivotAZ", "getPivotAZ", "setPivotAZ", "pivotBX", "getPivotBX", "setPivotBX", "pivotBY", "getPivotBY", "setPivotBY", "pivotBZ", "getPivotBZ", "setPivotBZ", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CreatePointJointAction extends TemporalAction {
    private Formula constraintName;
    private Formula objectA;
    private Formula objectB;
    private Formula pivotAX;
    private Formula pivotAY;
    private Formula pivotAZ;
    private Formula pivotBX;
    private Formula pivotBY;
    private Formula pivotBZ;
    private Scope scope;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getConstraintName() {
        return this.constraintName;
    }

    public final void setConstraintName(Formula formula) {
        this.constraintName = formula;
    }

    public final Formula getObjectA() {
        return this.objectA;
    }

    public final void setObjectA(Formula formula) {
        this.objectA = formula;
    }

    public final Formula getObjectB() {
        return this.objectB;
    }

    public final void setObjectB(Formula formula) {
        this.objectB = formula;
    }

    public final Formula getPivotAX() {
        return this.pivotAX;
    }

    public final void setPivotAX(Formula formula) {
        this.pivotAX = formula;
    }

    public final Formula getPivotAY() {
        return this.pivotAY;
    }

    public final void setPivotAY(Formula formula) {
        this.pivotAY = formula;
    }

    public final Formula getPivotAZ() {
        return this.pivotAZ;
    }

    public final void setPivotAZ(Formula formula) {
        this.pivotAZ = formula;
    }

    public final Formula getPivotBX() {
        return this.pivotBX;
    }

    public final void setPivotBX(Formula formula) {
        this.pivotBX = formula;
    }

    public final Formula getPivotBY() {
        return this.pivotBY;
    }

    public final void setPivotBY(Formula formula) {
        this.pivotBY = formula;
    }

    public final Formula getPivotBZ() {
        return this.pivotBZ;
    }

    public final void setPivotBZ(Formula formula) {
        this.pivotBZ = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        String str;
        String str2;
        Formula formula = this.constraintName;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        Formula formula2 = this.objectA;
        String strInterpretString2 = formula2 != null ? formula2.interpretString(this.scope) : null;
        Formula formula3 = this.objectB;
        String strInterpretString3 = formula3 != null ? formula3.interpretString(this.scope) : null;
        String str3 = strInterpretString;
        if (str3 == null || str3.length() == 0 || (str = strInterpretString2) == null || str.length() == 0 || (str2 = strInterpretString3) == null || str2.length() == 0) {
            return;
        }
        StageListener activeStageListener = StageActivity.getActiveStageListener();
        ThreeDManager threeDManager = activeStageListener != null ? activeStageListener.getThreeDManager() : null;
        if (threeDManager == null) {
            return;
        }
        Formula formula4 = this.pivotAX;
        Float fInterpretFloat = formula4 != null ? formula4.interpretFloat(this.scope) : null;
        float fFloatValue = fInterpretFloat == null ? 0.0f : fInterpretFloat.floatValue();
        Formula formula5 = this.pivotAY;
        Float fInterpretFloat2 = formula5 != null ? formula5.interpretFloat(this.scope) : null;
        float fFloatValue2 = fInterpretFloat2 == null ? 0.0f : fInterpretFloat2.floatValue();
        Formula formula6 = this.pivotAZ;
        Float fInterpretFloat3 = formula6 != null ? formula6.interpretFloat(this.scope) : null;
        float fFloatValue3 = fInterpretFloat3 == null ? 0.0f : fInterpretFloat3.floatValue();
        Formula formula7 = this.pivotBX;
        Float fInterpretFloat4 = formula7 != null ? formula7.interpretFloat(this.scope) : null;
        float fFloatValue4 = fInterpretFloat4 == null ? 0.0f : fInterpretFloat4.floatValue();
        Formula formula8 = this.pivotBY;
        Float fInterpretFloat5 = formula8 != null ? formula8.interpretFloat(this.scope) : null;
        float fFloatValue5 = fInterpretFloat5 == null ? 0.0f : fInterpretFloat5.floatValue();
        Formula formula9 = this.pivotBZ;
        Float fInterpretFloat6 = formula9 != null ? formula9.interpretFloat(this.scope) : null;
        threeDManager.createPoint2PointConstraint(strInterpretString, strInterpretString2, strInterpretString3, new Vector3(fFloatValue, fFloatValue2, fFloatValue3), new Vector3(fFloatValue4, fFloatValue5, fInterpretFloat6 != null ? fInterpretFloat6.floatValue() : 0.0f));
    }
}

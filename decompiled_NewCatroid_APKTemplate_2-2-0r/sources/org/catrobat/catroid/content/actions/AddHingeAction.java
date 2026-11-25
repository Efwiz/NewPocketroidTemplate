package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import kotlin.Metadata;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.raptor.SceneManager;
import org.catrobat.catroid.raptor.ThreeDManager;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.stage.StageListener;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/AddHingeAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: AddHingeAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b/\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u00109\u001a\u00020:2\u0006\u0010;\u001a\u00020<H\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001c\u0010\f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u0006\"\u0004\b\u000e\u0010\bR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0006\"\u0004\b\u0011\u0010\bR\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0006\"\u0004\b\u0014\u0010\bR\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0006\"\u0004\b\u0017\u0010\bR\u001c\u0010\u0018\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u0006\"\u0004\b\u001a\u0010\bR\u001c\u0010\u001b\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u0006\"\u0004\b\u001d\u0010\bR\u001c\u0010\u001e\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\u0006\"\u0004\b \u0010\bR\u001c\u0010!\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\u0006\"\u0004\b#\u0010\bR\u001c\u0010$\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b%\u0010\u0006\"\u0004\b&\u0010\bR\u001c\u0010'\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b(\u0010\u0006\"\u0004\b)\u0010\bR\u001c\u0010*\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b+\u0010\u0006\"\u0004\b,\u0010\bR\u001c\u0010-\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b.\u0010\u0006\"\u0004\b/\u0010\bR\u001c\u00100\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b1\u0010\u0006\"\u0004\b2\u0010\bR\u001c\u00103\u001a\u0004\u0018\u000104X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b5\u00106\"\u0004\b7\u00108¨\u0006="}, d2 = {"Lorg/catrobat/catroid/content/actions/AddHingeAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "axisAX", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getAxisAX", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setAxisAX", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "axisAY", "getAxisAY", "setAxisAY", "axisAZ", "getAxisAZ", "setAxisAZ", "axisBX", "getAxisBX", "setAxisBX", "axisBY", "getAxisBY", "setAxisBY", "axisBZ", "getAxisBZ", "setAxisBZ", "constraintId", "getConstraintId", "setConstraintId", "objectA", "getObjectA", "setObjectA", "objectB", "getObjectB", "setObjectB", "pivotAX", "getPivotAX", "setPivotAX", "pivotAY", "getPivotAY", "setPivotAY", "pivotAZ", "getPivotAZ", "setPivotAZ", "pivotBX", "getPivotBX", "setPivotBX", "pivotBY", "getPivotBY", "setPivotBY", "pivotBZ", "getPivotBZ", "setPivotBZ", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AddHingeAction extends TemporalAction {
    private Formula axisAX;
    private Formula axisAY;
    private Formula axisAZ;
    private Formula axisBX;
    private Formula axisBY;
    private Formula axisBZ;
    private Formula constraintId;
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

    public final Formula getConstraintId() {
        return this.constraintId;
    }

    public final void setConstraintId(Formula formula) {
        this.constraintId = formula;
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

    public final Formula getPivotAY() {
        return this.pivotAY;
    }

    public final Formula getPivotAZ() {
        return this.pivotAZ;
    }

    public final void setPivotAX(Formula formula) {
        this.pivotAX = formula;
    }

    public final void setPivotAY(Formula formula) {
        this.pivotAY = formula;
    }

    public final void setPivotAZ(Formula formula) {
        this.pivotAZ = formula;
    }

    public final Formula getAxisAX() {
        return this.axisAX;
    }

    public final Formula getAxisAY() {
        return this.axisAY;
    }

    public final Formula getAxisAZ() {
        return this.axisAZ;
    }

    public final void setAxisAX(Formula formula) {
        this.axisAX = formula;
    }

    public final void setAxisAY(Formula formula) {
        this.axisAY = formula;
    }

    public final void setAxisAZ(Formula formula) {
        this.axisAZ = formula;
    }

    public final Formula getPivotBX() {
        return this.pivotBX;
    }

    public final Formula getPivotBY() {
        return this.pivotBY;
    }

    public final Formula getPivotBZ() {
        return this.pivotBZ;
    }

    public final void setPivotBX(Formula formula) {
        this.pivotBX = formula;
    }

    public final void setPivotBY(Formula formula) {
        this.pivotBY = formula;
    }

    public final void setPivotBZ(Formula formula) {
        this.pivotBZ = formula;
    }

    public final Formula getAxisBX() {
        return this.axisBX;
    }

    public final Formula getAxisBY() {
        return this.axisBY;
    }

    public final Formula getAxisBZ() {
        return this.axisBZ;
    }

    public final void setAxisBX(Formula formula) {
        this.axisBX = formula;
    }

    public final void setAxisBY(Formula formula) {
        this.axisBY = formula;
    }

    public final void setAxisBZ(Formula formula) {
        this.axisBZ = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        SceneManager sceneManager;
        Formula formula = this.constraintId;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        if (strInterpretString == null) {
            strInterpretString = "hinge1";
        }
        String str = strInterpretString;
        Formula formula2 = this.objectA;
        String strInterpretString2 = formula2 != null ? formula2.interpretString(this.scope) : null;
        String str2 = strInterpretString2 == null ? "" : strInterpretString2;
        Formula formula3 = this.objectB;
        String strInterpretString3 = formula3 != null ? formula3.interpretString(this.scope) : null;
        String str3 = strInterpretString3 == null ? "" : strInterpretString3;
        Formula formula4 = this.pivotAX;
        Float fInterpretFloat = formula4 != null ? formula4.interpretFloat(this.scope) : null;
        float fFloatValue = fInterpretFloat == null ? 0.0f : fInterpretFloat.floatValue();
        Formula formula5 = this.pivotAY;
        Float fInterpretFloat2 = formula5 != null ? formula5.interpretFloat(this.scope) : null;
        float fFloatValue2 = fInterpretFloat2 == null ? 0.0f : fInterpretFloat2.floatValue();
        Formula formula6 = this.pivotAZ;
        Float fInterpretFloat3 = formula6 != null ? formula6.interpretFloat(this.scope) : null;
        float fFloatValue3 = fInterpretFloat3 == null ? 0.0f : fInterpretFloat3.floatValue();
        Formula formula7 = this.axisAX;
        Float fInterpretFloat4 = formula7 != null ? formula7.interpretFloat(this.scope) : null;
        float fFloatValue4 = fInterpretFloat4 == null ? 0.0f : fInterpretFloat4.floatValue();
        Formula formula8 = this.axisAY;
        Float fInterpretFloat5 = formula8 != null ? formula8.interpretFloat(this.scope) : null;
        float fFloatValue5 = fInterpretFloat5 == null ? 1.0f : fInterpretFloat5.floatValue();
        Formula formula9 = this.axisAZ;
        Float fInterpretFloat6 = formula9 != null ? formula9.interpretFloat(this.scope) : null;
        float fFloatValue6 = fInterpretFloat6 == null ? 0.0f : fInterpretFloat6.floatValue();
        Formula formula10 = this.pivotBX;
        Float fInterpretFloat7 = formula10 != null ? formula10.interpretFloat(this.scope) : null;
        float fFloatValue7 = fInterpretFloat7 == null ? 0.0f : fInterpretFloat7.floatValue();
        Formula formula11 = this.pivotBY;
        Float fInterpretFloat8 = formula11 != null ? formula11.interpretFloat(this.scope) : null;
        float fFloatValue8 = fInterpretFloat8 == null ? 0.0f : fInterpretFloat8.floatValue();
        Formula formula12 = this.pivotBZ;
        Float fInterpretFloat9 = formula12 != null ? formula12.interpretFloat(this.scope) : null;
        float fFloatValue9 = fInterpretFloat9 == null ? 0.0f : fInterpretFloat9.floatValue();
        Formula formula13 = this.axisBX;
        Float fInterpretFloat10 = formula13 != null ? formula13.interpretFloat(this.scope) : null;
        float fFloatValue10 = fInterpretFloat10 == null ? 0.0f : fInterpretFloat10.floatValue();
        Formula formula14 = this.axisBY;
        Float fInterpretFloat11 = formula14 != null ? formula14.interpretFloat(this.scope) : null;
        float fFloatValue11 = fInterpretFloat11 == null ? 1.0f : fInterpretFloat11.floatValue();
        Formula formula15 = this.axisBZ;
        Float fInterpretFloat12 = formula15 != null ? formula15.interpretFloat(this.scope) : null;
        float fFloatValue12 = fInterpretFloat12 == null ? 0.0f : fInterpretFloat12.floatValue();
        StageListener activeStageListener = StageActivity.getActiveStageListener();
        ThreeDManager threeDManager = (activeStageListener == null || (sceneManager = activeStageListener.sceneManager) == null) ? null : sceneManager.engine;
        if (threeDManager == null || str2.length() <= 0 || str3.length() <= 0) {
            return;
        }
        threeDManager.createHingeConstraint(str, str2, str3, new Vector3(fFloatValue, fFloatValue2, fFloatValue3), new Vector3(fFloatValue4, fFloatValue5, fFloatValue6), new Vector3(fFloatValue7, fFloatValue8, fFloatValue9), new Vector3(fFloatValue10, fFloatValue11, fFloatValue12));
    }
}

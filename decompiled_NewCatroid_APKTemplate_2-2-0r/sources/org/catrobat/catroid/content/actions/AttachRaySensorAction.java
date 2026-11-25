package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;
import org.catrobat.catroid.raptor.ThreeDManager;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.stage.StageListener;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/AttachRaySensorAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: AttachRaySensorAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u001d\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020*H\u0014R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\u0004X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001a\u0010\f\u001a\u00020\u0004X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u0006\"\u0004\b\u000e\u0010\bR\u001a\u0010\u000f\u001a\u00020\u0004X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0006\"\u0004\b\u0011\u0010\bR\u001a\u0010\u0012\u001a\u00020\u0004X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0006\"\u0004\b\u0014\u0010\bR\u001a\u0010\u0015\u001a\u00020\u0004X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0006\"\u0004\b\u0017\u0010\bR\u001a\u0010\u0018\u001a\u00020\u0004X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u0006\"\u0004\b\u001a\u0010\bR\u001a\u0010\u001b\u001a\u00020\u0004X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u0006\"\u0004\b\u001d\u0010\bR\u001a\u0010\u001e\u001a\u00020\u0004X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\u0006\"\u0004\b \u0010\bR\u001a\u0010!\u001a\u00020\"X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010$\"\u0004\b%\u0010&¨\u0006+"}, d2 = {"Lorg/catrobat/catroid/content/actions/AttachRaySensorAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "dirX", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getDirX", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setDirX", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "dirY", "getDirY", "setDirY", "dirZ", "getDirZ", "setDirZ", "dist", "getDist", "setDist", "objectId", "getObjectId", "setObjectId", "offX", "getOffX", "setOffX", "offY", "getOffY", "setOffY", "offZ", "getOffZ", "setOffZ", "rayName", "getRayName", "setRayName", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AttachRaySensorAction extends TemporalAction {
    public Formula dirX;
    public Formula dirY;
    public Formula dirZ;
    public Formula dist;
    public Formula objectId;
    public Formula offX;
    public Formula offY;
    public Formula offZ;
    public Formula rayName;
    public Scope scope;

    public final Scope getScope() {
        Scope scope = this.scope;
        if (scope != null) {
            return scope;
        }
        Intrinsics.throwUninitializedPropertyAccessException("scope");
        return null;
    }

    public final void setScope(Scope scope) {
        Intrinsics.checkNotNullParameter(scope, "<set-?>");
        this.scope = scope;
    }

    public final Formula getRayName() {
        Formula formula = this.rayName;
        if (formula != null) {
            return formula;
        }
        Intrinsics.throwUninitializedPropertyAccessException("rayName");
        return null;
    }

    public final void setRayName(Formula formula) {
        Intrinsics.checkNotNullParameter(formula, "<set-?>");
        this.rayName = formula;
    }

    public final Formula getObjectId() {
        Formula formula = this.objectId;
        if (formula != null) {
            return formula;
        }
        Intrinsics.throwUninitializedPropertyAccessException("objectId");
        return null;
    }

    public final void setObjectId(Formula formula) {
        Intrinsics.checkNotNullParameter(formula, "<set-?>");
        this.objectId = formula;
    }

    public final Formula getOffX() {
        Formula formula = this.offX;
        if (formula != null) {
            return formula;
        }
        Intrinsics.throwUninitializedPropertyAccessException("offX");
        return null;
    }

    public final void setOffX(Formula formula) {
        Intrinsics.checkNotNullParameter(formula, "<set-?>");
        this.offX = formula;
    }

    public final Formula getOffY() {
        Formula formula = this.offY;
        if (formula != null) {
            return formula;
        }
        Intrinsics.throwUninitializedPropertyAccessException("offY");
        return null;
    }

    public final void setOffY(Formula formula) {
        Intrinsics.checkNotNullParameter(formula, "<set-?>");
        this.offY = formula;
    }

    public final Formula getOffZ() {
        Formula formula = this.offZ;
        if (formula != null) {
            return formula;
        }
        Intrinsics.throwUninitializedPropertyAccessException("offZ");
        return null;
    }

    public final void setOffZ(Formula formula) {
        Intrinsics.checkNotNullParameter(formula, "<set-?>");
        this.offZ = formula;
    }

    public final Formula getDirX() {
        Formula formula = this.dirX;
        if (formula != null) {
            return formula;
        }
        Intrinsics.throwUninitializedPropertyAccessException("dirX");
        return null;
    }

    public final void setDirX(Formula formula) {
        Intrinsics.checkNotNullParameter(formula, "<set-?>");
        this.dirX = formula;
    }

    public final Formula getDirY() {
        Formula formula = this.dirY;
        if (formula != null) {
            return formula;
        }
        Intrinsics.throwUninitializedPropertyAccessException("dirY");
        return null;
    }

    public final void setDirY(Formula formula) {
        Intrinsics.checkNotNullParameter(formula, "<set-?>");
        this.dirY = formula;
    }

    public final Formula getDirZ() {
        Formula formula = this.dirZ;
        if (formula != null) {
            return formula;
        }
        Intrinsics.throwUninitializedPropertyAccessException("dirZ");
        return null;
    }

    public final void setDirZ(Formula formula) {
        Intrinsics.checkNotNullParameter(formula, "<set-?>");
        this.dirZ = formula;
    }

    public final Formula getDist() {
        Formula formula = this.dist;
        if (formula != null) {
            return formula;
        }
        Intrinsics.throwUninitializedPropertyAccessException("dist");
        return null;
    }

    public final void setDist(Formula formula) {
        Intrinsics.checkNotNullParameter(formula, "<set-?>");
        this.dist = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) throws InterpretationException {
        StageListener activeStageListener = StageActivity.getActiveStageListener();
        ThreeDManager threeDManager = activeStageListener != null ? activeStageListener.getThreeDManager() : null;
        if (threeDManager == null) {
            return;
        }
        String strInterpretString = getRayName().interpretString(getScope());
        String strInterpretString2 = getObjectId().interpretString(getScope());
        Float fInterpretFloat = getOffX().interpretFloat(getScope());
        Intrinsics.checkNotNullExpressionValue(fInterpretFloat, "interpretFloat(...)");
        float fFloatValue = fInterpretFloat.floatValue();
        Float fInterpretFloat2 = getOffY().interpretFloat(getScope());
        Intrinsics.checkNotNullExpressionValue(fInterpretFloat2, "interpretFloat(...)");
        float fFloatValue2 = fInterpretFloat2.floatValue();
        Float fInterpretFloat3 = getOffZ().interpretFloat(getScope());
        Intrinsics.checkNotNullExpressionValue(fInterpretFloat3, "interpretFloat(...)");
        float fFloatValue3 = fInterpretFloat3.floatValue();
        Float fInterpretFloat4 = getDirX().interpretFloat(getScope());
        Intrinsics.checkNotNullExpressionValue(fInterpretFloat4, "interpretFloat(...)");
        float fFloatValue4 = fInterpretFloat4.floatValue();
        Float fInterpretFloat5 = getDirY().interpretFloat(getScope());
        Intrinsics.checkNotNullExpressionValue(fInterpretFloat5, "interpretFloat(...)");
        float fFloatValue5 = fInterpretFloat5.floatValue();
        Float fInterpretFloat6 = getDirZ().interpretFloat(getScope());
        Intrinsics.checkNotNullExpressionValue(fInterpretFloat6, "interpretFloat(...)");
        float fFloatValue6 = fInterpretFloat6.floatValue();
        Float fInterpretFloat7 = getDist().interpretFloat(getScope());
        Intrinsics.checkNotNullExpressionValue(fInterpretFloat7, "interpretFloat(...)");
        threeDManager.attachRaySensor(strInterpretString, strInterpretString2, fFloatValue, fFloatValue2, fFloatValue3, fFloatValue4, fFloatValue5, fFloatValue6, fInterpretFloat7.floatValue());
    }
}

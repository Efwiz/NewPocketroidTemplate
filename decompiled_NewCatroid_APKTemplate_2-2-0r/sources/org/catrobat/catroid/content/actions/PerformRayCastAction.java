package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import kotlin.Metadata;
import kotlin.ranges.RangesKt;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/PerformRayCastAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: PerformRayCastAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u000b\u001a\u00020\f2\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004J\u0010\u0010\r\u001a\u00020\f2\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004J\u0010\u0010\u000e\u001a\u00020\f2\b\u0010\u0006\u001a\u0004\u0018\u00010\u0004J\u0010\u0010\u000f\u001a\u00020\f2\b\u0010\u0007\u001a\u0004\u0018\u00010\bJ\u0010\u0010\u0010\u001a\u00020\f2\b\u0010\t\u001a\u0004\u0018\u00010\u0004J\u0010\u0010\u0011\u001a\u00020\f2\b\u0010\n\u001a\u0004\u0018\u00010\u0004J\u0010\u0010\u0012\u001a\u00020\f2\u0006\u0010\u0013\u001a\u00020\u0014H\u0014R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lorg/catrobat/catroid/content/actions/PerformRayCastAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "endX", "Lorg/catrobat/catroid/formulaeditor/Formula;", "endY", "rayId", "scope", "Lorg/catrobat/catroid/content/Scope;", "startX", "startY", "setEndX", "", "setEndY", "setRayId", "setScope", "setStartX", "setStartY", ConfigConstants.CONFIG_KEY_UPDATE, "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PerformRayCastAction extends TemporalAction {
    private Formula endX;
    private Formula endY;
    private Formula rayId;
    private Scope scope;
    private Formula startX;
    private Formula startY;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        Formula formula = this.rayId;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        String str = strInterpretString;
        if (str == null || str.length() == 0) {
            return;
        }
        Formula formula2 = this.startX;
        Float fInterpretFloat = formula2 != null ? formula2.interpretFloat(this.scope) : null;
        if (fInterpretFloat == null) {
            return;
        }
        float fFloatValue = fInterpretFloat.floatValue();
        Formula formula3 = this.startY;
        Float fInterpretFloat2 = formula3 != null ? formula3.interpretFloat(this.scope) : null;
        if (fInterpretFloat2 == null) {
            return;
        }
        float fFloatValue2 = fInterpretFloat2.floatValue();
        Formula formula4 = this.endX;
        Float fInterpretFloat3 = formula4 != null ? formula4.interpretFloat(this.scope) : null;
        if (fInterpretFloat3 == null) {
            return;
        }
        float fFloatValue3 = fInterpretFloat3.floatValue();
        Formula formula5 = this.endY;
        Float fInterpretFloat4 = formula5 != null ? formula5.interpretFloat(this.scope) : null;
        if (fInterpretFloat4 == null) {
            return;
        }
        float fFloatValue4 = fInterpretFloat4.floatValue();
        if (Float.isInfinite(fFloatValue) || Float.isNaN(fFloatValue) || Float.isInfinite(fFloatValue2) || Float.isNaN(fFloatValue2) || Float.isInfinite(fFloatValue3) || Float.isNaN(fFloatValue3) || Float.isInfinite(fFloatValue4) || Float.isNaN(fFloatValue4)) {
            return;
        }
        float f = fFloatValue3 - fFloatValue;
        float f2 = fFloatValue4 - fFloatValue2;
        if ((f * f) + (f2 * f2) < 1.0E-4f) {
            return;
        }
        Vector2 vector2 = new Vector2(RangesKt.coerceIn(fFloatValue, -50000.0f, 50000.0f), RangesKt.coerceIn(fFloatValue2, -50000.0f, 50000.0f));
        Vector2 vector3 = new Vector2(RangesKt.coerceIn(fFloatValue3, -50000.0f, 50000.0f), RangesKt.coerceIn(fFloatValue4, -50000.0f, 50000.0f));
        Scene currentlyPlayingScene = ProjectManager.getInstance().getCurrentlyPlayingScene();
        if (currentlyPlayingScene == null) {
            return;
        }
        try {
            currentlyPlayingScene.getPhysicsWorld().performRayCast(strInterpretString, vector2, vector3);
        } catch (Exception e) {
            Log.e("PerformRayCast", "Failed to perform RayCast in native Box2D", e);
        }
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final void setRayId(Formula rayId) {
        this.rayId = rayId;
    }

    public final void setStartX(Formula startX) {
        this.startX = startX;
    }

    public final void setStartY(Formula startY) {
        this.startY = startY;
    }

    public final void setEndX(Formula endX) {
        this.endX = endX;
    }

    public final void setEndY(Formula endY) {
        this.endY = endY;
    }
}

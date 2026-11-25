package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import kotlin.Metadata;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.fast2d.FastTwoDManager;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.stage.StageListener;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/Fast2DSetColorAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: Fast2DSetColorAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eH\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001c\u0010\f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u0006\"\u0004\b\u000e\u0010\bR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0006\"\u0004\b\u0011\u0010\bR\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0006\"\u0004\b\u0014\u0010\bR\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u0016X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001a¨\u0006\u001f"}, d2 = {"Lorg/catrobat/catroid/content/actions/Fast2DSetColorAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "a", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getA", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setA", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "getB", "setB", "entityId", "getEntityId", "setEntityId", "g", "getG", "setG", "r", "getR", "setR", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Fast2DSetColorAction extends TemporalAction {
    private Formula a;
    private Formula b;
    private Formula entityId;
    private Formula g;
    private Formula r;
    private Scope scope;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getEntityId() {
        return this.entityId;
    }

    public final void setEntityId(Formula formula) {
        this.entityId = formula;
    }

    public final Formula getR() {
        return this.r;
    }

    public final void setR(Formula formula) {
        this.r = formula;
    }

    public final Formula getG() {
        return this.g;
    }

    public final void setG(Formula formula) {
        this.g = formula;
    }

    public final Formula getB() {
        return this.b;
    }

    public final void setB(Formula formula) {
        this.b = formula;
    }

    public final Formula getA() {
        return this.a;
    }

    public final void setA(Formula formula) {
        this.a = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        StageListener stageListener;
        FastTwoDManager fastTwoDManager;
        Formula formula = this.entityId;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        if (strInterpretString == null) {
            return;
        }
        Formula formula2 = this.r;
        Float fInterpretFloat = formula2 != null ? formula2.interpretFloat(this.scope) : null;
        float fFloatValue = fInterpretFloat == null ? 255.0f : fInterpretFloat.floatValue();
        Formula formula3 = this.g;
        Float fInterpretFloat2 = formula3 != null ? formula3.interpretFloat(this.scope) : null;
        float fFloatValue2 = fInterpretFloat2 == null ? 255.0f : fInterpretFloat2.floatValue();
        Formula formula4 = this.b;
        Float fInterpretFloat3 = formula4 != null ? formula4.interpretFloat(this.scope) : null;
        float fFloatValue3 = fInterpretFloat3 == null ? 255.0f : fInterpretFloat3.floatValue();
        Formula formula5 = this.a;
        Float fInterpretFloat4 = formula5 != null ? formula5.interpretFloat(this.scope) : null;
        float fFloatValue4 = fInterpretFloat4 == null ? 100.0f : fInterpretFloat4.floatValue();
        StageActivity stageActivity = StageActivity.activeStageActivity.get();
        if (stageActivity == null || (stageListener = stageActivity.stageListener) == null || (fastTwoDManager = stageListener.fastTwoDManager) == null) {
            return;
        }
        fastTwoDManager.setColor(strInterpretString, fFloatValue, fFloatValue2, fFloatValue3, fFloatValue4);
    }
}

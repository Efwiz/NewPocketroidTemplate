package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import kotlin.Metadata;
import org.catrobat.catroid.content.Look;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.Formula;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/SetCornerOffsetsAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: SetCornerOffsetsAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010$\u001a\u00020%H\u0016J\u0010\u0010&\u001a\u00020%2\u0006\u0010'\u001a\u00020(H\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001c\u0010\f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u0006\"\u0004\b\u000e\u0010\bR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0006\"\u0004\b\u0011\u0010\bR\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u001c\u0010\u0018\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u0006\"\u0004\b\u001a\u0010\bR\u001c\u0010\u001b\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u0006\"\u0004\b\u001d\u0010\bR\u001c\u0010\u001e\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\u0006\"\u0004\b \u0010\bR\u001c\u0010!\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\u0006\"\u0004\b#\u0010\b¨\u0006)"}, d2 = {"Lorg/catrobat/catroid/content/actions/SetCornerOffsetsAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "blx", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getBlx", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setBlx", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "bly", "getBly", "setBly", "brx", "getBrx", "setBrx", "bry", "getBry", "setBry", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "tlx", "getTlx", "setTlx", "tly", "getTly", "setTly", "trx", "getTrx", "setTrx", "tryFormula", "getTryFormula", "setTryFormula", "reset", "", ConfigConstants.CONFIG_KEY_UPDATE, "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SetCornerOffsetsAction extends TemporalAction {
    private Formula blx;
    private Formula bly;
    private Formula brx;
    private Formula bry;
    private Scope scope;
    private Formula tlx;
    private Formula tly;
    private Formula trx;
    private Formula tryFormula;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getTlx() {
        return this.tlx;
    }

    public final void setTlx(Formula formula) {
        this.tlx = formula;
    }

    public final Formula getTly() {
        return this.tly;
    }

    public final void setTly(Formula formula) {
        this.tly = formula;
    }

    public final Formula getTrx() {
        return this.trx;
    }

    public final void setTrx(Formula formula) {
        this.trx = formula;
    }

    public final Formula getTryFormula() {
        return this.tryFormula;
    }

    public final void setTryFormula(Formula formula) {
        this.tryFormula = formula;
    }

    public final Formula getBrx() {
        return this.brx;
    }

    public final void setBrx(Formula formula) {
        this.brx = formula;
    }

    public final Formula getBry() {
        return this.bry;
    }

    public final void setBry(Formula formula) {
        this.bry = formula;
    }

    public final Formula getBlx() {
        return this.blx;
    }

    public final void setBlx(Formula formula) {
        this.blx = formula;
    }

    public final Formula getBly() {
        return this.bly;
    }

    public final void setBly(Formula formula) {
        this.bly = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        Sprite sprite;
        Look look;
        Scope scope = this.scope;
        if (scope == null || (sprite = scope.getSprite()) == null || (look = sprite.look) == null) {
            return;
        }
        Formula formula = this.tlx;
        Float fInterpretFloat = formula != null ? formula.interpretFloat(this.scope) : null;
        float fFloatValue = fInterpretFloat == null ? 0.0f : fInterpretFloat.floatValue();
        Formula formula2 = this.tly;
        Float fInterpretFloat2 = formula2 != null ? formula2.interpretFloat(this.scope) : null;
        float fFloatValue2 = fInterpretFloat2 == null ? 0.0f : fInterpretFloat2.floatValue();
        Formula formula3 = this.trx;
        Float fInterpretFloat3 = formula3 != null ? formula3.interpretFloat(this.scope) : null;
        float fFloatValue3 = fInterpretFloat3 == null ? 0.0f : fInterpretFloat3.floatValue();
        Formula formula4 = this.tryFormula;
        Float fInterpretFloat4 = formula4 != null ? formula4.interpretFloat(this.scope) : null;
        float fFloatValue4 = fInterpretFloat4 == null ? 0.0f : fInterpretFloat4.floatValue();
        Formula formula5 = this.brx;
        Float fInterpretFloat5 = formula5 != null ? formula5.interpretFloat(this.scope) : null;
        float fFloatValue5 = fInterpretFloat5 == null ? 0.0f : fInterpretFloat5.floatValue();
        Formula formula6 = this.bry;
        Float fInterpretFloat6 = formula6 != null ? formula6.interpretFloat(this.scope) : null;
        float fFloatValue6 = fInterpretFloat6 == null ? 0.0f : fInterpretFloat6.floatValue();
        Formula formula7 = this.blx;
        Float fInterpretFloat7 = formula7 != null ? formula7.interpretFloat(this.scope) : null;
        float fFloatValue7 = fInterpretFloat7 == null ? 0.0f : fInterpretFloat7.floatValue();
        Formula formula8 = this.bly;
        Float fInterpretFloat8 = formula8 != null ? formula8.interpretFloat(this.scope) : null;
        look.setCornerOffsets(fFloatValue, fFloatValue2, fFloatValue3, fFloatValue4, fFloatValue5, fFloatValue6, fFloatValue7, fInterpretFloat8 == null ? 0.0f : fInterpretFloat8.floatValue());
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction, com.badlogic.gdx.scenes.scene2d.Action, com.badlogic.gdx.utils.Pool.Poolable
    public void reset() {
        super.reset();
        this.scope = null;
        this.tlx = null;
        this.tly = null;
        this.trx = null;
        this.tryFormula = null;
        this.brx = null;
        this.bry = null;
        this.blx = null;
        this.bly = null;
    }
}

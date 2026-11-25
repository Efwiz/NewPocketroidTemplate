package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.Action;
import kotlin.Metadata;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/IntervalRepeatAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: IntervalRepeatAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u001f\u001a\u00020\u000e2\u0006\u0010 \u001a\u00020\fH\u0016J\b\u0010!\u001a\u00020\"H\u0002J\b\u0010#\u001a\u00020\"H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0001X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001c\u0010\u0014\u001a\u0004\u0018\u00010\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\b\"\u0004\b\u0016\u0010\nR\u000e\u0010\u0017\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u0018\u001a\u0004\u0018\u00010\u0019X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u000e\u0010\u001e\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006$"}, d2 = {"Lorg/catrobat/catroid/content/actions/IntervalRepeatAction;", "Lcom/badlogic/gdx/scenes/scene2d/Action;", "()V", "executedCount", "", "interval", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getInterval", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setInterval", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "intervalValue", "", "isInitialized", "", "loopBodyAction", "getLoopBodyAction", "()Lcom/badlogic/gdx/scenes/scene2d/Action;", "setLoopBodyAction", "(Lcom/badlogic/gdx/scenes/scene2d/Action;)V", "repeatCount", "getRepeatCount", "setRepeatCount", "repeatCountValue", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "timer", "act", "delta", "initialize", "", "restart", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IntervalRepeatAction extends Action {
    private int executedCount;
    private Formula interval;
    private float intervalValue;
    private boolean isInitialized;
    private Action loopBodyAction;
    private Formula repeatCount;
    private int repeatCountValue;
    private Scope scope;
    private float timer;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getRepeatCount() {
        return this.repeatCount;
    }

    public final void setRepeatCount(Formula formula) {
        this.repeatCount = formula;
    }

    public final Formula getInterval() {
        return this.interval;
    }

    public final void setInterval(Formula formula) {
        this.interval = formula;
    }

    public final Action getLoopBodyAction() {
        return this.loopBodyAction;
    }

    public final void setLoopBodyAction(Action action) {
        this.loopBodyAction = action;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public boolean act(float delta) {
        if (!this.isInitialized) {
            initialize();
        }
        int i = this.repeatCountValue;
        if (i > 0 && this.executedCount >= i) {
            return true;
        }
        float f = this.timer + delta;
        this.timer = f;
        float f2 = this.intervalValue;
        if (f >= f2) {
            if (i > 0) {
                this.executedCount++;
            }
            this.timer = f - f2;
            Action action = this.loopBodyAction;
            ScriptSequenceAction scriptSequenceAction = action instanceof ScriptSequenceAction ? (ScriptSequenceAction) action : null;
            ScriptSequenceAction scriptSequenceActionClone = scriptSequenceAction != null ? scriptSequenceAction.clone() : null;
            if (scriptSequenceActionClone != null) {
                scriptSequenceActionClone.restart();
                this.actor.addAction(scriptSequenceActionClone);
            }
        }
        int i2 = this.repeatCountValue;
        return i2 > 0 && this.executedCount >= i2;
    }

    private final void initialize() {
        Float fInterpretFloat;
        this.isInitialized = true;
        int iIntValue = 0;
        this.executedCount = 0;
        float fFloatValue = 0.0f;
        this.timer = 0.0f;
        try {
            Formula formula = this.repeatCount;
            Integer numInterpretInteger = formula != null ? formula.interpretInteger(this.scope) : null;
            if (numInterpretInteger != null) {
                iIntValue = numInterpretInteger.intValue();
            }
        } catch (Exception e) {
            Log.d(getClass().getSimpleName(), "Repeat count interpretation failed.", e);
        }
        this.repeatCountValue = iIntValue;
        try {
            Formula formula2 = this.interval;
            if (formula2 != null && (fInterpretFloat = formula2.interpretFloat(this.scope)) != null) {
                Float f = fInterpretFloat.floatValue() >= 0.0f ? fInterpretFloat : null;
                if (f != null) {
                    fFloatValue = f.floatValue();
                }
            }
        } catch (Exception e2) {
            Log.d(getClass().getSimpleName(), "Interval interpretation failed.", e2);
        }
        this.intervalValue = fFloatValue;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public void restart() {
        this.isInitialized = false;
        this.executedCount = 0;
        this.timer = 0.0f;
        Action action = this.loopBodyAction;
        if (action != null) {
            action.restart();
        }
        super.restart();
    }
}

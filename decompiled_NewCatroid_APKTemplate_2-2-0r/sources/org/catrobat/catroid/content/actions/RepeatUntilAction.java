package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.Action;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.MethodDelegation;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/RepeatUntilAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: RepeatUntilAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0016\u001a\u00020\t2\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J\b\u0010\u0019\u001a\u00020\tH\u0002J\b\u0010\u001a\u001a\u00020\tH\u0002J\b\u0010\u001b\u001a\u00020\u001cH\u0016R\u001e\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0004@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001c\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015¨\u0006\u001d"}, d2 = {"Lorg/catrobat/catroid/content/actions/RepeatUntilAction;", "Lorg/catrobat/catroid/content/actions/LoopAction;", "()V", "<set-?>", "", "executedCount", "getExecutedCount", "()I", "isCurrentLoopInitialized", "", "repeatCondition", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getRepeatCondition", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setRepeatCondition", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "delta", "", "isConditionTrue", "isValidConditionFormula", "restart", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RepeatUntilAction extends LoopAction {
    private int executedCount;
    private boolean isCurrentLoopInitialized;
    private Formula repeatCondition;
    private Scope scope;

    public final int getExecutedCount() {
        return this.executedCount;
    }

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getRepeatCondition() {
        return this.repeatCondition;
    }

    public final void setRepeatCondition(Formula formula) {
        this.repeatCondition = formula;
    }

    private final boolean isValidConditionFormula() {
        try {
            Formula formula = this.repeatCondition;
            Double dInterpretDouble = formula != null ? formula.interpretDouble(this.scope) : null;
            if (dInterpretDouble == null) {
                return false;
            }
            dInterpretDouble.doubleValue();
            return true;
        } catch (InterpretationException e) {
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", e);
            return false;
        }
    }

    private final boolean isConditionTrue() {
        try {
            Formula formula = this.repeatCondition;
            return true ^ Intrinsics.areEqual(formula != null ? formula.interpretDouble(this.scope) : null, 0.0d);
        } catch (InterpretationException e) {
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", e);
            return true;
        }
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.RepeatAction, com.badlogic.gdx.scenes.scene2d.actions.DelegateAction
    public boolean delegate(float delta) {
        if (!isValidConditionFormula()) {
            return true;
        }
        if (!this.isCurrentLoopInitialized) {
            if (isConditionTrue()) {
                return true;
            }
            setCurrentTime(0.0f);
            this.isCurrentLoopInitialized = true;
        }
        setCurrentTime(getCurrentTime() + delta);
        if (this.action.act(delta) && !isLoopDelayNeeded()) {
            this.executedCount++;
            if (isConditionTrue()) {
                return true;
            }
            this.isCurrentLoopInitialized = false;
            Action action = this.action;
            if (action != null) {
                action.restart();
            }
        }
        return false;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.RepeatAction, com.badlogic.gdx.scenes.scene2d.actions.DelegateAction, com.badlogic.gdx.scenes.scene2d.Action
    public void restart() {
        this.isCurrentLoopInitialized = false;
        this.executedCount = 0;
        super.restart();
    }
}

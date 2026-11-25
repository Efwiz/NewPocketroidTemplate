package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.Action;
import kotlin.Metadata;
import net.bytebuddy.implementation.MethodDelegation;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/RepeatAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: RepeatAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u001f\u001a\u00020\f2\u0006\u0010 \u001a\u00020\u0004H\u0016J\b\u0010!\u001a\u00020\"H\u0002J\b\u0010#\u001a\u00020\"H\u0016R\u001a\u0010\u0003\u001a\u00020\u0004X\u0094\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\r\u001a\u00020\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0011\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u000e\u0010\u0018\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u0019\u001a\u0004\u0018\u00010\u001aX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001e¨\u0006$"}, d2 = {"Lorg/catrobat/catroid/content/actions/RepeatAction;", "Lorg/catrobat/catroid/content/actions/LoopAction;", "()V", "currentTime", "", "getCurrentTime", "()F", "setCurrentTime", "(F)V", "executedCount", "", "isCurrentLoopInitialized", "", "isForeverRepeat", "()Z", "setForeverRepeat", "(Z)V", "isRepeatActionInitialized", "repeatCount", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getRepeatCount", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setRepeatCount", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "repeatCountValue", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "delta", "interpretRepeatCount", "", "restart", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RepeatAction extends LoopAction {
    private float currentTime;
    private int executedCount;
    private boolean isCurrentLoopInitialized;
    private boolean isForeverRepeat;
    private boolean isRepeatActionInitialized;
    private Formula repeatCount;
    private int repeatCountValue;
    private Scope scope;

    @Override // org.catrobat.catroid.content.actions.LoopAction
    protected float getCurrentTime() {
        return this.currentTime;
    }

    @Override // org.catrobat.catroid.content.actions.LoopAction
    protected void setCurrentTime(float f) {
        this.currentTime = f;
    }

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    /* JADX INFO: renamed from: isForeverRepeat, reason: from getter */
    public final boolean getIsForeverRepeat() {
        return this.isForeverRepeat;
    }

    public final void setForeverRepeat(boolean z) {
        this.isForeverRepeat = z;
    }

    public final Formula getRepeatCount() {
        return this.repeatCount;
    }

    public final void setRepeatCount(Formula formula) {
        this.repeatCount = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.RepeatAction, com.badlogic.gdx.scenes.scene2d.actions.DelegateAction
    public boolean delegate(float delta) {
        if (!this.isRepeatActionInitialized) {
            this.isRepeatActionInitialized = true;
            interpretRepeatCount();
        }
        if (!this.isCurrentLoopInitialized) {
            setCurrentTime(0.0f);
            this.isCurrentLoopInitialized = true;
        }
        setCurrentTime(getCurrentTime() + delta);
        if (this.repeatCountValue < 0) {
            this.repeatCountValue = 0;
        }
        if (this.executedCount >= this.repeatCountValue && !this.isForeverRepeat) {
            return true;
        }
        if (this.action != null && this.action.act(delta) && !isLoopDelayNeeded()) {
            int i = this.executedCount + 1;
            this.executedCount = i;
            if (i >= this.repeatCountValue && !this.isForeverRepeat) {
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
        this.isRepeatActionInitialized = false;
        this.executedCount = 0;
        super.restart();
    }

    private final void interpretRepeatCount() {
        int iIntValue = 0;
        try {
            Formula formula = this.repeatCount;
            Integer numInterpretInteger = formula != null ? formula.interpretInteger(this.scope) : null;
            if (numInterpretInteger != null) {
                iIntValue = numInterpretInteger.intValue();
            }
        } catch (InterpretationException e) {
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", e);
        }
        this.repeatCountValue = iIntValue;
    }
}

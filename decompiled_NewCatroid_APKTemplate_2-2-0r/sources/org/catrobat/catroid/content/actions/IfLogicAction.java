package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.reandroid.arsc.chunk.Overlayable;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/IfLogicAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: IfLogicAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J\b\u0010\u001e\u001a\u00020\u001fH\u0002J\b\u0010 \u001a\u00020\u001fH\u0016J\u0010\u0010!\u001a\u00020\u001f2\u0006\u0010\"\u001a\u00020#H\u0016R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0001X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007R\u001c\u0010\b\u001a\u0004\u0018\u00010\u0001X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\u0005\"\u0004\b\n\u0010\u0007R\u001c\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u0016X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001a¨\u0006$"}, d2 = {"Lorg/catrobat/catroid/content/actions/IfLogicAction;", "Lcom/badlogic/gdx/scenes/scene2d/Action;", "()V", "elseAction", "getElseAction", "()Lcom/badlogic/gdx/scenes/scene2d/Action;", "setElseAction", "(Lcom/badlogic/gdx/scenes/scene2d/Action;)V", "ifAction", "getIfAction", "setIfAction", "ifCondition", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getIfCondition", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setIfCondition", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "ifConditionValue", "", "isInitialized", "isInterpretedCorrectly", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "act", "delta", "", "interpretCondition", "", "restart", "setActor", Overlayable.NAME_actor, "Lcom/badlogic/gdx/scenes/scene2d/Actor;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IfLogicAction extends Action {
    private Action elseAction;
    private Action ifAction;
    private Formula ifCondition;
    private boolean ifConditionValue;
    private boolean isInitialized;
    private boolean isInterpretedCorrectly;
    private Scope scope;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Action getIfAction() {
        return this.ifAction;
    }

    public final void setIfAction(Action action) {
        this.ifAction = action;
    }

    public final Action getElseAction() {
        return this.elseAction;
    }

    public final void setElseAction(Action action) {
        this.elseAction = action;
    }

    public final Formula getIfCondition() {
        return this.ifCondition;
    }

    public final void setIfCondition(Formula formula) {
        this.ifCondition = formula;
    }

    private final void interpretCondition() {
        Double dInterpretDouble;
        boolean z = false;
        try {
            Formula formula = this.ifCondition;
            this.ifConditionValue = formula == null || (dInterpretDouble = formula.interpretDouble(this.scope)) == null || ((int) dInterpretDouble.doubleValue()) != 0;
            if (this.ifCondition != null) {
                z = true;
            }
        } catch (InterpretationException e) {
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", e);
        }
        this.isInterpretedCorrectly = z;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public boolean act(float delta) {
        if (!this.isInitialized) {
            interpretCondition();
            this.isInitialized = true;
        }
        if (!this.isInterpretedCorrectly) {
            return true;
        }
        if (this.ifConditionValue) {
            Action action = this.ifAction;
            if (action != null) {
                return action.act(delta);
            }
            return true;
        }
        Action action2 = this.elseAction;
        if (action2 != null) {
            return action2.act(delta);
        }
        return true;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public void restart() {
        Action action = this.ifAction;
        if (action != null) {
            action.restart();
        }
        Action action2 = this.elseAction;
        if (action2 != null) {
            action2.restart();
        }
        this.isInitialized = false;
        super.restart();
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public void setActor(Actor actor) {
        Intrinsics.checkNotNullParameter(actor, "actor");
        super.setActor(actor);
        Action action = this.ifAction;
        if (action != null) {
            action.setActor(actor);
        }
        Action action2 = this.elseAction;
        if (action2 == null) {
            return;
        }
        action2.setActor(actor);
    }
}
